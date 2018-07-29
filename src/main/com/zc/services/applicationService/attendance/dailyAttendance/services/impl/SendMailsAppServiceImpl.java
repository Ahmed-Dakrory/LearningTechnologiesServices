/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.ISendMailsAppService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.FriendlyDate;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.domain.time.service.business.IGetMailsOFAttStatusDomainService;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 * 
 */
@Service
public class SendMailsAppServiceImpl implements ISendMailsAppService {

	@Autowired
	IGetMailsOFAttStatusDomainService getMailsService;
	@Autowired
	ITimeRepository timeRep;
	@Autowired
	IStudentRepository personRep;

	@Override
	public void sendMailsOfAttendanceToSomeStudent(Calendar day,
			List<Integer> excludedFileNumbers) {
		for (int i = 0; i < excludedFileNumbers.size(); i++) {

			Time time = timeRep.getTimeByFileNoAndDate(
					excludedFileNumbers.get(i), day);
			System.out.println(">>>>>>"+time.getPerson().getData().getNameInEnglish());
			time.setAttendanceStatus("Course has been changed");
			timeRep.update(time);
		}
		
		sendMailsOfAttendanceToStudents(day);
	}

	@Override
	public void sendMailsOfAttendanceToStudents(Calendar day) {

		List<Time> timesListScannedOnce = getMailsService
				.getTimesListByAttStatusAndDate(
						Constants.ATTENDANCE_STATUS_SCANNED_ONCE, day);
		List<Time> timeListAbsence = getMailsService
				.getTimesListByAttStatusAndDate(
						Constants.ATTENDANCE_STATUS_ABSENCE, day);
		List<Time> timeListAttended = getMailsService
				.getTimesListByAttStatusAndDate(
						Constants.ATTENDANCE_STATUS_ATTENDED, day);

		List<Student> PersonListAbsence = getMailsService
				.getMailListByListOfTimes(timeListAbsence);
		List<Student> PersonListScannedOnce = getMailsService
				.getMailListByListOfTimes(timesListScannedOnce);
		List<Student> PersonListAttended = getMailsService
				.getMailListByListOfTimes(timeListAttended);

		System.out.println("No of attended : " + PersonListAttended.size());
		System.out.println("No of absence : " + PersonListAbsence.size());
		System.out.println("No of scanned once is : "
				+ PersonListScannedOnce.size());
		
		System.out.println("Sending to absent students ........ ");
		sendMailsToAbsence(timeListAbsence, day);
		// System.out.println("Done sending for absence !");
		System.out.println("Sending to ScannedOnce students ........ ");
		sendMailsToScannedOnce(timesListScannedOnce, day);
		// System.out.println("Done sending for Scanned once");

		
	}

