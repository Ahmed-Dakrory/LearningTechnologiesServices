/**
 * 
 */
package main.com.zc.services.presentation.survey.config.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.primefaces.event.SelectEvent;

import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveyDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveySectionsDTO;
import main.com.zc.services.presentation.survey.config.facade.ISurveyFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class SurveyBean {

	private List<SurveyDTO> surveys;
	private SurveyDTO survey;
	List<SurveyDTO> filteredSurveys;
	@ManagedProperty("#{ISurveyFacade}")
	private ISurveyFacade facade;
	

	
	
	@PostConstruct
	public void init(){
		surveys=new ArrayList<SurveyDTO>();
		surveys=facade.getSurveys();
		survey=new SurveyDTO();
	}

	public void preAddSurvey(){
		survey=new SurveyDTO();
		
	}
	
	public void addSurvey(){
		survey.setDate(new Date());
		survey=facade.addSurvey(getSurvey());
		if(survey!=null)
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Survey was added successfully");
			surveys=facade.getSurveys();
		}
		else 
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be added!");
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		SurveyDTO selectedDTO=(SurveyDTO) event.getObject();
	  		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
	        flash.put("dto", selectedDTO);
	        FacesContext.getCurrentInstance().getExternalContext().redirect
			("sectionsConfig.xhtml");
	    
	    //    return "confirm?faces-redirect=true";
		//TODO navigate to 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	
	public void navigateToQuestions(SurveyDTO survey){
		
		   try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("surveyQuestions.xhtml?id="+survey.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public List<SurveyDTO> getSurveys() {
		return surveys;
	}


	public void setSurveys(List<SurveyDTO> surveys) {
		this.surveys = surveys;
	}


	public SurveyDTO getSurvey() {
		return survey;
	}


	public void setSurvey(SurveyDTO survey) {
		this.survey = survey;
	}

	public ISurveyFacade getFacade() {
		return facade;
	}

	public void setFacade(ISurveyFacade facade) {
		this.facade = facade;
	}

	public List<SurveyDTO> getFilteredSurveys() {
		return filteredSurveys;
	}

	public void setFilteredSurveys(List<SurveyDTO> filteredSurveys) {
		this.filteredSurveys = filteredSurveys;
	}
	
	
	
	
}
