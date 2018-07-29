package main.com.zc.services.presentation.survey.CourseEvalNew.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICourseEvaluationFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * 
 * @author asmaa
 *
 */

@ManagedBean(name="CourseEvaluationViewing",eager=false)
@SessionScoped
public class CourseEvaluationViewing {

	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade quesFacade;
	
	@ManagedProperty("#{ICourseEvaluationFacade}")
	private ICourseEvaluationFacade courseEvalQuesFacade;
	
	private List<CoursesDTO> courses = new ArrayList<CoursesDTO>();	
	private CoursesDTO selectedCourse = new CoursesDTO();
	
	private List<CourseEvalQuestionsDTO> studentEvalQuestions = new ArrayList<CourseEvalQuestionsDTO>();
	private List<CourseEvalQuestionsDTO> courseEvalQuestions = new ArrayList<CourseEvalQuestionsDTO>();
	private List<CourseEvalQuestionsDTO> languageOFInstructionQuestions = new ArrayList<CourseEvalQuestionsDTO>();
	
	private List<InstructorDTO> instructors = new ArrayList<InstructorDTO>();
	private List<InstructorDTO> tas = new ArrayList<InstructorDTO>();
	private Integer selectedInstructor;
	private Integer selectedTa;
	
	private List<CourseEvalInsQuestionsDTO> instructorQuestions = new ArrayList<CourseEvalInsQuestionsDTO>();
	private List<String> mainSelections = new ArrayList<String>();
	
	private int activeIndex = 0;
	
