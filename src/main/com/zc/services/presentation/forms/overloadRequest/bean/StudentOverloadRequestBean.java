/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.bean;

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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IStudentOverloadRequestFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean(name="StudentOverloadRequestBean")
@ViewScoped
public class StudentOverloadRequestBean {

	@ManagedProperty("#{StudentOverloadRequestFacadeImpl}")
	private IStudentOverloadRequestFacade facade;

    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
   	private ISharedAcademicPetFacade sharedAcademicPetFacade;
   	
    @ManagedProperty("#{StudentAcademicPetFacadeImpl}")
    private IStudentAcademicPetFacade coursefacade;
    
    @ManagedProperty("#{IMajorsFacade}")
    private IMajorsFacade majorFacade;
    
	private List<OverloadRequestDTO> pendingForms;
	private List<OverloadRequestDTO> archievedForms;
	private OverloadRequestDTO selectedPendingForms;
	private List<OverloadRequestDTO> filteredPendingForms;
	private OverloadRequestDTO selectedArchievedForms;
	private List<OverloadRequestDTO> filteredArchievedForms;
	private List<CoursesDTO> coursesLst;
	private OverloadRequestDTO detailedForm;
	private Integer selectedCourseID;
	private Integer selectedMajorID;
	private String mobile;
	private String selectedYear;
	private String reason;
	private List<String> yearLst;
	private List<MajorDTO>majorLst;
	private UploadedFile attachmentFile;
	
	private String gpa;

	private List<BaseDTO> semesterLst;
	private Integer selectedSemester;
	private Integer selectedYearCourse;
	private List<Integer> yearLstCourse;
	
	@PostConstruct
	public void init()

	{
		//fillCourseLst();
		fillSemesterLst();
		fillYearLst();
		fillArchievedPetitionsLst();
		fillPendingForms();
		setSelectedCourseID(null);
		setSelectedYear(null);
		setMobile(null);
		setReason(null);
		setGpa(null);
		fillMajorLst();
		setAttachmentFile(null);
		setSelectedMajorID(null);
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
	}
    

	
	
