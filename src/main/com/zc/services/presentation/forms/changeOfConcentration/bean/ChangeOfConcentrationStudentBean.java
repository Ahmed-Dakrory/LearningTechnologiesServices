/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.bean;

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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeOfConcentrationStudentFacade;
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
@ManagedBean
@ViewScoped
public class ChangeOfConcentrationStudentBean {

	private List<MajorDTO> majorsLst;
	private Integer selectedMajorId;
	private Integer currentConcentrationID;
	private Integer newConcentrationID;
	private String mobile;
	private Double gpa;
	private String moreDetails;
	private List<BaseDTO> concentrationLst;
	private List<ChangeConcentrationDTO> pendingForms;
	private ChangeConcentrationDTO selectedPendingForms;
	private List<ChangeConcentrationDTO> filteredPendingForms;
	private List<ChangeConcentrationDTO> archievedForms;
	private ChangeConcentrationDTO selectedArchievedForms;
	private List<ChangeConcentrationDTO> filteredArchievedForms;
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
    
    @ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
    
    @ManagedProperty("#{IChangeOfConcentrationStudentFacade}")
    IChangeOfConcentrationStudentFacade facade;
    
    @PostConstruct
    public void init(){
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
    	if(mail.startsWith("S-")||mail.startsWith("s-")){
			
    	majorsLst=new ArrayList<MajorDTO>();
		fillMajorsLst();
		fillPendingFormLst();
		fillArchievedPetitionsLst();
    	}}
    }
	public void submitRequest()
	{
		
		try{
		ChangeConcentrationDTO request=new ChangeConcentrationDTO();
		
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
		        request.setSubmissionDate(Calendar.getInstance());
		        MajorDTO major=new MajorDTO();
				major.setId(getSelectedMajorId());
				request.setMajor(major);
				request.setStep(PetitionStepsEnum.UNDER_REVIEW);
		        request.setMoreDetails(getMoreDetails());
		        BaseDTO currentConcen=new BaseDTO();
		        BaseDTO newConcen=new BaseDTO();
		        currentConcen.setId(getCurrentConcentrationID());
		        request.setCurrentConcen(currentConcen);
		        newConcen.setId(getNewConcentrationID());
		        request.setNewConcen(newConcen);
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
					("changeOfConcentrationStudent.xhtml?id="+request.getId());
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
	public void fillMajorsLst(){
		majorsLst=facade.getAllMajors();
	}
	public void fillConcentrations(AjaxBehaviorEvent event)
	{
		concentrationLst=facade.getConcentrationsByMajor(getSelectedMajorId());
		setCurrentConcentrationID(null);
		setNewConcentrationID(null);
	}
	public void fillPendingFormLst()
	{
		pendingForms=new ArrayList<ChangeConcentrationDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				pendingForms=facade.getPendingPetitionsByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Not Allowed To see This Petitions");
			}
		}
	}
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<ChangeConcentrationDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.startsWith("S-")||mail.startsWith("s-")){
			archievedForms=facade.getArchievedPetitionsByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, 	"Not Allowed To see This Petitions");
		}
	}
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		ChangeConcentrationDTO selectedDTO=(ChangeConcentrationDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void showDetails(ChangeConcentrationDTO form)
	{
		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Stduent&oldMood=1");
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
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
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public IChangeOfConcentrationStudentFacade getFacade() {
		return facade;
	}
	public void setFacade(IChangeOfConcentrationStudentFacade facade) {
		this.facade = facade;
	}
	public List<BaseDTO> getConcentrationLst() {
		return concentrationLst;
	}
	public void setConcentrationLst(List<BaseDTO> concentrationLst) {
		this.concentrationLst = concentrationLst;
	}
	public Integer getCurrentConcentrationID() {
		return currentConcentrationID;
	}
	public void setCurrentConcentrationID(Integer currentConcentrationID) {
		this.currentConcentrationID = currentConcentrationID;
	}
	public Integer getNewConcentrationID() {
		return newConcentrationID;
	}
	public void setNewConcentrationID(Integer newConcentrationID) {
		this.newConcentrationID = newConcentrationID;
	}
	public List<ChangeConcentrationDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<ChangeConcentrationDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public ChangeConcentrationDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(ChangeConcentrationDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<ChangeConcentrationDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<ChangeConcentrationDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public List<ChangeConcentrationDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<ChangeConcentrationDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public ChangeConcentrationDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(ChangeConcentrationDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<ChangeConcentrationDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(List<ChangeConcentrationDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	
	
	
}
