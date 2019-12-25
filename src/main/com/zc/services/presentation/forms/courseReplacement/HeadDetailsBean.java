/**
 * 
 */
package main.com.zc.services.presentation.forms.courseReplacement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;

import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.service.repository.heads.HeadsAppServiceImpl;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.forms.courseReplacement.implementation.courseReplacementAppServiceImpl;
import main.com.zc.services.presentation.shared.facade.impl.CouresFacadeImpl;
import main.com.zc.services.presentation.shared.facade.impl.MajorsFacadeImpl;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.impl.StudentFacadeImpl;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean(name="HeadDetailsBeanChangeCourseComfirmation")
@ApplicationScoped
public class HeadDetailsBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
    
    @ManagedProperty("#{IStudentFacade}")
    private StudentFacadeImpl studentFacadeImpl;
    
    @ManagedProperty("#{IStudentProfileFacade}")
	private StudentProfileFacadeImpl facade;
    
    @ManagedProperty("#{IMajorsFacade}")
	private MajorsFacadeImpl majorfacade;
    
    @ManagedProperty("#{ICouresFacade}")
	private CouresFacadeImpl coursesfacade;
    
    @ManagedProperty("#{courseReplacementFacadeImpl}")
	private courseReplacementAppServiceImpl cccAppServiceImpl;
    
	
    private StudentDTO student;
    private MajorDTO major;
    private int studentId;
    

    
    private int stateOfReq;
    private int courseTakenId;
    private int courseRelativeId;
    private courseReplacement selectedNewCourseComfirmation;
    

	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	

    private List<courseReplacement> pendingFormsList; 
    private List<courseReplacement> oldFormsFinal;
    private List<courseReplacement> submittedForms;
    private List<courseReplacement> auditingFormList;
    
    //Those parameters handled on the get request

    private int stepNow=-1;
    private int type=-1;
    private int majorId=-1;
    
    private String emailForState;

    private boolean auditingTabVisibility;
    private boolean submittedTabVisibility;
    private boolean oldTabVisibility;

    public static int PENDING_PAGE=0;
    public static int AUDITING_PAGE=1;
    public static int SUBMITTED_PAGE=2;
    public static int OLD_PAGE=3;
    
	@PostConstruct
	public void init()
	{

		auditingTabVisibility=false;
		submittedTabVisibility=false;
		oldTabVisibility=false;
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		stepNow=Integer.parseInt(origRequest.getParameter("stepNow"));
		emailForState=origRequest.getParameter("emailForState");
		type=Integer.parseInt(origRequest.getParameter("type"));
		majorId=Integer.parseInt(origRequest.getParameter("majorId"));
		
		
	

		
		
		updateListDependsOnLogedAccount();
		
		
		
		
		
	}
	
	
	private void updateListDependsOnLogedAccount() {
		// TODO Auto-generated method stub
		pendingFormsList=new ArrayList<courseReplacement>();
		oldFormsFinal=new ArrayList<courseReplacement>();
		auditingFormList=new ArrayList<courseReplacement>(); 
		submittedForms=new ArrayList<courseReplacement>(); 
		
		
		if(stepNow==courseReplacement.STEP_Registerar||stepNow==courseReplacement.STEP_AUDITING) {
			//Registerar
			Heads registerar= headFacades.getByType(Heads.REGISTRAR_STAFF);
			if(registerar.getHeadPersonId().getMail().equals(emailForState)){
				auditingFormList=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_AUDITING);
				pendingFormsList=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_Registerar);
				submittedForms=cccAppServiceImpl.getAll();
				oldFormsFinal=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_Finished);
				oldFormsFinal.addAll(cccAppServiceImpl.getAllRefused());
				auditingTabVisibility=true;
				oldTabVisibility=true;
				submittedTabVisibility=true;
			}
			
		}
		
		if(stepNow==courseReplacement.STEP_MajorHead){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			if(majorDetails.getHeadOfMajor().getMail().equals(emailForState)){
				pendingFormsList=cccAppServiceImpl.getAllForStepAndMajorId(majorId, courseReplacement.STEP_MajorHead);
				auditingTabVisibility=false;
				submittedTabVisibility=false;
				oldTabVisibility=false;
			}
		}

		
		
		
		
		
		
		if(stepNow==courseReplacement.STEP_DirectorOfAccredition){
			if(type==Heads.VICE_DIRECTOR_FOR_ENGINEERING) {
				Heads employee= headFacades.getByType(Heads.VICE_DIRECTOR_FOR_ENGINEERING);
				if(emailForState.equals(employee.getHeadPersonId().getMail())){
					
					pendingFormsList=cccAppServiceImpl.getAllForStepAndType(courseReplacement.TYPE_ENGINEERING, courseReplacement.STEP_DirectorOfAccredition);
					auditingTabVisibility=false;
					submittedTabVisibility=false;
					oldTabVisibility=false;
				}
			}else if(type==Heads.VICE_DIRECTOR_FOR_SCIENCE) {
				Heads employee= headFacades.getByType(Heads.VICE_DIRECTOR_FOR_SCIENCE);
				if(emailForState.equals(employee.getHeadPersonId().getMail())){
					
					pendingFormsList=cccAppServiceImpl.getAllForStepAndType(courseReplacement.TYPE_SCIENCE, courseReplacement.STEP_DirectorOfAccredition);
					auditingTabVisibility=false;
					submittedTabVisibility=false;
					oldTabVisibility=false;
				}
			}
			
		}
		
		
		
		
		
		
		if(stepNow==courseReplacement.STEP_DeanOfStratigicEnrollment) {
			
			Heads deanOfStratigicEnrollement= headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
			if(emailForState.equals(deanOfStratigicEnrollement.getHeadPersonId().getMail())){
				pendingFormsList=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_DeanOfStratigicEnrollment);
				submittedForms=cccAppServiceImpl.getAll();
				oldFormsFinal=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_Finished);
				oldFormsFinal.addAll(cccAppServiceImpl.getAllRefused());
				
				auditingTabVisibility=false;
				submittedTabVisibility=true;
				oldTabVisibility=true;
			}
			
		}

		
		
		if(stepNow==courseReplacement.STEP_AssociateDean) {
			
			Heads deanOfStratigicEnrollement= headFacades.getByType(Heads.ASSOCIATE_DEAN);
			if(emailForState.equals(deanOfStratigicEnrollement.getHeadPersonId().getMail())){
				pendingFormsList=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_AssociateDean);
				submittedForms=cccAppServiceImpl.getAll();
				oldFormsFinal=cccAppServiceImpl.getAllForStep(courseReplacement.STEP_Finished);
				oldFormsFinal.addAll(cccAppServiceImpl.getAllRefused());
				
				auditingTabVisibility=false;
				submittedTabVisibility=true;
				oldTabVisibility=true;
			}
			
		}


	}


	public void acceptRequist(int id){
		String EmailToSendTo="";
		
		studentId = selectedNewCourseComfirmation.getStudentId().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		
		
		if(stepNow==courseReplacement.STEP_AUDITING){
			
			selectedNewCourseComfirmation.setAction(courseReplacement.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(courseReplacement.STEP_AUDITING+1);
			EmailToSendTo=getEmailByState(courseReplacement.STEP_AUDITING+1);
			sendEmailForStudent(getNameByState(courseReplacement.STEP_AUDITING+1),EmailToSendTo,"Please Check your dashboard for a new course Replacement Form");
		
		}else if(stepNow==courseReplacement.STEP_MajorHead){
			
			selectedNewCourseComfirmation.setAction(courseReplacement.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(courseReplacement.STEP_MajorHead+1);
			EmailToSendTo=getEmailByState(courseReplacement.STEP_MajorHead+1);
			sendEmailForStudent(getNameByState(courseReplacement.STEP_MajorHead+1),EmailToSendTo,"Please Check your dashboard for a new course Replacement Form");
		
		}else if(stepNow==courseReplacement.STEP_DirectorOfAccredition){
			
			selectedNewCourseComfirmation.setAction(courseReplacement.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(courseReplacement.STEP_DirectorOfAccredition+1);
			EmailToSendTo=getEmailByState(courseReplacement.STEP_DirectorOfAccredition+1);
			sendEmailForStudent(getNameByState(courseReplacement.STEP_DirectorOfAccredition+1),EmailToSendTo,"Please Check your dashboard for a new course Replacement Form");
		
		}else if(stepNow==courseReplacement.STEP_DeanOfStratigicEnrollment){
			
			selectedNewCourseComfirmation.setAction(courseReplacement.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(courseReplacement.STEP_DeanOfStratigicEnrollment+1);
			EmailToSendTo=getEmailByState(courseReplacement.STEP_DeanOfStratigicEnrollment+1);
			sendEmailForStudent(getNameByState(courseReplacement.STEP_DeanOfStratigicEnrollment+1),EmailToSendTo,"Please Check your dashboard for a new course Replacement Form");
		
		}else if(stepNow==courseReplacement.STEP_AssociateDean){
			
			selectedNewCourseComfirmation.setAction(courseReplacement.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(courseReplacement.STEP_Registerar);
			EmailToSendTo=getEmailByState(courseReplacement.STEP_Registerar);
			sendEmailForStudent(getNameByState(courseReplacement.STEP_Registerar),EmailToSendTo,"Please Check your dashboard for a new course Replacement Form");
		
		}else if(stepNow==courseReplacement.STEP_Registerar){

			selectedNewCourseComfirmation.setAction(courseReplacement.STATE_ACCEPTED);
			selectedNewCourseComfirmation.setFormStep(courseReplacement.STEP_Finished);
			sendEmailForStudent(student.getName(),student.getMail(),"Your Graduation Requirement Form Has been Accepted");
		}
		
		cccAppServiceImpl.addcourseReplacement(selectedNewCourseComfirmation);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		updateListDependsOnLogedAccount();
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("pendingCourseReplacementForm.xhtml?stepNow="+stepNow+"&majorId="+majorId+"&type="+type+"&emailForState="+emailForState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public String getEmailByState(int step) {
		

		if(step==courseReplacement.STEP_MajorHead) {
			
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(selectedNewCourseComfirmation.getMajorId());
			return majorDetails.getHeadOfMajor().getMail();
		}else if(step==courseReplacement.STEP_AssociateDean){
			
			Heads employee= headFacades.getByType(Heads.ASSOCIATE_DEAN);
			return employee.getHeadPersonId().getMail();
		}else if(step==courseReplacement.STEP_AUDITING){
			
			Heads employee= headFacades.getByType(Heads.REGISTRAR_STAFF);
			return employee.getHeadPersonId().getMail();
		}else if(step==courseReplacement.STEP_DeanOfStratigicEnrollment){
			
			Heads employee= headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
			return employee.getHeadPersonId().getMail();
		}else if(step==courseReplacement.STEP_Registerar){
			
			Heads employee= headFacades.getByType(Heads.REGISTRAR_STAFF);
			return employee.getHeadPersonId().getMail();
		}else {
			if(type==courseReplacement.TYPE_ENGINEERING) {
			Heads employee= headFacades.getByType(Heads.VICE_DIRECTOR_FOR_ENGINEERING);
			return employee.getHeadPersonId().getMail();
			}else {
				Heads employee= headFacades.getByType(Heads.VICE_DIRECTOR_FOR_SCIENCE);
				return employee.getHeadPersonId().getMail();
			}
		}
		
		
	}

	public String getNameByState(int step) {
		

		if(step==courseReplacement.STEP_MajorHead) {
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(selectedNewCourseComfirmation.getMajorId());
			return majorDetails.getHeadOfMajor().getName();
		}else if(step==courseReplacement.STEP_AssociateDean){
			
			Heads employee= headFacades.getByType(Heads.ASSOCIATE_DEAN);
			return employee.getHeadPersonId().getName();
		}else if(step==courseReplacement.STEP_AUDITING){
			
			Heads employee= headFacades.getByType(Heads.REGISTRAR_STAFF);
			return employee.getHeadPersonId().getName();
		}else if(step==courseReplacement.STEP_DeanOfStratigicEnrollment){
			
			Heads employee= headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
			return employee.getHeadPersonId().getName();
		}else if(step==courseReplacement.STEP_Registerar){
			
			Heads employee= headFacades.getByType(Heads.REGISTRAR_STAFF);
			return employee.getHeadPersonId().getName();
		}else {
			if(type==courseReplacement.TYPE_ENGINEERING) {
			Heads employee= headFacades.getByType(Heads.VICE_DIRECTOR_FOR_ENGINEERING);
			return employee.getHeadPersonId().getName();
			}else {
				Heads employee= headFacades.getByType(Heads.VICE_DIRECTOR_FOR_SCIENCE);
				return employee.getHeadPersonId().getName();
			}
		}
	}


	public void refuseRequist(int id){
		selectedNewCourseComfirmation.setAction(courseReplacement.STATE_REFUSED);
		studentId = selectedNewCourseComfirmation.getStudentId().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		cccAppServiceImpl.addcourseReplacement(selectedNewCourseComfirmation);
		
		updateListDependsOnLogedAccount();
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		sendEmailForStudent(student.getName(),student.getMail(),"Course Replacement Form Has been Rejected");
		
		if(stepNow!=courseReplacement.STEP_AUDITING&&stepNow!=courseReplacement.STEP_Registerar) {
			//Registerar
			Heads registerar= headFacades.getByType(Heads.REGISTRAR_STAFF);
			sendEmailForStudent(registerar.getHeadPersonId().getName(),registerar.getHeadPersonId().getMail(),"Course Replacement Form Has been Rejected");
		}
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("pendingCourseReplacementForm.xhtml?stepNow="+stepNow+"&majorId="+majorId+"&type="+type+"&emailForState="+emailForState);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void sendEmailForStudent(String name,String mail, String string) {
		 String from = "learningtechnologies@zewailcity.edu.eg";
	        String pass = "DELF-651984@dr";
	        String[] to = {mail }; // list of recipient email addresses 
	        String subject = "Graduation Requirements Form New Message";
	        String htmlText = "<div style=\"width:700px;margin:0\" auto;font:normal=\"\" 13px=\"\" 30px=\"\" segoe,=\"\" segoe=\"\" ui,=\"\" dejavu=\"\" sans,=\"\" trebuchet=\"\" ms,=\"\" verdana,=\"\" sans-serif=\"\" !important;=\"\">\n" + 
	        		"					<ul style=\"margin:0;padding:0;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin:0;\">\n" + 
	        		"						<ul style=\"margin:0;padding:0;width:700px;margin-top:18px;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:260px;padding:0;\"><img src=\"http://lts.zclt.info/LearningTechnologiesServices/javax.faces.resource/zewailLogo.png.xhtml?ln=Copy%20of%20images\" alt=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\" style=\"\n" + 
	        		"    width: 77px;\n" + 
	        		"\"></li>\n" + 
	        		"					<li style=\"list-style:none;float:right;width:121px;margin-right: 55px;padding:0;\"><img src=\"http://lts.zclt.info/LearningTechnologiesServices/javax.faces.resource/welocome_logo.png.xhtml?ln=Copy%20of%20images\" alt=\"Center\" for=\"\" learning=\"\" technologies=\"\" style=\"margin-top:4px;width: 170px;\"></li>\n" + 
	        		"					</ul>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom: 25px;background:#f1f2f2;\" 0=\"\" 24px=\"\" 0;padding:1px=\"\" 0;=\"\">&nbsp;</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;\">\n" + 
	        		"					<h2 style=\"margin:0;padding:0;color:#404040\" !important;=\"\">Learning Technologies Services</h2>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;\">\n" + 
	        		"					<div style=\"padding:24px\" 36px;color:#676767=\"\" !important;=\"\">\n" + 
	        		"					<span style=\"color:#676767\">Dear \n" + 
	        		"					"+name+"\n" + 
	        		"					,</span><br><br><br>\n" + 
	        		"					<span style=\"color:#676767\">"+string+"</span><br><br><br>\n" + 
	        		"					<br><br>\n" + 
	        		"					<span style=\"color:#676767\">Thank you, </span><br><br>\n" + 
	        		"					<span style=\"color:#676767\">Center for Learning Technologies</span>\n" + 
	        		"					</div>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;\">\n" + 
	        		"					<ul style=\"margin:0;padding:0;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:134px;margin:0;padding:18px\" 36px=\"\" !important;color:#717070;=\"\">\n" + 
	        		"					<a href=\"http://www.zclt.info/\" title=\"Center\" for=\"\" learning=\"\" technologies=\"\"><img src=\"http://lts.zclt.info/LearningTechnologiesServices/javax.faces.resource/welocome_logo.png.xhtml?ln=Copy%20of%20images\" alt=\"Center\" for=\"\" learning=\"\" technologies=\"\" style=\"\n" + 
	        		"    width: 139px;\n" + 
	        		"\"></a><br>\n" + 
	        		"					<span style=\"color:#404040;font-size:11px;\">Giving Fuel to Innovation</span>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:right;padding: 24px;\" 36px=\"\" !important;color:#717070;=\"\">\n" + 
	        		"					<a href=\"http://www.zewailcity.edu.eg/\" title=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\"><img src=\"http://lts.zclt.info/LearningTechnologiesServices/javax.faces.resource/zewailLogo.png.xhtml?ln=Copy%20of%20images\" alt=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\" style=\"\n" + 
	        		"    width: 66px;\n" + 
	        		"\"></a>\n" + 
	        		"					</li>\n" + 
	        		"					</ul>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;\">\n" + 
	        		"					<div style=\"padding:8px\" 16px;color:#a1a0a0;font-size:11px;line-height:20px;=\"\">\n" + 
	        		"					 <br><b><span style=\"color:#a1a0a0;font-size:11px;\">Follow us:</span></b><a href=\"https://www.facebook.com/learning.technologies.zewailcity\" title=\"ZC\" lt=\"\" facebook=\"\" style=\"\n" + 
	        		"\"><img src=\"https://static.xx.fbcdn.net/rsrc.php/yo/r/iRmz9lCMBD2.ico\" alt=\"ZC\" lt=\"\" facebook=\"\" style=\"vertical-align:middle;width: 21px;margin-left: 13px;\"></a>\n" + 
	        		"					  <a href=\"https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A\" title=\"ZC\" lt=\"\" youtube=\"\"><img src=\"https://s.ytimg.com/yts/img/favicon_144-vfliLAfaB.png\" alt=\"ZC\" lt=\"\" youtube=\"\" style=\"vertical-align:middle;width: 23px;margin-left: 14px;\"></a>\n" + 
	        		"					</div> </li> </ul> </div>";

	        

	        System.out.println("Email Sent To: "+name+" With Mail: "+mail);
	        sendFromGMail(from, pass, to, subject, htmlText);
	        
	
	}

	 private static void sendFromGMail(final String from, final String pass, final String[] to, final String subject, final String body) {
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
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
			        }
			        catch (MessagingException me) {
			            me.printStackTrace();
			        }
			}
		}).start();
	       
	    }
	 
	 
	


	public void goBack() {
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("pendingCourseReplacementForm.xhtml?stepNow="+stepNow+"&majorId="+majorId+"&type="+type+"&emailForState="+emailForState);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public String getCourseCountAsString(int countAs) {
		switch(countAs){
		case 0:
			return "Major Req";
		case 1:
			return "Humanities";
		case 2:
			return "Major Elective";
		case 3:
			return "Collateral Req";
			
		}
		return "";
	}
	
	public String getMajorType(int type) {
		switch(type){
		case 1:
			return "Science";
		case 2:
			return "Engineering";
			
		}
		return "";
	}

	
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		selectedNewCourseComfirmation = (courseReplacement) event.getObject();
	  		
	  		studentId = selectedNewCourseComfirmation.getStudentId().getId();
			student=studentFacadeImpl.getById(studentId);
						
			student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
			setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
						
					
			
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("headStudentformDetails.xhtml");
	    			
	    			
	    		
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void onRowSelect1(SelectEvent event) {  
	  	try {
	  		selectedNewCourseComfirmation = (courseReplacement) event.getObject();
	  		
	  		studentId = selectedNewCourseComfirmation.getStudentId().getId();
			student=studentFacadeImpl.getById(studentId);
						
			student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
			setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
						
					
			
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("headStudentformDetailsNotEdit.xhtml?id="+selectedNewCourseComfirmation.getId());
	    			
	    			
	    		
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getFriendlySubmissionDate(Calendar calender) {
		if(calender!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(calender.getTime());
			    return strDate;
			}
			
			else return "";
	}
	
	
	
	
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public StudentFacadeImpl getStudentFacadeImpl() {
		return studentFacadeImpl;
	}
	public void setStudentFacadeImpl(StudentFacadeImpl studentFacadeImpl) {
		this.studentFacadeImpl = studentFacadeImpl;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public StudentProfileFacadeImpl getFacade() {
		return facade;
	}
	
	public void setFacade(StudentProfileFacadeImpl facade) {
		this.facade = facade;
	}

	public MajorsFacadeImpl getMajorfacade() {
		return majorfacade;
	}

	public void setMajorfacade(MajorsFacadeImpl majorfacade) {
		this.majorfacade = majorfacade;
	}

	public CouresFacadeImpl getCoursesfacade() {
		return coursesfacade;
	}

	public void setCoursesfacade(CouresFacadeImpl coursesfacade) {
		this.coursesfacade = coursesfacade;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}


	public courseReplacementAppServiceImpl getCccAppServiceImpl() {
		return cccAppServiceImpl;
	}
	
	public void setCccAppServiceImpl(courseReplacementAppServiceImpl cccAppServiceImpl) {
		this.cccAppServiceImpl = cccAppServiceImpl;
	}


	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}


	public int getStateOfReq() {
		return stateOfReq;
	}


	public void setStateOfReq(int stateOfReq) {
		this.stateOfReq = stateOfReq;
	}


	public int getCourseTakenId() {
		return courseTakenId;
	}


	public void setCourseTakenId(int courseTakenId) {
		this.courseTakenId = courseTakenId;
	}


	public int getCourseRelativeId() {
		return courseRelativeId;
	}


	public void setCourseRelativeId(int courseRelativeId) {
		this.courseRelativeId = courseRelativeId;
	}

	public List<courseReplacement> getCourseChangeComfirmationsForHead() {
		return pendingFormsList;
	}

	public void setCourseChangeComfirmationsForHead(
			List<courseReplacement> pendingFormsList) {
		this.pendingFormsList = pendingFormsList;
	}


	public courseReplacement getNewCourseComfirmation() {
		return selectedNewCourseComfirmation;
	}


	public void setNewCourseComfirmation(courseReplacement selectedNewCourseComfirmation) {
		this.selectedNewCourseComfirmation = selectedNewCourseComfirmation;
	}


	public courseReplacement getSelectedNewCourseComfirmation() {
		return selectedNewCourseComfirmation;
	}


	public void setSelectedNewCourseComfirmation(courseReplacement selectedNewCourseComfirmation) {
		this.selectedNewCourseComfirmation = selectedNewCourseComfirmation;
	}


	public HeadsAppServiceImpl getHeadFacades() {
		return headFacades;
	}


	public void setHeadFacades(HeadsAppServiceImpl headFacades) {
		this.headFacades = headFacades;
	}


	public int getStepNow() {
		return stepNow;
	}


	public void setStepNow(int stepNow) {
		this.stepNow = stepNow;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public int getMajorId() {
		return majorId;
	}


	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}


	public String getEmailForState() {
		return emailForState;
	}


	public void setEmailForState(String emailForState) {
		this.emailForState = emailForState;
	}


	public List<courseReplacement> getCourseChangeComfirmationsForHeadWaiting() {
		return oldFormsFinal;
	}


	public void setCourseChangeComfirmationsForHeadWaiting(List<courseReplacement> oldFormsFinal) {
		this.oldFormsFinal = oldFormsFinal;
	}


	




	public boolean isAuditingTabVisibility() {
		return auditingTabVisibility;
	}


	public void setAuditingTabVisibility(boolean auditingTabVisibility) {
		this.auditingTabVisibility = auditingTabVisibility;
	}


	public boolean isSubmittedTabVisibility() {
		return submittedTabVisibility;
	}


	public void setSubmittedTabVisibility(boolean submittedTabVisibility) {
		this.submittedTabVisibility = submittedTabVisibility;
	}


	public boolean isOldTabVisibility() {
		return oldTabVisibility;
	}


	public void setOldTabVisibility(boolean oldTabVisibility) {
		this.oldTabVisibility = oldTabVisibility;
	}


	public List<courseReplacement> getPendingFormsList() {
		return pendingFormsList;
	}


	public void setPendingFormsList(List<courseReplacement> pendingFormsList) {
		this.pendingFormsList = pendingFormsList;
	}


	public List<courseReplacement> getOldFormsFinal() {
		return oldFormsFinal;
	}


	public void setOldFormsFinal(List<courseReplacement> oldFormsFinal) {
		this.oldFormsFinal = oldFormsFinal;
	}


	public List<courseReplacement> getSubmittedForms() {
		return submittedForms;
	}


	public void setSubmittedForms(List<courseReplacement> submittedForms) {
		this.submittedForms = submittedForms;
	}


	public List<courseReplacement> getAuditingFormList() {
		return auditingFormList;
	}


	public void setAuditingFormList(List<courseReplacement> auditingFormList) {
		this.auditingFormList = auditingFormList;
	}
	
	

	
}
