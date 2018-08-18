package com.bridgelabz.noteservice.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.Utility.JsoupImpl;
import com.bridgelabz.Utility.Messages;
import com.bridgelabz.noteservice.model.Description;
import com.bridgelabz.noteservice.model.Label;
import com.bridgelabz.noteservice.model.Link;
import com.bridgelabz.noteservice.model.NoteDto;
import com.bridgelabz.noteservice.model.Notes;
import com.bridgelabz.noteservice.repository.LabelElasticRepository;
import com.bridgelabz.noteservice.repository.LabelRepository;
import com.bridgelabz.noteservice.repository.NoteElasticRepository;
import com.bridgelabz.noteservice.repository.NoteRepository;
import com.bridgelabz.sequence.dao.SequenceDao;
import com.google.common.base.Preconditions;

/**
 * Purpose : To provide the implementation for the NoteService.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 24/07/2018
 */
@Service
public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteRepository noteRepository;

	@Autowired
	LabelRepository labelRepository;

	/*@Autowired
	Notes note;*/

	@Autowired
	ModelMapper mapper;

	private final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	@Autowired
	private SequenceDao sequenceDao;
	
	@Autowired
	Messages messages;
	
	@Autowired
	NoteElasticRepository noteElasticRepository;
	
	@Autowired
	LabelElasticRepository labelElasticRepository;
	
	private static final String HOSTING_SEQ_KEY = "hosting";
	
	/**
	 * Method is written to create note for the logged in User.
	 * 
	 * @param newNote
	 * @param token
	 * @return List<Notes>
	 */
	@Override
	public String createNote(NoteDto newNote, String userId, String labelName) throws Exception {
		Preconditions.checkNotNull(newNote.getDescription(),messages.get("161"));
		Preconditions.checkNotNull(newNote.getTitle(),messages.get("162"));
		Preconditions.checkNotNull(labelName,messages.get("163"));
			if ((!newNote.getDescription().equals("") || !newNote.getTitle().equals("")))
		{
			//Notes note = mapper.map(newNote, Notes.class);
			Notes note = new Notes();
			note.setTitle(newNote.getTitle());
			note.setDescription(NoteServiceImpl.getDescription(newNote.getDescription()));
			note.setArchive(newNote.isArchive());
			note.setPinned(newNote.isPinned());
			note.setLabel(newNote.getLabel());
			
			note.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
			note.setCreationDate(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
			note.setUserId(userId);
			note.setLastModified(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
			note.setTrash(false);
			
			// If note is created using label.
			if (!labelName.equals("")) {
				/*Optional<Label> label = Preconditions.checkNotNull(labelRepository.findLabelByLabelName(labelName),
						messages.get("164")+ labelName);*/
				Optional<Label> label = Preconditions.checkNotNull(labelElasticRepository.findLabelByLabelName(labelName),
						messages.get("164")+ labelName);
				newNote.getLabel().add(label.get());
			}
			
			List<Label> labels = note.getLabel();
			for (Label label : labels) {
				label.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
				label.setUserId(userId);
				//Optional<Label> foundLabel = labelRepository.findLabelByLabelName(label.getLabelName());
				Optional<Label> foundLabel = labelElasticRepository.findLabelByLabelName(label.getLabelName());
				if (foundLabel.isPresent() == false && !label.getLabelName().equals("")) {
					labelRepository.save(label);
					labelElasticRepository.save(label);
				}
			}
			// If archive is true then pinned must be false.  
			if (note.isArchive()==true) {
				note.setPinned(false);
			}
			logger.info(messages.get("165"));
			
			noteRepository.save(note);
			noteElasticRepository.save(note);
			
			logger.info(messages.get("166")+note.getId());
			return note.getId();
		}
		logger.error(messages.get("121"));
		throw new Exception(messages.get("121"));
	}

	public static Description getDescription(String newNoteDescription) throws IOException {
		Description description=new Description();	
		String[] splittedStrings=newNoteDescription.split(" ");	
		
		List<Link> foundLinks=new ArrayList<>(); 
		List<String> foundDescriptions=new ArrayList<>();
		
		for(String splittedString:splittedStrings){
			if(
					splittedString.startsWith("https") || splittedString.startsWith("http")
				){
				Link link=new Link();
				
				link.setImage(JsoupImpl.image(splittedString));
				link.setLinkTitle(JsoupImpl.getLinkTitle(splittedString));
				link.setDomain(JsoupImpl.getDomain(splittedString));
				
				foundLinks.add(link);
			}else if(!splittedString.equals("")){
				foundDescriptions.add(splittedString);
			}
		}
		description.setDescription(foundDescriptions);
		description.setLink(foundLinks);
		return description;
	}
	
	/**
	 * This method is written to return all the notes present in the database.
	 * 
	 * @return List<Notes>
	 */
	@Override
	public List<Notes> getAllNotes() {
		return noteRepository.findAll();
	}

	/**
	 * This method is written to return all notes (from the list which is not
	 * archived) of a particular User.
	 * 
	 * @param token
	 * @return List<Notes>
	 */
	@Override
	public List<Notes> getNotes(String userId) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		
		/*List<Notes> notes = Preconditions.checkNotNull(
				noteRepository.findByUserId(userId),messages.get("167"));*/
		
		List<Notes> notes = Preconditions.checkNotNull(
				noteElasticRepository.findByUserId(userId),messages.get("167"));
		List<Notes> listNotes = new ArrayList<>();

		notes.stream().filter(streamNote->(streamNote.isArchive()==false) && (streamNote.isPinned()==true) &&
                (streamNote.isTrash()==false)).forEach(noteFilter->listNotes.add(noteFilter));
		
		notes.stream().filter(streamNote->(streamNote.isArchive()==false) && (streamNote.isPinned()==false) &&
                (streamNote.isTrash()==false)).forEach(noteFilter->listNotes.add(noteFilter));

		return listNotes;
	}

	/**
	 * Method is written to return archived notes of a particular user.
	 * 
	 * @param token
	 * @return List<Notes>
	 */
	@Override
	public List<Notes> getArchiveNotes(String userId) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		//List<Notes> notes = noteRepository.findByUserId(userId);
		List<Notes> notes = noteElasticRepository.findByUserId(userId);
		List<Notes> listNotes = new ArrayList<>();
		/*for (Notes n : notes) {
			if (n.isArchive()==true) {
				listNotes.add(n);
			}
		}*/
		
		notes.stream().filter(streamNote->(streamNote.isArchive()==true)).forEach(noteFilter->listNotes.add(noteFilter));
		
		return listNotes;
	}

	/**
	 * Method is written to get all the label created by the User.
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public List<String> getLabel(String userId) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		//List<Label> labels = labelRepository.findByUserId(userId);
		List<Label> labels = labelElasticRepository.findByUserId(userId);
		List<String> labelName = new ArrayList<>();
		/*for (Label label : labels) {
			labelName.add(label.getLabelName());
		}*/
		
		labels.stream().forEach(foundLabel->labelName.add(foundLabel.getLabelName()));
		return labelName;
	}

	/**
	 * Method is written to create Label for the particular user.
	 * 
	 * @param token
	 * @param label
	 * @throws Exception
	 */
	@Override
	public void createLabel(String userId, Label label) throws Exception {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(label.getLabelName(),messages.get("163"));
		
		//List<Label> foundLabels = labelRepository.findByUserId(userId);
		List<Label> foundLabels = labelElasticRepository.findByUserId(userId);
		for (Label foundLabel : foundLabels) {
			if (foundLabel.getLabelName().equals(label.getLabelName())) {
				throw new Exception(messages.get("170") + label.getLabelName());
			}
		}
		label.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
		label.setUserId(userId);
		labelElasticRepository.save(label);
		labelRepository.save(label);
	}

	/**
	 * Method is written to add Label on an existing Note.
	 * 
	 * @param token
	 * @param newLabel
	 * @param noteId
	 * @throws Exception
	 */
	@Override
	public void addLabelInNote(String userId, Label newLabel, String noteId) throws Exception {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(newLabel.getLabelName(),messages.get("163"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		
		/*List<Notes> notes = Preconditions.checkNotNull(noteRepository.findByUserId(userId),
				messages.get("167") + userId);*/
		List<Notes> notes = Preconditions.checkNotNull(noteElasticRepository.findByUserId(userId),
				messages.get("167") + userId);
		Notes foundNote = Preconditions.checkNotNull(
				notes.stream().filter(t -> t.getId().equals(noteId)).findFirst().orElse(null),
				"No Note Found With id " + noteId);
		
		// Checking label is already present on note .
		List<Label> foundLabels = foundNote.getLabel();
		
		/*for (Label foundLabel : foundLabels) {
			if (foundLabel.getLabelName().equals(newLabel.getLabelName())) {
				throw new Exception(
						messages.get("170") + newLabel.getLabelName());
			}
		}*/
		Preconditions.checkNotNull(foundLabels.stream().filter(foundLabel->foundLabel.getLabelName().equals(newLabel.getLabelName())).collect(Collectors.toList()),
				messages.get("170")+newLabel.getLabelName());
		
		newLabel.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
		newLabel.setUserId(userId);
		foundNote.getLabel().add(newLabel);
		noteRepository.save(foundNote);
		noteElasticRepository.save(foundNote);
		
		//List<Label> labels = labelRepository.findByUserId(userId);
		List<Label> labels = labelElasticRepository.findByUserId(userId);
		Boolean labelfoundStatus = true;
		for (Label label : labels) {
			if (label.getLabelName().equals(newLabel.getLabelName())) {
				labelfoundStatus = true;
			}
		}
		if (labelfoundStatus == false) {
			labelRepository.save(newLabel);
			labelElasticRepository.save(newLabel);
		}
	}

	/**
	 * Method Is Written To Get All the Notes According To Its Label.
	 * 
	 * @param token
	 * @param labelName
	 * @return List<Notes>
	 */
	@Override
	public List<Notes> getNoteWithLabel(String userId, String labelName) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(labelName,messages.get("163"));
		
		//List<Notes> notes = noteRepository.findByUserId(userId);
		List<Notes> notes = noteElasticRepository.findByUserId(userId);
		Preconditions.checkNotNull(notes,messages.get("167"));
		List<Notes> foundNotes = new ArrayList<>();

		for (Notes note : notes) {
			List<Label> labels = note.getLabel();
			/*for (Label label : labels) {
				if (label.getLabelName().equals(labelName)) {
					foundNotes.add(note);
				}
			}*/
			labels.stream().filter(streamLabel->streamLabel.getLabelName().equals(labelName)).forEach(label->foundNotes.add(note));
		}
		return foundNotes;
	}

	/**
	 * Method is written to remove the label and remove the label from every notes
	 * of the user.
	 * 
	 * @param token
	 * @param labelName
	 */
	@Override
	public void removeLabel(String userId, String labelName) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(labelName,messages.get("163"));
		
		/*List<Notes> notes = Preconditions.checkNotNull(
				noteRepository.findByUserId(userId), messages.get("167"));*/
		List<Notes> notes = Preconditions.checkNotNull(
				noteElasticRepository.findByUserId(userId), messages.get("167"));
		for (Notes note : notes) {
			List<Label> labels = note.getLabel();

			for (Label label : labels) {
				if (label.getLabelName().equals(labelName)) {
					labelRepository.deleteById(label.getId());
					labelElasticRepository.deleteById(label.getId());
					labels.remove(label);
				}
				break;
			}
			note.setLabel(labels);
			noteElasticRepository.save(note);
			noteRepository.save(note);
		}
	}

	/**
	 * To remove the label from the particular Note.
	 * 
	 * @param token
	 * @param labelName
	 * @param noteId
	 */
	@Override
	public void removeNoteLabel(String userId, String labelName, String noteId) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(labelName,messages.get("163"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		
		/*List<Notes> notes = Preconditions.checkNotNull(
				noteRepository.findByUserId(userId), messages.get("167"));*/
		List<Notes> notes = Preconditions.checkNotNull(
				noteElasticRepository.findByUserId(userId), messages.get("167"));
		
		Notes foundNote = Preconditions.checkNotNull(
				notes.stream().filter(t -> t.getId().equals(noteId)).findFirst().orElse(null),
				messages.get("167") + noteId);

		List<Label> labels = foundNote.getLabel();
		for (Label label : labels) {
			if (label.getLabelName().equals(labelName)) {
				labels.remove(label);
			}
			break;
		}
		foundNote.setLabel(labels);
		noteElasticRepository.save(foundNote);
		noteRepository.save(foundNote);
	}

	/**
	 * This method is written to return particular note of the particular User.
	 * 
	 * @param token
	 * @param id
	 * @return Notes
	 * @throws Exception
	 */
	@Override
	public Notes getParticularNote(String userId, String id) throws Exception {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(id,messages.get("169"));
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId), messages.get("167") + userId);*/
		
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167") + userId);
		Notes foundNote = Preconditions.checkNotNull(
				notes.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null),
				messages.get("167") + id );
		return foundNote;
	}

	/**
	 * Method is written to update particular note of the logged in User.
	 * 
	 * @param token
	 * @param title
	 * @param description
	 * @return
	 * @throws Exception
	 */
	@Override
	public Notes updateNotes(String userId, NoteDto updatedNote,String noteId) throws Exception {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		
		Preconditions.checkNotNull(updatedNote.getDescription(),messages.get("161"));
		Preconditions.checkNotNull(updatedNote.getTitle(),messages.get("162"));
		
		/*List<Notes> notes = Preconditions.checkNotNull(noteRepository.findByUserId(userId),
				messages.get("167")  + userId);*/
		List<Notes> notes = Preconditions.checkNotNull(noteElasticRepository.findByUserId(userId),
				messages.get("167")  + userId);
		Notes foundNote = Preconditions.checkNotNull(
				notes.stream().filter(t -> t.getId().equals(noteId)).findFirst().orElse(null),
				messages.get("167") + noteId );
		
		Notes note=mapper.map(updatedNote, Notes.class);
		note.setId(foundNote.getId());
		note.setCreationDate(foundNote.getCreationDate());
		note.setUserId(foundNote.getUserId());
		note.setLastModified(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()));
		note.setTrash(foundNote.isTrash());
		if (note.isArchive()==true) {
			note.setPinned(false);
		}
		
		List<Label> foundNoteLabels=foundNote.getLabel();
		
		List<Label> labels = note.getLabel();
		
		// Only new labels from the updated note which is already present on the existing note will be added.
		for(Label label:labels) {
		Label foundNoteLabel=	foundNoteLabels.stream().filter(t -> t.getLabelName().equals(label.getLabelName())).findFirst().orElse(null);
		if(foundNoteLabel==null) {	
			foundNoteLabels.add(label);
		}
		}
		
		// New label which is already not present on the label will be saved.
		for (Label label : labels) {
			logger.info(label.toString());
			label.setId(sequenceDao.getNextSequenceId(HOSTING_SEQ_KEY));
			label.setUserId(userId);
			//Optional<Label> foundLabel = labelRepository.findLabelByLabelName(label.getLabelName());
			Optional<Label> foundLabel = labelElasticRepository.findLabelByLabelName(label.getLabelName());
			if (foundLabel.isPresent() == false && !label.getLabelName().equals("")) {
				labelRepository.save(label);
				labelElasticRepository.save(label);
			}
		}
		note.setLabel(foundNoteLabels);
		noteRepository.save(note);
		noteElasticRepository.save(note);
		return foundNote;
	}

	/**
	 * Method is written to remove particular Note of the User.
	 * 
	 * @param token
	 * @param title
	 * @throws Exception
	 */
	@Override
	public void removeNote(String userId, String id) throws Exception {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(id,messages.get("169"));
		
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId), messages.get("167"));*/
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167"));
		Notes foundNote = Preconditions.checkNotNull(
				notes.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null),
				messages.get("167") + id);
		
		if(foundNote.isTrash()==false) {
			foundNote.setTrash(true);
			logger.info(" Trashed Note : " + foundNote.toString());
			noteRepository.save(foundNote);
			noteElasticRepository.save(foundNote);
			return;
		}
		logger.info(" Deleted Note : " + foundNote.toString());
		noteElasticRepository.delete(foundNote);
		noteRepository.delete(foundNote);
	}

	/**
	 * Method is written to set the reminder to the Notes.
	 * 
	 * @param token
	 * @param noteId
	 * @param reminderTime
	 * @throws ParseException
	 */
	@Override
	public void doSetReminder(String userId, String noteId, String reminderTime) throws ParseException {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		Preconditions.checkNotNull(reminderTime,messages.get("171"));
		
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId), messages.get("167"));*/
		
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167"));
		for (Notes note : notes) {
			if (note.getId().equals(noteId)) {
				Date reminder = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(reminderTime);
				long timeDifference = reminder.getTime() - new Date().getTime();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						logger.info( messages.get("172")+ note.toString());
					}
				}, timeDifference);

			}
		}
	}
	
	/**
	 * To get all notes which is trashed. 
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public List<Notes> getTrashedNotes(String userId){
		Preconditions.checkNotNull(userId,messages.get("168"));
		
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId), 
						messages.get("167"));*/
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), 
						messages.get("167"));
		List<Notes> foundNotes=new ArrayList<>();
		/*for(Notes note : notes) {
			if(note.isTrash()==true) {
				foundNotes.add(note);
			}
		}*/
		notes.stream().filter(streamNote->streamNote.isTrash()==true).forEach(forEachNote->foundNotes.add(forEachNote));
		return foundNotes;
	}

	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@Override
	public String pinnedUnpinned(String userId, String noteId) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId),messages.get("167"));*/
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId),messages.get("167"));
		Notes note=	Preconditions.checkNotNull(notes.stream().filter(t -> t.getId().equals(noteId)).findFirst().orElse(null), 
				messages.get("167"));
		if(note.isArchive()!=true) {
			if(note.isPinned()==false) {
			note.setPinned(true);
			}else {
			note.setPinned(false);
			}
			noteElasticRepository.save(note);
			noteRepository.save(note);
			return note.toString() +" is Pinned";
		}
		return "Archived Note Cannot Be Pinned";
	}

	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@Override
	public String archivedOrRemoveArchived(String userId, String noteId) {
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId), messages.get("167"));*/
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167"));
		Notes note=	Preconditions.checkNotNull(notes.stream().filter(t -> t.getId().equals(noteId)).findFirst().orElse(null), 
				messages.get("167") + noteId );
		if(note.isArchive()!=true) {
			note.setArchive(true);
			note.setPinned(false);
		}else {
			note.setArchive(false);
		}
		noteRepository.save(note);
		noteElasticRepository.save(note);
		return note.toString()+"Note Is Archived";
	}
	
	/**
	 * To view all the trashed Notes.
	 * 
	 * @param token can not be null.
	 * @param noteId can not be null.
	 * 
	 * @return List<Notes>
	 */
	@Override
	public List<String> viewTrashList(String userId, String noteId){
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(noteId,messages.get("169"));
		
		/*List<Notes> notes = Preconditions
				.checkNotNull(noteRepository.findByUserId(userId), messages.get("167"));*/
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167"));
		List<String> trashedNotesList=new ArrayList<>();
		
		/*for(Notes note:notes) {
			if(note.isTrash()==true) {
				trashedNotesList.add(note.getTitle());
			}
		}*/
		notes.stream().filter(streamNote->streamNote.isTrash()==true).forEach(note->trashedNotesList.add(note.getTitle()));
		return trashedNotesList;
	}

	@Override
	public List<Notes> sortByName(String userId) {
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167"));
		return notes.stream().sorted((x,y)->x.getTitle().compareTo(y.getTitle())).collect(Collectors.toList());
	}

	@Override
	public List<Notes> sortByCreatedDate(String userId) {
		List<Notes> notes = Preconditions
				.checkNotNull(noteElasticRepository.findByUserId(userId), messages.get("167"));
		return notes.stream().sorted((x,y)->x.getCreationDate().compareTo(y.getCreationDate())).collect(Collectors.toList());
	}

}
