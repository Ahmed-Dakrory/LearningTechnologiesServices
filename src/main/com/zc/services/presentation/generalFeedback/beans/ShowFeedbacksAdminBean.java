/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.beans;


import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.generalFeedback.facade.IFeedbacksFacade;
import main.com.zc.shared.appService.ILoginSecurityAppService;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name="showFeedbacksAdminBean")
@ViewScoped
public class ShowFeedbacksAdminBean {
	
	
	private List<FeedbackDTO> feedbacks;
	private List<FeedbackDTO> filteredFeedback;
	private List<FeedbackDTO> selectedFeedback;
	private int detStudentID;
	private String detStudentName;
	private String detCategory;
	private String detDate;
	private String detFeedbackForm;
	
	
	@ManagedProperty("#{feedbacksFacadeImpl}")
	private IFeedbacksFacade feedbackFacade;
	
	@ManagedProperty("#{loginSecurityAppServiceImpl}")
	private ILoginSecurityAppService loginSecAppService;
	
	@PostConstruct
	public void init()
	{
		//test security
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String mail = authentication.getName();
		if(!mail.equals(Constants.LTS_SYSTEM_ADMIN))
		{
			try 
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("/LearningTechnologiesServices/pages/secured/generalFeedback/fillFeedbackForm.xhtml");
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			fillFeedbacksList();
		}
	}


	public void fillFeedbacksList()
	{
		setFeedbacks(feedbackFacade.getAll());
	}

	public void showDetails(FeedbackDTO dao)
	{
		RequestContext.getCurrentInstance().reset("mainForm:form:detailsGrid");
	    setDetStudentName(dao.getStudentName());
		setDetCategory(dao.getCategoryName());
		setDetFeedbackForm(dao.getFeedbackForm());
		setDetDate(dao.getFriendlyDate());
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("mainForm:form:detailsGrid");
		
	}
	public List<FeedbackDTO> getFeedbacks() {
		setFeedbacks(feedbackFacade.getAll());
		
		return feedbacks;
	}



	public void setFeedbacks(List<FeedbackDTO> feedbacks) {
		this.feedbacks = feedbacks;
	}



	public List<FeedbackDTO> getFilteredFeedback() {
		return filteredFeedback;
	}



	public void setFilteredFeedback(List<FeedbackDTO> filteredFeedback) {
		this.filteredFeedback = filteredFeedback;
	}



	public List<FeedbackDTO> getSelectedFeedback() {
		return selectedFeedback;
	}



	public void setSelectedFeedback(List<FeedbackDTO> selectedFeedback) {
		this.selectedFeedback = selectedFeedback;
	}


	public IFeedbacksFacade getFeedbackFacade() {
		return feedbackFacade;
	}


	public void setFeedbackFacade(IFeedbacksFacade feedbackFacade) {
		this.feedbackFacade = feedbackFacade;
	}


	public int getDetStudentID() {
		return detStudentID;
	}


	public void setDetStudentID(int detStudentID) {
		this.detStudentID = detStudentID;
	}


	public String getDetStudentName() {
		return detStudentName;
	}


	public void setDetStudentName(String detStudentName) {
		this.detStudentName = detStudentName;
	}


	public String getDetCategory() {
		return detCategory;
	}


	public void setDetCategory(String detCategory) {
		this.detCategory = detCategory;
	}




	public String getDetDate() {
		return detDate;
	}


	public void setDetDate(String detDate) {
		this.detDate = detDate;
	}


	public String getDetFeedbackForm() {
		return detFeedbackForm;
	}


	public void setDetFeedbackForm(String detFeedbackForm) {
		this.detFeedbackForm = detFeedbackForm;
	}


	public ILoginSecurityAppService getLoginSecAppService() {
		return loginSecAppService;
	}


	public void setLoginSecAppService(ILoginSecurityAppService loginSecAppService) {
		this.loginSecAppService = loginSecAppService;
	}





	
	

}
