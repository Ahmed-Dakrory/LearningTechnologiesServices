/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatActionsSharedFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatStudentFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IOverloadRequestActionsSharedFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="CourseRepeatStudentBean")
@ViewScoped

public class CourseRepeatStudentBean {

	
	
	@ManagedProperty("#{CourseRepeatStudentFacadeImpl}")
	private ICourseRepeatStudentFacade facade;
	
	
	@ManagedProperty("#{ICourseRepeatActionsSharedFacade}")
	private ICourseRepeatActionsSharedFacade facadeCourseRepeat;
	
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
    @ManagedProperty("#{StudentAcademicPetFacadeImpl}")
    private IStudentAcademicPetFacade coursefacade;
    
    @ManagedProperty("#{IMajorsFacade}")
    private IMajorsFacade majorFacade;
    
    
    
	private List<CourseRepeatDTO> pendingForms;
	private List<CourseRepeatDTO> archievedForms;
	private CourseRepeatDTO selectedPendingForms;
	private List<CourseRepeatDTO> filteredPendingForms;
	private CourseRepeatDTO selectedArchievedForms;
	private List<CourseRepeatDTO> filteredArchievedForms;
	private List<CoursesDTO> coursesLst;
	private List<MajorDTO> majorLst;
	private CourseRepeatDTO detailedForm;
	private Integer selectedCourseID;
	private String mobileNo;
	private String reason;
	private Integer selectedMajorID;
	private Integer selectedNewSem;
	private Integer selectedOldSem;
	private String grade;
	

    @ManagedProperty("#{IStudentProfileFacade}")
    private StudentProfileFacadeImpl profileFacade;
    
