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
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import main.com.zc.services.domain.survey.model.Survey;
import main.com.zc.services.domain.survey.model.SurveySections;
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
public class SectionsBean {

	private List<SectionsDTO> sections;
	private  SectionsDTO section;
	List<SectionsDTO> filteredSections;
	
	@ManagedProperty("#{ISurveyFacade}")
	private ISurveyFacade facade;
	
	private List<SurveyDTO> otherSurveys;//other s
	private List<SurveyDTO> mysurveys;
	private Integer[] selectedSurveys;
	SectionsDTO selectedSection;
	
	@PostConstruct
	public void init(){
		sections=new ArrayList<SectionsDTO>();
		sections=facade.getSections();
		section=new SectionsDTO();
		otherSurveys=new ArrayList<SurveyDTO>();
		mysurveys=new ArrayList<SurveyDTO>();
		
		selectedSection=new SectionsDTO();
		
	}

	public void preAddSection(){
		section=new SectionsDTO();
		
	}
	
	public void addSection(){
		section.setDate(new Date());
		section=facade.addSection(getSection());
		if(section!=null)
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Section was added successfully");
			sections=facade.getSections();
		}
		else 
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failed to be added!");
	}

	public void preAddSurvey(SectionsDTO section){
		//TODO 
		//get not my surveys
		//get my surveys
		setSelectedSection(section);
		mysurveys=new ArrayList<SurveyDTO>();
		mysurveys=facade.getSurveysBySectionID(section.getId());
		otherSurveys=new ArrayList<SurveyDTO>();
		otherSurveys=facade.getOtherSurveysBySectionID(section.getId());
		
	}
	public void saveSurveys(){
		List<SurveySectionsDTO> dtos=new ArrayList<SurveySectionsDTO>();
		for(int i=0;i<selectedSurveys.length;i++)
		{
			SurveySectionsDTO dto=new SurveySectionsDTO();
			dto.setSection(getSelectedSection());
			SurveyDTO survey=new SurveyDTO();
			//Integer temp=(Integer)Integer.parseInt(selectedSurveys[].toString()+"");
			survey.setId(selectedSurveys[i]);
			dto.setSurvey(survey);
			dtos.add(dto);
		}
		//TODO 
		//Save Lst of relationships into DB
		boolean added=facade.saveSurveysSections(dtos);
		if(added)
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Surveys are added successfully");
		}
		else
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not all surveys added successfully,please contact the developer for this issue");
		}
	}
	public void deleteSurvey(SurveyDTO survey){
		//TODO 
		boolean b=facade.deleteSurvey(survey.getId(),getSelectedSection().getId());
		if(b)
			
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Survey Deleted successfully");
			fillSurveys();
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Survey can not be deleted");
		}
	}
	public void addSurvey(SurveyDTO survey,SectionsDTO section){
		//TODO 
		List<SurveySectionsDTO> dtos=new ArrayList<SurveySectionsDTO>();
		SurveySectionsDTO dto=new SurveySectionsDTO();
		dto.setSurvey(survey);
		dto.setSection(section);
		dtos.add(dto);
		//TODO 
		//Save Lst of relationships into DB
		boolean added=facade.saveSurveysSections(dtos);
		if(added)
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Survey  added successfully");
			fillSurveys();
		}
		else
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not all surveys added successfully,"
					+ "please contact the developer for this issue");
		}
		
		
	
	}
	
	public void fillSurveys(){
		mysurveys=new ArrayList<SurveyDTO>();
		mysurveys=facade.getSurveysBySectionID(getSelectedSection().getId());
		otherSurveys=new ArrayList<SurveyDTO>();
		otherSurveys=facade.getOtherSurveysBySectionID(getSelectedSection().getId());
	
	}
	public void navigateToQuestions(SectionsDTO dto){
		   try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("sectionQuestions.xhtml?id="+dto.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
      
	}
	public List<SectionsDTO> getSections() {
		return sections;
	}



	public void setSections(List<SectionsDTO> sections) {
		this.sections = sections;
	}



	public SectionsDTO getSection() {
		return section;
	}



	public void setSection(SectionsDTO section) {
		this.section = section;
	}



	public List<SectionsDTO> getFilteredSections() {
		return filteredSections;
	}



	public void setFilteredSections(List<SectionsDTO> filteredSections) {
		this.filteredSections = filteredSections;
	}



	public ISurveyFacade getFacade() {
		return facade;
	}



	public void setFacade(ISurveyFacade facade) {
		this.facade = facade;
	}


	public List<SurveyDTO> getOtherSurveys() {
		return otherSurveys;
	}

	public void setOtherSurveys(List<SurveyDTO> otherSurveys) {
		this.otherSurveys = otherSurveys;
	}

	public List<SurveyDTO> getMysurveys() {
		return mysurveys;
	}

	public void setMysurveys(List<SurveyDTO> mysurveys) {
		this.mysurveys = mysurveys;
	}

	public Integer[] getSelectedSurveys() {
		return selectedSurveys;
	}

	public void setSelectedSurveys(Integer[] selectedSurveys) {
		this.selectedSurveys = selectedSurveys;
	}

	public SectionsDTO getSelectedSection() {
		return selectedSection;
	}

	public void setSelectedSection(SectionsDTO selectedSection) {
		this.selectedSection = selectedSection;
	}

	
	
	
}
