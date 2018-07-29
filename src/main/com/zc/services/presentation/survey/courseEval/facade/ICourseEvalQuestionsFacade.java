/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseEvalQuestionsFacade {

	public List<CourseEvalQuestionsDTO> getAll();
	public List<CourseEvalQuestionsDTO> getByCategoryID(Integer id);
	public List<CourseEvalQuestionsDTO> getBySectionID(Integer id);
	public CourseEvalQuestionsDTO getById(Integer id);
	public CourseEvalQuestionsDTO add(CourseEvalQuestionsDTO form) ;
	public CourseEvalQuestionsDTO update(CourseEvalQuestionsDTO form) ;
	public boolean delete(CourseEvalQuestionsDTO form) ;
	public boolean deleteSelection(ScaleSelectionsDTO selection) ;
	public InstructorDTO getInsByID(Integer id);
	public List<CoursesDTO> getCoursesByStudentID(Integer studentId);
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(Integer studentId,Integer semester,Integer year);
	public List<CourseEvalQuestionsDTO> getByCourseID(int id);
}
