/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formAdminFacade;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
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
@ManagedBean
@ViewScoped
public class course_replacement_formAdminBean {

	@ManagedProperty("#{course_replacement_formAdminFacadeImpl}")
	private Icourse_replacement_formAdminFacade facade;
	
	
	private List<course_replacement_formDTO> pendingForms;
	private List<course_replacement_formDTO> archievedForms;
	private List<course_replacement_formDTO> selectedPendingForms;
	private List<course_replacement_formDTO> filteredPendingForms;
	private List<course_replacement_formDTO> selectedArchievedForms;
	private List<course_replacement_formDTO> filteredArchievedForms;
	private course_replacement_formDTO detailedForm;
	
	/**
	 * @author Omnya
	 *
	 */
	private Integer loginCase;
	private boolean detailView;
	/**/
	
	public void downloadAttachments(course_replacement_formDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	
	/**
	 * @Edited By  Omnya
	 *
	 */
	
	@PostConstruct
	public void init()

	{	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
		//if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
		if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
				authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.ADMISSION_HEAD)||
				authentication.getName().equals(Constants.ADMISSION_DEPT))
		{
			fillPendingFormLst();
			fillArchievedPetitionsLst();
			detailedForm=new course_replacement_formDTO();
		    
			//It will be use in rendering of tabs tp leave in-progress tab to right direction
			if(authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
		    {
		    	setLoginCase(1);
		    }
		    else if(authentication.getName().equals(Constants.ADMISSION_HEAD))
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
		pendingForms=new ArrayList<course_replacement_formDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			/**
			 * @Edited By  Omnya
			 *
			 */
			// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
			//if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
			if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.ADMISSION_HEAD)||
					authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				pendingForms=facade.getPendingPetitionsOfstuent();
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
	
	public void fillArchievedPetitionsLst()
	{archievedForms=new ArrayList<course_replacement_formDTO>();
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
	{
		String mail = authentication.getName();
		if(mail.equals(Constants.LTS_SYSTEM_ADMIN) || mail.equals(Constants.DEAN_OF_STRATEGIC)){
			archievedForms=facade.getArchievedPetitionsOfstuent();
		}
		/**
		 * @Edited By  Omnya
		 *
		 */
		else if(authentication.getName().equals(Constants.ADMISSION_DEPT)||authentication.getName().equals(Constants.ADMISSION_HEAD)||
				authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)){
			//DO NOTHING
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
		}
	}
	}
	public void showDetails(course_replacement_formDTO form)
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
			}
    		
    		
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
	public void notifyUser()
	{
		facade.notifyNextUser(getDetailedForm());
	}
	
	public Icourse_replacement_formAdminFacade getFacade() {
		return facade;
	}
	public void setFacade(Icourse_replacement_formAdminFacade facade) {
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
	public List<course_replacement_formDTO> getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(List<course_replacement_formDTO> selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<course_replacement_formDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<course_replacement_formDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public List<course_replacement_formDTO> getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(
			List<course_replacement_formDTO> selectedArchievedForms) {
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
