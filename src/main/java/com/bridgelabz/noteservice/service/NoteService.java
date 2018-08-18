package com.bridgelabz.noteservice.service;

import java.text.ParseException;
import java.util.List;

import com.bridgelabz.noteservice.model.Label;
import com.bridgelabz.noteservice.model.NoteDto;
import com.bridgelabz.noteservice.model.Notes;

/**
 * Purpose : To provide the methods for the Note service implementation.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 20/07/2018
 */
public interface NoteService {
	/**
	 * Method is provided to create note  for the logged in User.
	 * 
	 * @param note
	 * @param token
	 * @throws Exception
	 */
	public String createNote(NoteDto note, String token,String labelName) throws Exception;

	/**
	 * This method is provided to return all the notes present in the database.
	 * 
	 * @return
	 */
	public List<Notes> getAllNotes();

	/**
	 * This method is provided to return all notes (from the list which is not
	 * archived) of a particular User.
	 * 
	 * @param token
	 * @return
	 */
	public List<Notes> getNotes(String token);

	/**
	 * This method is written to return particular note of the particular User.
	 * 
	 * @param token
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Notes getParticularNote(String token, String id) throws Exception;

	/**
	 * Method is written to update particular note of the logged in User.
	 * 
	 * @param token
	 * @param note
	 * @return
	 * @throws Exception
	 */
	public Notes updateNotes(String token, NoteDto note,String noteId) throws Exception;

	/**
	 * Method is written to remove particular Note of the User.S
	 * 
	 * @param token
	 * @param id
	 * @throws Exception
	 */
	public void removeNote(String token, String id) throws Exception;
	
	/**
	 * Method is written to return archived notes of a particular user.
	 * 
	 * @param token
	 * @return List<Notes>
	 */
	public List<Notes> getArchiveNotes(String token);
	
	
	/**
	 * Method is written to get all the label created by the User.
	 * 
	 * @param token
	 * @return
	 */
	public List<String> getLabel(String token);
	
	/**
	 * Method is written to create Label for the particular user.
	 * 
	 * @param token
	 * @param label
	 * @throws Exception 
	 */
	public void createLabel(String token,Label label) throws Exception;
	
	/**
	 * Method is written to add Label on an existing Note.
	 * 
	 * @param token
	 * @param newLabel
	 * @param noteId
	 * @throws Exception 
	 */
	public void addLabelInNote(String token,Label newLabel,String noteId) throws Exception;
	
	/**
	 * Method Is Written To Get All the Notes According To Its Label.
	 *  
	 * @param token
	 * @param labelName
	 * @return List<Notes>
	 */
	public List<Notes> getNoteWithLabel(String token,String labelName);
	
	/**
	 * Method is written to remove the label and remove the label from every notes of the user.
	 * 
	 * @param token
	 * @param labelName
	 */
	public void removeLabel(String token,String labelName);
	
	
	/**
	 * To remove the label from the particular Note.
	 * 
	 * @param token
	 * @param labelName
	 * @param noteId
	 */
	public void removeNoteLabel(String token,String labelName,String noteId);
	
	/**
	 * Method is written to set the reminder to the Notes.
	 * 
	 * @param token
	 * @param noteId
	 * @param reminderTime
	 * @throws ParseException
	 */
	public void doSetReminder(String token, String noteId, String reminderTime) throws ParseException;
	
	/**
	 * To get all notes which is trashed. 
	 * 
	 * @param token
	 * @return
	 */
	public List<Notes> getTrashedNotes(String token);
	
	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	public String pinnedUnpinned(String token, String noteId);
	
	/**
	 * To set the pinned status to true or false for the particular noteId if note archived status is false.
	 * 
	 * @param token
	 * @param noteId
	 * @return
	 */
	public String archivedOrRemoveArchived(String token, String noteId);
	
	/**
	 * To view all the trashed Notes.
	 * 
	 * @param token can not be null.
	 * @param noteId can not be null.
	 * 
	 * @return List<Notes>
	 */
	public List<String> viewTrashList(String token, String noteId);

	/**
	 * To get all notes sorted by name.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Notes> sortByName(String userId);

	/**
	 * To get all notes sorted by creation date.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Notes> sortByCreatedDate(String userId);
}
