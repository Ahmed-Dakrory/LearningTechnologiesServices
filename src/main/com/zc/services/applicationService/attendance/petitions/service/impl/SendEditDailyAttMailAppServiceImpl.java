/**
 * 
 */
package main.com.zc.services.applicationService.attendance.petitions.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.petitions.service.ISendEditDailyAttMailAppService;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.FriendlyDate;
import main.com.zc.services.domain.time.model.Time;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class SendEditDailyAttMailAppServiceImpl implements ISendEditDailyAttMailAppService{

	@Override
	public boolean sendMailByEditedAtt(String mail, Calendar cal) {
		
		String friendlyDate = FriendlyDate.getFriendlyDate(cal);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"attendance@zewailcity.edu.eg",
								"alaaeddin123456");
					}
				});

		try {
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[1];
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			addressTo[0] = new javax.mail.internet.InternetAddress("oalaaeddin@zewailcity.edu.eg");
			addressBCC[0]= new javax.mail.internet.InternetAddress(mail);
		

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + friendlyDate + ")");
			message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);

			message.setText("Dear Student , "
					+ "\n We'd like to inform you that your attendance on   : "
					+ friendlyDate + " has been edited " + "\n\n Thanks , "
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");

			Transport.send(message);

			return true;
		} 
		catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendMailByEditedAttTest(String mail, Calendar cal, int fileNo) {
	String friendlyDate = FriendlyDate.getFriendlyDate(cal);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"attendance@zewailcity.edu.eg",
								"alaaeddin123456");
					}
				});

		try {
			//javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[1];
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			addressTo[0] = new javax.mail.internet.InternetAddress("oalaaeddin@zewailcity.edu.eg");
			//addressBCC[0]= new javax.mail.internet.InternetAddress("oalaaeddin@zewailcity.edu.eg");
		

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + friendlyDate + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);
*/
			message.setText("Dear Student , with file No  "+fileNo
					+ "\n We'd like to inform you that your attendance on   : "
					+ friendlyDate + " has been edited " + "\n\n Thanks , "
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");

			Transport.send(message);

			return true;
		} 
		catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

}