	@PostConstruct
	public void init() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().equals("lts-admin@zewailcity.edu.eg"))
			{							
				setCourses(ansFacade.getCoursesByYearAndSemester(0, 2015));		// get courses of Year 2015 Semester 0	 			
			}
		}					
		
		mainSelections.add(0, "Very good");
		mainSelections.add(1, "Fine");
		mainSelections.add(2, "Needs improvement");
	}
	
	public void startView() {
		
		fillInstructorsLst();		
		navigateTo(1, 0);
	}
	
	public void updateSelectedCourse(CoursesDTO course) {
		
		for (CoursesDTO dto : courses) {
			
			if(dto.getId() == course.getId()) selectedCourse = dto;							
		}
	}
	
	public void navigateTo (int page, int dir) {
		
		switch (page) {
		case 1:
			
			try {
				
				fillCourseQuestions();
				FacesContext.getCurrentInstance().getExternalContext().redirect("courseEvalInst.xhtml?-faces-redirect=true");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		
		case 2:					
			
			try {
			
				fillStudentQuestions();
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("studentEvalInst.xhtml?-faces-redirect=true");
				
			} catch (Exception e) {
				e.printStackTrace();
			}					
			
			break;
			
		case 3:
			
			try {
				
				if(selectedInstructor == null) selectedInstructor = instructors.get(0).getId();
				
				setInstructorQuestions(ansFacade.getInstCourseEvalDynamic(selectedCourse.getId(), QuestionsCategory.Instructor_Asis.getID(), selectedInstructor));
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("instructorEvalInst.xhtml?-faces-redirect=true");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		case 4:
			
			try {
				
				if(!tas.isEmpty()){
				
					if(selectedTa == null) selectedTa = tas.get(0).getId();
					
					setInstructorQuestions(ansFacade.getInstCourseEvalDynamic(selectedCourse.getId(), QuestionsCategory.Teaching_Asis.getID(), selectedTa));
					
					FacesContext.getCurrentInstance().getExternalContext().redirect("taEvalInst.xhtml?-faces-redirect=true");
				
				} else {
					
					if(dir == 0)		
						navigateTo(5, dir);
					else  navigateTo(3, dir);
					
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		case 5:
			
			if(tas.isEmpty())
	 			activeIndex = 3;	 	
	 		else 
	 			activeIndex = 4;
			
			try {							
				
				setInstructorQuestions(ansFacade.getInstCourseEvalDynamic(selectedCourse.getId(), QuestionsCategory.Lab_Asis.getID(), null));
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("labEvalInst.xhtml?-faces-redirect=true");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;	
			
		case 6:					
			
			if(tas.isEmpty())
	 			activeIndex = 4;	 	
	 		else 
	 			activeIndex = 5;
			
			try {
			
				fillLanguageQuestions();
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("langOfInstEvalInst.xhtml?-faces-redirect=true");
				
			} catch (Exception e) {
				e.printStackTrace();
			}					
			
			break;	
			
		default:
			break;
		}
	}
	
	public void fillCourseQuestions() {		
		
		setCourseEvalQuestions(ansFacade.getCourseEvalDynamic(selectedCourse.getId(), QuestionsCategory.Course_Eval.getID()));
	}
	
	public void fillStudentQuestions() {
		
		setCourseEvalQuestions(ansFacade.getCourseEvalDynamic(selectedCourse.getId(), QuestionsCategory.Student_Eval.getID()));
	}
		
	public void fillLanguageQuestions(){		
		
		setCourseEvalQuestions(ansFacade.getCourseEvalDynamic(selectedCourse.getId(), QuestionsCategory.Languag_Of_Instruction.getID()));
	}
	
	public void fillInstructorsLst()
	{		
		instructors = courseEvalQuesFacade.getInstructorsByCourseID(getSelectedCourse().getId());
		
		setTas(courseEvalQuesFacade.getTasByCourseID(getSelectedCourse().getId()));			
	}
	
	public PieChartModel getSelection(List<PieChartModel> models, String mainSelection) {
		
		PieChartModel model = new PieChartModel();
		
		switch (mainSelection) {
		case "Very good":
			model = models.get(0);
			break;

		case "Fine":
			model = models.get(1);
			break;
			
		case "Needs improvement":
			model = models.get(2);
			break;
		
		default:
			break;
		}
		
		return model;
	}
	
	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}

	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}

	public List<CoursesDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CoursesDTO> courses) {
		this.courses = courses;
	}

	public ICourseEvalQuestionsFacade getQuesFacade() {
		return quesFacade;
	}
	public void setQuesFacade(ICourseEvalQuestionsFacade quesFacade) {
		this.quesFacade = quesFacade;
	}

	public List<CourseEvalQuestionsDTO> getStudentEvalQuestions() {
		return studentEvalQuestions;
	}

	public void setStudentEvalQuestions(List<CourseEvalQuestionsDTO> studentEvalQuestions) {
		this.studentEvalQuestions = studentEvalQuestions;
	}

	public List<CourseEvalQuestionsDTO> getCourseEvalQuestions() {
		return courseEvalQuestions;
	}

	public void setCourseEvalQuestions(List<CourseEvalQuestionsDTO> courseEvalQuestions) {
		this.courseEvalQuestions = courseEvalQuestions;
	}

	public List<CourseEvalQuestionsDTO> getLanguageOFInstructionQuestions() {
		return languageOFInstructionQuestions;
	}

	public void setLanguageOFInstructionQuestions(
			List<CourseEvalQuestionsDTO> languageOFInstructionQuestions) {
		this.languageOFInstructionQuestions = languageOFInstructionQuestions;
	}

	public CoursesDTO getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(CoursesDTO selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public ICourseEvaluationFacade getCourseEvalQuesFacade() {
		return courseEvalQuesFacade;
	}
	public void setCourseEvalQuesFacade(ICourseEvaluationFacade courseEvalQuesFacade) {
		this.courseEvalQuesFacade = courseEvalQuesFacade;
	}

	public List<InstructorDTO> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<InstructorDTO> instructors) {
		this.instructors = instructors;
	}

	public Integer getSelectedInstructor() {
		return selectedInstructor;
	}

	public void setSelectedInstructor(Integer selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}

	public List<InstructorDTO> getTas() {
		return tas;
	}

	public void setTas(List<InstructorDTO> tas) {
		this.tas = tas;
	}

	public List<CourseEvalInsQuestionsDTO> getInstructorQuestions() {
		return instructorQuestions;
	}

	public void setInstructorQuestions(List<CourseEvalInsQuestionsDTO> instructorQuestions) {
		this.instructorQuestions = instructorQuestions;
	}

	public List<String> getMainSelections() {
		return mainSelections;
	}

	public void setMainSelections(List<String> mainSelections) {
		this.mainSelections = mainSelections;
	}

	public Integer getSelectedTa() {
		return selectedTa;
	}

	public void setSelectedTa(Integer selectedTa) {
		this.selectedTa = selectedTa;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
}
