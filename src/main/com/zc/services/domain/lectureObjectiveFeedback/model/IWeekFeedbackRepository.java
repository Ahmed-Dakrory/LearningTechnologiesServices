/**
 * 
 */
package main.com.zc.services.domain.lectureObjectiveFeedback.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IWeekFeedbackRepository {

	public List<WeekFeedback> getAll();
	public List<WeekFeedback> getByCategoryID(Integer id);
	public List<WeekFeedback> getByQuestID(Integer id);
	public List<WeekFeedback> getByStudentID(Integer id);
	public List<WeekFeedback> getByStudentIDAndCourseID(Integer id, Integer courseID);
	public List<WeekFeedback> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID);
	public WeekFeedback getByStudentIDAndCourseIDAndInstructorAndQuesID(Integer id, Integer courseID, Integer insID , Integer quesID);
	public WeekFeedback getByQuestIDAndStudentIDAndCourseID(Integer quesID, Integer stID,Integer courseId);
	public WeekFeedback getById(Integer id);
	public WeekFeedback add(WeekFeedback form) ;
	public WeekFeedback update(WeekFeedback form) ;
	public boolean delete(WeekFeedback form) ;
	public WeekFeedback getByQuestIDAndStudentID(Integer id, Integer stID);
	public List<WeekFeedback> getAnswresByQuestionIDAndCourseIDAndInstructorID(Integer courseID, Integer insID, Integer quesID);
	public List<WeekFeedback>getAnswresByQuesIDAndCourseID(Integer courseID, Integer quesID);
	public List<WeekFeedback> getByCourseID(Integer courseID);
	public List<WeekFeedback> getByCourseIDAndInsID(Integer courseId, Integer insID);
	public List<WeekFeedback> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID);
	
	public List<WeekFeedback> getByCourseIDAndInsIDAndStudentIdAndWeek(
			 Integer courseID, Integer insID,
			Integer studentID,Integer week);
	public List<WeekFeedback> getByCourseIDAndWeek(Integer courseID,Integer week);
	public List<WeekFeedback> getDistinctStudentByCourseIDandWeekAndIns(Integer courseID, Integer week,Integer insID);
	public List<WeekFeedback> getDistinctStudentByCourseIDandWeekAndInsAndQuesAndSelection
	(Integer courseID, Integer week,Integer insID,Integer quesId, Integer selectionID);
	public Integer getTotalNumOfParticipatedStudents(Integer week);
}
