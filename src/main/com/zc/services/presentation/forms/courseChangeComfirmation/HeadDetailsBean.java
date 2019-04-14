/**
 * 
 */
package main.com.zc.services.presentation.forms.courseChangeComfirmation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;

import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.service.repository.heads.HeadsAppServiceImpl;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.implementation.CCCAppServiceImpl;
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
    
    @ManagedProperty("#{CCCFacadeImpl}")
	private CCCAppServiceImpl cccAppServiceImpl;
    
	
    private StudentDTO student;
    private MajorDTO major;
    private int studentId;
    

    
    private int stateOfReq;
    private int courseTakenId;
    private int courseRelativeId;
    private CCC newCourseComfirmation;
    private CCC selectedNewCourseComfirmation;
    

	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	

    private List<CCC> courseChangeComfirmationsForHead; 
    private List<CCC> courseChangeComfirmationsForHeadWaiting; 
    
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
		newCourseComfirmation=new CCC();
		/*
		 * to get the major head list of requirments
		 * as the type of major state the needed flow of the next steps
		 * 
		 * type 1 mean science then type 3 in heads is the corrosponding head in accredition
		 * type 2 mean Engineering then type 2 in heads is the corrosponding head in accredition
		 * 
		 */
		courseChangeComfirmationsForHead=new ArrayList<CCC>();
		courseChangeComfirmationsForHeadWaiting=new ArrayList<CCC>();
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
		newCourseComfirmation=cccAppServiceImpl.getById(id);
		if(stateNow==0){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setStateStep(1);
		}else if(stateNow==1){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setStateStep(2);
		}else if(stateNow==2){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setStateStep(3);
		}else if(stateNow==3){

			newCourseComfirmation.setAction(0);
			newCourseComfirmation.setStateStep(4);
		}else if(stateNow==4){

			newCourseComfirmation.setAction(1);
			newCourseComfirmation.setStateStep(5);
		}
		studentId = newCourseComfirmation.getStudent().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		cccAppServiceImpl.addCCC(newCourseComfirmation);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Action Taken");

		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		origRequest.getRequestURL();
		

		courseChangeComfirmationsForHead=new ArrayList<CCC>();
		courseChangeComfirmationsForHeadWaiting=new ArrayList<CCC>();
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
	
	public void refuseRequist(int id){
		newCourseComfirmation=cccAppServiceImpl.getById(id);
		newCourseComfirmation.setAction(2);
		studentId = newCourseComfirmation.getStudent().getId();
		student=studentFacadeImpl.getById(studentId);
		student.setStudentProfileDTO(getFacade().getCurrentPRofileByStudentID(getStudentId()));
		setMajor(majorfacade.getById(getStudent().getStudentProfileDTO().getMajor().getId()));
		
		cccAppServiceImpl.addCCC(newCourseComfirmation);
		

		courseChangeComfirmationsForHead=new ArrayList<CCC>();
		courseChangeComfirmationsForHeadWaiting=new ArrayList<CCC>();
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
	  		newCourseComfirmation = (CCC) event.getObject();
	  		
	  		studentId = newCourseComfirmation.getStudent().getId();
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


	public CCCAppServiceImpl getCccAppServiceImpl() {
		return cccAppServiceImpl;
	}
	
	public void setCccAppServiceImpl(CCCAppServiceImpl cccAppServiceImpl) {
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

	public List<CCC> getCourseChangeComfirmationsForHead() {
		return courseChangeComfirmationsForHead;
	}

	public void setCourseChangeComfirmationsForHead(
			List<CCC> courseChangeComfirmationsForHead) {
		this.courseChangeComfirmationsForHead = courseChangeComfirmationsForHead;
	}


	public CCC getNewCourseComfirmation() {
		return newCourseComfirmation;
	}


	public void setNewCourseComfirmation(CCC newCourseComfirmation) {
		this.newCourseComfirmation = newCourseComfirmation;
	}


	public CCC getSelectedNewCourseComfirmation() {
		return selectedNewCourseComfirmation;
	}


	public void setSelectedNewCourseComfirmation(CCC selectedNewCourseComfirmation) {
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


	public List<CCC> getCourseChangeComfirmationsForHeadWaiting() {
		return courseChangeComfirmationsForHeadWaiting;
	}


	public void setCourseChangeComfirmationsForHeadWaiting(List<CCC> courseChangeComfirmationsForHeadWaiting) {
		this.courseChangeComfirmationsForHeadWaiting = courseChangeComfirmationsForHeadWaiting;
	}


	public boolean isTheTabWaitingShown() {
		return theTabWaitingShown;
	}


	public void setTheTabWaitingShown(boolean theTabWaitingShown) {
		this.theTabWaitingShown = theTabWaitingShown;
	}
	
	

}