	public void fillCourseLst(AjaxBehaviorEvent event)
	{
		//coursesLst=facade.getAllCourses();
		coursesLst=coursefacade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYearCourse());
	
	}
	public void fillYearLst()
	{
		yearLst=new ArrayList<String>();
		yearLst.add("2013-2014");
		yearLst.add("2014-2015");
		
		
	}
	public void fillMajorLst()
	{
		majorLst=new ArrayList<MajorDTO>();
		//majorLst=facade.getAllMajors();
		majorLst=majorFacade.getAll();
		
	}
	public void fillPendingForms()
	{
		pendingForms=new ArrayList<OverloadRequestDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				pendingForms=facade.getPendingRequestsOfStudent(studentDataFacade.getPersonByPersonMail(mail).getId());
				
			}
			else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Not Allowed To see This Petitions", ""));
			}
		}
	
		
	}
	
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<OverloadRequestDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")){
			archievedForms=facade.getArchievedRequestsOfStudent(studentDataFacade.getPersonByPersonMail(mail).getId());
		}
		else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Not Allowed To see This Petitions", ""));
		}
	}
	}
	
	public void showDetails(OverloadRequestDTO form)
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
	  		OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void submitRequest()
	{
		OverloadRequestDTO dto=new OverloadRequestDTO();
		
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				
			    StudentDTO student=new StudentDTO();
				student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
				student.setFacultyId(studentDataFacade.getPersonByPersonMail(mail).getFileNo());
		        student.setMail(studentDataFacade.getPersonByPersonMail(mail).getEmail());
		        student.setName(studentDataFacade.getPersonByPersonMail(mail).getNameInEng());
		        dto.setStudent(student);
		        CoursesDTO course=new CoursesDTO();
				course.setId(getSelectedCourseID());
				dto.setCourse(course);
				dto.setMobile(getMobile());
				dto.setYear(getSelectedYear());
				dto.setStatus(PetitionStepsEnum.AUDITING.getName());
				dto.setSubmissionDate(Calendar.getInstance());
				dto.setStep(PetitionStepsEnum.AUDITING);
				dto.setReason(getReason());
				dto.setGpa(getGpa());
				MajorDTO major=new MajorDTO();
				major.setId(getSelectedMajorID());
				dto.setMajor(major);
				if(this.attachmentFile != null)
				{
					AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
					dto.setAttachments(attachment);
				}
				
				dto=facade.submitRequest(dto);
				
		        if(dto!=null)
		        {
		        
		        	init();
		        	
		        	FacesContext.getCurrentInstance().getExternalContext().redirect
					("overloadRequestStudent.xhtml?id="+dto.getId());
		        
		        	sharedAcademicPetFacade.notifayNextStepOwner(dto);
		        	}
		        else 
		        {
		        	FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Form Can't Be Submitted", ""));
		        }
		        
			}
		}
		 else 
	        {
	        	FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Allowed Only For students", ""));
	        }
		}
		catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Form Can not Be Submitted", ""));
		}
	}
	
	public void upload(FileUploadEvent event) {  
	    // Do what you want with the file      
	    setAttachmentFile(event.getFile());

	    try {
		} catch (Exception e) {
		}
	}  

	public String getAttachmentFileName()
	{
		if(attachmentFile == null)
			return "None";
		else
			return attachmentFile.getFileName();
	}

	public void removeAttachment()
	{
		setAttachmentFile(null);
	}
	
	public void downloadAttachments(OverloadRequestDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearCourseLst(AjaxBehaviorEvent event)
	{
		 yearLstCourse=new ArrayList<Integer>();
		 setSelectedYearCourse(null);
		 setSelectedCourseID(null);
		for(int i=2013;i<2031;i++)
		{
			yearLstCourse.add(i);
		}
	}

	
	public IStudentOverloadRequestFacade getFacade() {
		return facade;
	}

	public void setFacade(IStudentOverloadRequestFacade facade) {
		this.facade = facade;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public List<OverloadRequestDTO> getPendingForms() {
		return pendingForms;
	}

	public void setPendingForms(List<OverloadRequestDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public List<OverloadRequestDTO> getArchievedForms() {
		return archievedForms;
	}

	public void setArchievedForms(List<OverloadRequestDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	

	public OverloadRequestDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}




	public void setSelectedPendingForms(OverloadRequestDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}




	public List<OverloadRequestDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}

	public void setFilteredPendingForms(List<OverloadRequestDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}


	public OverloadRequestDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}




	public void setSelectedArchievedForms(OverloadRequestDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}




	public List<OverloadRequestDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<OverloadRequestDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}

	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}

	public OverloadRequestDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(OverloadRequestDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public Integer getSelectedCourseID() {
		return selectedCourseID;
	}

	public void setSelectedCourseID(Integer selectedCourseID) {
		this.selectedCourseID = selectedCourseID;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(String selectedYear) {
		this.selectedYear = selectedYear;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<String> getYearLst() {
		return yearLst;
	}

	public void setYearLst(List<String> yearLst) {
		this.yearLst = yearLst;
	}

	public String getGpa() {
		return gpa;
	}

	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public List<MajorDTO> getMajorLst() {
		return majorLst;
	}

	public void setMajorLst(List<MajorDTO> majorLst) {
		this.majorLst = majorLst;
	}
	
	
	
	public UploadedFile getAttachmentFile() {
	    return attachmentFile;
	}

	public void setAttachmentFile(UploadedFile file) {
	    this.attachmentFile = file;
	}




	public Integer getSelectedMajorID() {
		return selectedMajorID;
	}




	public void setSelectedMajorID(Integer selectedMajorID) {
		this.selectedMajorID = selectedMajorID;
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




	public Integer getSelectedYearCourse() {
		return selectedYearCourse;
	}




	public void setSelectedYearCourse(Integer selectedYearCourse) {
		this.selectedYearCourse = selectedYearCourse;
	}




	public List<Integer> getYearLstCourse() {
		return yearLstCourse;
	}




	public void setYearLstCourse(List<Integer> yearLstCourse) {
		this.yearLstCourse = yearLstCourse;
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
