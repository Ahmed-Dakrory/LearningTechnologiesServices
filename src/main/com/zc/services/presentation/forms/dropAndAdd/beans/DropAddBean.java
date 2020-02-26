/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.beans;

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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.configuration.bean.FormsStatusBean;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya.alaa
 *
 */
@ManagedBean(name="DropAddBean")
@ViewScoped
public class DropAddBean {

	@ManagedProperty("#{StudentAddDropFormFacadeImpl}")
	private IStudentAddDropFormFacade facade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;

	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade coursefacade;
	
	@ManagedProperty("#{IFormsStatusFacade}")
	private IFormsStatusFacade formSettingFacade;
	
	@ManagedProperty("#{IMajorsFacade}")
	private IMajorsFacade majorFacade;
	
	@ManagedProperty("#{FormsStatusBean}")
	private FormsStatusBean formsStatusBean;
	
	
	private PersonDataDTO personDTO;
	private List<DropAddFormDTO> pendingForms;
	private List<DropAddFormDTO> archievedForms;
	private DropAddFormDTO selectedPendingForms;
	private List<DropAddFormDTO> filteredPendingForms;
	private List<DropAddFormDTO> selectedArchievedForms;
	private List<DropAddFormDTO> filteredArchievedForms;
	private List<BaseDTO> actions;
	private List<BaseDTO> actionsPhase1;
	private List<MajorDTO> majorsLst;
	private List<CoursesDTO> coursesList;
	private Integer selectedMajorId;
	private String mobile;
	private Integer selectedAction;
	private Integer selectedAddCourseID;
	private Integer selectedDropCourseID;
    private boolean showAddPnl;
    private boolean showDropPnl;
    private boolean showDropAddPnl;
    private String detailedAddedCourseName;
    private String detailedDroppedCourseName;
    private String detailedAddedSectionName;
    private String detailedDroppedSectionName;
	private String addedSection;
    private String droppedSection;
    private String detailedStatus;
    private String detailedContact;
    private String typeOfAction;
    private boolean renderAddAction;
    private boolean renderDropAction;
	private UploadedFile attachmentFile;
	private String detailedFormType;
    private List<InstructorDTO>insLst;
	private Integer selectedInsID;
	private Boolean repeatedCourse;
	private Boolean hasCourseLab;
	private String courseLab;
	private String mail;

    @ManagedProperty("#{IStudentProfileFacade}")
    private StudentProfileFacadeImpl profileFacade;

