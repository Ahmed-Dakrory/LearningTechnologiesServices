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
    private courseReplacement newCourseComfirmation;
    private courseReplacement selectedNewCourseComfirmation;
    

	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	

    private List<courseReplacement> courseChangeComfirmationsForHead; 
    private List<courseReplacement> courseChangeComfirmationsForHeadWaiting; 
    
    //Those parameters handled on the get request

    private int stateNow=-1;
    private int type=-1;
    private int majorId=-1;
    
    private String emailForState;
    
    private boolean theTabWaitingShown;
	@PostConstruct
	public void init()
	{
		
		theTabWaitingShown=false;
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		stateNow=Integer.parseInt(origRequest.getParameter("stateNow"));
		emailForState=origRequest.getParameter("emailForState");
		type=Integer.parseInt(origRequest.getParameter("type"));
		majorId=Integer.parseInt(origRequest.getParameter("majorId"));
		
		
		System.out.println("AhmedDakrory:StateNow: "+stateNow);
		System.out.println("AhmedDakrory:type: "+type);
		System.out.println("AhmedDakrory:majorId: "+majorId);
		System.out.println("AhmedDakrory:emailForState: "+emailForState);
		newCourseComfirmation=new courseReplacement();
		/*
		 * to get the major head list of requirments
		 * as the type of major state the needed flow of the next steps
		 * 
		 * type 1 mean science then type 3 in heads is the corrosponding head in accredition
		 * type 2 mean Engineering then type 2 in heads is the corrosponding head in accredition
		 * 
		 */
		courseChangeComfirmationsForHead=new ArrayList<courseReplacement>();
		courseChangeComfirmationsForHeadWaiting=new ArrayList<courseReplacement>();
		if(stateNow==0){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			if(majorDetails.getHeadOfMajor().getMail().equals(emailForState)){
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStepAndMajorId(majorId, 0);	
			}
		}else if(stateNow==1){
			//this mean the head of accredition
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			if(emailForState.equals(employee.getHeadPersonId().getMail())){
				
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStepAndType(type, 1);
			}
		}else {
			/*
			 * this mean 2 for the dean
			 * 3 for the director of addmission
			 * 4 for the registrar staff
			 */
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			if(emailForState.equals(employee.getHeadPersonId().getMail())){
				theTabWaitingShown=true;
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStep(stateNow);

				courseChangeComfirmationsForHeadWaiting=cccAppServiceImpl.getAll();
			}
			
			
		}
		
		
		
	}
	
	
	public void acceptRequist(int id){
		String EmailToSendTo="";
		newCourseComfirmation=cccAppServiceImpl.getById(id);
		
		studentId = newCourseComfirmation.getStudentId().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		
		
		if(stateNow==0){
			/*
			 * from major head to accredation 
			 */
			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setFormStep(1);
			EmailToSendTo=getEmailByState(1);
			sendEmailForStudent(getNameByState(1),EmailToSendTo,"Please Check your dashboard for a new Graduation Requirement Form");
		}else if(stateNow==1){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setFormStep(2);
			EmailToSendTo=getEmailByState(2);
			sendEmailForStudent(getNameByState(2),EmailToSendTo,"Please Check your dashboard for a new Graduation Requirement Form");
		}else if(stateNow==2){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setFormStep(3);
			EmailToSendTo=getEmailByState(3);
			sendEmailForStudent(getNameByState(3),EmailToSendTo,"Please Check your dashboard for a new Graduation Requirement Form");
		}else if(stateNow==3){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setFormStep(4);
			EmailToSendTo=getEmailByState(4);
			sendEmailForStudent(getNameByState(4),EmailToSendTo,"Please Check your dashboard for a new Graduation Requirement Form");
		}else if(stateNow==4){

			newCourseComfirmation.setAction(1);
			newCourseComfirmation.setFormStep(5);
			sendEmailForStudent(student.getName(),student.getMail(),"Your Graduation Requirement Form Has been Accepted");
		}
		
		cccAppServiceImpl.addcourseReplacement(newCourseComfirmation);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
		

		//Update the list of the courseChangeComifrmation
		courseChangeComfirmationsForHead=new ArrayList<courseReplacement>();
		courseChangeComfirmationsForHeadWaiting=new ArrayList<courseReplacement>();
		if(stateNow==0){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			if(majorDetails.getHeadOfMajor().getMail().equals(emailForState)){
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStepAndMajorId(majorId, 0);	
			}
		}else if(stateNow==1){
			//this mean the head of accredition
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			if(emailForState.equals(employee.getHeadPersonId().getMail())){
				
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStepAndType(type, 1);
			}
		}else {
			/*
			 * this mean 2 for the dean
			 * 3 for the director of addmission
			 * 4 for the registrar staff
			 */
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			if(emailForState.equals(employee.getHeadPersonId().getMail())){
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStep(stateNow);

				courseChangeComfirmationsForHeadWaiting=cccAppServiceImpl.getAll();
				theTabWaitingShown=true;
			}
			
			
		}
		

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("programHeadformDetails.xhtml?stateNow="+stateNow+"&majorId="+majorId+"&type="+type+"&emailForState="+emailForState);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public String getEmailByState(int stateNow) {
		

		if(stateNow==0){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			return majorDetails.getHeadOfMajor().getMail();
			
		}else if(stateNow==1){
			//this mean the head of accredition
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			return employee.getHeadPersonId().getMail();
		}else {
			/*
			 * this mean 2 for the dean
			 * 3 for the director of addmission
			 * 4 for the registrar staff
			 */
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			
			return employee.getHeadPersonId().getMail();
			
		}
	}

	public String getNameByState(int stateNow) {
		

		if(stateNow==0){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			return majorDetails.getHeadOfMajor().getName();
			
		}else if(stateNow==1){
			//this mean the head of accredition
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			return employee.getHeadPersonId().getName();
		}else {
			/*
			 * this mean 2 for the dean
			 * 3 for the director of addmission
			 * 4 for the registrar staff
			 */
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			
			return employee.getHeadPersonId().getName();
			
		}
	}


	public void refuseRequist(int id){
		newCourseComfirmation=cccAppServiceImpl.getById(id);
		newCourseComfirmation.setAction(2);
		studentId = newCourseComfirmation.getStudentId().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		cccAppServiceImpl.addcourseReplacement(newCourseComfirmation);
		

		courseChangeComfirmationsForHead=new ArrayList<courseReplacement>();
		courseChangeComfirmationsForHeadWaiting=new ArrayList<courseReplacement>();
		if(stateNow==0){
			//this mean the head of major
			MajorDTO majorDetails=majorfacade.getById(majorId);
			if(majorDetails.getHeadOfMajor().getMail().equals(emailForState)){
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStepAndMajorId(majorId, 0);	
			}
		}else if(stateNow==1){
			//this mean the head of accredition
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			if(emailForState.equals(employee.getHeadPersonId().getMail())){
				
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStepAndType(type, 1);
			}
		}else {
			/*
			 * this mean 2 for the dean
			 * 3 for the director of addmission
			 * 4 for the registrar staff
			 */
			int typeHead=-1;
			if(type==1){
				typeHead=3;
			}else if(type==2){
				typeHead=2;
			}else{
				typeHead=type;
			}
			Heads employee= headFacades.getByType(typeHead);
			if(emailForState.equals(employee.getHeadPersonId().getMail())){
				courseChangeComfirmationsForHead=cccAppServiceImpl.getAllForStep(stateNow);
				courseChangeComfirmationsForHeadWaiting=cccAppServiceImpl.getAll();
				theTabWaitingShown=true;
			}
			
			
		}
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		sendEmailForStudent(student.getName(),student.getMail(),"Your Graduation Requirement Form Has been Rejected");
		
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("programHeadformDetails.xhtml?stateNow="+stateNow+"&majorId="+majorId+"&type="+type+"&emailForState="+emailForState);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void sendEmailForStudent(String name,String mail, String string) {
		 String from = "learningtechnologies@zewailcity.edu.eg";
	        String pass = "zcltinfo";
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

	        sendFromGMail(from, pass, to, subject, htmlText);
	        
	
	}

	 private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
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
	 
	 
	


	public void goBack() {
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("programHeadformDetailsAll.xhtml?stateNow="+stateNow+"&majorId="+majorId+"&type="+type+"&emailForState="+emailForState);
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
	  		newCourseComfirmation = (courseReplacement) event.getObject();
	  		
	  		studentId = newCourseComfirmation.getStudentId().getId();
			student=studentFacadeImpl.getById(studentId);
						
			student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
			setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
						
					
			
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("headStudentformDetails.xhtml?id="+newCourseComfirmation.getId());
	    			
	    			
	    		
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
	  		newCourseComfirmation = (courseReplacement) event.getObject();
	  		
	  		studentId = newCourseComfirmation.getStudentId().getId();
			student=studentFacadeImpl.getById(studentId);
						
			student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
			setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
						
					
			
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("headStudentformDetails2.xhtml?id="+newCourseComfirmation.getId());
	    			
	    			
	    		
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
		return courseChangeComfirmationsForHead;
	}

	public void setCourseChangeComfirmationsForHead(
			List<courseReplacement> courseChangeComfirmationsForHead) {
		this.courseChangeComfirmationsForHead = courseChangeComfirmationsForHead;
	}


	public courseReplacement getNewCourseComfirmation() {
		return newCourseComfirmation;
	}


	public void setNewCourseComfirmation(courseReplacement newCourseComfirmation) {
		this.newCourseComfirmation = newCourseComfirmation;
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


	public int getStateNow() {
		return stateNow;
	}


	public void setStateNow(int stateNow) {
		this.stateNow = stateNow;
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
		return courseChangeComfirmationsForHeadWaiting;
	}


	public void setCourseChangeComfirmationsForHeadWaiting(List<courseReplacement> courseChangeComfirmationsForHeadWaiting) {
		this.courseChangeComfirmationsForHeadWaiting = courseChangeComfirmationsForHeadWaiting;
	}


	public boolean isTheTabWaitingShown() {
		return theTabWaitingShown;
	}


	public void setTheTabWaitingShown(boolean theTabWaitingShown) {
		this.theTabWaitingShown = theTabWaitingShown;
	}
	
	

}
