package com.bridgelabz.noteservice.model;

import java.util.List;

public class Description {
	
	private List<String> description;
	private List<Link> link;
	
	public List<String> getDescription() {
		return description;
	}
	public void setDescription(List<String> description) {
		this.description = description;
	}
	public List<Link> getLink() {
		return link;
	}
	public void setLink(List<Link> link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "Description [description=" + description + ", link=" + link + "]";
	}
	
	
}	
