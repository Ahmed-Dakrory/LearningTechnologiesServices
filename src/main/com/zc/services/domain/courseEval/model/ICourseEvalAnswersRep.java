/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;

/**
 * @author omnya
 *
 */
public interface ICourseEvalAnswersRep {

	public List<CourseEvalAnswers> getAll();
	public List<CourseEvalAnswers> getByCategoryID(Integer id);
	public List<CourseEvalAnswers> getByQuestID(Integer id);
	public List<CourseEvalAnswers> getByStudentID(Integer id);
	public List<CourseEvalAnswers> getByStudentIDAndCourseID(Integer id, Integer courseID,Integer type);
	public List<CourseEvalAnswers> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID);
	public CourseEvalAnswers getByStudentIDAndCourseIDAndInstructorAndQuesID(Integer id, Integer courseID, Integer insID , Integer quesID);
	public CourseEvalAnswers getByQuestIDAndStudentIDAndCourseID(Integer quesID, Integer stID,Integer courseId);
	public CourseEvalAnswers getById(Integer id);
	public CourseEvalAnswers add(CourseEvalAnswers form) ;
	public CourseEvalAnswers update(CourseEvalAnswers form) ;
	public boolean delete(CourseEvalAnswers form) ;
	public boolean deleteAllcourseDataNew(CourseEvalAnswersDTO form);
	public CourseEvalAnswers getByQuestIDAndStudentID(Integer id, Integer stID);
	public List<CourseEvalAnswers> getAnswresByQuestionIDAndCourseIDAndInstructorID(Integer courseID, Integer insID, Integer quesID);
	public List<CourseEvalAnswers> getCommentsByCategoryIDAndCourseIDAndInstructorID(Integer courseID, Integer insID, Integer categoryID);
	public List<CourseEvalAnswers>getAnswresByQuesIDAndCourseID(Integer courseID, Integer quesID);
	public List<CourseEvalAnswers>getCommentsByCategoryIDAndCourseID(Integer courseID,
			Integer categoryID);
	public List<Student> getTotalStudentsOfCourse(Integer courseID,Integer surveyID);
	public List<CourseEvalAnswers> getByCourseID(Integer courseID);
	public List<CourseEvalAnswers> getByCourseIDAndInsID(Integer courseId, Integer insID);
	public List<CourseEvalAnswers> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID);
	public boolean removeDuplicatesFromCourses();
	public boolean removeDuplicatesFromIns();
	
	
	/**
	 * @author asmaa
	 * @param form
	 * @return
	 */
	public CourseEvalAnswers addInst(CourseEvalAnswers form) ;
	public List<CourseEvalAnswers> getAllByQuestIDAndStudentIDAndCourseID(Integer quesID, Integer stID,Integer courseId);
	public CourseEvalAnswers getByStudentIDAndCourseIDAndInstructorAndQuesIDAndAnsID(
			Integer student, int course, Integer ins, Integer ques, Integer ans);
	public CourseEvalAnswers getByQuestionIDAndCourseIDAndStudentIdAndAns(
			Integer ques, int course, Integer student, Integer ans);
	/*
	 * (Ahmed Dakrory)
	 * @see main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep#deleteAllcourseData(main.com.zc.services.domain.courseEval.model.CourseEvalAnswers)
	 */
	List<CourseEvalAnswers> getListByStudentIDAndCourseIDAndInstructorAndQuesID(
			Integer id, Integer courseID, Integer insID, Integer quesID);
}
