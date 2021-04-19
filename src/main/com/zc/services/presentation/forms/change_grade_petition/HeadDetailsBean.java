/**
 * 
 */
package main.com.zc.services.presentation.forms.change_grade_petition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
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
import main.com.zc.services.presentation.forms.change_grade_petition.implementation.change_grade_petitionAppServiceImpl;
import main.com.zc.services.presentation.shared.facade.impl.CouresFacadeImpl;
import main.com.zc.services.presentation.shared.facade.impl.MajorsFacadeImpl;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
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
@ManagedBean(name="HeadDetailsBeanchange_grade_petition")
@SessionScoped
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
    
    @ManagedProperty("#{change_grade_petitionFacadeImpl}")
	private change_grade_petitionAppServiceImpl cccAppServiceImpl;
    
	
    private StudentDTO student;
    private MajorDTO major;
    private int studentId;
    

    
    private int stateOfReq;
    private change_grade_petition selectedNewCourseComfirmation;
    

	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	

    private List<change_grade_petition> pendingFormsList; 
    private List<change_grade_petition> oldFormsFinal;
    private List<change_grade_petition> submittedForms;
    private List<change_grade_petition> auditingFormList;
    
    //Those parameters handled on the get request

    private int stepNow=-1;
    private int majorId=-1;
    private int instructorId=-1;
    
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
		majorId=Integer.parseInt(origRequest.getParameter("majorId"));
		instructorId=Integer.parseInt(origRequest.getParameter("instructorId"));
		
		
	

		
		
		updateListDependsOnLogedAccount();
		
		
		
		
		
	}
	
	
	private void updateListDependsOnLogedAccount() {
		// TODO Auto-generated method stub
		pendingFormsList=new ArrayList<change_grade_petition>();
		oldFormsFinal=new ArrayList<change_grade_petition>();
		auditingFormList=new ArrayList<change_grade_petition>(); 
		submittedForms=new ArrayList<change_grade_petition>(); 
		
		
		if(stepNow==change_grade_petition.STEP_Registerar) {
			//Registerar
			Heads registerar= headFacades.getByType(Heads.REGISTRAR_STAFF);
			if(registerar.getHeadPersonId().getMail().equals(emailForState)){
				pendingFormsList=cccAppServiceImpl.getAllForStep(change_grade_petition.STEP_Registerar);
				submittedForms=cccAppServiceImpl.getAll();
				oldFormsFinal=cccAppServiceImpl.getAllForStep(change_grade_petition.STEP_Finished);
				oldFormsFinal.addAll(cccAppServiceImpl.getAllRefused());
				auditingTabVisibility=false;
				oldTabVisibility=true;
				submittedTabVisibility=true;
			}
			
		}
		
		if(stepNow==change_grade_petition.STEP_ProgramHead){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			if(majorDetails.getHeadOfMajor().getMail().equals(emailForState)){
				pendingFormsList=cccAppServiceImpl.getAllForStepAndMajorId(majorId, change_grade_petition.STEP_ProgramHead);
				auditingTabVisibility=false;
				submittedTabVisibility=false;
				oldTabVisibility=false;
			}
		}

		
		
		
		
		
		
		
		
		if(stepNow==change_grade_petition.STEP_DeanOfStratigicEnrollment) {

			Heads deanOfStratigicEnrollement= headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
			if(emailForState.equals(deanOfStratigicEnrollement.getHeadPersonId().getMail())){
				pendingFormsList=cccAppServiceImpl.getAllForStep(change_grade_petition.STEP_DeanOfStratigicEnrollment);
				submittedForms=cccAppServiceImpl.getAll();
				oldFormsFinal=cccAppServiceImpl.getAllForStep(change_grade_petition.STEP_Finished);
				oldFormsFinal.addAll(cccAppServiceImpl.getAllRefused());
				
				auditingTabVisibility=false;
				submittedTabVisibility=true;
				oldTabVisibility=true;
			}
			
		}

		
		
		if(stepNow==change_grade_petition.STEP_Course_Instructor) {
			
			InstructorDTO ins = getInsDataFacade.getInsByPersonID(instructorId);
			if(emailForState.equals(ins.getMail())){
				pendingFormsList=cccAppServiceImpl.getAllForStepAndInstructorId(instructorId, change_grade_petition.STEP_Course_Instructor);
				submittedForms=cccAppServiceImpl.getAll();
				oldFormsFinal=cccAppServiceImpl.getAllForStep(change_grade_petition.STEP_Finished);
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
		
		
		
		if(stepNow==change_grade_petition.STEP_Course_Instructor){
			
			selectedNewCourseComfirmation.setAction(change_grade_petition.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_Course_Instructor+1);
			selectedNewCourseComfirmation.setCourseInstructorDate(Calendar.getInstance());
			EmailToSendTo=getEmailByState(change_grade_petition.STEP_Course_Instructor+1);
			sendEmailForStudent(getNameByState(change_grade_petition.STEP_Course_Instructor+1),EmailToSendTo,"Please Check your dashboard for a new Change Of Grade Form with id "+String.valueOf(selectedNewCourseComfirmation.getId()));
		
		}else if(stepNow==change_grade_petition.STEP_ProgramHead){
			
			selectedNewCourseComfirmation.setAction(change_grade_petition.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_ProgramHead+1);
			selectedNewCourseComfirmation.setProgramDirectorDate(Calendar.getInstance());
			EmailToSendTo=getEmailByState(change_grade_petition.STEP_ProgramHead+1);
			sendEmailForStudent(getNameByState(change_grade_petition.STEP_ProgramHead+1),EmailToSendTo,"Please Check your dashboard for a new Change Of Grade Form with id "+String.valueOf(selectedNewCourseComfirmation.getId()));
		
		}else if(stepNow==change_grade_petition.STEP_DeanOfStratigicEnrollment){
		
			
			selectedNewCourseComfirmation.setAction(change_grade_petition.STATE_INPROCESS);
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_DeanOfStratigicEnrollment+1);
			selectedNewCourseComfirmation.setDeanDate(Calendar.getInstance());
			EmailToSendTo=getEmailByState(change_grade_petition.STEP_DeanOfStratigicEnrollment+1);
			sendEmailForStudent(getNameByState(change_grade_petition.STEP_Registerar),EmailToSendTo,"Please Check your dashboard for a new Change Of Grade Form with id "+String.valueOf(selectedNewCourseComfirmation.getId()));
		
			
		}else if(stepNow==change_grade_petition.STEP_Registerar){

			selectedNewCourseComfirmation.setAction(change_grade_petition.STATE_ACCEPTED);
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_Registerar+1);
			selectedNewCourseComfirmation.setRegisterationDate(Calendar.getInstance());
			sendEmailForStudent(student.getName(),student.getMail(),"Your Change Of Grade Form Has been Accepted");
		}
		
		cccAppServiceImpl.addchange_grade_petition(selectedNewCourseComfirmation);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		updateListDependsOnLogedAccount();
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("pendingchange_grade_petitionForm.xhtml?stepNow="+stepNow+"&majorId="+majorId+"&emailForState="+emailForState+"&instructorId="+instructorId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public String getEmailByState(int step) {
		

		if(step==change_grade_petition.STEP_ProgramHead) {
			
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(selectedNewCourseComfirmation.getMajorId());
			return majorDetails.getHeadOfMajor().getMail();
		}else if(step==change_grade_petition.STEP_Course_Instructor){
			InstructorDTO ins = getInsDataFacade.getInsByPersonID(instructorId);
			return ins.getMail();
		}else if(step==change_grade_petition.STEP_DeanOfStratigicEnrollment){
			
			Heads employee= headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
			return employee.getHeadPersonId().getMail();
		}else{
			
			Heads employee= headFacades.getByType(Heads.REGISTRAR_STAFF);
			return employee.getHeadPersonId().getMail();
		}
		
		
	}

	public String getNameByState(int step) {
		

		if(step==change_grade_petition.STEP_ProgramHead) {
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(selectedNewCourseComfirmation.getMajorId());
			return majorDetails.getHeadOfMajor().getName();
		}else if(step==change_grade_petition.STEP_Course_Instructor){
			InstructorDTO ins = getInsDataFacade.getInsByPersonID(instructorId);
			return ins.getName();
		}else if(step==change_grade_petition.STEP_DeanOfStratigicEnrollment){
			
			Heads employee= headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
			return employee.getHeadPersonId().getName();
		}else{
			
			Heads employee= headFacades.getByType(Heads.REGISTRAR_STAFF);
			return employee.getHeadPersonId().getName();
		}
	}


	public void refuseRequist(int id){
		selectedNewCourseComfirmation.setAction(change_grade_petition.STATE_REFUSED);
		studentId = selectedNewCourseComfirmation.getStudentId().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		
		if(stepNow==change_grade_petition.STEP_Course_Instructor){
			
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_Course_Instructor+1);
			selectedNewCourseComfirmation.setCourseInstructorDate(Calendar.getInstance());
			
		}else if(stepNow==change_grade_petition.STEP_ProgramHead){
			
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_ProgramHead+1);
			selectedNewCourseComfirmation.setProgramDirectorDate(Calendar.getInstance());
			
		}else if(stepNow==change_grade_petition.STEP_DeanOfStratigicEnrollment){
		
			
			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_DeanOfStratigicEnrollment+1);
			selectedNewCourseComfirmation.setDeanDate(Calendar.getInstance());
			
			
		}else if(stepNow==change_grade_petition.STEP_Registerar){

			selectedNewCourseComfirmation.setFormStep(change_grade_petition.STEP_Registerar+1);
			selectedNewCourseComfirmation.setRegisterationDate(Calendar.getInstance());
		}

		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		cccAppServiceImpl.addchange_grade_petition(selectedNewCourseComfirmation);
		
		updateListDependsOnLogedAccount();
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		sendEmailForStudent(student.getName(),student.getMail(),"Change Of Grade Form Has been Rejected with id "+String.valueOf(selectedNewCourseComfirmation.getId()));
		
		if(stepNow!=change_grade_petition.STEP_Registerar) {
			//Registerar
			Heads registerar= headFacades.getByType(Heads.REGISTRAR_STAFF);
			sendEmailForStudent(registerar.getHeadPersonId().getName(),registerar.getHeadPersonId().getMail(),"Change Of Grade Form with id "+String.valueOf(selectedNewCourseComfirmation.getId())+" Has been Rejected");
		}
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("pendingchange_grade_petitionForm.xhtml?stepNow="+stepNow+"&majorId="+majorId+"&emailForState="+emailForState+"&instructorId="+instructorId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void sendEmailForStudent(String name,String mail, String string) {
		 String from = "learningtechnologies@zewailcity.edu.eg";
	        String pass = "DELF-651984@dr";
	        String[] to = {mail }; // list of recipient email addresses 
	        String subject = "Change Of Grade Form New Message";
	        String htmlText = "<div style=\"width:700px;margin:0\" auto;font:normal=\"\" 13px=\"\" 30px=\"\" segoe,=\"\" segoe=\"\" ui,=\"\" dejavu=\"\" sans,=\"\" trebuchet=\"\" ms,=\"\" verdana,=\"\" sans-serif=\"\" !important;=\"\">\n" + 
	        		"					<ul style=\"margin:0;padding:0;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin:0;\">\n" + 
	        		"						<ul style=\"margin:0;padding:0;width:700px;margin-top:18px;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:260px;padding:0;\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/zewailLogo.png.xhtml?ln=Copy%20of%20images\" alt=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\" style=\"\n" + 
	        		"    width: 77px;\n" + 
	        		"\"></li>\n" + 
	        		"					<li style=\"list-style:none;float:right;width:121px;margin-right: 55px;padding:0;\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/welocome_logo.png.xhtml?ln=Copy%20of%20images\" alt=\"Center\" for=\"\" learning=\"\" technologies=\"\" style=\"margin-top:4px;width: 170px;\"></li>\n" + 
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
	        		"					<a href='https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml' style=\"color:#237cd8\">Login Now</a><br><br><br>\n" + 
	        		"					<br><br>\n" + 
	        		"					<span style=\"color:#676767\">Thank you, </span><br><br>\n" + 
	        		"					<span style=\"color:#676767\">Center for Learning Technologies</span>\n" + 
	        		"					</div>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;\">\n" + 
	        		"					<ul style=\"margin:0;padding:0;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:134px;margin:0;padding:18px\" 36px=\"\" !important;color:#717070;=\"\">\n" + 
	        		"					<a href=\"http://www.zclt.info/\" title=\"Center\" for=\"\" learning=\"\" technologies=\"\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/welocome_logo.png.xhtml?ln=Copy%20of%20images\" alt=\"Center\" for=\"\" learning=\"\" technologies=\"\" style=\"\n" + 
	        		"    width: 139px;\n" + 
	        		"\"></a><br>\n" + 
	        		"					<span style=\"color:#404040;font-size:11px;\">Giving Fuel to Innovation</span>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:right;padding: 24px;\" 36px=\"\" !important;color:#717070;=\"\">\n" + 
	        		"					<a href=\"http://www.zewailcity.edu.eg/\" title=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/zewailLogo.png.xhtml?ln=Copy%20of%20images\" alt=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\" style=\"\n" + 
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
			            try {
			            transport.connect(host, from, pass);
			            transport.sendMessage(message, message.getAllRecipients());
			            transport.close();
			            }catch(Error e) {
			            	
			            }catch(Exception e) {
			            	
			            }
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
	 
	 
	 public String getMajorType(int type) {
			switch(type){
			case 1:
				return "Science";
			case 2:
				return "Engineering";
				
			}
			return "";
		}



	public void goBack() {
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("pendingchange_grade_petitionForm.xhtml?stepNow="+stepNow+"&majorId="+majorId+"&emailForState="+emailForState+"&instructorId="+instructorId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	

	
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		selectedNewCourseComfirmation = (change_grade_petition) event.getObject();
	  		
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
	  		selectedNewCourseComfirmation = (change_grade_petition) event.getObject();
	  		
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


	public change_grade_petitionAppServiceImpl getCccAppServiceImpl() {
		return cccAppServiceImpl;
	}
	
	public void setCccAppServiceImpl(change_grade_petitionAppServiceImpl cccAppServiceImpl) {
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


	

	public List<change_grade_petition> getCourseChangeComfirmationsForHead() {
		return pendingFormsList;
	}

	public void setCourseChangeComfirmationsForHead(
			List<change_grade_petition> pendingFormsList) {
		this.pendingFormsList = pendingFormsList;
	}


	public change_grade_petition getNewCourseComfirmation() {
		return selectedNewCourseComfirmation;
	}


	public void setNewCourseComfirmation(change_grade_petition selectedNewCourseComfirmation) {
		this.selectedNewCourseComfirmation = selectedNewCourseComfirmation;
	}


	public change_grade_petition getSelectedNewCourseComfirmation() {
		return selectedNewCourseComfirmation;
	}


	public void setSelectedNewCourseComfirmation(change_grade_petition selectedNewCourseComfirmation) {
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


	public List<change_grade_petition> getCourseChangeComfirmationsForHeadWaiting() {
		return oldFormsFinal;
	}


	public void setCourseChangeComfirmationsForHeadWaiting(List<change_grade_petition> oldFormsFinal) {
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


	public List<change_grade_petition> getPendingFormsList() {
		return pendingFormsList;
	}


	public void setPendingFormsList(List<change_grade_petition> pendingFormsList) {
		this.pendingFormsList = pendingFormsList;
	}


	public List<change_grade_petition> getOldFormsFinal() {
		return oldFormsFinal;
	}


	public void setOldFormsFinal(List<change_grade_petition> oldFormsFinal) {
		this.oldFormsFinal = oldFormsFinal;
	}


	public List<change_grade_petition> getSubmittedForms() {
		return submittedForms;
	}


	public void setSubmittedForms(List<change_grade_petition> submittedForms) {
		this.submittedForms = submittedForms;
	}


	public List<change_grade_petition> getAuditingFormList() {
		return auditingFormList;
	}


	public void setAuditingFormList(List<change_grade_petition> auditingFormList) {
		this.auditingFormList = auditingFormList;
	}
	
	

	
}
