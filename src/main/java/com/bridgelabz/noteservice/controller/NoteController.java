package com.bridgelabz.noteservice.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.Utility.Messages;
import com.bridgelabz.noteservice.model.Label;
import com.bridgelabz.noteservice.model.NoteDto;
import com.bridgelabz.noteservice.model.Notes;
import com.bridgelabz.noteservice.model.Response;
import com.bridgelabz.noteservice.service.NoteService;
import com.bridgelabz.noteservice.service.UserServiceProxy;
import com.google.common.base.Preconditions;

import io.swagger.annotations.ApiOperation;

/**
 * Purpose : Controller class to handle Note Service.
 * 	
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    24/07/2018
 */
@RestController
@RequestMapping(value="/note")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	Response response;
	
	@Autowired
	Messages messages;
	
	private final Logger logger = LoggerFactory.getLogger(NoteController.class);

	final String REQ_IN = "REQ_IN";
	final String RES_OUT = "RES_OUT";
	
	
	/**
	 * This method is written to create Notes for the logged in User.
	 * 
	 * @param note
	 * @param token
	 * @throws Exception 
	 */
	@ApiOperation(value = "Create New Note")
	@PostMapping(value = "/createnote")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto note,HttpServletRequest req ) throws Exception {
		logger.info(REQ_IN +messages.get("137"));
		String userId=req.getHeader("userId").toString();
		
		Preconditions.checkNotNull(userId,messages.get("168"));
		Preconditions.checkNotNull(note,messages.get("173") );
		
		String noteId=noteService.createNote(note, userId,"");
		
		response.setMessage(messages.get("166")+noteId);
		response.setStatus(200);
		logger.info(RES_OUT + messages.get("137"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This method is written to get all the notes present in the database And
	 * return back to the client side.
	 * 
	 * @return List<Notes>
	 */
	@ApiOperation(value = "Get All Notes")
	@GetMapping(value = "/getallnotes")
	public List<Notes> getAllNotes() {
		return noteService.getAllNotes();
	}

	/**
	 * This method is written to get all notes of a particular User 
	 * from the notes list which is not Archived  And return back
	 * to client side.
	 * 
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "Get Notes")
	@PostMapping(value = "/getnotes")
	public ResponseEntity<Response> getNotes(HttpServletRequest req) {
		logger.info(REQ_IN +messages.get("159"));
		
		response.setMessage(noteService.getNotes(req.getHeader("userId").toString()).toString());
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Method is written to get the Archived Notes.
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "Get Archive Notes")
	@PostMapping(value = "/archivenotes")
	public List<Notes> getArchiveNotes(HttpServletRequest req){
		logger.info(REQ_IN +messages.get("160"));
		return noteService.getArchiveNotes(req.getHeader("userId").toString());
	}
	
	/**
	 * This method is written to return particular note of the particular User And
	 * return back to client side.
	 * 
	 * @param token
	 * @param title
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "Get Particular Note")
	@PostMapping(value = "/getparticularnote")
	public ResponseEntity<Response> getParticularNote(HttpServletRequest req, @RequestParam String id) throws Exception {
		logger.info(REQ_IN + messages.get("138"));
		
		Notes note=noteService.getParticularNote(req.getHeader("userId").toString(), id);
		
		response.setMessage(note.toString());
		response.setStatus(200);
		
		logger.info(RES_OUT +messages.get("138"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Method is written to get all the label created by the User.
	 * 
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "Prints All Label")
	@PostMapping(value = "/getallabel")
	public ResponseEntity<Response> getLabel(HttpServletRequest req) {
		logger.info(REQ_IN + messages.get("139"));
		
		List<String> labelName=noteService.getLabel(req.getHeader("userId").toString());
		
		response.setMessage(labelName.toString());
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("139"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Method is written to add Label on existing Note.
	 * @param token
	 * @param newLabel
	 * @param noteId
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "Add Label To Note")
	@PostMapping(value = "/addlebel")
	public ResponseEntity<Response> addLabelInNote(HttpServletRequest req,@RequestBody Label newLabel,@RequestParam String noteId) throws Exception {
		logger.info(REQ_IN + messages.get("140"));
		
		noteService.addLabelInNote(req.getHeader("userId").toString(),newLabel,noteId);
	
		response.setMessage("Label Added");
		response.setStatus(1);
		
		logger.info(RES_OUT + messages.get("140"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * To create Label for the particular User.
	 * 
	 * @param token
	 * @param newLabel
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "Create Label")
	@PostMapping(value = "/createlabel")
	public ResponseEntity<Response> createLabel(HttpServletRequest req,@RequestBody Label newLabel) throws Exception {
		logger.info(REQ_IN + messages.get("141"));
	
		noteService.createLabel(req.getHeader("userId").toString(),newLabel);
		
		response.setMessage("Label Created");
		response.setStatus(1);
		
		logger.info(RES_OUT + messages.get("141") );
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Method is written to create Note with existing Label. 
	 * 
	 * @param note
	 * @param token
	 * @param labelName
	 * @return ResponseEntity<Response>
	 * @throws Exception
	 */
	@ApiOperation(value = "Create Note With Existing Label")
	@PostMapping(value = "/createnotewithlabel")
	public ResponseEntity<Response> createNoteWithLabel(@RequestBody NoteDto note,HttpServletRequest req,@RequestParam String labelName) throws Exception {
		logger.info(REQ_IN + messages.get("142"));
		
		noteService.createNote(note,req.getHeader("userId").toString(),labelName);
		
		response.setMessage("Note Created With Label");
		response.setStatus(1);
		
		logger.info(RES_OUT + messages.get("142"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	
	/**
	 * Method Is Written To Get All the Notes According To Its Label.
	 * 
	 * @param token
	 * @param labelName
	 * @return ResponseEntity<Response>
	 */
	@ApiOperation(value = "Prints Notes With Label")
	@PostMapping(value = "/getnoteswithlabel")
	public ResponseEntity<Response> getNoteWithLabel(HttpServletRequest req,@RequestParam String labelName) {
		logger.info(REQ_IN + messages.get("149"));
		List<Notes> notes=null;
		
		notes=noteService.getNoteWithLabel(req.getHeader("userId").toString(),labelName);
		
		response.setMessage(notes.toString());
		response.setStatus(1);
		
		logger.info(RES_OUT +  messages.get("149"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * To remove the Label from the Label List.
	 * 
	 * @param token
	 * @param labelName
	 * @return
	 */
	@ApiOperation(value = "Remove Label ")
	@DeleteMapping(value = "/removelabel")
	public ResponseEntity<Response> removeLabel(HttpServletRequest req,@RequestParam String labelName) {
		logger.info(REQ_IN + messages.get("150"));
		
		noteService.removeLabel(req.getHeader("userId").toString(),labelName);
		
		response.setMessage(labelName+" removed");
		response.setStatus(1);
		
		logger.info(RES_OUT + messages.get("150"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * To remove the label from Note.
	 * 
	 * @param token
	 * @param labelName
	 * @param noteId
	 * @return
	 */
	@ApiOperation(value = "Remove Note Label ")
	@DeleteMapping(value = "/removeNoteLabel")
	public ResponseEntity<Response> removeNoteLabel(HttpServletRequest req,
			@RequestParam String labelName,@RequestParam String noteId) {
		logger.info(REQ_IN + messages.get("151"));
		
		noteService.removeNoteLabel(req.getHeader("userId").toString(),labelName,noteId);
		
		response.setMessage(labelName+" removed from note");
		response.setStatus(1);
		
		logger.info(RES_OUT + messages.get("151"));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
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
	@ApiOperation(value = "Update Note")
	@PostMapping(value = "/updatenote")
	public ResponseEntity<Response> updateNotes(HttpServletRequest req,@RequestBody NoteDto note,
			@RequestParam String noteId) throws Exception {
		
		logger.info(REQ_IN + messages.get("152") );
		
		Notes updatedNote=noteService.updateNotes(req.getHeader("userId").toString(),note,noteId);
		
		response.setMessage("Updated Note Is :- "+ updatedNote);
		response.setStatus(200);
		
		logger.info(RES_OUT +  messages.get("152") );
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This method is written to delete the as per the User request according to the
	 * title of the Note.
	 * 
	 * @param token
	 * @param title
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "Remove Note")
	@DeleteMapping(value = "/removenote/{id}")
	public ResponseEntity<Response> removeNote(HttpServletRequest req, @PathVariable String id) throws Exception {
		logger.info(REQ_IN + messages.get("153") );
		
		noteService.removeNote(req.getHeader("userId").toString(), id);
		
		response.setMessage("Note removed.");
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("153"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Method is written to set the reminder to the Notes. 
	 * 
	 * @param token
	 * @param noteId
	 * @param reminderTime
	 * @return
	 * @throws ParseException 
	 */
	@ApiOperation(value = "Set Reminder")
	@PostMapping(value = "/setreminder")
	public ResponseEntity<Response> doSetReminder(HttpServletRequest req, @RequestParam String noteId,@RequestParam String reminderTime) throws ParseException {
		logger.info(REQ_IN + messages.get("154"));
		
		noteService.doSetReminder(req.getHeader("userId").toString(),noteId,reminderTime);
		
		response.setMessage("Reminder Set");
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("154"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * To get all notes which is trashed. 
	 * 
	 * @param token
	 * @return
	 */
	@ApiOperation(value="Get Trashed Notes")
	@PostMapping(value="/gettrashednotes")
	public ResponseEntity<Response> getTrashedNotes(HttpServletRequest req){
		logger.info(REQ_IN + messages.get("155"));
		
		response.setMessage(noteService.getTrashedNotes(req.getHeader("userId").toString()).toString());
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("155"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@ApiOperation(value="Pinned Or Unpinned Notes")
	@PostMapping(value="/pinnedorunpinnednotes")
	public ResponseEntity<Response>  pinnedUnpinned(HttpServletRequest req,@RequestParam String noteId) {
		logger.info(REQ_IN + messages.get("156"));
		
		response.setMessage(noteService.pinnedUnpinned(req.getHeader("userId").toString(),noteId));
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("156"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@ApiOperation(value="Pinned Notes")
	@PostMapping(value="/dopinnednotes")
	public ResponseEntity<Response>  archivedOrRemoveArchived(HttpServletRequest req,@RequestParam String noteId) {
		logger.info(REQ_IN + messages.get("157"));
		
		response.setMessage(noteService.archivedOrRemoveArchived(req.getHeader("userId").toString(),noteId));
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("157"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	@ApiOperation(value="View Trashed Notes List")
	@PostMapping(value="/viewtrashednoteslist")
	public ResponseEntity<Response>  viewTrashList(HttpServletRequest req,@RequestParam String noteId) {
		logger.info(REQ_IN + messages.get("158"));
		
		Preconditions.checkNotNull(req.getHeader("userId").toString(), "Token Is Null");
		Preconditions.checkNotNull(noteId, "Note Id Is Null");
		
		response.setMessage(noteService.viewTrashList(req.getHeader("userId").toString(),noteId).toString());
		response.setStatus(200);
		
		logger.info(RES_OUT + messages.get("158"));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value="Get Notes Sorted By Name")
	@GetMapping(value="/sortnotesbyname")
	public ResponseEntity<?> sortByName(HttpServletRequest req){
		System.out.println("asdakjdkajshdaksjhd");
		Preconditions.checkNotNull(req.getHeader("userId").toString(), "Token Is Null");
		System.out.println("user id : "+req.getHeader("userId").toString());
		return new ResponseEntity<>(noteService.sortByName(req.getHeader("userId").toString()),HttpStatus.OK);
	}
	
	@ApiOperation(value="Get Notes Sorted By Date")
	@GetMapping(value="/sortnotesbydate")
	public ResponseEntity<?> sortByCreatedDate(HttpServletRequest req){
		System.out.println("asdakjdkajshdaksjhd");
		Preconditions.checkNotNull(req.getHeader("userId").toString(), "Token Is Null");
		System.out.println("user id : "+req.getHeader("userId").toString());
		return new ResponseEntity<>(noteService.sortByCreatedDate(req.getHeader("userId").toString()),HttpStatus.OK);
	}
											/* Rest Template */
		 /* @GetMapping("/message{name}")
		  public String getMessage(@PathVariable String name) {

			ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:9700/user/send_message",
					String.class);
		    String response = responseEntity.getBody();
		    
		    System.out.println(response+" "+name);
		    return response+" "+ name;
		  }
		  									 Feign Client 
		  @Autowired
		  private UserServiceProxy proxy;

		  @GetMapping("/new_message{name}")
		  public String printMessage(@PathVariable String name) {

		    String fullName = proxy.getMessage()+" "+name;

		    return fullName;
		  }*/
}
