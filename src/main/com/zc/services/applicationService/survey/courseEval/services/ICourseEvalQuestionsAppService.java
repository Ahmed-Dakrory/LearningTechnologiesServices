/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services;

import java.util.List;

import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseEvalQuestionsAppService {
	public List<CourseEvalQuestionsDTO> getAll();
	public List<CourseEvalQuestionsDTO> getByCategoryID(Integer id);
	public List<CourseEvalQuestionsDTO> getBySectionID(Integer id);
	public CourseEvalQuestionsDTO getById(Integer id);
	public CourseEvalQuestionsDTO add(CourseEvalQuestionsDTO form) ;
	public CourseEvalQuestionsDTO update(CourseEvalQuestionsDTO form) ;
	public boolean delete(CourseEvalQuestionsDTO form) ;
	public InstructorDTO getInsByID(Integer id);
	public List<CoursesDTO> getCoursesByStudentID(Integer studentId);
	public List<InstructorDTO> getTasByCourseID(Integer courseID);
	public List<InstructorDTO> getInstructorsByCourseID(Integer courseID);
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(Integer studentId,Integer semester,Integer year);
	/**
	 * @author asmaa
	 */
	public List<CourseEvalInsQuestionsDTO> getInstSectionQuestions(Integer id);
	public List<CoursesDTO> getAllCourses();
	public boolean deleteSelection(ScaleSelectionsDTO selection);
	public List<CourseEvalQuestionsDTO> getByCourseID(int id);
}
