/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeStudentFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="IncompleteGradeStudentBean")
@ViewScoped
public class IncompleteGradeStudentBean {

	@ManagedProperty("#{IIncompleteGradeStudentFacade}")
	private IIncompleteGradeStudentFacade facade;
	
	@ManagedProperty("#{IMajorsFacade}")
	private IMajorsFacade majorFacade;
	
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
    @ManagedProperty("#{StudentAcademicPetFacadeImpl}")
    private IStudentAcademicPetFacade coursefacade;
    
    
	private List<IncompleteGradeDTO> pendingForms;
	private List<IncompleteGradeDTO> archievedForms;
	private IncompleteGradeDTO selectedPendingForms;
	private List<IncompleteGradeDTO> filteredPendingForms;
	private IncompleteGradeDTO selectedArchievedForms;
	private List<IncompleteGradeDTO> filteredArchievedForms;
	private List<CoursesDTO> coursesLst;
	private List<InstructorDTO> insLst;
	private List<MajorDTO> majorLst;
	private IncompleteGradeDTO detailedForm;
	private UploadedFile attachmentFile;
	private List<BaseDTO> semesters;
	private Integer selectedSem;
	private String mobileNo;
	private String reason;
	private Integer selectedMajorID;
	private Integer selectedCourseID;
	private Integer selectedInsID;
	private Date dateOfExam;
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
		//fillCourseLst();
		fillSemesterLstCourse();
		fillMajorLst();
		fillSemesterLst();
		setSelectedCourseID(null);
		setSelectedMajorID(null);
		setSelectedSem(null);
		setSelectedInsID(null);
		setMobileNo(null);
		setReason(null);
			
			
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
		pendingForms=new ArrayList<IncompleteGradeDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				pendingForms=facade.getPendingPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
		}

		}
	
	public void fillArchievedLst()
	{
		archievedForms=new ArrayList<IncompleteGradeDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
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
		majorLst=majorFacade.getAll();
		
	}
	public void fillSemesterLst()
	{
		semesters=new ArrayList<BaseDTO>();
		semesters.add(new BaseDTO(0,"Fall"));
		semesters.add(new BaseDTO(1,"Spring"));
		semesters.add(new BaseDTO(2,"Summer"));
		//semesters.add(new BaseDTO(3,"Winter"));
		
	}
	public void fillInstructorsLst()
	{
		insLst=new ArrayList<InstructorDTO>();
		insLst=facade.getAllInstructorsOfCourse(getSelectedCourseID());
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
	public void showDetails(IncompleteGradeDTO form)
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
	  		IncompleteGradeDTO selectedDTO=(IncompleteGradeDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void submitForm()
	{
		IncompleteGradeDTO dto=new IncompleteGradeDTO();
		
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				
			    StudentDTO student=new StudentDTO();
				student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
				
		        dto.setStudent(student);
		        CoursesDTO course =new CoursesDTO();
		        course.setId(getSelectedCourseID());
				dto.setCourse(course);
				
				MajorDTO major =new MajorDTO();
				major.setId(getSelectedMajorID());
				dto.setMajor(major);
				
				if(getSelectedSem()==0)
					dto.setSemester(SemesterEnum.Fall);
				else if(getSelectedSem()==1)
					dto.setSemester(SemesterEnum.Spring);
				else if(getSelectedSem()==2)
					dto.setSemester(SemesterEnum.Summer);
				else if(getSelectedSem()==3)
					dto.setSemester(SemesterEnum.Winter);
				
				
				
				dto.setMobile(getMobileNo());
			
				dto.setStatus(PetitionStepsEnum.UNDER_REVIEW.getName());
				dto.setSubmissionDate(Calendar.getInstance());
				dto.setStep(PetitionStepsEnum.UNDER_REVIEW);
				InstructorDTO insDto=new InstructorDTO();
				insDto.setId(getSelectedInsID());
				dto.setInstructor(insDto);
				Calendar cal=Calendar.getInstance();
				cal.setTime(getDateOfExam());
				dto.setDateOfExam(cal);
				dto.setReason(getReason());
				
				if(this.attachmentFile != null)
				{
					AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
					dto.setAttachments(attachment);
				}
				
				dto=facade.add(dto);
				
		        if(dto!=null)
		        {
		        	
		        	init();
		        	FacesContext.getCurrentInstance().getExternalContext().redirect
					("incompleteGradeStudent.xhtml?id="+dto.getId());
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
		 else 
	        {
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only For students");
	        }
		}
		catch(Exception ex){
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can not Be Submitted");
		}
	}
	public void downloadAttachments(IncompleteGradeDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
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
	public IIncompleteGradeStudentFacade getFacade() {
		return facade;
	}
	public void setFacade(IIncompleteGradeStudentFacade facade) {
		this.facade = facade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public List<IncompleteGradeDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<IncompleteGradeDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<IncompleteGradeDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<IncompleteGradeDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public IncompleteGradeDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(IncompleteGradeDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<IncompleteGradeDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(
			List<IncompleteGradeDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}

	public IncompleteGradeDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(IncompleteGradeDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<IncompleteGradeDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<IncompleteGradeDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public List<InstructorDTO> getInsLst() {
		return insLst;
	}
	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}
	public List<MajorDTO> getMajorLst() {
		return majorLst;
	}
	public void setMajorLst(List<MajorDTO> majorLst) {
		this.majorLst = majorLst;
	}
	public IncompleteGradeDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(IncompleteGradeDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public UploadedFile getAttachmentFile() {
		return attachmentFile;
	}
	public void setAttachmentFile(UploadedFile attachmentFile) {
		this.attachmentFile = attachmentFile;
	}
	public List<BaseDTO> getSemesters() {
		return semesters;
	}
	public void setSemesters(List<BaseDTO> semesters) {
		this.semesters = semesters;
	}
	public Integer getSelectedSem() {
		return selectedSem;
	}
	public void setSelectedSem(Integer selectedSem) {
		this.selectedSem = selectedSem;
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
	public Integer getSelectedCourseID() {
		return selectedCourseID;
	}
	public void setSelectedCourseID(Integer selectedCourseID) {
		this.selectedCourseID = selectedCourseID;
	}
	public Integer getSelectedInsID() {
		return selectedInsID;
	}
	public void setSelectedInsID(Integer selectedInsID) {
		this.selectedInsID = selectedInsID;
	}
	public Date getDateOfExam() {
		return dateOfExam;
	}
	public void setDateOfExam(Date dateOfExam) {
		this.dateOfExam = dateOfExam;
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
	
	
	
	

}
