/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdmissionDOverloadRequestFacade;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IOverloadRequestActionsSharedFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean(name="admissionDAuditingOverloadRequestBean")
@ViewScoped
public class  AdmissionDAuditingOverloadRequestBean  {

	@ManagedProperty("#{AdmissionDOverloadRequestFacadeImpl}")
	private IAdmissionDOverloadRequestFacade facade;
	@ManagedProperty("#{IOverloadRequestActionsSharedFacade}")
	private IOverloadRequestActionsSharedFacade actionFacade;	
	
	private List<OverloadRequestDTO> auditingForms;
	
	private OverloadRequestDTO selectedAuditingForms;
	private List<OverloadRequestDTO> filteredAuditingForms;
	private OverloadRequestDTO detailedForm;
	private String newComment;
	
	private boolean detailView;
	private FormDTO formDetail;
		
	@PostConstruct
	public void init()
	{
		detailView = false;
		fillAuditingPettions();
		detailedForm=new OverloadRequestDTO();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			OverloadRequestDTO dto=new OverloadRequestDTO();
			dto=actionFacade.getByID(dtoID);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form of student : "+dto.getStudent().getName()+"is Audited successfully !" );
			
		}
		catch(Exception ex){
			System.out.println("No redirect");
		}
		
	}

	public void fillAuditingPettions()
	{
		auditingForms=new ArrayList<OverloadRequestDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_DEPT)){
				auditingForms=facade.getAuditingPet();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,"Not Allowed To see This Petitions" );
			}
		}}
	
	  public void onRowSelect(SelectEvent event) {  
	      	
		  OverloadRequestDTO selectedDTO=(OverloadRequestDTO) event.getObject();
        	try {
        			FacesContext.getCurrentInstance().getExternalContext().redirect
    				("formDetails.xhtml?id="+selectedDTO.getId()+"&cases=AdmissionD&audit=true");
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
	  }
		
	public Date getCurrentDate(){
		return new Date();
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

	public boolean isDetailView() {
		return detailView;
	}
	public void setDetailView(boolean detailView) {
		this.detailView = detailView;
	}
	public FormDTO getFormDetail() {
		return formDetail;
	}
	public void setFormDetail(FormDTO formDetail) {
		this.formDetail = formDetail;
	}
	
	public List<OverloadRequestDTO> getAuditingForms() {
		return auditingForms;
	}
	public void setAuditingForms(List<OverloadRequestDTO> auditingForms) {
		this.auditingForms = auditingForms;
	}
	public OverloadRequestDTO getSelectedAuditingForms() {
		return selectedAuditingForms;
	}
	public void setSelectedAuditingForms(OverloadRequestDTO selectedAuditingForms) {
		this.selectedAuditingForms = selectedAuditingForms;
	}
	public List<OverloadRequestDTO> getFilteredAuditingForms() {
		return filteredAuditingForms;
	}
	public void setFilteredAuditingForms(List<OverloadRequestDTO> filteredAuditingForms) {
		this.filteredAuditingForms = filteredAuditingForms;
	}
	public IOverloadRequestActionsSharedFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(IOverloadRequestActionsSharedFacade actionFacade) {
		this.actionFacade = actionFacade;
	}
	public IAdmissionDOverloadRequestFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdmissionDOverloadRequestFacade facade) {
		this.facade = facade;
	}
}
