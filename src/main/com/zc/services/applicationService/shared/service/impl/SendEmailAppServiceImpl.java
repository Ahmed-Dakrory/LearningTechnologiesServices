/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.shared.service.ISendEmailAppService;

/**
 * @author omnya
 *
 */
@Service
public class SendEmailAppServiceImpl implements ISendEmailAppService{

	@Override
	public boolean sendMail(ArrayList<String> emailLstTO,ArrayList<String> emailLstCC,ArrayList<String> emailLstBCC, String content, String subject) {
try{
			
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
									"LearningTechnologies@zewailcity.edu.eg",
									"learningtechnologies@zc");
						}
					});
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(
					"LearningTechnologies@zewailcity.edu.eg"));

			message.setSubject(subject);

			message.setContent(content, "text/html; charset=ISO-8859-1");

			
			if(emailLstTO!=null)
			{
				if(emailLstTO.size()>0)
				{
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[emailLstTO.size()];
			for(int i=0;i<emailLstTO.size();i++)
			{
			addressTo[i] = new javax.mail.internet.InternetAddress(emailLstTO.get(i));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			}
				}
			}
			if(emailLstCC!=null)
			{
				if(emailLstCC.size()>0)
				{
			javax.mail.internet.InternetAddress[] addressCC = new javax.mail.internet.InternetAddress[emailLstCC.size()];
			for(int i=0;i<emailLstCC.size();i++)
			{
				addressCC[i] = new javax.mail.internet.InternetAddress(emailLstCC.get(i));
			message.setRecipients(Message.RecipientType.CC, addressCC);
			}
				}
			}
			if(emailLstBCC!=null)
			{
				if(emailLstBCC.size()>0)
				{
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[emailLstBCC.size()];
			for(int i=0;i<emailLstBCC.size();i++)
			{
				addressBCC[i] = new javax.mail.internet.InternetAddress(emailLstBCC.get(i));
			message.setRecipients(Message.RecipientType.BCC, addressBCC);
			}
				}
			}
		
			Transport.send(message);

		
return true;
		

	} catch (Exception excep) {
		return false;

	}
	}

}
