package main.com.zc.services.presentation.survey.CourseEvalNew.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICoursesFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

@ManagedBean(name="InstructorEvaluation",eager=false)
@SessionScoped
public class InstructorEvaluation {

	@ManagedProperty("#{ICoursesEvalFacade}")
	private ICoursesFacade coursesFacade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	private Integer selectedIns;
	private Integer oldIns;
	private List<InstructorDTO> instructorsList = new ArrayList<InstructorDTO>();
	private List<CourseEvalInsQuestionsDTO> questionsList = new ArrayList<CourseEvalInsQuestionsDTO>();
	
	private List<CourseEvalAnswersDTO> answersList = new ArrayList<CourseEvalAnswersDTO>();

	private StudentDTO student;
	
	@PostConstruct
	public void init(){
			
		student=new StudentDTO();			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
			
			student.setId(person.getId());
			student.setMail(person.getEmail());				
		}
		
		setInstructorsList(coursesFacade.getAllInstructorsOfCourse(3));			
		
		if (selectedIns == null) 
			setSelectedIns(instructorsList.get(0).getId());
		
		setOldIns(selectedIns);
		
		setQuestionsList(coursesFacade.getAllInstructorQuestions(10));
		
		List<CourseEvalAnswersDTO> answers = coursesFacade.getEmpQuestAns(selectedIns, 3, student.getId()); 
		
		if(!answers.isEmpty() && answers != null){
			
			setAnswersList(answers);
			
			for (CourseEvalInsQuestionsDTO quest : questionsList) {
				
				List<String> strengthsSelections = new ArrayList<String>();
				List<String> concernsSelections = new ArrayList<String>();
			
				for (CourseEvalAnswersDTO courseEvalAnswersDTO : answersList) {
											
					if(quest.getId().equals(courseEvalAnswersDTO.getQuestion().getId())) {
						
						List<ScaleSelectionsDTO> selectionsDto = quest.getScaleType().getSelections();											
						
						for (ScaleSelectionsDTO scaleSelectionsDTO : selectionsDto) {
							
							if(scaleSelectionsDTO.getId().equals(courseEvalAnswersDTO.getSelections())){
								
								switch (scaleSelectionsDTO.getType().getValue()) {
								case 0:
									quest.setMainSelection(courseEvalAnswersDTO.getSelections());
									break;
								case 1:
									strengthsSelections.add(courseEvalAnswersDTO.getSelections().toString());
									break;
								case 2:
									concernsSelections.add(courseEvalAnswersDTO.getSelections().toString());
									break;	
								default:
									break;
								}
							}
						}
					}
				}
								
				quest.setStrengthsSelection(strengthsSelections);
				quest.setConcernsSelection(concernsSelections);
			}
		}else {
			answersList = new ArrayList<CourseEvalAnswersDTO>();
		}		
	}

	public List<ScaleSelectionsDTO> getQuestionsBySelection(Integer type, List<ScaleSelectionsDTO> selections) {
		
		List<ScaleSelectionsDTO> selectionsType = new ArrayList<ScaleSelectionsDTO>(); 
		
		for (ScaleSelectionsDTO scaleSelectionsDTO : selections) {
			
			if(scaleSelectionsDTO.getType().getValue() == type) {
				
				selectionsType.add(scaleSelectionsDTO);
			}
			
		}
		
		return selectionsType;
	}
	
	public void addSelection(CourseEvalInsQuestionsDTO quest, Integer i) {		
		
		quest.setMainSelection(null);
		
		quest.setMainSelection(i);
		
	}
	
	public void addSelectionArr(CourseEvalInsQuestionsDTO quest, List<String> selections, Integer type) {
		
		if(type == 1) {
			quest.setStrengthsSelection(null);
			quest.setStrengthsSelection(selections);			
		}else {
			quest.setConcernsSelection(null);
			quest.setConcernsSelection(selections);
		}
	}
	
	public void submitInsEval() {
		
		try {					
			
			if(!answersList.isEmpty()){
				for (CourseEvalAnswersDTO courseEvalAnswersDTO : answersList) {
					coursesFacade.deleteAns(courseEvalAnswersDTO);														
				}
				answersList = new ArrayList<CourseEvalAnswersDTO>();
			}
			
			for (CourseEvalInsQuestionsDTO quest : questionsList) {
				
				if(quest.getMainSelection() != null) {
				
					CourseEvalAnswersDTO answer = new CourseEvalAnswersDTO();
					answer.setInstructor(coursesFacade.getInstructorById(oldIns));										
					answer.setQuestion(coursesFacade.getQuestionById(quest.getId()));
					answer.setSelections(quest.getMainSelection());
					answer.setCourse(coursesFacade.getCourseById(3));
					answer.setStudent(student);
																						
					answersList.add(answer);
				}
				
				if(quest.getStrengthsSelection()!=null) {
				
					for (String selectionDto : quest.getStrengthsSelection()) {
						
						CourseEvalAnswersDTO stAnswer = new CourseEvalAnswersDTO();
						stAnswer.setInstructor(coursesFacade.getInstructorById(oldIns));										
						stAnswer.setQuestion(coursesFacade.getQuestionById(quest.getId()));
						stAnswer.setSelections(Integer.parseInt(selectionDto));
						stAnswer.setCourse(coursesFacade.getCourseById(3));
						stAnswer.setStudent(student);				
						
						answersList.add(stAnswer);
					}
				}
				
				if(quest.getConcernsSelection() != null) {
				
					for (String selectionDto : quest.getConcernsSelection()) {
						
						CourseEvalAnswersDTO stAnswer = new CourseEvalAnswersDTO();
						stAnswer.setInstructor(coursesFacade.getInstructorById(oldIns));										
						stAnswer.setQuestion(coursesFacade.getQuestionById(quest.getId()));
						stAnswer.setSelections(Integer.parseInt(selectionDto));
						stAnswer.setCourse(coursesFacade.getCourseById(3));
						stAnswer.setStudent(student);				
						
						answersList.add(stAnswer);
					}
				
				}
								
			}
									
			coursesFacade.saveInstructorEval(answersList,1);					
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	
	public void selectInstructor() {
		
		submitInsEval();						
		
		init();
		
//		RequestContext.getCurrentInstance().update(
//				"questRepeat:questRadio");
	}
	
	public void next() {			
		
		submitInsEval();
		
		try {
		
//			FacesContext.getCurrentInstance().getExternalContext().redirect("instructorEval.xhtml?-faces-redirect=true");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}			
	}
	
	public Integer getSelectedIns() {
		return selectedIns;
	}

	public void setSelectedIns(Integer selectedIns) {
		this.selectedIns = selectedIns;
	}

	public List<InstructorDTO> getInstructorsList() {
		return instructorsList;
	}

	public void setInstructorsList(List<InstructorDTO> instructorsList) {
		this.instructorsList = instructorsList;
	}		
	
	public ICoursesFacade getCoursesFacade() {
		return this.coursesFacade;
	}
	
	public void setCoursesFacade(ICoursesFacade coursesFacade) {
		
		this.coursesFacade = coursesFacade;
	}

	public List<CourseEvalInsQuestionsDTO> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<CourseEvalInsQuestionsDTO> questionsList) {
		this.questionsList = questionsList;
	}

	public List<CourseEvalAnswersDTO> getAnswersList() {
		return answersList;
	}

	public void setAnswersList(List<CourseEvalAnswersDTO> answersList) {
		this.answersList = answersList;
	}

	public Integer getOldIns() {
		return oldIns;
	}

	public void setOldIns(Integer oldIns) {
		this.oldIns = oldIns;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
}
