/**
 * 
 */
package main.com.zc.services.domain.shared;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;


/**
 * @author Omnya
 *
 */
@Service
public class Constants implements IConstants {

		
		
		
		
		
	
	public static final String ATTENDANCE_STATUS_ABSENCE = "Absence";
	public static final String ATTENDANCE_STATUS_ATTENDED = "Attended";
	public static final String ATTENDANCE_STATUS_SCANNED_ONCE = "Scanned Once";
	public static final String ATTENDANCE_STATUS_EVENT="Event";
	public static final String ATTENDANCE_STATUS_EXCUSE = "Excuse";
	public static final String ATTENDANCE_STATUS_WAITING_RESPONSE = "Waiting Response";
	public static final String ATTENDANCE_STATUS_REFUSE_EXCUSE = "Refused";
	public static final String ATTENDANCE_STATUS_APPROVE_EXCUSE = "Edited";
	public static final String SENT_MAIL_STATUS_SENT="Sent";
	public static final String SENT_MAIL_STATUS_SENT_edit="Sent editing email";
	public static final String SENT_MAIL_STATUS_FAILED="Failed";
	public static final String SENT_MAIL_STATUS_FAILED_edited="Failed to Send editing Email";
	public static final String PETITION_STATUS_UNDER_REVIEW="Under Review";
	public static final String PETITION_STATUS_REVIEWED="Reviewed";
	public static final String PETITION_STATUS_APPROVED_BY_INS="Approved By ";
	public static final String PETITION_STATUS_APPROVED_BY_PA="Approved By Program Advisor";
	public static final String PETITION_STATUS_APPROVED_BY_DEPARTMENT="Approved By Admission Department and will be performed soon";
	public static final String PETITION_STATUS_APPROVED_BY_DEAN="Approved By Dean Of Students ";
	public static final String PETITION_STATUS_APPROVED_BY_PROVOST="Approved By the Provost";
	public static final String PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD="Approved By Ms. Abla Osman";
	public static final String PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD="Refused By Ms. Abla Osman";
	public static final String PETITION_STATUS_REFUSED_BY_DEAN="Refused By Dean Of Students ";
	public static final String PETITION_STATUS_REFUSED_BY_PROVOST="Refused By the Provost ";
	public static final String PETITION_STATUS_REFUSED="Refused";
	public static final String PETITION_STATUS_REFUSED_BY_INS="Refused By ";
	public static final String PETITION_STATUS_REFUSED_BY_PA="Refused By Program Advisor";
	public static final String PETITION_STATUS_REFUSED_BY_DEPARTMENT="Refused By Admission department";
	/*public static final String DEAN_OF_STUDENTS="abadawi@zewailcity.edu.eg";
	public static final Integer DEAN_OF_STUDENTS_ID=6;
	public static final String DEAN_OF_STUDENTS_NAME="Dr. Ashraf Badawi";
	*/
	public static  String DEAN_OF_STRATEGIC;//"mabdrabou@zewailcity.edu.eg";
	public static  Integer DEAN_OF_STRATEGIC_ID;//1379;
	public static  String DEAN_OF_STRATEGIC_NAME;//"Dr. Mahmoud Abd Rabouh";
	public static  String DEAN_OF_ACADEMIC;//"tibrahim@zewailcity.edu.eg";
	public static  Integer DEAN_OF_ACADEMIC_ID;//20;
	public static  String DEAN_OF_ACADEMIC_NAME;//"Dr. Tarek Ibrahim";
	
//	public static  String ADMISSION_HEAD_EMAIL;//"ghazem@zewailcity.edu.eg";
//	public static  Integer ADMISSION_HEAD_ID;//888;
//	public static  String ADMISSION_HEAD_NAME;//"Mrs. Ghada";
	
	public static  String REGISTRAR_HEAD_EMAIL;//"ghazem@zewailcity.edu.eg";
	public static  Integer REGISTRAR_HEAD_ID;//888;
	public static  String REGISTRAR_HEAD_NAME;//"Mrs. Ghada";
	
//	public static  String ADMISSION_HEAD;//"ghazem@zewailcity.edu.eg";
//	public static  Integer ADMISSION_HEAD_ID;//888;
//	public static  String ADMISSION_HEAD_NAME;//"Mrs. Ghada";
	
	public static  String Financial_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer Financial_DEP_ID;//888;
	public static  String Financial_DEP_NAME;//"Mrs. Ghada";
	

	public static  String TeachingEffectiveness_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer TeachingEffectiveness_DEP_ID;//888;
	public static  String TeachingEffectiveness_DEP_NAME;//"Mrs. Ghada";
	

	public static  String AcademicAdvising_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer AcademicAdvising_DEP_ID;//888;
	public static  String AcademicAdvising_DEP_NAME;//"Mrs. Ghada";
	
	
	public static  String ACCREDITION_ENG_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer ACCREDITION_ENG_ID;//888;
	public static  String ACCREDITION_ENG_NAME;//"Mrs. Ghada";
	
