/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.bean;

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
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorAdminFacade;
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
public class ChangeMajorAdminBean {

	@ManagedProperty("#{ChangeMajorAdminFacadeImpl}")
	private IChangeMajorAdminFacade facade;
	
	
	private List<ChangeMajorDTO> pendingForms;
	private List<ChangeMajorDTO> archievedForms;
	private List<ChangeMajorDTO> selectedPendingForms;
	private List<ChangeMajorDTO> filteredPendingForms;
	private List<ChangeMajorDTO> selectedArchievedForms;
	private List<ChangeMajorDTO> filteredArchievedForms;
	private ChangeMajorDTO detailedForm;
	
	/**
	 * @author Omnya
	 *
	 */
	private Integer loginCase;
	private boolean detailView;
	/**/
	
	public void downloadAttachments(ChangeMajorDTO form)
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
				authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
				authentication.getName().equals(Constants.ADMISSION_DEPT))
		{
			fillPendingFormLst();
			fillArchievedPetitionsLst();
			detailedForm=new ChangeMajorDTO();
		    
			//It will be use in rendering of tabs tp leave in-progress tab to right direction
			if(authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
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
		pendingForms=new ArrayList<ChangeMajorDTO>();
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
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
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
	{archievedForms=new ArrayList<ChangeMajorDTO>();
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
		else if(authentication.getName().equals(Constants.ADMISSION_DEPT)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
				authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)){
			//DO NOTHING
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
		}
	}
	}
	public void showDetails(ChangeMajorDTO form)
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
		  		ChangeMajorDTO selectedDTO=(ChangeMajorDTO) event.getObject();
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
	
	public IChangeMajorAdminFacade getFacade() {
		return facade;
	}
	public void setFacade(IChangeMajorAdminFacade facade) {
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
	public List<ChangeMajorDTO> getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(List<ChangeMajorDTO> selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<ChangeMajorDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<ChangeMajorDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public List<ChangeMajorDTO> getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(
			List<ChangeMajorDTO> selectedArchievedForms) {
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
