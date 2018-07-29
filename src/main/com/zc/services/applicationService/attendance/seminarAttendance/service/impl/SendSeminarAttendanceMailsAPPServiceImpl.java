/**
 * 
 */
package main.com.zc.services.applicationService.attendance.seminarAttendance.service.impl;

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

import main.com.zc.services.applicationService.attendance.seminarAttendance.service.ISendSeminarAttendanceMailsAPPService;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.FriendlyDate;
import main.com.zc.services.domain.time.model.ISeminarTimesRepository;
import main.com.zc.services.domain.time.model.SeminarTimes;
import main.com.zc.services.domain.time.service.business.IGetMailsOFAttStatusDomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service("")
public class SendSeminarAttendanceMailsAPPServiceImpl implements ISendSeminarAttendanceMailsAPPService{

	@Autowired
	IGetMailsOFAttStatusDomainService getMailsService;
    @Autowired
    ISeminarTimesRepository SeminarRep;
    
	@Override
	public void sendEmails(Calendar cal) {
		
		List<SeminarTimes> timeListAbsent=SeminarRep.getAllByStatusAndDate(Constants.ATTENDANCE_STATUS_ABSENCE,cal);
		//List<SeminarTimes> timeListAttended=SeminarRep.getAllByStatusAndDate(Constants.ATTENDANCE_STATUS_ATTENDED,cal);
		List<Student> PersonListAbsence=getMailsService.getMailListByListOfSeminarTimes(timeListAbsent);
		//List<Person> PersonListAttended=getMailsService.getMailListByListOfSeminarTimes(timeListAttended);
		
		// System.out.println("No of attended : "+PersonListAttended.size());
		  System.out.println("No of absence : "+PersonListAbsence.size());
		  System.out.println("Sending to absent students ........ ");	
		  sendMailsToAbsence(timeListAbsent,cal);
	}

	
	
	
	
	
	
	
	public void sendMailsToAbsence(List<SeminarTimes> times,Calendar day)
	{
	String date =FriendlyDate.getFriendlyDate(day);
	//get mails of times of abscent students
	List<Student> PersonListAbsence=getMailsService.getMailListByListOfSeminarTimes(times);
	
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
				return new PasswordAuthentication("attendance@zewailcity.edu.eg","alaaeddin123456");
			}
		});
    

try {
	javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[PersonListAbsence.size()];
	javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[3];
	addressTo[0]=new javax.mail.internet.InternetAddress("oalaaeddin@zewailcity.edu.eg");
	addressTo[1]=new javax.mail.internet.InternetAddress("abadawi@zewailcity.edu.eg");
	addressTo[2]=new javax.mail.internet.InternetAddress("ngalal@zewailcity.edu.eg");
	for (int i = 0; i < PersonListAbsence.size(); i++)
	{
		addressBCC[i] = new javax.mail.internet.InternetAddress(PersonListAbsence.get(i).getData().getMail());
	    System.out.println("send to "+PersonListAbsence.get(i).getData().getMail());
	    
	}

	Message message = new MimeMessage(session);
	message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
	message.setRecipients(Message.RecipientType.TO,addressTo);
	
	message.setSubject("Seminar Attendance Alarm ("+date+")");
	message.setRecipients(javax.mail.Message.RecipientType.BCC, addressBCC); 
	
	message.setText("Dear Student , " +
			"\n We'd like to inform you that you didn't attend the Seminar on  : " +date+
			"\n\n Thanks"+
			"\n Learning Technologies Department"+
			"\n\n Please do not reply to this email ");

	Transport.send(message);

	System.out.println("Done sending "+PersonListAbsence.size());
    for(int i=0;i<times.size();i++)
    {
    	SeminarTimes time=times.get(i);
    	time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT);
    	SeminarRep.update(time);
    }
} catch (MessagingException e) {
	for(int i=0;i<times.size();i++)
    {
		SeminarTimes time=times.get(i);
    	time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED);
    	SeminarRep.update(time);
    }
	throw new RuntimeException(e);
}
}
	
	
}