	public static  String ACCREDITION_SCI_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer ACCREDITION_SCI_ID;//888;
	public static  String ACCREDITION_SCI_NAME;//"Mrs. Ghada";

	
	public static  String ASSOCIATE_DEAN_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer ASSOCIATE_DEAN_ID;//888;
	public static  String ASSOCIATE_DEAN_NAME;//"Mrs. Ghada";
	
	

	
	public static  String ADMISSION_HEAD_EMAIL;//"ghazem@zewailcity.edu.eg";
	public static  Integer ADMISSION_HEAD_ID;//888;
	public static  String ADMISSION_HEAD_NAME;//"Mrs. Ghada";
	
	
	
	public static  String ADMISSION_DEPT;//"registrar@zewailcity.edu.eg";
	public static  Integer ADMISSION_DEPT_ID;//999;
	public static final String CHANGE_MAJOR_YASMINE="yasmine@zewailcity.edu.eg";
	public static final String CHANGE_MAJOR_HEND="hend@zewailcity.edu.eg";
	public static  String PROVOST;//"sobbaya@zewailcity.edu.eg";
	public static  Integer PROVOST_ID;//21;
	public static  String PROVOST_NAME;//"Dr. Salah Obayya";
	public static final String LTS_SYSTEM_ADMIN="lts-admin@zewailcity.edu.eg";
	public static final String LTS_FEEDBACK_HANDLER="lts-admin@zewailcity.edu.eg";
	public static final String GRADUATION_FORM_EMAIL="graduation@zewailcity.edu.eg";
	
	
	

	public static String getMessageforEmailWithMessage(String name,String message) {
		return  "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
				+ "<ul style=margin:0;padding:0;>"
				+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
				+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
				+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/resources/images/zewailLogo.png\"  style=\"width:55px\" alt=Zewail City of Science and Technology /></li>"
				+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/resources/images/welocome_logo.png\"  style=\"width:118px\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
				+ "</ul>"
				+ "</li>"
				+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
				+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
				+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
				+ "</li>"
				+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				+ "<div style=padding:24px 36px;color:#676767 !important;>"
				+ "<span style=color:#676767>Dear "
				+ name
				+ ",</span><br/><br/><br/>"
				+ "<span style=color:#676767>"+message+"</span><br/><br/><br/>"
				+ "<span style=color:#676767>Thank you, </span><br/><br/>"
				+ "<span style=color:#676767>Center for Learning Technologies</span>"
				+ "</div>"
				+ "</li>"
				+ "<li style=\"list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec\">"
				+ "<ul style=margin:0;padding:0;>"
				+ "<li style=\"list-style: none; float:left; width:134px; margin:0; padding:18px 36px !important; color:#717070;\">"
				+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/resources/images/zewailLogo.png\" style=\"width:61px\" alt=Center for Learning Technologies /></a><br/>"
				+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
				+ "</li>"
				+ "<li style=\"list-style:none;float:right;margin:0;padding:18px;\">"
				+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/resources/images/welocome_logo.png\" style=\"width:145px\"  alt=Zewail City of Science and Technology /></a>"
				+ "</li>"
				+ "</ul>"
				+ "</li>"
				+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
				+ "</li>" + "</ul>" + "</div>";
		
	}

	private static void sendFromGMail( final String[] to, final String subject, final String body) {
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				String from = "learningtechnologies@zewailcity.edu.eg";
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

			        try {
			            message.setFrom(new InternetAddress(from));
			            InternetAddress[] toAddress = new InternetAddress[to.length];

			            // To get the array of addresses
			            for( int i = 0; i < to.length; i++ ) {
			                toAddress[i] = new InternetAddress(to[i]);
			            }

			            for( int i = 0; i < toAddress.length; i++) {
			                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			            }

			            message.setSubject(subject);
			            message.setText(body);

			    		message.setContent(body, "text/html; charset=ISO-8859-1");
			            Transport transport = session.getTransport("smtp");
			            transport.connect(host, from, pass);
			            transport.sendMessage(message, message.getAllRecipients());
			            transport.close();
			        }
			        catch (AddressException ae) {
			            ae.printStackTrace();
			            System.out.println("Problem"+ae.toString());
			        }
			        catch (MessagingException me) {
			            me.printStackTrace();
			            System.out.println("Problem"+me.toString());
			        }
			        
			}
		}).start();
	       
	    }
	
public static void sendEmailNotificationForThisEmailWithMessage(String name, String subject, String message,String mail) {
		
        String[] to = {mail }; // list of recipient email addresses 
       
        String htmlText = getMessageforEmailWithMessage(name, message);

        sendFromGMail( to, subject, htmlText);
        

}
}
