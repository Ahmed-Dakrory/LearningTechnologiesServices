/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.beans;

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
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatActionsSharedFacade;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdmissionDeptFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean(name="courseRepeatAuditingAdmissionDeptBean")
@ViewScoped
public class  CourseRepeatAuditingAdmissionDeptBean  {

	@ManagedProperty("#{ICourseRepeatAdmissionDeptFacade}")
	private ICourseRepeatAdmissionDeptFacade facade;
	@ManagedProperty("#{ICourseRepeatActionsSharedFacade}")
	private ICourseRepeatActionsSharedFacade actionFacade;	
	
	private List<CourseRepeatDTO> auditingForms;
	
	private CourseRepeatDTO selectedAuditingForms;
	private List<CourseRepeatDTO> filteredAuditingForms;
	private CourseRepeatDTO detailedForm;
	private String newComment;
	
	private boolean detailView;
	private FormDTO formDetail;
		
	@PostConstruct
	public void init()
	{
		detailView = false;
		fillAuditingPettions();
		detailedForm=new CourseRepeatDTO();
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		try
		{
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			CourseRepeatDTO dto=new CourseRepeatDTO();
			dto=actionFacade.getByID(dtoID);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form of student : "+dto.getStudent().getName()+"is Audited successfully !" );
			
		}
		catch(Exception ex){
			System.out.println("No redirect");
		}
		
	}

	public void fillAuditingPettions()
	{
		auditingForms=new ArrayList<CourseRepeatDTO>();
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
	      	
		  CourseRepeatDTO selectedDTO=(CourseRepeatDTO) event.getObject();
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
	
	public List<CourseRepeatDTO> getAuditingForms() {
		return auditingForms;
	}
	public void setAuditingForms(List<CourseRepeatDTO> auditingForms) {
		this.auditingForms = auditingForms;
	}
	public CourseRepeatDTO getSelectedAuditingForms() {
		return selectedAuditingForms;
	}
	public void setSelectedAuditingForms(CourseRepeatDTO selectedAuditingForms) {
		this.selectedAuditingForms = selectedAuditingForms;
	}
	public List<CourseRepeatDTO> getFilteredAuditingForms() {
		return filteredAuditingForms;
	}
	public void setFilteredAuditingForms(List<CourseRepeatDTO> filteredAuditingForms) {
		this.filteredAuditingForms = filteredAuditingForms;
	}
	public ICourseRepeatActionsSharedFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(ICourseRepeatActionsSharedFacade actionFacade) {
		this.actionFacade = actionFacade;
	}
	public ICourseRepeatAdmissionDeptFacade getFacade() {
		return facade;
	}
	public void setFacade(ICourseRepeatAdmissionDeptFacade facade) {
		this.facade = facade;
	}
}
