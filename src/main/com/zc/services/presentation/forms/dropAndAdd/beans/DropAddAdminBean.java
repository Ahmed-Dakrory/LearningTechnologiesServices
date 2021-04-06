/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.beans;

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

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.impl.AdminAddDropFormFacadeImpl;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.impl.AdmissionHAddDropFormFacadeImpl;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author momen
 *
 */
@ManagedBean
@ViewScoped
public class DropAddAdminBean {
	
	@ManagedProperty("#{AdminAddDropFormFacadeImpl}")
	private AdminAddDropFormFacadeImpl facade;
	
	private List<DropAddFormDTO> pendingForms;
	private List<DropAddFormDTO> archievedForms;
	private List<DropAddFormDTO> selectedPendingForms;
	private List<DropAddFormDTO> filteredPendingForms;
	private List<DropAddFormDTO> selectedArchievedForms;
	private List<DropAddFormDTO> filteredArchievedForms;
	private DropAddFormDTO detailedForm;
	private boolean renderAddAction;
    private boolean renderDropAction;
	/**
	 * @author Omnya
	 *
	 */
	private Integer loginCase;
	private boolean detailView;
	
	public void downloadAttachments(DropAddFormDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	
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
					authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				fillPendingFormLst();
				fillArchievedPetitionsLst();
				detailedForm=new DropAddFormDTO();
			    
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
		pendingForms=new ArrayList<DropAddFormDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			
			/**
			 * @Edited By  Omnya
			 *
			 */
			// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
			/*if(mail.equals(Constants.LTS_SYSTEM_ADMIN)  || mail.equals(Constants.DEAN_OF_STRATEGIC)){
			
		}*/
			if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
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
		archievedForms=new ArrayList<DropAddFormDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.LTS_SYSTEM_ADMIN)  || mail.equals(Constants.DEAN_OF_STRATEGIC)){
				archievedForms=facade.getArchievedFormsOfAdmissionHead();
				
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
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
	
	public void showDetails(DropAddFormDTO form)
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
				if(form.getType().equals(AddDropFormTypesEnum.ADD))
		    	{
		    		setRenderAddAction(true);
		    		setRenderDropAction(false);
		    		
		    		
		    	}
		    	else if(form.getType().equals(AddDropFormTypesEnum.DROP))
		    	{
		    		setRenderAddAction(false);
		    		setRenderDropAction(true);
		    		
		    	}
		    	else if(form.getType().equals(AddDropFormTypesEnum.DROPANDADD))
		    	{
		    		setRenderAddAction(true);
		    		setRenderDropAction(true);
		    		
		    	}
				
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
	
	 public void onRowSelect(SelectEvent event) {  
	      	try {
	          DropAddFormDTO selectedDTO=(DropAddFormDTO) event.getObject();
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
	
	public AdminAddDropFormFacadeImpl getFacade() {
		return facade;
	}

	public void setFacade(AdminAddDropFormFacadeImpl facade) {
		this.facade = facade;
	}

	public List<DropAddFormDTO> getPendingForms() {
		return pendingForms;
	}

	public void setPendingForms(List<DropAddFormDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public List<DropAddFormDTO> getArchievedForms() {
		return archievedForms;
	}

	public void setArchievedForms(List<DropAddFormDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	public List<DropAddFormDTO> getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(List<DropAddFormDTO> selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public List<DropAddFormDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}

	public void setFilteredPendingForms(List<DropAddFormDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
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

	public DropAddFormDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(DropAddFormDTO detailedForm) {
		this.detailedForm = detailedForm;
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
