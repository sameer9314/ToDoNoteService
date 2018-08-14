package com.bridgelabz.noteservice.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Purpose : POSO class of Notes.
 * 	
 * @author   Sameer Saurabh
 * @version  1.0
 * @Since    24/08/2018
 */
@Document(indexName="notedatabase",type="notes")
public class Notes implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
	private String title;
	private Description description;
	
	private String creationDate;
	private String userId;
	private String lastModified;
	private boolean archive;
	private boolean pinned;
	private boolean trash;
	
	//@DBRef
	//@Field("label")
	private List<Label> label;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
		this.description = description;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

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

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public List<Label> getLabel() {
		return label;
	}

	public void setLabel(List<Label> label) {
		this.label = label;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Notes [id=" + id + ", title=" + title + ", description=" + description + ", creationDate="
				+ creationDate + ", userId=" + userId + ", lastModified=" + lastModified + ", archive=" + archive
				+ ", pinned=" + pinned + ", trash=" + trash + ", label=" + label + "]";
	}
		
}