	@PostConstruct
	public void init()
	{
		pendingForms=new ArrayList<DropAddFormDTO>();
		archievedForms=new ArrayList<DropAddFormDTO>();
		actions=new ArrayList<BaseDTO>();
		actionsPhase1=new ArrayList<BaseDTO>();
		fillActions();
		fillActionsPhase1();
		majorsLst=new ArrayList<MajorDTO>();
		fillMajorsLst();
		coursesList=new ArrayList<CoursesDTO>();
		fillCoursesList();
		showAddPnl =false;
	    showDropPnl=false;
	    showDropAddPnl=false;
	    setSelectedAction(0);
	    setSelectedAddCourseID(0);
	    setSelectedDropCourseID(0);
	    setSelectedMajorId(0);
	    setMobile(null);
	    fillPendingForms();
	    fillArchievedForms();
	    renderDropAction=false;
	    renderAddAction=false;
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
	
	public void fillPendingForms()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			pendingForms=  facade.getPendingFormsOfStudent(getPersonDTO().getId());
		    if(pendingForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	
		
	}
	public void fillArchievedForms()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			archievedForms=  facade.getArchievedFormsOfStudent(getPersonDTO().getId());
		    if(archievedForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
	
		
	}
	public void fillActions(){
		//actions.add(new BaseDTO(1, "Add Course"));
		actions.add(new BaseDTO(2, "Drop Course"));
		//actions.add(new BaseDTO(3, "Drop and Add Course"));
	}
	public void fillActionsPhase1(){
		actionsPhase1.add(new BaseDTO(1, "Add Course"));
		actionsPhase1.add(new BaseDTO(2, "Drop Course"));
		actionsPhase1.add(new BaseDTO(3, "Drop and Add Course"));
	}
	public void fillMajorsLst(){
	//	majorsLst=facade.getAllMajors();
	//	majorsLst=facade.getAllMajors();
		majorsLst=majorFacade.getAll();
	}
    public void fillCoursesList()
    {
    	
    	FormsStatusDTO formSetting=formSettingFacade.getById(2);
    	coursesList=coursefacade.getAllCoursesBySemesterAndYear(formSetting.getSemester().getId(), formSetting.getYear());
    }	
    public void changePanels(AjaxBehaviorEvent event)
    {
    	if(getSelectedAction()==1)
    	{
    		showAddPnl =true;
    	    showDropPnl=false;
    	    showDropAddPnl=false;
    	 
    	}
    	else if(getSelectedAction()==(2))
    	{
    		showAddPnl =false;
    	    showDropPnl=true;
    	    showDropAddPnl=false;
    	}
    	else if(getSelectedAction()==3)
    	{
    		showAddPnl =false;
    	    showDropPnl=false;
    	    showDropAddPnl=true;
    	}
    }
    public void cancelPnls()
    {
    	init();
    }
    public void submitRequest( Integer choice)
   	{
   	try{
   		DropAddFormDTO request=new DropAddFormDTO();
   		
   		if(	formsStatusBean.isDropAdd())
   		{
   			request.setStep(PetitionStepsEnum.UNDER_PROCESSING);
   			request.setStatus(PetitionStepsEnum.UNDER_PROCESSING.getName()+" By Admission Department");
   		}
   		else {
   			request.setStep(PetitionStepsEnum.UNDER_REVIEW);
   			request.setStatus(PetitionStepsEnum.UNDER_REVIEW.getName());
   		}
   		StudentProfileDTO profile = profileFacade.getCurrentPRofileByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
		 if(profile!=null) {
	        	
			
			MajorDTO major=profile.getMajor();
			if(major!=null) {
				
				request.setMajor(major);
			}	
       	if(choice==1)//Add Action
   	{
   		CoursesDTO course=new CoursesDTO();
   		course.setId(getSelectedAddCourseID());
   		request.setAddedCourse(course);
   		request.setSubmittedDate(Calendar.getInstance());
   		StudentDTO student=new StudentDTO();
   		student.setId(getPersonDTO().getId());
   		student.setFacultyId(getPersonDTO().getFileNo());
           student.setMail(getPersonDTO().getEmail());
           student.setName(getPersonDTO().getNameInEng());
           student.setStudentProfileDTO(profile);
           request.setStudent(student);
           request.setType(AddDropFormTypesEnum.ADD);
           request.setRepeatedCourse(getRepeatedCourse());
           request.setCourseLab(getCourseLab());
           if(this.attachmentFile != null)
   		{
   			AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
   			request.setAttachments(attachment);
   		}
           
           request=facade.addForm(request);
           if(request!=null)
           {
           	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Form has been submitted successfully");
           	init();
           	
           	FacesContext.getCurrentInstance().getExternalContext().redirect
   			("addDropStudent.xhtml?id="+request.getId());
           	sharedAcademicPetFacade.notifayNextStepOwner(request);	
                 }
           else 
           {
           	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can't Be Submitted");
           }
   		
   	}
   	else if(choice==2)//Drop
   	{
   		request.setStep(PetitionStepsEnum.ADMISSION_PROCESSING);
			request.setStatus(PetitionStepsEnum.ADMISSION_PROCESSING.getName());
   		request.setType(AddDropFormTypesEnum.DROP);
   		CoursesDTO course=new CoursesDTO();
   		course.setId(getSelectedDropCourseID());
   		request.setDropCourse(course);
   		request.setSubmittedDate(Calendar.getInstance());
   			StudentDTO student=new StudentDTO();
   		student.setId(getPersonDTO().getId());
   		student.setFacultyId(getPersonDTO().getFileNo());
           student.setMail(getPersonDTO().getEmail());
           student.setName(getPersonDTO().getNameInEng());
           student.setStudentProfileDTO(profile);
           request.setStudent(student);
            request.setRepeatedCourse(getRepeatedCourse());
           request.setCourseLab(getCourseLab());
           if(this.attachmentFile != null)
   		{
   			AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
   			request.setAttachments(attachment);
   		}
           //TODO Comment it in phase one and two
          /* if(getSelectedInsID()!=null)
           {
           InstructorDTO insDTO=new InstructorDTO();
           insDTO.setId(getSelectedInsID());
           
           request.setDroppedCourseIns(insDTO);
           }
           */
           request=facade.addForm(request);
           if(request!=null)
           {
           	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Form has been submitted successfully");
           	init();
           	FacesContext.getCurrentInstance().getExternalContext().redirect
   			("addDropStudent.xhtml?id="+request.getId());
           	sharedAcademicPetFacade.notifayNextStepOwner(request);	
           	}
           else 
           {
           	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can Be Submitted");
           }
   	}
   	else if(choice==3)//Drop And Add
   	{
   		request.setType(AddDropFormTypesEnum.DROPANDADD);
   		CoursesDTO added=new CoursesDTO();
   		added.setId(getSelectedAddCourseID());
   		request.setAddedCourse(added);
   		CoursesDTO droped=new CoursesDTO();
   		droped.setId(getSelectedDropCourseID());
   		request.setDropCourse(droped);
   		request.setSubmittedDate(Calendar.getInstance());
   		StudentDTO student=new StudentDTO();
   		student.setId(getPersonDTO().getId());
   		student.setFacultyId(getPersonDTO().getFileNo());
           student.setMail(getPersonDTO().getEmail());
           student.setName(getPersonDTO().getNameInEng());
           student.setStudentProfileDTO(profile);
           request.setStudent(student);
           request.setAddedSection(addedSection);
           request.setDroppedSection(droppedSection);
           request.setRepeatedCourse(getRepeatedCourse());
           request.setCourseLab(getCourseLab());
           if(this.attachmentFile != null)
   		{
   			AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
   			request.setAttachments(attachment);
   		}
           
           request=facade.addForm(request);
           
   		
   		
           
         
           
           if(request!=null)
           {
           	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Form has been submitted successfully");
           	init();
           	FacesContext.getCurrentInstance().getExternalContext().redirect
   			("addDropStudent.xhtml?id="+request.getId());
           	sharedAcademicPetFacade.notifayNextStepOwner(request);	
           	}
         
          
   	}
       	
   	else if(choice==4)//Drop phase 3 
   	{
   		request.setType(AddDropFormTypesEnum.DROP);
   		CoursesDTO course=new CoursesDTO();
   		course.setId(getSelectedDropCourseID());
   		request.setDropCourse(course);
   		request.setSubmittedDate(Calendar.getInstance());
   		request.setPhone(getMobile());
   		StudentDTO student=new StudentDTO();
   		student.setId(getPersonDTO().getId());
   		student.setFacultyId(getPersonDTO().getFileNo());
           student.setMail(getPersonDTO().getEmail());
           student.setName(getPersonDTO().getNameInEng());
           student.setStudentProfileDTO(profile);
           request.setStudent(student);
           request.setRepeatedCourse(getRepeatedCourse());
           request.setCourseLab(getCourseLab());
           if(this.attachmentFile != null)
   		{
   			AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
   			request.setAttachments(attachment);
   		}
          
          if(getSelectedInsID()!=null)
           {
           InstructorDTO insDTO=new InstructorDTO();
           insDTO.setId(getSelectedInsID());
           
           request.setDroppedCourseIns(insDTO);
           request.setPhase(3);
           }
           
           request=facade.addForm(request);
           
           if(request!=null)
           {
           	JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Form has been submitted successfully");
           	init();
           	FacesContext.getCurrentInstance().getExternalContext().redirect
   			("addDropStudent.xhtml?id="+request.getId());
           	request.setPhase(3);
           	sharedAcademicPetFacade.notifayNextStepOwner(request);	
           	}
           else 
           {
           	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can Be Submitted");
           }
   	}
       	}
   	}
   	catch(Exception ex)
   	{
   		ex.printStackTrace();
   		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error");
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
	
	public void downloadAttachments(DropAddFormDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
    public void showDetails(DropAddFormDTO dto)
    {
    	/*RequestContext.getCurrentInstance().reset("detForm:detGrid");
    	
    	setDetailedContact(dto.getPhone());
    	setDetailedStatus(dto.getStatus());
    	setDetailedFormType(dto.getType().getName());
    	if(dto.getType().equals(AddDropFormTypesEnum.ADD))
    	{
    		setRenderAddAction(true);
    		setRenderDropAction(false);
    		
    		setDetailedAddedCourseName(dto.getAddedCourse().getName());
    	}
    	else if(dto.getType().equals(AddDropFormTypesEnum.DROP))
    	{
    		setRenderAddAction(false);
    		setRenderDropAction(true);
    		setDetailedDroppedCourseName(dto.getDropCourse().getName());
    	}
    	else if(dto.getType().equals(AddDropFormTypesEnum.DROPANDADD))
    	{
    		setRenderAddAction(true);
    		setRenderDropAction(true);
    		setDetailedAddedCourseName(dto.getAddedCourse().getName());
        	setDetailedDroppedCourseName(dto.getDropCourse().getName());
        	setDetailedAddedSectionName(dto.getAddedSection());
        	setDetailedDroppedSectionName(dto.getDroppedSection());
    	}
    	RequestContext.getCurrentInstance().execute("detDlg.show();");
		 FacesContext.getCurrentInstance().getPartialViewContext()
			.getRenderIds().add("detForm:detGrid");*/
    	try {
    		
    		
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml?id="+dto.getId()+"&cases=Stduent&oldMood=1");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    public void onRowSelect(SelectEvent event) {  
      	try {
          DropAddFormDTO selectedDTO=(DropAddFormDTO) event.getObject();
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"formDetails.xhtml?id=" + selectedDTO.getId()
									+ "&cases=Stduent&oldMood=1");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    public void fillInstructorsLst()
	{
		insLst=new ArrayList<InstructorDTO>();
		insLst=facade.getAllInstructorsOfCourse(getSelectedDropCourseID());
	}
    

	public IStudentAddDropFormFacade getFacade() {
		return facade;
	}

	public void setFacade(IStudentAddDropFormFacade facade) {
		this.facade = facade;
	}

	

	public void setPendingForms(List<DropAddFormDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public void setArchievedForms(List<DropAddFormDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	public PersonDataDTO getPersonDTO() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			mail = authentication.getName();
			System.out.print("Dakrory: "+String.valueOf(mail));
			return studentDataFacade.getPersonByPersonMail(mail);
		}
		
		else 
			return null;
	}

	public void setPersonDTO(PersonDataDTO personDTO) {
		this.personDTO = personDTO;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public List<BaseDTO> getActions() {
		return actions;
	}

	public void setActions(List<BaseDTO> actions) {
		this.actions = actions;
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

	public Integer getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(Integer selectedAction) {
		this.selectedAction = selectedAction;
	}

	public List<CoursesDTO> getCoursesList() {
		return coursesList;
	}

	public void setCoursesList(List<CoursesDTO> coursesList) {
		this.coursesList = coursesList;
	}

	public Integer getSelectedAddCourseID() {
		return selectedAddCourseID;
	}

	public void setSelectedAddCourseID(Integer selectedAddCourseID) {
		this.selectedAddCourseID = selectedAddCourseID;
	}

	public Integer getSelectedDropCourseID() {
		return selectedDropCourseID;
	}

	public void setSelectedDropCourseID(Integer selectedDropCourseID) {
		this.selectedDropCourseID = selectedDropCourseID;
	}

	public boolean isShowAddPnl() {
		return showAddPnl;
	}

	public void setShowAddPnl(boolean showAddPnl) {
		this.showAddPnl = showAddPnl;
	}

	public boolean isShowDropPnl() {
		return showDropPnl;
	}

	public void setShowDropPnl(boolean showDropPnl) {
		this.showDropPnl = showDropPnl;
	}

	public boolean isShowDropAddPnl() {
		return showDropAddPnl;
	}

	public void setShowDropAddPnl(boolean showDropAddPnl) {
		this.showDropAddPnl = showDropAddPnl;
	}

	

	public DropAddFormDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(DropAddFormDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public List<DropAddFormDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}

	public void setFilteredPendingForms(List<DropAddFormDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}

	public List<DropAddFormDTO> getPendingForms() {
		return pendingForms;
	}

	public List<DropAddFormDTO> getArchievedForms() {
		return archievedForms;
	}

	public List<DropAddFormDTO> getSelectedArchievedForms() {
		return selectedArchievedForms;
	}

	public void setSelectedArchievedForms(
			List<DropAddFormDTO> selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}

	public List<DropAddFormDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<DropAddFormDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	
	public String getDetailedAddedCourseName() {
		return detailedAddedCourseName;
	}

	public void setDetailedAddedCourseName(String detailedAddedCourseName) {
		this.detailedAddedCourseName = detailedAddedCourseName;
	}

	public String getDetailedDroppedCourseName() {
		return detailedDroppedCourseName;
	}

	public void setDetailedDroppedCourseName(String detailedDroppedCourseName) {
		this.detailedDroppedCourseName = detailedDroppedCourseName;
	}

	public String getDetailedStatus() {
		return detailedStatus;
	}

	public void setDetailedStatus(String detailedStatus) {
		this.detailedStatus = detailedStatus;
	}

	public String getDetailedContact() {
		return detailedContact;
	}

	public void setDetailedContact(String detailedContact) {
		this.detailedContact = detailedContact;
	}

	public String getTypeOfAction() {
		return typeOfAction;
	}

	public void setTypeOfAction(String typeOfAction) {
		this.typeOfAction = typeOfAction;
	}
		public UploadedFile getAttachmentFile() {
		    return attachmentFile;
		}

		public void setAttachmentFile(UploadedFile file) {
		    this.attachmentFile = file;
		}
		
		public boolean isRenderAddAction() {
			return renderAddAction;
		}

		public void setRenderAddAction(boolean renderAddAction) {
			this.renderAddAction = renderAddAction;
		}

		public boolean isRenderDropAction() {
			return renderDropAction;
		}

		public void setRenderDropAction(boolean renderDropAction) {
			this.renderDropAction = renderDropAction;
		}

		public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
			return sharedAcademicPetFacade;
		}

		public void setSharedAcademicPetFacade(
				ISharedAcademicPetFacade sharedAcademicPetFacade) {
			this.sharedAcademicPetFacade = sharedAcademicPetFacade;
		}

		public String getDroppedSection() {
			return droppedSection;
		}

		public void setDroppedSection(String droppedSection) {
			this.droppedSection = droppedSection;
		}

		public String getAddedSection() {
			return addedSection;
		}

		public void setAddedSection(String addedSection) {
			this.addedSection = addedSection;
		}

		public String getDetailedAddedSectionName() {
			return detailedAddedSectionName;
		}

		public void setDetailedAddedSectionName(String detailedAddedSectionName) {
			this.detailedAddedSectionName = detailedAddedSectionName;
		}

		public String getDetailedDroppedSectionName() {
			return detailedDroppedSectionName;
		}

		public void setDetailedDroppedSectionName(
				String detailedDroppedSectionName) {
			this.detailedDroppedSectionName = detailedDroppedSectionName;
		}

		public String getDetailedFormType() {
			return detailedFormType;
		}

		public void setDetailedFormType(String detailedFormType) {
			this.detailedFormType = detailedFormType;
		}

		public List<InstructorDTO> getInsLst() {
			return insLst;
		}

		public void setInsLst(List<InstructorDTO> insLst) {
			this.insLst = insLst;
		}

		public Integer getSelectedInsID() {
			return selectedInsID;
		}

		public void setSelectedInsID(Integer selectedInsID) {
			this.selectedInsID = selectedInsID;
		}

		public IStudentAcademicPetFacade getCoursefacade() {
			return coursefacade;
		}

		public void setCoursefacade(IStudentAcademicPetFacade coursefacade) {
			this.coursefacade = coursefacade;
		}

		public Boolean getRepeatedCourse() {
			return repeatedCourse;
		}

		public void setRepeatedCourse(Boolean repeatedCourse) {
			this.repeatedCourse = repeatedCourse;
		}

		public List<BaseDTO> getActionsPhase1() {
			return actionsPhase1;
		}

		public void setActionsPhase1(List<BaseDTO> actionsPhase1) {
			this.actionsPhase1 = actionsPhase1;
		}

		public IFormsStatusFacade getFormSettingFacade() {
			return formSettingFacade;
		}

		public void setFormSettingFacade(IFormsStatusFacade formSettingFacade) {
			this.formSettingFacade = formSettingFacade;
		}

		public Boolean getHasCourseLab() {
			return hasCourseLab;
		}

		public void setHasCourseLab(Boolean hasCourseLab) {
			this.hasCourseLab = hasCourseLab;
		}

		public String getCourseLab() {
			return courseLab;
		}

		public void setCourseLab(String courseLab) {
			this.courseLab = courseLab;
		}

		public IMajorsFacade getMajorFacade() {
			return majorFacade;
		}

		public void setMajorFacade(IMajorsFacade majorFacade) {
			this.majorFacade = majorFacade;
		}

	
		public FormsStatusBean getFormsStatusBean() {
			return formsStatusBean;
		}

		public void setFormsStatusBean(FormsStatusBean formsStatusBean) {
			this.formsStatusBean = formsStatusBean;
		}

		public StudentProfileFacadeImpl getProfileFacade() {
			return profileFacade;
		}

		public void setProfileFacade(StudentProfileFacadeImpl profileFacade) {
			this.profileFacade = profileFacade;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}

		
	
}
