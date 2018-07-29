/**
 * 
 */
package main.com.zc.services.presentation.survey.config.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author Omnya Alaa
 *
 */
@ManagedBean
@ViewScoped
public class SectionQuestionsBean {

	private List<CourseEvalQuestionsDTO> questions;
	
	
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade facade;
	
	private CourseEvalQuestionsDTO addedQuestion;
	
	private Integer sectionID;
	private   ScaleTypeDTO scale;
	private CourseEvalQuestionsDTO processedQues;
	@PostConstruct
	public void init(){
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
	
		try{
			 sectionID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(sectionID!=null){
				   questions=facade.getBySectionID(sectionID);
			        addedQuestion=new CourseEvalQuestionsDTO();
			        scale=new ScaleTypeDTO();
			        processedQues=new CourseEvalQuestionsDTO();
	
			}}
			catch(Exception ex){
				
			}
        
     
	}


	public void preAddQuestion(){
		   addedQuestion=new CourseEvalQuestionsDTO();
	}
	public void addQuestion(){
		SectionsDTO section=new SectionsDTO();
		section.setId(getSectionID());
		getAddedQuestion().setSectionDTO(section);
		//TODO 
		//call question facade
		// add question
		CourseEvalQuestionsDTO dto=facade.add(addedQuestion);
		//if(sectionID==null) setSectionID(sectionID);
		 if(dto!=null)
		 {
			 if(dto.getId()!=null)
				 {questions=facade.getBySectionID(sectionID);
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions added successfully");
				 }
			 else 
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions failed to be added!");
		 }
		 else 
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions failed to be added!");
		
	}
	public void delete(CourseEvalQuestionsDTO ques){
		
		boolean b=facade.delete(addedQuestion);
		 if(b)
		 {
			
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions deleted successfully");
				  questions=facade.getBySectionID(getSectionID());
		 }
		 else 
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions failed to be deleted!");
		
	}
	
	public void preAddScaleType(CourseEvalQuestionsDTO ques){
		processedQues=null;
		setProcessedQues(ques);
		 getProcessedQues().setEditMode(true);
		
		 
	}
	public void addSelection(){
		ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
		selection.setName(getProcessedQues().getSelectionName());
		selection.setType(ScaleSelectionTypeEnum.MAIN);
		selection.setScaleType(getProcessedQues().getScaleType());
		getProcessedQues().getScaleType().getSelections().add(selection);
		facade.add(getProcessedQues());
		//getProcessedQues().getScaleType().getSelections().add(new ScaleSelectionsDTO());
		questions=facade.getBySectionID(sectionID);
		for(CourseEvalQuestionsDTO ques:questions)
		{
			ques.setEditMode(false);
		}
		getProcessedQues().setSelectionName(null);
	}
public void deleteSelection(ScaleSelectionsDTO selection){
		
		boolean b=facade.deleteSelection(selection);
		 if(b)
		 {
			
				 //JavaScriptMessagesHandler.RegisterErrorMessage(null, "Questions deleted successfully");
				  questions=facade.getBySectionID(getSectionID());
		 }
		 else 
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Selection is failed to be deleted!");
		
	}
	public List<CourseEvalQuestionsDTO> getQuestions() {
		return questions;
	}



	public void setQuestions(List<CourseEvalQuestionsDTO> questions) {
		this.questions = questions;
	}



	public ICourseEvalQuestionsFacade getFacade() {
		return facade;
	}



	public void setFacade(ICourseEvalQuestionsFacade facade) {
		this.facade = facade;
	}


	public CourseEvalQuestionsDTO getAddedQuestion() {
		return addedQuestion;
	}


	public void setAddedQuestion(CourseEvalQuestionsDTO addedQuestion) {
		this.addedQuestion = addedQuestion;
	}


	public Integer getSectionID() {
		return sectionID;
	}


	public void setSectionID(Integer sectionID) {
		this.sectionID = sectionID;
	}


	public ScaleTypeDTO getScale() {
		return scale;
	}


	public void setScale(ScaleTypeDTO scale) {
		this.scale = scale;
	}


	public CourseEvalQuestionsDTO getProcessedQues() {
		return processedQues;
	}


	public void setProcessedQues(CourseEvalQuestionsDTO processedQues) {
		this.processedQues = processedQues;
	}


	
	
}
