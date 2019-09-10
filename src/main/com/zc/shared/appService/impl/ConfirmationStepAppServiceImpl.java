/**
 * 
 */
package main.com.zc.shared.appService.impl;

import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.appService.IConfirmationStepAppService;

import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service("confirmationStepAppServiceImpl")
public class ConfirmationStepAppServiceImpl implements IConfirmationStepAppService{

	@Override
	public boolean  sendConfirmMail(String mail , int code) {

		/*Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((1000000000 - 10000000) + 1) + 10000000;*/
	    
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

		try {
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
		/*	addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");*/
			addressTo[0] = new javax.mail.internet.InternetAddress(
                 mail);
			String htmlText="<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>" 
			    	+"<ul style=margin:0;padding:0;>" 
			        	+"<li style=list-style:none;float:left;width:700px;margin:0;>"
			            +"	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
			                	+"<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
			                    +"<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
			                +"</ul>"
			            +"</li>"
			            +"<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
			            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
			            +"</li>"
			            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
			            	+"<div style=padding:24px 36px;color:#676767 !important;>"
			                	+"<span style=color:#676767>Thank you for signing up for ZC LT Online Services</span><br/><br/><br/>"
			                	+"<span style=color:#676767>To get started and finish registering your account, please activate your account by copy and paste the confirmation code below into your browser.</span><br/><br/>"
			                	+"<span style=color:#676767>If you did not recently attempt to activate your ZC LT Online Services account, please disregard this email.</span><br/><br/>"
			                	+"<span style=color:#676767><b>Your Confirmation Code is :</b> "+code +"</span><br/><br/>"
			                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
			                    +"<span style=color:#676767>Center for Learning Technologies</span>"
			                 +"</div>"
						+"</li>"
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>" 
			            	+"<ul style=margin:0;padding:0;>" 
			                	+"<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
			            			+"<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
			                		+"<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
			                    +"</li>"
			                    +"<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
			                    	+"<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
			                    +"</li>"
			                +"</ul>" 
			            +"</li>" 
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
			            	+"<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
			                	
			                	+" <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
			                	+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
			                +"</div>"
			            +"</li>"
			            +"</ul>"
			        	+"</div>";
			/*message.setText("Dear " + dao.getName() + " ,"
					+ "\n Your Password is : " + dao.getPassword()
					+ "\n\n Regards"
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");

			Transport.send(message);*/
		
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(
					"LearningTechnologies@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Confirmation Mail");
/*
			message.setText("Hello , " 
					+ "\n Thank You For Registeration  "
					+ "\n Your Confirmation Code is : "+code 
					+ "\n\n Regards"
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/

			message.setContent(htmlText, "text/html; charset=ISO-8859-1");

			Transport.send(message);
			JavaScriptMessagesHandler.RegisterNotificationMessage("", "Please, Check Your Inbox");
			return true;

		} catch (MessagingException e) {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error Please Try Registeration Again! ");
			e.printStackTrace();
			return false;
		}
		
	}

}
