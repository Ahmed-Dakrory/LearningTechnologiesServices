/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.bean;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdminOverloadRequestFacade;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdmissionHOverloadRequestFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author momen
 *
 */
@ManagedBean(name="AdminOverloadRequestBean")
@ViewScoped
public class AdminOverloadRequestBean {

	@ManagedProperty("#{AdminOverloadRequestFacadeImpl}")
	private IAdminOverloadRequestFacade facade;
	
	private List<OverloadRequestDTO> pendingForms;
	private List<OverloadRequestDTO> archievedForms;
	private OverloadRequestDTO selectedPendingForms;
	private List<OverloadRequestDTO> filteredPendingForms;
	private List<OverloadRequestDTO> selectedArchievedForms;
	private List<OverloadRequestDTO> filteredArchievedForms;
	private OverloadRequestDTO detailedForm;
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
		//if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
		if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
				authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
				authentication.getName().equals(Constants.ADMISSION_DEPT)||
				authentication.getName().equals(Constants.PROVOST))
		{
			fillPendingForms();
			fillArchievedForms();
			detailedForm=new OverloadRequestDTO();
		    
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
		    else if(authentication.getName().equals(Constants.PROVOST))
		    {
		    	setLoginCase(4);
		    }
		}
		else 
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Allowed Only for System Adminstrator");
		}
	
	}
	
	}
	public void fillPendingForms()
	{
		pendingForms=new ArrayList<OverloadRequestDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		/**
		 * @Edited By  Omnya
		 *
		 */
		// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
		/*if(mail.equals(Constants.LTS_SYSTEM_ADMIN)  || mail.equals(Constants.DEAN_OF_STRATEGIC)){
		
	}*/
		if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
				authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
				authentication.getName().equals(Constants.ADMISSION_DEPT)||authentication.getName().equals(Constants.PROVOST))
		{
			pendingForms=  facade.getPendingForms();
		    if(pendingForms==null)
		    {
		    	JavaScriptMessagesHandler.RegisterErrorMessage(null, "Error In getting Petitions");
		    }
		}
		else 
		{
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/LearningTechnologiesServices/pages/public/login.xhtml?faces-redirect=true");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void fillArchievedForms()
	{
		archievedForms=new ArrayList<OverloadRequestDTO>();
	
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.LTS_SYSTEM_ADMIN)  || mail.equals(Constants.DEAN_OF_STRATEGIC)){
				archievedForms=  facade.getArchievedForms();	
				
			}
			/**
			 * @Edited By  Omnya
			 *
			 */
			else if(authentication.getName().equals(Constants.ADMISSION_DEPT)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.PROVOST)){
				//DO NOTHING
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}
	public void showDetails(OverloadRequestDTO form)
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
    		else 	if(loginCase.equals(1))
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
			else if(loginCase.equals(4))
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Provost&oldMood=1");
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
	  		OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
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
	public void downloadAttachments(OverloadRequestDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	public IAdminOverloadRequestFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdminOverloadRequestFacade facade) {
		this.facade = facade;
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
	public void setFilteredPendingForms(
			List<OverloadRequestDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public List<OverloadRequestDTO> getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(
			List<OverloadRequestDTO> selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<OverloadRequestDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<OverloadRequestDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public OverloadRequestDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(OverloadRequestDTO detailedForm) {
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
