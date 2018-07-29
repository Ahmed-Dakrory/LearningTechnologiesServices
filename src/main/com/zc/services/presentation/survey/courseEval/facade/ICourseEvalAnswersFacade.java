/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public interface ICourseEvalAnswersFacade {

	//student methods
	public List<CourseEvalAnswersDTO> getAll();
	public List<CourseEvalAnswersDTO> getByCategoryID(Integer id);
	public List<CourseEvalAnswersDTO> getByQuestID(Integer id);
	public List<CourseEvalAnswersDTO> getByStudentID(Integer id);
	public CourseEvalAnswersDTO getById(Integer id);
	public CourseEvalAnswersDTO add(CourseEvalAnswersDTO form, Integer i) ;
	public CourseEvalAnswersDTO update(CourseEvalAnswersDTO form) ;
	public boolean delete(CourseEvalAnswersDTO form) ;
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseID(Integer id,
			Integer courseID,Integer type);
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseIDAndInstructor(Integer id,
			Integer courseID, Integer insID);
	public List<CourseEvalAnswersDTO> getByCourseID(Integer courseID);
	public CoursesDTO getCourseById(Integer id);
	public List<String> getCommentsByCategoryIDAndCourseIDAndInstructorID(Integer courseID, Integer insID, Integer categoryID);
	public List<String> getCommentsByCategoryIDAndCourseID(Integer courseID,
			Integer categoryID);
	//instructor methods
	public List<CourseEvalQuestionsDTO> getFacEvalByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getCourseByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getCourseAssignByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getTAByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getLabFacByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getLabExpByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getOthersTextByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getOthersRateByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CoursesDTO> getCoursesByInsID(Integer insID);
	public List<CoursesDTO> getAllCourses();
	public List<StudentDTO> getTotalStudentsOfCourse(Integer courseID,Integer SurveyID) ;
	public Integer getStudentsNumberOfCourse(Integer courseID);

	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseID(Integer questionID, Integer courseID);

	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsID(Integer questionID, Integer courseID, Integer insID);
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(Integer questionID, Integer courseID, Integer insID,Integer studentID);
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndStudentId(Integer questionID, Integer courseID,Integer studentID);
	public List<CourseEvalAnswersDTO> getByCourseIDAndInsID(Integer courseID, Integer insID);
	public boolean removeDuplicatesFromCourses();
	public boolean removeDuplicatesFromIns();
	/**
	 * @author asmaa
	 */	
	public List<CourseEvalQuestionsDTO> getCourseEvalDynamic(Integer courseID,
			Integer category);
	
	public List<CourseEvalInsQuestionsDTO> getInstCourseEvalDynamic(Integer courseID,
			Integer category, Integer instId);
	
	public List<CoursesDTO> getCoursesByYearAndSemester(Integer semester, Integer year);
	
}
