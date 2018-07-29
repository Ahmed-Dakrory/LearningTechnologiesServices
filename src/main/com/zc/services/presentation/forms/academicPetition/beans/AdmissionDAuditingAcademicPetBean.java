/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.beans;

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
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAcademicPetitionActionsFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionDeptAcademicPetFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean(name="admissionDAuditingAcademicPetBean")
@ViewScoped
public class AdmissionDAuditingAcademicPetBean {

	@ManagedProperty("#{AdmissionDeptAcademicPetFacadeImpl}")
	private IAdmissionDeptAcademicPetFacade facade;

	@ManagedProperty("#{IAcademicPetitionActionsFacade}")
	private IAcademicPetitionActionsFacade actionFacade;	
	
	
	private List<CoursePetitionDTO> auditingForms;
	
	private CoursePetitionDTO selectedAuditingForms;
	private List<CoursePetitionDTO> filteredAuditingForms;
	private CoursePetitionDTO detailedForm;
	private String newComment;
	
	private boolean detailView;
	private FormDTO formDetail;
	
	@PostConstruct
	public void init()
	{
		detailView = false;
		fillAuditingPettions();
		detailedForm=new CoursePetitionDTO();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			CoursePetitionDTO dto=new CoursePetitionDTO();
			dto=actionFacade.getByID(dtoID);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form of student : "+dto.getStudentName()+"is Audited successfully !" );
			
		}
		catch(Exception ex){
			System.out.println("No redirect");
		}
		
	}

	public void fillAuditingPettions()
	{
		auditingForms=new ArrayList<CoursePetitionDTO>();
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
	      	
        CoursePetitionDTO selectedDTO=(CoursePetitionDTO) event.getObject();
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
	public IAdmissionDeptAcademicPetFacade getFacade() {
		return facade;
	}
	public void setFacade(IAdmissionDeptAcademicPetFacade facade) {
		this.facade = facade;
	}
	
	
	
	public CoursePetitionDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(CoursePetitionDTO detailedForm) {
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
	public IAcademicPetitionActionsFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(IAcademicPetitionActionsFacade actionFacade) {
		this.actionFacade = actionFacade;
	}

	public List<CoursePetitionDTO> getAuditingForms() {
		return auditingForms;
	}
	public void setAuditingForms(List<CoursePetitionDTO> auditingForms) {
		this.auditingForms = auditingForms;
	}
	public CoursePetitionDTO getSelectedAuditingForms() {
		return selectedAuditingForms;
	}
	public void setSelectedAuditingForms(CoursePetitionDTO selectedAuditingForms) {
		this.selectedAuditingForms = selectedAuditingForms;
	}
	public List<CoursePetitionDTO> getFilteredAuditingForms() {
		return filteredAuditingForms;
	}
	public void setFilteredAuditingForms(List<CoursePetitionDTO> filteredAuditingForms) {
		this.filteredAuditingForms = filteredAuditingForms;
	}

}
