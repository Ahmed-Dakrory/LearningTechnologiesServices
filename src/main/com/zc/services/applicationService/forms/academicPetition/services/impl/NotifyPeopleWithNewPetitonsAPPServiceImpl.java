/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services.impl;

import java.util.ArrayList;
import java.util.List;
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

import main.com.zc.services.applicationService.forms.academicPetition.services.INotifyPeopleWithNewPetitonsAPPService;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 * 
 */
@Service("notifyPeopleWithNewPetitonsAPPServiceImpl")
public class NotifyPeopleWithNewPetitonsAPPServiceImpl implements
		INotifyPeopleWithNewPetitonsAPPService {

	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	ICoursePetitionRep coursePetRep;
	@Override
	public boolean createMail(CoursePetitionDTO dao) {
		String subject = " Dear Instructor , \n"
				+ " we would like to inform you that there is a new petition to review \n"
				+ " This petition is related to course : "+ dao.getCourseName()+"\n\n\n"
				+ " Regards , \n"
				+ " Learning Technologies \n";
		System.out.println("Subject is : " + subject);
	
		List<Courses_Instructors> courseInsList=courseInsRep.getByCourseId(dao.getCourseID());
		List<String> mailsList=new ArrayList<String>();
		for(int i=0;i<courseInsList.size();i++)
		{
			
			mailsList.add(courseInsList.get(i).getInstructor().getMail());
			
		}
	return notifyUser(mailsList,subject) ;
	}

	@Override
	public boolean notifyUser(List<String> mail, String subject) { // to send it to the real instructors Un-comment BCC Part

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
								"learningtechnologies@zewailcity.edu.eg",
								"DELF-651984@dr");
					}
				});

		try {
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[mail.size()];
			for(int i=0;i<mail.size();i++)
			{
				addressBCC[i]=new javax.mail.internet.InternetAddress(
						mail.get(i));
			}
			/*javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"mshoieb@zewailcity.edu.eg");*/
			/*addressTo[1] = new javax.mail.internet.InternetAddress(
					"lsarhaan@zewailcity.edu.eg");
			addressTo[2] = new javax.mail.internet.InternetAddress(
					"fhanteera@zewailcity.edu.eg");
			addressTo[3] = new javax.mail.internet.InternetAddress(
					"shussein@zewailcity.edu.eg");
			addressTo[4] = new javax.mail.internet.InternetAddress(
					"tsaid@zewailcity.edu.eg");
			addressTo[5] = new javax.mail.internet.InternetAddress(
					"mheragi@zewailcity.edu.eg");
			addressTo[6] = new javax.mail.internet.InternetAddress(
					"helbadry@zewailcity.edu.eg");*/
			/*addressTo[1] = new javax.mail.internet.InternetAddress(
					"abadawi@zewailcity.edu.eg");*/
		/*	addressTo[8] = new javax.mail.internet.InternetAddress(
					"melshawarby@zewailcity.edu.eg");*/
			// addressTo[1]=new
			// javax.mail.internet.InternetAddress("abadawi@zewailcity.edu.eg");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("learningtechnologies@zewailcity.edu.eg"));
			//message.setRecipients(Message.RecipientType.TO, addressTo);
            message.setRecipients(Message.RecipientType.TO.BCC, addressBCC);
			message.setSubject("New Petition Alarm ! ");

			message.setText(subject);

			Transport.send(message);
			System.out.println("Done sending notifiction mail ");
			return true;

		} catch (MessagingException e) {

			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Unexpected Error Please Try Registeration Again! ",
									""));
			e.printStackTrace();
			// /throw new RuntimeException(e);
			return false;
		}

	}

	@Override
	public boolean notifyDean(CoursePetitionDTO dao) {
		String subject = " Dear Dr Ashraf , \n"
				+ " we would like to inform you that there is a new petition to review \n"
				+ " This petition is related to course : "+ dao.getCourseName()+"\n\n\n"
				+ " Regards , \n"
				+ " Learning Technologies \n";
		System.out.println("Subject is : " + subject);
	
	
		List<String> mailsList=new ArrayList<String>();
	    //mailsList.add("abadawi@zewailcity.edu.eg");
		mailsList.add("abadawi@zewailcity.edu.eg");
		return notifyUser(mailsList,subject) ;
	}

	@Override
	public boolean notifyAdmissionHead(CoursePetitionDTO dao) {
		String subject = " Dear Ms. Abla , \n"
				+ " we would like to inform you that there is a new petition to review \n"
				+ " This petition is related to course : "+ dao.getCourseName()+"\n\n\n"
				+ " Regards , \n"
				+ " Learning Technologies \n";
		System.out.println("Subject is : " + subject);
	
	
		List<String> mailsList=new ArrayList<String>();
	    mailsList.add("ghazem@zewailcity.edu.eg");
		return notifyUser(mailsList,subject) ;
	}

	@Override
	public boolean notifyRegistrar(CoursePetitionDTO dao) {
		String subject = " Dear  , \n"
				+ " we would like to inform you that there is a new petition to perform \n"
				+ " This petition is related to course : "+ dao.getCourseName()+"\n\n\n"
				+ " Regards , \n"
				+ " Learning Technologies \n";
		System.out.println("Subject is : " + subject);
	
	
		List<String> mailsList=new ArrayList<String>();
	    mailsList.add("registrar@zewailcity.edu.eg");
		return notifyUser(mailsList,subject) ;
	}

}
