package com.bridgelabz.Utility;
import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

/**
 * Purpose : Interface to provide the method to send the mail.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 21/07/2018
 */
@Service
public interface MailService {

	public void sendMail(String subject, String body, String to) throws MessagingException;
}
