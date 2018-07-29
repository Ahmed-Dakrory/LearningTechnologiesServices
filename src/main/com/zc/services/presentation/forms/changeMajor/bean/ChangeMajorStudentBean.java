/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.bean;

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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorStudentFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
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
public class ChangeMajorStudentBean {

	@ManagedProperty("#{IChangeMajorStudentFacade}")
	private IChangeMajorStudentFacade facade;
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    @ManagedProperty("#{IMajorsFacade}")
  	private IMajorsFacade majorFacade;
    
	private List<ChangeMajorDTO> pendingForms;
	private List<ChangeMajorDTO> archievedForms;
	private ChangeMajorDTO selectedPendingForms;
	private List<ChangeMajorDTO> filteredPendingForms;
	private ChangeMajorDTO selectedArchievedForms;
	private List<ChangeMajorDTO> filteredArchievedForms;
	private ChangeMajorDTO detailedForm;
	private List<MajorDTO> majorsLst;
	private List<MajorDTO> currentmajorsLst;
	private Integer selectedMajorId;
	private String mobile;
	private Double gpa;
	private Integer selectedCurMajorId;
	private Integer selectedNewMajorId;
	private String curSpec;
	private String doubleSpec;
	private String newSpec;
	private String moreDetails;

	
	@PostConstruct
	public void init()

	{
		fillPendingFormLst();
		fillArchievedPetitionsLst();
		detailedForm=new ChangeMajorDTO();
		majorsLst=new ArrayList<MajorDTO>();
		currentmajorsLst=new ArrayList<MajorDTO>();
		fillMajorsLst();
		setSelectedMajorId(0);
	    setMobile(null);
	    setSelectedNewMajorId(0);
	    setSelectedCurMajorId(0);
	    setNewSpec(null);
	    setCurSpec(null);
	    setDoubleSpec(null);
	    setMoreDetails(null);
	    setGpa(null);
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
		pendingForms=new ArrayList<ChangeMajorDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				pendingForms=facade.getPendingPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Not Allowed To see This Petitions");
			}
		}
	}
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<ChangeMajorDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")){
			archievedForms=facade.getArchievedPetitionsOfstuent(studentDataFacade.getPersonByPersonMail(mail).getId());
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Not Allowed To see This Petitions");
		}
	}
	}
	public void showDetails(ChangeMajorDTO form)
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
	  		ChangeMajorDTO selectedDTO=(ChangeMajorDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void submitRequest()
	{
		if(getSelectedNewMajorId()==0&&(getNewSpec().trim().equals("")||getNewSpec()==null)
				&&(getCurSpec().trim().equals("")||getCurSpec()==null)&&(getDoubleSpec().trim().equals("")||getDoubleSpec()==null))
		{
			JavaScriptMessagesHandler.RegisterWarningMessage(null, "Can't submit empty form");
		}
		else {
		try{
		ChangeMajorDTO request=new ChangeMajorDTO();
		
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
		        request.setStudent(student);
		        request.setStatus(PetitionStepsEnum.UNDER_REVIEW.getName());
		        request.setSubmissionDate(Calendar.getInstance());
				request.setMobile(getMobile());
				MajorDTO major=new MajorDTO();
				major.setId(getSelectedMajorId());
				request.setMajor(major);
				request.setStep(PetitionStepsEnum.UNDER_REVIEW);
				if(getSelectedCurMajorId()!=0)
				{
				MajorDTO curmajor=new MajorDTO();
				curmajor.setId(getSelectedCurMajorId());
				request.setCurMajor(curmajor);
				}
				if(getSelectedNewMajorId()!=0)
				{
				MajorDTO newmajor=new MajorDTO();
				newmajor.setId(getSelectedNewMajorId());
				request.setNewMajor(newmajor);
				}
		        request.setCurSpecialization(getCurSpec());
		        request.setNewSpecialization(getNewSpec());
		        request.setDoubleSpecialization(getDoubleSpec());
		        request.setMoreDetails(getMoreDetails());
		        request.setGpa(getGpa());
		        
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
					("changeMajorStudent.xhtml?id="+request.getId());
		        	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Form has been submitted successfully");
		        	sharedAcademicPetFacade.notifayNextStepOwner(request);
		        }
		        else 
		        {
		        	JavaScriptMessagesHandler.RegisterErrorMessage(null,"Form Can't Be Submitted" );
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
	}
	public void fillMajorsLst(){
		//majorsLst=facade.getAllMajors();
		majorsLst=majorFacade.getAvailableOnly();
		currentmajorsLst=majorFacade.getAll();
	}
	public IChangeMajorStudentFacade getFacade() {
		return facade;
	}
	public void setFacade(IChangeMajorStudentFacade facade) {
		this.facade = facade;
	}

	public List<ChangeMajorDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<ChangeMajorDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<ChangeMajorDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<ChangeMajorDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	
	public ChangeMajorDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(ChangeMajorDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<ChangeMajorDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<ChangeMajorDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	
	public ChangeMajorDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(ChangeMajorDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<ChangeMajorDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<ChangeMajorDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public ChangeMajorDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(ChangeMajorDTO detailedForm) {
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Double getGpa() {
		return gpa;
	}
	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}
	public Integer getSelectedCurMajorId() {
		return selectedCurMajorId;
	}
	public void setSelectedCurMajorId(Integer selectedCurMajorId) {
		this.selectedCurMajorId = selectedCurMajorId;
	}
	public Integer getSelectedNewMajorId() {
		return selectedNewMajorId;
	}
	public void setSelectedNewMajorId(Integer selectedNewMajorId) {
		this.selectedNewMajorId = selectedNewMajorId;
	}
	public String getCurSpec() {
		return curSpec;
	}
	public void setCurSpec(String curSpec) {
		this.curSpec = curSpec;
	}
	public String getDoubleSpec() {
		return doubleSpec;
	}
	public void setDoubleSpec(String doubleSpec) {
		this.doubleSpec = doubleSpec;
	}
	public String getNewSpec() {
		return newSpec;
	}
	public void setNewSpec(String newSpec) {
		this.newSpec = newSpec;
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

	public void downloadAttachments(ChangeMajorDTO form)
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
	
}