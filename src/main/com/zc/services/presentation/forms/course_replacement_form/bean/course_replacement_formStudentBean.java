/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formActionsFacade;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formStudentFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean
@ViewScoped
public class course_replacement_formStudentBean {

	@ManagedProperty("#{Icourse_replacement_formStudentFacade}")
	private Icourse_replacement_formStudentFacade facade;
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    @ManagedProperty("#{IMajorsFacade}")
  	private IMajorsFacade majorFacade;

	@ManagedProperty("#{Icourse_replacement_formActionsFacade}")
	private Icourse_replacement_formActionsFacade facadecourse_replacement_form;
	

    
    
    @ManagedProperty("#{IStudentProfileFacade}")
    private StudentProfileFacadeImpl profileFacade;
	private List<course_replacement_formDTO> pendingForms;
	private List<course_replacement_formDTO> archievedForms;
	private course_replacement_formDTO selectedPendingForms;
	private List<course_replacement_formDTO> filteredPendingForms;
	private course_replacement_formDTO selectedArchievedForms;
	private List<course_replacement_formDTO> filteredArchievedForms;
	private course_replacement_formDTO detailedForm;
	private List<MajorDTO> majorsLst;
	private List<MajorDTO> currentmajorsLst;
	private Integer selectedMajorId;
	private String courseFinished;
	private String toReplaceCourse;

	
	@PostConstruct
	public void init()

	{
		fillPendingFormLst();
		fillArchievedPetitionsLst();
		detailedForm=new course_replacement_formDTO();
		majorsLst=new ArrayList<MajorDTO>();
		currentmajorsLst=new ArrayList<MajorDTO>();
		fillMajorsLst();
		setSelectedMajorId(0);
	   
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
	public void fillPendingFormLst()
	{
		pendingForms=new ArrayList<course_replacement_formDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				pendingForms=facade.getPendingPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Not Allowed To see This Petitions");
			}
		}
	}
	
	public void closeFormcourse_replacement_form(int id) {
		course_replacement_formDTO course_replacement_form = facadecourse_replacement_form.getByID(id);
		course_replacement_form.setStep(PetitionStepsEnum.CLOSED);
		course_replacement_form.setStatus(PetitionStepsEnum.CLOSED.getName());
		course_replacement_form.setPerformed(true);
		facadecourse_replacement_form.updateStatusOfForm(course_replacement_form);
		JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Request with Id "+String.valueOf(id)+" has been closed");
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("course_replacement_formStudent.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<course_replacement_formDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
			archievedForms=facade.getArchievedPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Not Allowed To see This Petitions");
		}
	}
	}
	public void showDetails(course_replacement_formDTO form)
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
	  		course_replacement_formDTO selectedDTO=(course_replacement_formDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void submitRequest()
	{
	
		try{
		course_replacement_formDTO request=new course_replacement_formDTO();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				 StudentProfileDTO profile = profileFacade.getCurrentPRofileByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());

			        if(profile!=null) {
			    StudentDTO student=new StudentDTO();
				student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
				student.setFacultyId(studentDataFacade.getPersonByPersonMail(mail).getFileNo());
		        student.setMail(studentDataFacade.getPersonByPersonMail(mail).getEmail());
		        student.setName(studentDataFacade.getPersonByPersonMail(mail).getNameInEng());
		        request.setStudent(student);
		        request.setStatus(PetitionStepsEnum.UNDER_REVIEW.getName());
		        request.setSubmissionDate(Calendar.getInstance());
				MajorDTO major=new MajorDTO();
				major.setId(getSelectedMajorId());
				request.setStep(PetitionStepsEnum.UNDER_REVIEW);
				
		        request.setCourseFinished(getCourseFinished());
		        request.setToReplaceCourse(getToReplaceCourse());
		        
		        if(this.attachmentFile != null)
				{
					AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
					request.setAttachments(attachment);
				}
		        
		        request=facade.add(request);
		        if(request!=null)
		        {
		        	
		        	init();
		        
		        	FacesContext.getCurrentInstance().getExternalContext().redirect
					("course_replacement_formStudent.xhtml?id="+request.getId());
		        	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Form has been submitted successfully");
		        	sharedAcademicPetFacade.notifayNextStepOwner(request);
		        }
		        else 
		        {
		        	JavaScriptMessagesHandler.RegisterErrorMessage(null,"Form Can't Be Submitted" );
		        }
		        
			}else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can not Be Submitted");
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
	public void fillMajorsLst(){
		//majorsLst=facade.getAllMajors();
		majorsLst=majorFacade.getAvailableOnly();
		currentmajorsLst=majorFacade.getAll();
	}
	public Icourse_replacement_formStudentFacade getFacade() {
		return facade;
	}
	public void setFacade(Icourse_replacement_formStudentFacade facade) {
		this.facade = facade;
	}

	public List<course_replacement_formDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<course_replacement_formDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<course_replacement_formDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<course_replacement_formDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	
	public course_replacement_formDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(course_replacement_formDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<course_replacement_formDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<course_replacement_formDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	
	public course_replacement_formDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(course_replacement_formDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<course_replacement_formDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<course_replacement_formDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public course_replacement_formDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(course_replacement_formDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public List<MajorDTO> getMajorsLst() {
		return majorsLst;
	}
	public void setMajorsLst(List<MajorDTO> majorsLst) {
		this.majorsLst = majorsLst;
	}
	public Integer getSelectedMajorId() {
		return selectedMajorId;
	}
	public void setSelectedMajorId(Integer selectedMajorId) {
		this.selectedMajorId = selectedMajorId;
	}

	
	public String getCourseFinished() {
		return courseFinished;
	}
	public void setCourseFinished(String courseFinished) {
		this.courseFinished = courseFinished;
	}
	public String getToReplaceCourse() {
		return toReplaceCourse;
	}
	public void setToReplaceCourse(String toReplaceCourse) {
		this.toReplaceCourse = toReplaceCourse;
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

	public void downloadAttachments(course_replacement_formDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}
	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}
	public List<MajorDTO> getCurrentmajorsLst() {
		return currentmajorsLst;
	}
	public void setCurrentmajorsLst(List<MajorDTO> currentmajorsLst) {
		this.currentmajorsLst = currentmajorsLst;
	}
	public StudentProfileFacadeImpl getProfileFacade() {
		return profileFacade;
	}
	public void setProfileFacade(StudentProfileFacadeImpl profileFacade) {
		this.profileFacade = profileFacade;
	}
	public Icourse_replacement_formActionsFacade getFacadecourse_replacement_form() {
		return facadecourse_replacement_form;
	}
	public void setFacadecourse_replacement_form(Icourse_replacement_formActionsFacade facadecourse_replacement_form) {
		this.facadecourse_replacement_form = facadecourse_replacement_form;
	}
	
}