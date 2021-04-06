/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdminFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdmissionHeadFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author momen
 *
 */
@ManagedBean(name="CourseRepeatAdminBean")
@ViewScoped
public class CourseRepeatAdminBean {

	@ManagedProperty("#{ICourseRepeatAdminFacade}")
	private ICourseRepeatAdminFacade facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	private List<CourseRepeatDTO> pendingForms;
	private List<CourseRepeatDTO> archievedForms;
	private CourseRepeatDTO selectedPendingForms;
	private List<CourseRepeatDTO> filteredPendingForms;
	private CourseRepeatDTO selectedArchievedForms;
	private List<CourseRepeatDTO> filteredArchievedForms;
	private CourseRepeatDTO detailedForm;
	private String newComment;
	
	/**
	 * @author Omnya
	 *
	 */
	private Integer loginCase;
	private boolean detailView;
	
	/**
	 * @Edited By  Omnya
	 *
	 */
	@PostConstruct
	public void init()

	{
		
	
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
			//if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_ACADEMIC))
			if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_ACADEMIC)||
					authentication.getName().equals(Constants.DEAN_OF_ACADEMIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
					authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				fillPendingFormLst();
				fillArchievedPetitionsLst();
				detailedForm=new CourseRepeatDTO();
			    
				//It will be use in rendering of tabs tp leave in-progress tab to right direction
				if(authentication.getName().equals(Constants.DEAN_OF_ACADEMIC))
			    {
			    	setLoginCase(1);
			    }
			    else if(authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL))
			    {
			    	setLoginCase(2);
			    }
			    else if(authentication.getName().equals(Constants.ADMISSION_DEPT))
			    {
			    	setLoginCase(3);
			    }
			}
			else 
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for System Adminstrator");
			}
		}
			
	
	}
	public void fillPendingFormLst()
	{
		pendingForms=new ArrayList<CourseRepeatDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			/**
			 * @Edited By  Omnya
			 *
			 */
			
			/*if(mail.equals(Constants.LTS_SYSTEM_ADMIN) || mail.equals(Constants.DEAN_OF_ACADEMIC)){
				pendingForms=facade.getPendingFormsOfAdmissionHead();*/
			// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
			
			//if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_ACADEMIC))
			if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_ACADEMIC)||
					authentication.getName().equals(Constants.DEAN_OF_ACADEMIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
					authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				pendingForms=facade.getPendingFormsOfAdmissionHead();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}

	public void fillArchievedPetitionsLst()
	{
		archievedForms=new ArrayList<CourseRepeatDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.LTS_SYSTEM_ADMIN) || mail.equals(Constants.DEAN_OF_ACADEMIC)){
				archievedForms=facade.getArchievedFormsOfAdmissionHead();
				
			}
			else if(authentication.getName().equals(Constants.ADMISSION_DEPT)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
					authentication.getName().equals(Constants.DEAN_OF_ACADEMIC)){
				//DO NOTHING
			}
			/**
			 * @Edited By  Omnya
			 *
			 */
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
	/**
	 * @Edited By  Omnya
	 *
	 */
	public void showDetails(CourseRepeatDTO form)
	{
		
			
			try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    	
	    		if(url.toString().contains("Admin")) // admin case
				{
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+form.getId()+"&cases=Admin&oldMood=1");
				}
	// other than admin    		
	    		else if(loginCase.equals(1))
				{
	    		
	        		
	        		FacesContext.getCurrentInstance().getExternalContext().redirect
	    			("formDetails.xhtml?id="+form.getId()+"&cases=Dean&oldMood=1");
	        		
	        		
	    			
				}
				else if(loginCase.equals(2))
				{
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionH&oldMood=1");
				}
				else if(loginCase.equals(3))
				{
					FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionD&oldMood=1");
				}
				else  // admin case
				{
					RequestContext.getCurrentInstance().reset("detForm:detGrid");
					setDetailedForm(form);
					RequestContext.getCurrentInstance().execute("detDlg.show();");
					 FacesContext.getCurrentInstance().getPartialViewContext()
						.getRenderIds().add("detForm:detGrid");
			
						if (!url.toString().contains("1")) {
							FacesContext.getCurrentInstance().getPartialViewContext()
									.getRenderIds().add("detForm:dialogBtn");
							RequestContext.getCurrentInstance().reset("detForm:dialogBtn");
						}
				}
	    		
	    		
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * @author Omnya
	 *
	 */
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		CourseRepeatDTO selectedDTO=(CourseRepeatDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public void notifyUser()
	{
		facade.notifyNextUser(getDetailedForm());
	}
	
	public void downloadAttachments(CourseRepeatDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	public ICourseRepeatAdminFacade getFacade() {
		return facade;
	}
	public void setFacade(ICourseRepeatAdminFacade facade) {
		this.facade = facade;
	}
	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}
	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
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

	public CourseRepeatDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(CourseRepeatDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
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
	public CourseRepeatDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(CourseRepeatDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public String getNewComment() {
		return newComment;
	}
	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}
	public Integer getLoginCase() {
		return loginCase;
	}
	public void setLoginCase(Integer loginCase) {
		this.loginCase = loginCase;
	}
	public boolean isDetailView() {
		return detailView;
	}
	public void setDetailView(boolean detailView) {
		this.detailView = detailView;
	}
	
	
}
