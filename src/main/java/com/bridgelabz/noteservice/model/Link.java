package com.bridgelabz.noteservice.model;

public class Link {
	private String linkTitle;
	private String domain;
	private String image;
	
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "Link [linkTitle=" + linkTitle + ", domain=" + domain + ", image=" + image + "]";
	}
	
	
}
