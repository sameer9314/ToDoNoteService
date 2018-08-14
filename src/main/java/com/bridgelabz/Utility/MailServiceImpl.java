package com.bridgelabz.Utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Purpose : To provide the implementation of the sendEmail method which is used
 * to send the mail.
 * 
 * @author Sameer Saurabh
 * @version 1.0
 * @Since 21/07/2018
 */
@Component
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgelabz.todoapplication.Utility.MailService#sendMail(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	/**
	 * Method to send the mail to the user.
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 */
	public void sendMail(String subject, String body, String to) throws MessagingException {

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body);

		javaMailSender.send(message);
	}
}
