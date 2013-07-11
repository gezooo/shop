package com.zg.service;

import java.util.Map;

import com.zg.entity.Member;

/*
* @author gez
* @version 0.1
*/

public interface MailService {
	
	
	/**
	 * Check if the configuration of the mail service is complete or not, the check items includes 
	 * teh sender mail box, SMTP server address, SMTP server port, SMTP user, SMTP password.
	 * if any item is missing, the check is failed.
	 * @return 
	 */
	public boolean isMailConfigComplete();
	
	public void sendMail(String subject, String templateFilePath, Map<String, Object> data, String toMail);
	
	public void sendSmtpTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail);

	public void sendPasswordRecoverMail(Member member);

}
