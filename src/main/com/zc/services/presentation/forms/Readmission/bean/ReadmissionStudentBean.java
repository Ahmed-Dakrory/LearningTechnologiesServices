/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.bean;

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
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionActionsFacade;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionStudentFacade;
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
public class ReadmissionStudentBean {

	@ManagedProperty("#{IReadmissionStudentFacade}")
	private IReadmissionStudentFacade facade;
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    @ManagedProperty("#{IMajorsFacade}")
  	private IMajorsFacade majorFacade;

	@ManagedProperty("#{IReadmissionActionsFacade}")
	private IReadmissionActionsFacade facadeReadmission;
	

    
    
    @ManagedProperty("#{IStudentProfileFacade}")
    private StudentProfileFacadeImpl profileFacade;
	private List<ReadmissionDTO> pendingForms;
	private List<ReadmissionDTO> archievedForms;
	private ReadmissionDTO selectedPendingForms;
	private List<ReadmissionDTO> filteredPendingForms;
	private ReadmissionDTO selectedArchievedForms;
	private List<ReadmissionDTO> filteredArchievedForms;
	private ReadmissionDTO detailedForm;
	private List<MajorDTO> majorsLst;
	private List<MajorDTO> currentmajorsLst;
	private Integer selectedMajorId;
	private String moreDetails;

	
	@PostConstruct
	public void init()

	{
		fillPendingFormLst();
		fillArchievedPetitionsLst();
		detailedForm=new ReadmissionDTO();
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
		pendingForms=new ArrayList<ReadmissionDTO>();
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
	
	public void closeFormReadmission(int id) {
		ReadmissionDTO readmission = facadeReadmission.getByID(id);
		readmission.setStep(PetitionStepsEnum.CLOSED);
		readmission.setStatus(PetitionStepsEnum.CLOSED.getName());
		readmission.setPerformed(true);
		facadeReadmission.updateStatusOfForm(readmission);
		JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Request with Id "+String.valueOf(id)+" has been closed");
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("readmissionStudent.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<ReadmissionDTO>();
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
	public void showDetails(ReadmissionDTO form)
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
	  		ReadmissionDTO selectedDTO=(ReadmissionDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void submitRequest()
	{
	
		try{
		ReadmissionDTO request=new ReadmissionDTO();
		
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
				
		        request.setMoreDetails(getMoreDetails());
		        
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
					("readmissionStudent.xhtml?id="+request.getId());
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
	public IReadmissionStudentFacade getFacade() {
		return facade;
	}
	public void setFacade(IReadmissionStudentFacade facade) {
		this.facade = facade;
	}

	public List<ReadmissionDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<ReadmissionDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<ReadmissionDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<ReadmissionDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	
	public ReadmissionDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(ReadmissionDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<ReadmissionDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<ReadmissionDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	
	public ReadmissionDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(ReadmissionDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<ReadmissionDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<ReadmissionDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public ReadmissionDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(ReadmissionDTO detailedForm) {
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

	public String getMoreDetails() {
		return moreDetails;
	}
	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
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

	public void downloadAttachments(ReadmissionDTO form)
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
	public IReadmissionActionsFacade getFacadeReadmission() {
		return facadeReadmission;
	}
	public void setFacadeReadmission(IReadmissionActionsFacade facadeReadmission) {
		this.facadeReadmission = facadeReadmission;
	}
	
}