/**
 * 
 */
package main.com.zc.services.domain.shared;

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
	
	
	
	public static  String ADMISSION_HEAD;//"ghazem@zewailcity.edu.eg";
	public static  Integer ADMISSION_HEAD_ID;//888;
	
	public static  String Financial_DEP;//"ghazem@zewailcity.edu.eg";
	public static  Integer Financial_DEP_ID;//888;
	public static  String Financial_DEP_NAME;//"Mrs. Ghada";
	
	
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
}
