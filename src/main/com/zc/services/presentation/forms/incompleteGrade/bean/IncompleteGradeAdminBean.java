/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.bean;

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
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeAdminFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="IncompleteGradeAdminBean")
@ViewScoped
public class IncompleteGradeAdminBean {

	@ManagedProperty("#{IIncompleteGradeAdminFacade}")
	private IIncompleteGradeAdminFacade facade;
	
	
	private List<IncompleteGradeDTO> pendingForms;
	private List<IncompleteGradeDTO> archievedForms;
	private IncompleteGradeDTO selectedPendingForms;
	private List<IncompleteGradeDTO> filteredPendingForms;
	private IncompleteGradeDTO selectedArchievedForms;
	private List<IncompleteGradeDTO> filteredArchievedForms;
	private IncompleteGradeDTO detailedForm;
	private boolean renderAddAction;
    private boolean renderDropAction;
    private Integer loginCase;
	private boolean detailView;
	
	@PostConstruct
	public void init()

	{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
					authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				fillPendingFormLst();
				fillArchievedPetitionsLst();
				detailedForm=new IncompleteGradeDTO();
			    
				//It will be use in rendering of tabs to leave in-progress tab to right direction
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
		pendingForms=new ArrayList<IncompleteGradeDTO>();
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
				pendingForms=facade.getPendingForms();
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}

	public void fillArchievedPetitionsLst()
	{
		archievedForms=new ArrayList<IncompleteGradeDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.LTS_SYSTEM_ADMIN)  || mail.equals(Constants.DEAN_OF_STRATEGIC)){
				archievedForms=facade.getArchievedForms();
				
			}
			/**
			 * @Edited By  Omnya
			 *
			 */
			if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL)||
					authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				//DO NOTHING
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
	
	public void showDetails(IncompleteGradeDTO form)
	{
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    	
    		if(url.toString().contains("Admin")) // admin case
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Admin&oldMood=1");
			}
    	/*	else if(url.toString().contains("Admin")) // admin case
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=Admin&oldMood=1");
			}*/
    		
    		
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
			
    		
    		
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	 public void onRowSelect(SelectEvent event) {  
	      	try {
	      		IncompleteGradeDTO selectedDTO=(IncompleteGradeDTO) event.getObject();
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
	
	public void downloadAttachments(IncompleteGradeDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	public IIncompleteGradeAdminFacade getFacade() {
		return facade;
	}
	public void setFacade(IIncompleteGradeAdminFacade facade) {
		this.facade = facade;
	}
	public List<IncompleteGradeDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<IncompleteGradeDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<IncompleteGradeDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<IncompleteGradeDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public IncompleteGradeDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(IncompleteGradeDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<IncompleteGradeDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(
			List<IncompleteGradeDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public IncompleteGradeDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(IncompleteGradeDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<IncompleteGradeDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<IncompleteGradeDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public IncompleteGradeDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(IncompleteGradeDTO detailedForm) {
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
