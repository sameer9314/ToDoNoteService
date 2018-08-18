package com.bridgelabz.noteservice.model;

import org.springframework.stereotype.Service;

/**
 * Purpose : POJO class which provides the fields to set the message and status
 * which will be sent to the client side.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 20/07/2018
 */
@Service
public class Response {

	private String message;
	private int status;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", status=" + status + "]";
	}

	

}
