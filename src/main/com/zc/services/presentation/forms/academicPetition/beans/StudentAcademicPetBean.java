/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean(name="StudentAcademicPetBean")
@ViewScoped
public class StudentAcademicPetBean {

@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
private IGetLoggedInStudentDataFacade studentDataFacade;
@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
private ISharedAcademicPetFacade sharedAcademicPetFacade;

@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
private IStudentAcademicPetFacade facade;

private List<CoursePetitionDTO> pendingForms;
private List<CoursePetitionDTO> archievedForms;
private  CoursePetitionDTO selectedPendingForms;
private List<CoursePetitionDTO> filteredPendingForms;
private CoursePetitionDTO selectedArchievedForms;
private List<CoursePetitionDTO> filteredArchievedForms;
private List<CoursesDTO> coursesLst;
private CoursePetitionDTO detailedForm;
private Integer selectedCourseID;
private String mobileNo;
private String requestText;
private String title;
private List<BaseDTO> semesterLst;
private Integer selectedSemester;
private Integer selectedYear;
private List<Integer> yearLst;


@PostConstruct
public void init()
{
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		if(authentication.getName().startsWith("S-")||authentication.getName().startsWith("s-"))
		{
	fillPendingLst();
	fillArchievedLst();
	fillSemesterLst();
	setSelectedCourseID(null);
	setMobileNo(null);
	setTitle(null);
	setRequestText(null);
	HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	//StringBuffer url=origRequest.getRequestURL();
	try
	{
		Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
		setSelectedPendingForms(facade.getById(dtoID));
		JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
	}
	catch(Exception ex)
	{
		System.out.println("Not redirect");
	}
	
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for students");
		}
	}
}

public void fillPendingLst()
{
	pendingForms=new ArrayList<CoursePetitionDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")){
			pendingForms=facade.getPendingPetOfStudent(studentDataFacade.getPersonByPersonMail(mail).getId());
			
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
		}
	}

	}

