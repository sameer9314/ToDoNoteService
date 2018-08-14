package com.bridgelabz.Utility;

import java.io.Serializable;

import org.springframework.stereotype.Service;

/**
 * Purpose : POSO Class for Mail.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 21/07/2018
 */
@Service
public class Mail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String to;
	private String subject;
	private String body;

	public Mail() {
		super();
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Mail [to=" + to + ", subject=" + subject + ", body=" + body + "]";
	}
}
