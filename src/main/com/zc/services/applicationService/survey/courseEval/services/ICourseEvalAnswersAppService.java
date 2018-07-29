/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services;

import java.util.List;

import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public interface ICourseEvalAnswersAppService {

	//stduent methods
	public List<CourseEvalAnswersDTO> getAll();
	public List<CourseEvalAnswersDTO> getByCategoryID(Integer id);
	public List<CourseEvalAnswersDTO> getByQuestID(Integer id);
	public List<CourseEvalAnswersDTO> getByStudentID(Integer id);
	public CourseEvalAnswersDTO getById(Integer id);
	public CourseEvalAnswersDTO add(CourseEvalAnswersDTO form, Integer surveyType) ;
	public CourseEvalAnswersDTO update(CourseEvalAnswersDTO form) ;
	public boolean delete(CourseEvalAnswersDTO form) ;
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseID(Integer id, Integer courseID,Integer type);
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID);
	public List<CourseEvalAnswersDTO> getByCourseID(Integer courseID);
	// instructor methods
	public List<CourseEvalQuestionsDTO> getFacEvalByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getCourseByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getCourseAssignByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getTAByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getLabFacByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getLabExpByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getOthersTextByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CourseEvalQuestionsDTO> getOthersRateByCourseIDAndInstructorID(Integer courseID, Integer insID);
	public List<CoursesDTO> getCoursesByInsID(Integer insID);
	public List<String> getCommentsByCategoryIDAndCourseIDAndInstructorID(Integer courseID, Integer insID, Integer categoryID);
	public List<String> getCommentsByCategoryIDAndCourseID(Integer courseID, Integer categoryID);
	public boolean removeDuplicatesFromCourses();
	public boolean removeDuplicatesFromIns();
	
	/**
	 * @author asmaa
	 * @return list of question answers 
	 */
	public List<CourseEvalAnswersDTO> getEvalAnsByCourseIdAndEmpIdAndStudentId(Integer courseId, Integer empId, Integer studentId);
	public CourseEvalAnswersDTO addInstAns(CourseEvalAnswersDTO form, Integer type) ;
	
	public List<CourseEvalQuestionsDTO> getCourseEvalDynamic(
			Integer courseID, Integer category);
	
	public List<CourseEvalInsQuestionsDTO> getInstCourseEvalDynamic(
			Integer courseID, Integer category, Integer instId);
	
	public void exportResultsToPDF(CoursesDTO courseDto);
	
	public Integer getStudentsNumberOfCourse(Integer courseID);
	
	//////Other questions
	public List<CourseEvalAnswersDTO> getAllOtherAnswers();
	public List<CourseEvalAnswersDTO> getByCategoryIDOtherAns(Integer id);
	public List<CourseEvalAnswersDTO> getByQuestIDOtherAns(Integer id);
	public List<CourseEvalAnswersDTO> getByStudentIDOtherAns(Integer id);
	public CourseEvalAnswersDTO getByIdOtherAns(Integer id);
	public CourseEvalAnswersDTO addOtherAns(CourseEvalAnswersDTO form) ;
	public CourseEvalAnswersDTO updateOtherAns(CourseEvalAnswersDTO form) ;
	public boolean deleteOtherAns(CourseEvalAnswersDTO form) ;
	public List<StudentDTO> getTotalStudentsOfCourse(Integer courseID,Integer surveyID) ;
	
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseID(Integer questionID, Integer courseID);
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsID(Integer questionID, Integer courseID, Integer insID);
	public List<CourseEvalAnswersDTO> getByCourseIDAndInsID(Integer courseID,
			Integer insID);
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID);
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndStudentId(
			Integer questionID, Integer courseID, Integer studentID);
}