public void fillArchievedLst()
{
	archievedForms=new ArrayList<CoursePetitionDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")){
			archievedForms=facade.getOldPetOfStudent(studentDataFacade.getPersonByPersonMail(mail).getId());
			
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
		}
	}	
}
public void fillCourseLst(AjaxBehaviorEvent event)
{
	coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
	
}
public void submitForm()
{
	CoursePetitionDTO dto=new CoursePetitionDTO();
	
	try{
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")){
			
		    StudentDTO student=new StudentDTO();
			student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
			
	        dto.setPersonId(studentDataFacade.getPersonByPersonMail(mail).getId());
			dto.setCourseID(getSelectedCourseID());
			dto.setMobileNo(getMobileNo());
		
			dto.setStatus(PetitionStepsEnum.AUDITING.getName());
			dto.setSubmittedDate(Calendar.getInstance());
			dto.setStep(PetitionStepsEnum.AUDITING);
			dto.setTitle(getTitle());
			dto.setRequestText(getRequestText());
			
			if(this.attachmentFile != null)
			{
				AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
				dto.setAttachments(attachment);
			}
			
			dto=facade.submit(dto);
			
	        if(dto!=null)
	        {
	        	
	        	init();
	        	
	        	FacesContext.getCurrentInstance().getExternalContext().redirect
				("studentCoursePetitionPage.xhtml?id="+dto.getId());
	        	JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
	        	sharedAcademicPetFacade.notifayNextStepOwner(dto);
	        	
	        }
	        else 
	        {
	        	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can't Be Submitted");
	        }
	        
		}
	}
	 else 
        {
		 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only For students");
        }
	}
	catch(Exception ex){
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can not Be Submitted");
	}
}
public void showDetails(CoursePetitionDTO form)
{/*
	RequestContext.getCurrentInstance().reset("detForm:detGrid");
	setDetailedForm(form);
	RequestContext.getCurrentInstance().execute("detDlg.show();");
	FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("detForm:detGrid");*/
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Stduent&oldMood=1");
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

public void onRowSelect(SelectEvent event) {  
  	try {
  		CoursePetitionDTO selectedDTO=(CoursePetitionDTO) event.getObject();
		showDetails(selectedDTO);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}  

public void fillSemesterLst()
{
	semesterLst=new ArrayList<BaseDTO>();
	semesterLst.add(new BaseDTO(0,"Fall"));
	semesterLst.add(new BaseDTO(1,"Spring"));
	semesterLst.add(new BaseDTO(2,"Summer"));
	//semesterLst.add(new BaseDTO(3,"Winter"));
}
public void fillYearLst(AjaxBehaviorEvent event)
{
	 yearLst=new ArrayList<Integer>();
	 setSelectedYear(null);
	 setSelectedCourseID(null);
	for(int i=2013;i<2031;i++)
	{
		yearLst.add(i);
	}
}

public IGetLoggedInStudentDataFacade getStudentDataFacade() {
	return studentDataFacade;
}

public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
	this.studentDataFacade = studentDataFacade;
}

public IStudentAcademicPetFacade getFacade() {
	return facade;
}

public void setFacade(IStudentAcademicPetFacade facade) {
	this.facade = facade;
}

public List<CoursePetitionDTO> getPendingForms() {
	return pendingForms;
}

public void setPendingForms(List<CoursePetitionDTO> pendingForms) {
	this.pendingForms = pendingForms;
}

public List<CoursePetitionDTO> getArchievedForms() {
	return archievedForms;
}

public void setArchievedForms(List<CoursePetitionDTO> archievedForms) {
	this.archievedForms = archievedForms;
}



public List<CoursePetitionDTO> getFilteredPendingForms() {
	return filteredPendingForms;
}

public void setFilteredPendingForms(List<CoursePetitionDTO> filteredPendingForms) {
	this.filteredPendingForms = filteredPendingForms;
}



public CoursePetitionDTO getSelectedArchievedForms() {
	return selectedArchievedForms;
}

public void setSelectedArchievedForms(CoursePetitionDTO selectedArchievedForms) {
	this.selectedArchievedForms = selectedArchievedForms;
}

public List<CoursePetitionDTO> getFilteredArchievedForms() {
	return filteredArchievedForms;
}

public void setFilteredArchievedForms(
		List<CoursePetitionDTO> filteredArchievedForms) {
	this.filteredArchievedForms = filteredArchievedForms;
}

public List<CoursesDTO> getCoursesLst() {
	return coursesLst;
}

public void setCoursesLst(List<CoursesDTO> coursesLst) {
	this.coursesLst = coursesLst;
}

public CoursePetitionDTO getDetailedForm() {
	return detailedForm;
}

public void setDetailedForm(CoursePetitionDTO detailedForm) {
	this.detailedForm = detailedForm;
}

public Integer getSelectedCourseID() {
	return selectedCourseID;
}

public void setSelectedCourseID(Integer selectedCourseID) {
	this.selectedCourseID = selectedCourseID;
}

public String getMobileNo() {
	return mobileNo;
}

public void setMobileNo(String mobileNo) {
	this.mobileNo = mobileNo;
}

public String getRequestText() {
	return requestText;
}

public void setRequestText(String requestText) {
	this.requestText = requestText;
}

public String getTitle() {
	return title;
}

public void setTitle(String title) {
	this.title = title;
}

public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
	return sharedAcademicPetFacade;
}

public void setSharedAcademicPetFacade(
		ISharedAcademicPetFacade sharedAcademicPetFacade) {
	this.sharedAcademicPetFacade = sharedAcademicPetFacade;
}

private UploadedFile attachmentFile;

public UploadedFile getAttachmentFile() {
    return attachmentFile;
}

public void setAttachmentFile(UploadedFile file) {
    this.attachmentFile = file;
}
 
public void upload(FileUploadEvent event) {  
    // Do what you want with the file      
    setAttachmentFile(event.getFile());

    try {
	} catch (Exception e) {
	}
}  

public void removeAttachment()
{
	setAttachmentFile(null);
}

public String getAttachmentFileName()
{
	if(attachmentFile == null)
		return "None";
	else
		return attachmentFile.getFileName();
}

public void downloadAttachments(CoursePetitionDTO form)
{
	AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
}

public CoursePetitionDTO getSelectedPendingForms() {
	return selectedPendingForms;
}

public void setSelectedPendingForms(CoursePetitionDTO selectedPendingForms) {
	this.selectedPendingForms = selectedPendingForms;
}

public List<BaseDTO> getSemesterLst() {
	return semesterLst;
}

public void setSemesterLst(List<BaseDTO> semesterLst) {
	this.semesterLst = semesterLst;
}

public Integer getSelectedSemester() {
	return selectedSemester;
}

public void setSelectedSemester(Integer selectedSemester) {
	this.selectedSemester = selectedSemester;
}

public Integer getSelectedYear() {
	return selectedYear;
}

public void setSelectedYear(Integer selectedYear) {
	this.selectedYear = selectedYear;
}

public List<Integer> getYearLst() {
	return yearLst;
}

public void setYearLst(List<Integer> yearLst) {
	this.yearLst = yearLst;
}





}
