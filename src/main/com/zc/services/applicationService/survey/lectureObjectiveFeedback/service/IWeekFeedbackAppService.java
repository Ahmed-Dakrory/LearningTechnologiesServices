/**
 * 
 */
package main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service;

import java.util.List;

import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.CoursesPercentageDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackResultsDTO;

/**
 * @author omnya
 *
 */
public interface IWeekFeedbackAppService {
	public List<WeekFeedbackDTO> getAll();
	public List<WeekFeedbackDTO> getByCategoryID(Integer id);
	public List<WeekFeedbackDTO> getByQuestID(Integer id);
	public List<WeekFeedbackDTO> getByStudentID(Integer id);
	public List<WeekFeedbackDTO> getByStudentIDAndCourseID(Integer id, Integer courseID);
	public List<WeekFeedbackDTO> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID);
	public WeekFeedbackDTO getByStudentIDAndCourseIDAndInstructorAndQuesID(Integer id, Integer courseID, Integer insID , Integer quesID);
	public WeekFeedbackDTO getByQuestIDAndStudentIDAndCourseID(Integer quesID, Integer stID,Integer courseId);
	public WeekFeedbackDTO getById(Integer id);
	public WeekFeedbackDTO add(WeekFeedbackDTO form) ;
	public WeekFeedbackDTO update(WeekFeedbackDTO form) ;
	public boolean delete(WeekFeedbackDTO form) ;
	public WeekFeedbackDTO getByQuestIDAndStudentID(Integer id, Integer stID);
	public List<WeekFeedbackDTO> getAnswresByQuestionIDAndCourseIDAndInstructorID(Integer courseID, Integer insID, Integer quesID);
	public List<WeekFeedbackDTO>getAnswresByQuesIDAndCourseID(Integer courseID, Integer quesID);
	public List<WeekFeedbackDTO> getByCourseID(Integer courseID);
	public List<WeekFeedbackDTO> getByCourseIDAndInsID(Integer courseId, Integer insID);
	public List<WeekFeedbackDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID);
	
	public  List<WeekFeedbackDTO> getByCourseIDAndInsIDAndStudentIdAndWeek(
			 Integer courseID, Integer insID,
			Integer studentID, Integer week);
	public List<WeekFeedbackDTO> getByCourseIDAndWeek(Integer courseID,Integer week);
	public List<CoursesPercentageDTO> getPercentageofCourse(SemesterWeeksDTO week);
	public List<WeekFeedbackResultsDTO> getResultsDetails(Integer courseId, Integer insID, Integer weekID);
	public Integer getTotalNumOfParticipatedStudents(SemesterWeeksDTO week);
	public boolean sendEmail(CoursesPercentageDTO course , SemesterWeeksDTO week,List<WeekFeedbackResultsDTO> details);
}
