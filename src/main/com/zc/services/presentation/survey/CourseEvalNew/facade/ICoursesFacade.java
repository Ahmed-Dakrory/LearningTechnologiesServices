package main.com.zc.services.presentation.survey.CourseEvalNew.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * 
 * @author asmaa
 *
 */

public interface ICoursesFacade {

	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id);
	
	public List<CourseEvalInsQuestionsDTO> getAllInstructorQuestions(int category);
	
	public List<CourseEvalAnswersDTO> getEmpQuestAns(Integer empId,
			Integer courseId, Integer studentId);
	
	public InstructorDTO getInstructorById(Integer id);
	
	public CoursesDTO getCourseById(Integer id);
	
	public void saveInstructorEval(List<CourseEvalAnswersDTO> answers,Integer type);
	
	public CourseEvalQuestionsDTO getQuestionById(Integer id);
	
	public void deleteAns(CourseEvalAnswersDTO form);
	
	
	public List<CoursesDTO> getAllCourses();
}
