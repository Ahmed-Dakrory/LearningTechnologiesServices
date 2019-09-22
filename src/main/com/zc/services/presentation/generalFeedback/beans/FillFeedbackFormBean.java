/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.beans;

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

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.generalFeedback.service.ICategoriesAppService;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ITAJuniorProgramFacadeStudent;
import main.com.zc.services.presentation.generalFeedback.dto.CategoryDTO;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.generalFeedback.facade.IAddFeedbackFormFacade;
import main.com.zc.services.presentation.generalFeedback.facade.IFeedbacksFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.appService.ILoginSecurityAppService;
import main.com.zc.shared.presentation.dto.AttachmentDTO;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Omnya Alaa
 * 
 */
@ManagedBean(name = "fillFeedbackFormBean")
@ViewScoped
public class FillFeedbackFormBean {
	private String feedBackText;
	public String fileNo;
	private boolean willUpdated;
	private String text;
	private boolean editMood;
	private FeedbackDTO updatedFeedback;
	private List<FeedbackDTO> feedbacks;
	private List<FeedbackDTO> filteredFeedback;
	private List<FeedbackDTO> selectedFeedback;
	private List<CategoryDTO> categories;
	private List<FeedbackDTO> pendingFeedback;
	private List<FeedbackDTO> oldFeedback;
	private String studentName;
	private FeedbackDTO deletedFeedback;
	private String categoryName;
	private Integer categoryID;
	private String title;
	private FeedbackDTO detailedDTO;
	private FeedbackDTO submittedFeedback;
	private List<MajorDTO> majorLst;
	/*
	 * @Autowired IAddFeedbackFormFacade feedbackFacade;
	 */
	@ManagedProperty("#{addFeedbackFormFacadeImpl}")
	private IAddFeedbackFormFacade feedbackFacade;

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;

	
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
    
	@ManagedProperty("#{loginSecurityAppServiceImpl}")
	private ILoginSecurityAppService loginSecAppService;
	@ManagedProperty("#{categoriesAppServiceImpl}")
	private ICategoriesAppService cateAppService;

	@ManagedProperty("#{feedbacksFacadeImpl}")
	private IFeedbacksFacade facade;
	
	@ManagedProperty("#{IMajorsFacade}")
	private IMajorsFacade majorFacade;
	
	
	@PostConstruct
	public void init() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))

		{
			setEditMood(false);

			fillCategories();
			setTitle(null);
			setText(null);
			setCategoryID(null);
			fillFeedbaks();
			fillPendingFeedback();
			fillOldFeedback();
			fillMajorLst();
			submittedFeedback=new FeedbackDTO();
		}
	}



	public void fillCategories() {
		categories = cateAppService.getAll();
	}

	

	

	public void cancel() {
		editMood = false;
		setText("");
		setCategoryID(0);
		init();
	}



	public void addFeedback() {
		try {
			if (getCategoryID() != 0 && getCategoryID() != null) {

				Authentication authentication = SecurityContextHolder
						.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))

				{

					if (!getText().equals("") && getText() != null
							&& !getText().trim().equals("")) {
						System.out.println("Name >> " + getCategoryName());
						String mail = authentication.getName();
						LoginStaffDTO dto = loginSecAppService
								.getUserByMail(mail);

						FeedbackDTO feedbackDto = new FeedbackDTO();
						feedbackDto.setCategoryID(getCategoryID());
						feedbackDto.setFeedbackForm(getText());
						feedbackDto.setTitle(getTitle());
						feedbackDto.setCategoryID(getCategoryID());
						feedbackDto.setStudentID(dto.getId());
						feedbackDto.setSubmittedDate(Calendar.getInstance());

						feedbackDto = feedbackFacade
								.addFeedbackForm(feedbackDto);
						if (feedbackDto != null)

						{
							JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Submitted Successfully");
							init();
							RequestContext.getCurrentInstance().execute("addDlg.hide();");
						} else
							JavaScriptMessagesHandler.RegisterErrorMessage(null,"Form Can't be submitted");

						selectedFeedback = new ArrayList<FeedbackDTO>();
						setText("");
						setCategoryID(0);
						cancel();
					} else
						JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Is Empty");
				}
			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Please Choose Category");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage(null,"Form Can't be submitted");
		}
	}

	public void fillFeedbaks()
	{
		try
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				String mail = authentication.getName();
				
					List<FeedbackDTO> feedbacks=feedbackFacade.getfeedbacksByEmail(mail);
					setFeedbacks(feedbacks);
			
				
				}
			
		}
		catch(Exception ex)
		{
			
		}
	}
	public void fillPendingFeedback(){
		try
		
		{
			pendingFeedback=new ArrayList<FeedbackDTO>();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				String mail = authentication.getName();
				
					//List<FeedbackDTO> feedbacks=feedbackFacade.getfeedbacksByEmail(mail);
					List<FeedbackDTO> feedbacks=facade.getPendingFeedbacks(studentDataFacade.getPersonByPersonMail(mail).getId());
					
					pendingFeedback=feedbacks;
			
				
				}
			
		}
		catch(Exception ex)
		{
			
		}
	}
	
	public void fillOldFeedback(){
		try
		{
			oldFeedback=new ArrayList<FeedbackDTO>();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				String mail = authentication.getName();
				
					//List<FeedbackDTO> feedbacks=feedbackFacade.getfeedbacksByEmail(mail);
					List<FeedbackDTO> feedbacks=facade.getOldFeedbacks(studentDataFacade.getPersonByPersonMail(mail).getId());
					
					oldFeedback=feedbacks;
			
				
				}
			
		}
		catch(Exception ex)
		{
			
		}
	}
