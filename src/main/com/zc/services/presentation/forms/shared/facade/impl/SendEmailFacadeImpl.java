/**
 * 
 */
package main.com.zc.services.presentation.forms.shared.facade.impl;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.ISendEmailAppService;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author Omnya Alaa
 *
 */
@Service("ISendEmailFacade")
public class SendEmailFacadeImpl implements ISendEmailFacade{

	@Autowired
	ISendEmailAppService service;
	@Override
	public boolean sendEmail(String senderName, String senderEmail,
			String content, String title) {
try{
	String from = "LearningTechnologies@zewailcity.edu.eg";
    String pass = "DELF-651984@dr";
	
	// TODO Auto-generated method stub
	 Properties props = System.getProperties();

        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			
			
			 
			 //TODO the real sender
			addressTo[0] = new javax.mail.internet.InternetAddress(senderEmail);


			
			message.setFrom(new InternetAddress(
					from));
			message.setRecipients(Message.RecipientType.TO, addressTo);


			String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
					+ "<ul style=margin:0;padding:0;>"
					+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
					+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
					+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
					+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
					+ "</ul>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
					+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
					+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
					+ "<div style=padding:24px 36px;color:#676767 !important;>"
					+ "<span style=color:#676767>Dear "
					+ senderName
					+ ",</span><br/><br/><br/>"
					+content
					+"<br/><br/><br/>"
					+ "<span style=color:#676767>Thank you, </span><br/><br/>"
					+ "<span style=color:#676767>Center for Learning Technologies</span>"
					+ "</div>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
					+ "<ul style=margin:0;padding:0;>"
					+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
					+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
					+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
					+ "</li>"
					+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
					+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
					+ "</li>"
					+ "</ul>"
					+ "</li>"
					+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
					+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
					
					+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
					+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
					+ "</div>" + "</li>" + "</ul>" + "</div>";
			

			 message.setSubject(title);
	            message.setText(htmlText);

	    		message.setContent(htmlText, "text/html; charset=ISO-8859-1");
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, from, pass);
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();

		/*	JavaScriptMessagesHandler.RegisterNotificationMessage("",
					"Please, Check Your Inbox");
		*/	// System.out.println("Done sending ");

		return true;

	} catch (Exception excep) {
		JavaScriptMessagesHandler.RegisterErrorMessage("",
				"System Error");
return false;
	}

	}

	@Override
	public boolean sendMail(ArrayList<String> emailLstTO, ArrayList<String> emailLstCC, ArrayList<String> emailLstBCC,
			String content, String subject) {	// TODO Auto-generated method stub
		return service.sendMail(emailLstTO, emailLstCC, emailLstBCC, content, subject);
	}

}
