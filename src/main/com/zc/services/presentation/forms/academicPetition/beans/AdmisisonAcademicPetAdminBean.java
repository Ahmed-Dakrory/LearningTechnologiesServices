/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionAdminAcademicPetFacade;
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
@ManagedBean(name="AdmisisonAcademicPetAdminBean")
@ViewScoped
public class AdmisisonAcademicPetAdminBean {

	@ManagedProperty("#{AdmissionAdminAcademicPetFacadeImpl}")
	private IAdmissionAdminAcademicPetFacade facade;
	
	private List<CoursePetitionDTO> pendingForms;
	private List<CoursePetitionDTO> archievedForms;
	private List<CoursePetitionDTO> selectedPendingForms;
	private List<CoursePetitionDTO> filteredPendingForms;
	private List<CoursePetitionDTO> selectedArchievedForms;
	private List<CoursePetitionDTO> filteredArchievedForms;
	private CoursePetitionDTO detailedForm;
	private Integer loginCase;
	private boolean detailView;
	public void downloadAttachments(CoursePetitionDTO form)
	{
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form.getAttachments());
	}
	
	@PostConstruct
	public void init()

	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
				detailedForm=new CoursePetitionDTO();
			    
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
		pendingForms=new ArrayList<CoursePetitionDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			// will allow to Dr. Ashraf , Admission Head , Registrar to see the in progress petitions
						//if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
						if(authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN) || authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||
								authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)||authentication.getName().equals(Constants.ADMISSION_HEAD)||
								authentication.getName().equals(Constants.ADMISSION_DEPT))
						{
				pendingForms=facade.getPendingPet();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}

	public void fillArchievedPetitionsLst()
	{
		archievedForms=new ArrayList<CoursePetitionDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.LTS_SYSTEM_ADMIN) || mail.equals(Constants.DEAN_OF_STRATEGIC)){
				archievedForms=facade.getOldPet();
				
			}
			else if(authentication.getName().equals(Constants.ADMISSION_DEPT)||authentication.getName().equals(Constants.ADMISSION_HEAD)||
					authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)){
				//DO NOTHING
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
	public void showDetails(CoursePetitionDTO form)
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
    		
    		
			}} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	 }
	  public void onRowSelect(SelectEvent event) {  
	      	
          CoursePetitionDTO selectedDTO=(CoursePetitionDTO) event.getObject();
          try {
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
	
	public IAdmissionAdminAcademicPetFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdmissionAdminAcademicPetFacade facade) {
		this.facade = facade;
	}
	public List<CoursePetitionDTO> getPendingForms() {
		return pendingForms;
	}
	public void setPendingForms(List<CoursePetitionDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}
	public List<CoursePetitionDTO> getArchievedForms() {
		return archievedForms;
	}
	public void setArchievedForms(List<CoursePetitionDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}
	public List<CoursePetitionDTO> getSelectedPendingForms() {
		return selectedPendingForms;
	}
	public void setSelectedPendingForms(List<CoursePetitionDTO> selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}
	public List<CoursePetitionDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}
	public void setFilteredPendingForms(List<CoursePetitionDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}
	public List<CoursePetitionDTO> getSelectedArchievedForms() {
		return selectedArchievedForms;
	}
	public void setSelectedArchievedForms(
			List<CoursePetitionDTO> selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}
	public List<CoursePetitionDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}
	public void setFilteredArchievedForms(
			List<CoursePetitionDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}
	public CoursePetitionDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(CoursePetitionDTO detailedForm) {
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
