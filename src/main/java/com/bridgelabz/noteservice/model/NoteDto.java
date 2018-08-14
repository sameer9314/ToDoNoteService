package com.bridgelabz.noteservice.model;

import java.io.Serializable;
import java.util.List;

/**
 * Purpose : Note DTO class to take the input from the user for the note 
 * 			 and map it to the Note object. 
 *  	
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    24/08/2018
 */
public class NoteDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String description;
	private boolean archive=false;
	private boolean pinned=false;
	
	private List<Label> label;
	
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public boolean isPinned() {
		return pinned;
	}
	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}
	public List<Label> getLabel() {
		return label;
	}
	public void setLabel(List<Label> label) {
		this.label = label;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "NoteDto [title=" + title + ", description=" + description + ", archive=" + archive + ", pinned="
				+ pinned + ", label=" + label + "]";
	}
	
}