	private UploadedFile attachmentFile;
	private List<BaseDTO> semesters;
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
			if(authentication.getName().startsWith("S-")||authentication.getName().startsWith("s-")||StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			{
		fillPendingLst();
		fillArchievedLst();
		//fillCourseLst();
		fillMajorLst();
		setSelectedCourseID(null);
		setSelectedMajorID(null);
		setSelectedNewSem(null);
		setSelectedOldSem(null);
		setMobileNo(null);
		 fillSemesterLst();
		setReason(null);
		fillSemesterLstCourse();
			
		}
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		//StringBuffer url=origRequest.getRequestURL();
		try
		{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			setSelectedPendingForms(facade.getByID(dtoID));
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
		}
		catch(Exception ex)
		{
			System.out.println("Not redirect");
		}
		
		}else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for students");
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/pages/public/login.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}


	public void fillPendingLst()
	{
		pendingForms=new ArrayList<CourseRepeatDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				pendingForms=facade.getPendingPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
		}

		}
	
	public void closeFormCourseRepeat(int id) {
		CourseRepeatDTO overLoadReq = facadeCourseRepeat.getByID(id);
		overLoadReq.setStep(PetitionStepsEnum.CLOSED);
		overLoadReq.setStatus(PetitionStepsEnum.CLOSED.getName());
		overLoadReq.setPerformed(true);
		facadeCourseRepeat.updateStatusOfForm(overLoadReq);
		JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Request with Id "+String.valueOf(id)+" has been closed");
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("courseRepeatFormStudent.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void fillArchievedLst()
	{
		archievedForms=new ArrayList<CourseRepeatDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				archievedForms=facade.getArchievedPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}	
	}
	
	public void fillCourseLst(AjaxBehaviorEvent event)
	{
		//coursesLst=facade.getAllCourses();
		coursesLst=coursefacade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		
	}
	public void fillMajorLst()
	{
		//majorLst=facade.getAllMajors();
		//majorLst=facade.getAllOLdNew();
		 majorLst=majorFacade.getAll();
		
	}
	
	public void submitForm()
	{
		CourseRepeatDTO dto=new CourseRepeatDTO();
		
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				
			    StudentDTO student=new StudentDTO();
				student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
				
				StudentProfileDTO profile = profileFacade.getCurrentPRofileByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
				 if(profile!=null) {
			        	if(validProfileToSubmit(profile)) {
					
					MajorDTO major=profile.getMajor();
					if(major!=null) {
						
						dto.setMajor(major);
					}

		        dto.setStudent(student);
		        CoursesDTO course =new CoursesDTO();
		        course.setId(getSelectedCourseID());
				dto.setCourse(course);
				
				
				if(getSelectedNewSem()==0)
					dto.setNewSem(SemesterEnum.Fall);
				else if(getSelectedNewSem()==1)
					dto.setNewSem(SemesterEnum.Spring);
				else if(getSelectedNewSem()==2)
					dto.setNewSem(SemesterEnum.Summer);
				else if(getSelectedNewSem()==3)
					dto.setNewSem(SemesterEnum.Winter);
				
				if(getSelectedOldSem()==0)
					dto.setOldSem(SemesterEnum.Fall);
				else if(getSelectedOldSem()==1)
					dto.setOldSem(SemesterEnum.Spring);
				else if(getSelectedOldSem()==2)
					dto.setOldSem(SemesterEnum.Summer);
				else if(getSelectedOldSem()==3)
					dto.setOldSem(SemesterEnum.Winter);
				
				
				dto.setMobile(profile.getMobile());
			

				dto.setStatus(PetitionStepsEnum.ADMISSION_PROCESSING.getName());
				dto.setSubmissionDate(Calendar.getInstance());
				dto.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
				
				dto.setReason(getReason());
				
				if(this.attachmentFile != null)
				{
					AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
					dto.setAttachments(attachment);
				}
				dto.setGrade(grade);
				dto=facade.add(dto);
				
		        if(dto!=null)
		        {
		        	
		        	init();
		        	FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseRepeatFormStudent.xhtml?id="+dto.getId());
		        	setSelectedPendingForms(dto);
		        	JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
		        	sharedAcademicPetFacade.notifayNextStepOwner(dto);
		        	
		        }
		        else 
		        {
		        	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can't Be Submitted");
		        }
			        	}
			        	
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
	
	
	private boolean validProfileToSubmit(StudentProfileDTO profile) {
		// TODO Auto-generated method stub
		if(profile.getGpa()<3 && profile.getCompletedCreditHrs()>65) {
			return true;
		}
		return true;
	}
	
	public void showDetails(CourseRepeatDTO form)
	{
		/*RequestContext.getCurrentInstance().reset("detForm:detGrid");
		setDetailedForm(form);
		RequestContext.getCurrentInstance().execute("detDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
			.getRenderIds().add("detForm:detGrid");*/
		try {
    		
    		
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Stduent&oldMood=1");
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		CourseRepeatDTO selectedDTO=(CourseRepeatDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void fillSemesterLst()
	{
		semesters=new ArrayList<BaseDTO>();
		semesters.add(new BaseDTO(0,"Fall"));
		semesters.add(new BaseDTO(1,"Spring"));
		semesters.add(new BaseDTO(2,"Summer"));
		semesters.add(new BaseDTO(3,"Winter"));
		
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

	public void fillSemesterLstCourse()
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
	
	public ICourseRepeatStudentFacade getFacade() {
		return facade;
	}


	public void setFacade(ICourseRepeatStudentFacade facade) {
		this.facade = facade;
	}


	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}


	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}


	public List<CourseRepeatDTO> getPendingForms() {
		return pendingForms;
	}


	public void setPendingForms(List<CourseRepeatDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}


	public List<CourseRepeatDTO> getArchievedForms() {
		return archievedForms;
	}


	public void setArchievedForms(List<CourseRepeatDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}




	public List<CourseRepeatDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}


	public void setFilteredPendingForms(List<CourseRepeatDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}


	


	public CourseRepeatDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}


	public void setSelectedArchievedForms(CourseRepeatDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}


	public List<CourseRepeatDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}


	public void setFilteredArchievedForms(
			List<CourseRepeatDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}


	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}


	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}


	public CourseRepeatDTO getDetailedForm() {
		return detailedForm;
	}


	public void setDetailedForm(CourseRepeatDTO detailedForm) {
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


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public Integer getSelectedMajorID() {
		return selectedMajorID;
	}


	public void setSelectedMajorID(Integer selectedMajorID) {
		this.selectedMajorID = selectedMajorID;
	}


	public Integer getSelectedNewSem() {
		return selectedNewSem;
	}


	public void setSelectedNewSem(Integer selectedNewSem) {
		this.selectedNewSem = selectedNewSem;
	}


	public Integer getSelectedOldSem() {
		return selectedOldSem;
	}


	public void setSelectedOldSem(Integer selectedOldSem) {
		this.selectedOldSem = selectedOldSem;
	}




	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public List<MajorDTO> getMajorLst() {
		return majorLst;
	}


	public void setMajorLst(List<MajorDTO> majorLst) {
		this.majorLst = majorLst;
	}


	public List<BaseDTO> getSemesters() {
		return semesters;
	}


	public void setSemesters(List<BaseDTO> semesters) {
		this.semesters = semesters;
	}

	public UploadedFile getAttachmentFile() {
	    return attachmentFile;
	}

	public void setAttachmentFile(UploadedFile file) {
	    this.attachmentFile = file;
	}
	 

	public void downloadAttachments(CourseRepeatDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}


	public CourseRepeatDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}


	public void setSelectedPendingForms(CourseRepeatDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}


	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}


	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
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


	public IStudentAcademicPetFacade getCoursefacade() {
		return coursefacade;
	}


	public void setCoursefacade(IStudentAcademicPetFacade coursefacade) {
		this.coursefacade = coursefacade;
	}


	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}


	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}


	public StudentProfileFacadeImpl getProfileFacade() {
		return profileFacade;
	}


	public void setProfileFacade(StudentProfileFacadeImpl profileFacade) {
		this.profileFacade = profileFacade;
	}


	public ICourseRepeatActionsSharedFacade getFacadeCourseRepeat() {
		return facadeCourseRepeat;
	}


	public void setFacadeCourseRepeat(ICourseRepeatActionsSharedFacade facadeCourseRepeat) {
		this.facadeCourseRepeat = facadeCourseRepeat;
	}


	
	
	
	
}