	public void sendMailsToAbsence(List<Time> times, Calendar day) {
		String date = FriendlyDate.getFriendlyDate(day);
		// get mails of times of abscent students
		List<Student> PersonListAbsence = getMailsService
				.getMailListByListOfTimes(times);

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
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[PersonListAbsence
					.size()];
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			// addressTo[1]=new
			// javax.mail.internet.InternetAddress("abadawi@zewailcity.edu.eg");
			for (int i = 0; i < PersonListAbsence.size(); i++) {
				addressBCC[i] = new javax.mail.internet.InternetAddress(
						PersonListAbsence.get(i).getData().getMail());
				System.out.println("send to "
						+ PersonListAbsence.get(i).getData().getMail());

			}
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
			            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
			            +"</li>"
			            	
			            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
			            	+"<div style=padding:24px 36px;color:#676767 !important;>"
			                	+"<span style=color:#676767>Dear Student , ,</span><br/><br/><br/>"
			                    +"<span style=color:#676767>We'd like to inform you that you didn't scan your access card on :"+date+" </span><br/><br/>"
			                    +"<span style=color:#676767>For more details you can see your daily attendance via: http://lts.zclt.info  </span><br/><br/>"
			                    +"<br/>"
			                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
			                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
			                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Attendance Alarm (" + date + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/

			/*message.setText("Dear Student , "
					+ "\n We'd like to inform you that you didn't scan your access card on : "
					+ date + "\n\n Thanks"
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/
			  message.setContent(htmlText, "text/html; charset=ISO-8859-1");
				
			Transport.send(message);

			System.out.println("Done sending " + PersonListAbsence.size());
			for (int i = 0; i < times.size(); i++) {
				Time time = times.get(i);
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT);
				timeRep.update(time);
			}
		} catch (MessagingException e) {
			for (int i = 0; i < times.size(); i++) {
				Time time = times.get(i);
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED);
				timeRep.update(time);
			}
			throw new RuntimeException(e);
		}
	}

	public void sendMailsToScannedOnce(List<Time> times, Calendar day) {
		String date = FriendlyDate.getFriendlyDate(day);

		// get the e-mails of given times
		List<Student> PersonListScannedOnce = getMailsService
				.getMailListByListOfTimes(times);

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
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[PersonListScannedOnce
					.size()];
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			// addressTo[1]=new
			// javax.mail.internet.InternetAddress("abadawi@zewailcity.edu.eg");
			for (int i = 0; i < PersonListScannedOnce.size(); i++) {
				addressBCC[i] = new javax.mail.internet.InternetAddress(
						PersonListScannedOnce.get(i).getData().getMail());
			}

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));

			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Attendance Alarm (" + date + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/
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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
				            +"</li>"
				            	
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767>Dear Student , ,</span><br/><br/><br/>"
				                    +"<span style=color:#676767>We'd like to inform you that you scanned your access card just once as you should scan it twice one in the morning and the afternoon for the day :"+date+" </span><br/><br/>"
				                    +"<span style=color:#676767>For more details you can see your daily attendance via: http://lts.zclt.info  </span><br/><br/>"
				                    +"<br/>"
				                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
				                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
				                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
			/*message.setText("Dear Student , "
					+ "\n We'd like to inform you that you scanned your access card just once as you should scan it twice one in the morning and the afternoon for the day :  "
					+ date + "\n\n Thanks"
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/
			  message.setContent(htmlText, "text/html; charset=ISO-8859-1");
				
			Transport.send(message);

			System.out.println("Done sending " + PersonListScannedOnce.size());
			for (int i = 0; i < times.size(); i++) {
				Time time = times.get(i);
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT);
				timeRep.update(time);
			}
		} catch (MessagingException e) {
			for (int i = 0; i < times.size(); i++) {
				Time time = times.get(i);
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED);
				timeRep.update(time);
			}
			throw new RuntimeException(e);
		}
	}

	
	public void sendEditAttendanceTimesMailConformation(Time times) {
		List<Time> attendance = new ArrayList<Time>();
		attendance.add(times);
		List<Student> PersonListAbsence = getMailsService
				.getMailListByListOfTimes(attendance);
		String date = FriendlyDate.getFriendlyDate(times.getDate());
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
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[PersonListAbsence
					.size()];
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			// addressTo[1]=new
			// javax.mail.internet.InternetAddress("abadawi@zewailcity.edu.eg");
			// addressTo[2]=new
			// javax.mail.internet.InternetAddress("ngalal@zewailcity.edu.eg");
			for (int i = 0; i < PersonListAbsence.size(); i++) {
				addressBCC[i] = new javax.mail.internet.InternetAddress(
						PersonListAbsence.get(i).getData().getMail());
				System.out.println("send to "
						+ PersonListAbsence.get(i).getData().getMail());

			}

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + date + ")");
		/*	message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/
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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
				            +"</li>"
				            	
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767>Dear Student , ,</span><br/><br/><br/>"
				                    +"<span style=color:#676767>We'd like to inform you that your attendance on:"+date+" + has been edited</span><br/><br/>"
				                    +"<span style=color:#676767>For more details you can see your daily attendance via: http://lts.zclt.info  </span><br/><br/>"
				                    +"<br/>"
				                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
				                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
				                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
			/*message.setText("Dear Student , "
					+ "\n We'd like to inform you that your attendance on   : "
					+ date + " has been edited " + "\n\n Thanks , "
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/
			 message.setContent(htmlText, "text/html; charset=ISO-8859-1");
				
			Transport.send(message);

			System.out.println("Done sending " + PersonListAbsence.size());
			for (int i = 0; i < attendance.size(); i++) {
				Time time = attendance.get(i);
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT);
				timeRep.update(time);
			}
		} catch (MessagingException e) {
			for (int i = 0; i < attendance.size(); i++) {
				Time time = attendance.get(i);
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED);
				timeRep.update(time);
			}
			throw new RuntimeException(e);
		}
	}

	@Override
	public void sendMailsOfAttendanceToSpecialTypeOfStudents(Calendar day,
			String status) {
		List<Time> timesList = getMailsService
				.getTimesListByAttStatusAndDate(
						status, day);
		

		if(status.equals(Constants.ATTENDANCE_STATUS_SCANNED_ONCE))
		{
			System.out.println("Sending to ScannedOnce students ........ ");
			sendMailsToScannedOnce(timesList, day);
		}
		else if(status.equals(Constants.ATTENDANCE_STATUS_ABSENCE))
		{
			System.out.println("Sending to absent students ........ ");
			sendMailsToAbsence(timesList, day);
		}
		
		

		
	}

	@Override
	public boolean sendEditAttendanceTimesMailConformation(DailyAttDataDTO dto) {
		try
		{
		Time time=timeRep.getAllByAttendanceFileNoAndDate(dto.getDate(), dto.getPersonId());
		String date = FriendlyDate.getFriendlyDate(time.getDate());
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
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			
			
				addressBCC[0] = new javax.mail.internet.InternetAddress(time.getPerson().getData().getMail());
					
				System.out.println("send to "+ time.getPerson().getData().getMail());

			

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + date + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/
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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
				            +"</li>"
				            	
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767>Dear "+time.getPerson().getData().getNameInEnglish()+",</span><br/><br/><br/>"
				                    +"<span style=color:#676767>We'd like to inform you that your attendance on:"+date+" has been edited </span><br/><br/>"
				                    +"<span style=color:#676767>For more details you can see your daily attendance via: http://lts.zclt.info  </span><br/><br/>"
				                    +"<br/>"
				                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
				                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
				                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
		/*	message.setText("Dear "+time.getPerson().getData().getNameInEnglish()+" ,"
					+ "\n We'd like to inform you that your attendance on   : "
					+ date + " has been edited " + "\n\n Thanks , "
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/
			 message.setContent(htmlText, "text/html; charset=ISO-8859-1");
			Transport.send(message);

			
		
				
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT_edit);
				timeRep.update(time);
			
		return true;
		} catch (MessagingException e) {
			
				time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED_edited);
				timeRep.update(time);
			
			throw new RuntimeException(e);
		}
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return false;
		}
		
		
	}

	@Override
	public boolean sendRefuseEditAtt(DailyAttDataDTO dto) {
	
		try
		{
		Time time=timeRep.getAllByAttendanceFileNoAndDate(dto.getDate(), dto.getPersonId());
		String date = FriendlyDate.getFriendlyDate(time.getDate());
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
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			
			
				addressBCC[0] = new javax.mail.internet.InternetAddress(time.getPerson().getData().getMail());
					
				System.out.println("send to "+ time.getPerson().getData().getMail());

			

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + date + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/
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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
				            +"</li>"
				            	
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767>Dear Student , ,</span><br/><br/><br/>"
				                    +"<span style=color:#676767>We'd like to inform you that your attendance on:"+date+" has been edited </span><br/><br/>"
				                    +"<span style=color:#676767>For more details you can see your daily attendance via: http://lts.zclt.info  </span><br/><br/>"
				                    +"<br/>"
				                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
				                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
				                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
			/*message.setText("Dear "+time.getPerson().getData().getNameInEnglish()+" ,"
					+ "\n We'd like to inform you that your Excuse to edit the attendance on "+date+ " has been refused "
					+ "For More Information please visit us" + "\n\n Thanks , "
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/
			 message.setContent(htmlText, "text/html; charset=ISO-8859-1");

			Transport.send(message);

			
		
				
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT_edit);
				//timeRep.update(time);
			
		return true;
		} catch (MessagingException e) {
			
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED_edited);
				//timeRep.update(time);
			
			throw new RuntimeException(e);
		}
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean sendApproveEditAtt(DailyAttDataDTO dto) {
		try
		{
		Time time=timeRep.getAllByAttendanceFileNoAndDate(dto.getDate(), dto.getPersonId());
		String date = FriendlyDate.getFriendlyDate(time.getDate());
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
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			
			
				addressBCC[0] = new javax.mail.internet.InternetAddress(time.getPerson().getData().getMail());
					
				System.out.println("send to "+ time.getPerson().getData().getMail());

			

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + date + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/

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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
				            +"</li>"
				            	
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767>Dear "+time.getPerson().getData().getNameInEnglish()+",</span><br/><br/><br/>"
				                    +"<span style=color:#676767>We'd like to inform you that your excuse on "+date+" has been  approved and your attendance status has been edited </span><br/><br/>"
				                    +"<span style=color:#676767>For more details you can see your daily attendance via: http://lts.zclt.info  </span><br/><br/>"
				                    +"<br/>"
				                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
				                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
				                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
			 message.setContent(htmlText, "text/html; charset=ISO-8859-1");
			/*message.setText("Dear "+time.getPerson().getData().getNameInEnglish()+" ,"
					+ "\n We'd like to inform you that your excuse on "+date+" has been  approved and your attendance status has been edited"
					+  "\n\n Thanks , "
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/

			Transport.send(message);

			
		
				
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT_edit);
				//timeRep.update(time);
			
		return true;
		} catch (MessagingException e) {
			
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED_edited);
				//timeRep.update(time);
			
			throw new RuntimeException(e);
		}
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendCustomEditEmailToStudent(DailyAttDataDTO dto ,String msg) {
		try
		{
		Time time=timeRep.getAllByAttendanceFileNoAndDate(dto.getDate(), dto.getPersonId());
		String date = FriendlyDate.getFriendlyDate(time.getDate());
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
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			
			
				addressBCC[0] = new javax.mail.internet.InternetAddress(time.getPerson().getData().getMail());
					
				System.out.println("send to "+ time.getPerson().getData().getMail());

			

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Editing Attendance Alarm (" + date + ")");
			/*message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);*/
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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Daily Attendance</h2>"
				            +"</li>"
				            	
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767> "+msg+" </span><br/><br/><br/>"
				                  
				                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
				                    +"<span style=color:#676767>Center for Learning Technologies</span><br/>"
				                    +"<span style=color:#676767>Please do not reply to this email</span>"
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
			 message.setContent(htmlText, "text/html; charset=ISO-8859-1");
			 
		/*	message.setText(msg
					
					+ "\n Learning Technologies Department"
					+ "\n\n Please do not reply to this email ");*/

			Transport.send(message);

			
		
				
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT_edit);
				//timeRep.update(time);
			
		return true;
		} catch (MessagingException e) {
			
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED_edited);
				//timeRep.update(time);
			
			throw new RuntimeException(e);
		}
		}
		catch(Exception ex)
		{
			
			ex.printStackTrace();
			return false;
		}
	}

}