public void showFeedbackDetails(FeedbackDTO dto)
	{
		RequestContext.getCurrentInstance().reset("detForm:detGrid");
		setDetailedDTO(dto);
		RequestContext.getCurrentInstance().execute("detDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("detForm:detGrid");
	}

/////////////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
public void showDetails(FeedbackDTO form)
{
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("detailedFeedback.xhtml?id="+form.getId()+"&cases=Stduent&oldMood=1");
    		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
public void fillMajorLst()
{
	majorLst=majorFacade.getAll();
	
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
public void onRowSelect(SelectEvent event) {  
  	try {
  		FeedbackDTO selectedDTO=(FeedbackDTO) event.getObject();
		showDetails(selectedDTO);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}  
public void submitForm(){
	
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				String mail = authentication.getName();
				if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
					  StudentDTO student=new StudentDTO();
					  student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
					//TODO 
						//1-Set step of Form ,set student , set Date
						getSubmittedFeedback().setStep(PetitionStepsEnum.UNDER_REVIEW);
						getSubmittedFeedback().setStudent(student);
						getSubmittedFeedback().setSubmittedDate(Calendar.getInstance());
						//Check if the category is not academic 
						if(getSubmittedFeedback().getCategoryID()!=1)
						{
							getSubmittedFeedback().setMajor(null);
						}
						if(this.attachmentFile != null)
						{
							AttachmentDTO attachment = new AttachmentDTO(attachmentFile.getFileName(), attachmentFile.getContents());
							getSubmittedFeedback().setAttachments(attachment);
							
						}
						
						//2-Save the form into DB
							FeedbackDTO addedFeedback=facade.add(getSubmittedFeedback());
							
						//3-Notify the handler with the feedback
							if(addedFeedback!=null)
					        {
					        	
					        	init();
					        	
					        	FacesContext.getCurrentInstance().getExternalContext().redirect
								("pendingFeedback.xhtml?id="+addedFeedback.getId());
					        	JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
					        	
					        	
					        }
					        else 
					        {
					        	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can't Be Submitted");
					        }
							
							//TODO
						//4-Navigate to the pending page	
							//sharedAcademicPetFacade.notifayNextStepOwner(dto);
				}}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

}


	public String getFeedBackText() {
		return feedBackText;
	}

	public void setFeedBackText(String feedBackText) {
		this.feedBackText = feedBackText;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public boolean isWillUpdated() {
		return willUpdated;
	}

	public void setWillUpdated(boolean willUpdated) {
		this.willUpdated = willUpdated;
	}

	public List<FeedbackDTO> getFilteredFeedback() {
		return filteredFeedback;
	}

	public void setFilteredFeedback(List<FeedbackDTO> filteredFeedback) {
		this.filteredFeedback = filteredFeedback;
	}

	public List<FeedbackDTO> getSelectedFeedback() {
		return selectedFeedback;
	}

	public void setSelectedFeedback(List<FeedbackDTO> selectedFeedback) {
		this.selectedFeedback = selectedFeedback;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isEditMood() {
		return editMood;
	}

	public void setEditMood(boolean editMood) {
		this.editMood = editMood;
	}

	public FeedbackDTO getUpdatedFeedback() {
		return updatedFeedback;
	}

	public void setUpdatedFeedback(FeedbackDTO updatedFeedback) {
		this.updatedFeedback = updatedFeedback;
	}


	public FeedbackDTO getDeletedFeedback() {
		return deletedFeedback;
	}

	public void setDeletedFeedback(FeedbackDTO deletedFeedback) {
		this.deletedFeedback = deletedFeedback;
	}

	public List<FeedbackDTO> getFeedbacks() {
		
	
		return feedbacks;
	}

	public void setFeedbacks(List<FeedbackDTO> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public IAddFeedbackFormFacade getFeedbackFacade() {
		return feedbackFacade;
	}

	public void setFeedbackFacade(IAddFeedbackFormFacade feedbackFacade) {
		this.feedbackFacade = feedbackFacade;
	}

	public ILoginSecurityAppService getLoginSecAppService() {
		return loginSecAppService;
	}

	public void setLoginSecAppService(
			ILoginSecurityAppService loginSecAppService) {
		this.loginSecAppService = loginSecAppService;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	public ICategoriesAppService getCateAppService() {
		return cateAppService;
	}

	public void setCateAppService(ICategoriesAppService cateAppService) {
		this.cateAppService = cateAppService;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public FeedbackDTO getDetailedDTO() {
		return detailedDTO;
	}

	public void setDetailedDTO(FeedbackDTO detailedDTO) {
		this.detailedDTO = detailedDTO;
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



	public FeedbackDTO getSubmittedFeedback() {
		return submittedFeedback;
	}



	public void setSubmittedFeedback(FeedbackDTO submittedFeedback) {
		this.submittedFeedback = submittedFeedback;
	}



	public List<FeedbackDTO> getPendingFeedback() {
		return pendingFeedback;
	}



	public void setPendingFeedback(List<FeedbackDTO> pendingFeedback) {
		this.pendingFeedback = pendingFeedback;
	}



	public List<FeedbackDTO> getOldFeedback() {
		return oldFeedback;
	}



	public void setOldFeedback(List<FeedbackDTO> oldFeedback) {
		this.oldFeedback = oldFeedback;
	}



	public IFeedbacksFacade getFacade() {
		return facade;
	}



	public void setFacade(IFeedbacksFacade facade) {
		this.facade = facade;
	}





	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}



	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}



	public List<MajorDTO> getMajorLst() {
		return majorLst;
	}



	public void setMajorLst(List<MajorDTO> majorLst) {
		this.majorLst = majorLst;
	}

}
