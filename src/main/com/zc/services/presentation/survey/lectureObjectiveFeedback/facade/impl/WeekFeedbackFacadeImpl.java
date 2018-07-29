/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.IWeekFeedbackAppService;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.CoursesPercentageDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackResultsDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.IWeekFeedbackFacade;

/**
 * @author Omnya Alaa
 *
 */
@Service("IWeekFeedbackFacade")
public class WeekFeedbackFacadeImpl implements IWeekFeedbackFacade{

	@Autowired
	IWeekFeedbackAppService service;
	@Override
	public List<WeekFeedbackDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public List<WeekFeedbackDTO> getByCategoryID(Integer id) {
		return service.getByCategoryID(id);
	}

	@Override
	public List<WeekFeedbackDTO> getByQuestID(Integer id) {
		return service.getByQuestID(id);
	}

	@Override
	public List<WeekFeedbackDTO> getByStudentID(Integer id) {
		return service.getByStudentID(id);
	}

	@Override
	public List<WeekFeedbackDTO> getByStudentIDAndCourseID(Integer id,
			Integer courseID) {
		return service.getByStudentIDAndCourseID(id,courseID);
	}

	@Override
	public List<WeekFeedbackDTO> getByStudentIDAndCourseIDAndInstructor(
			Integer id, Integer courseID, Integer insID) {
		return service.getByStudentIDAndCourseIDAndInstructor(id,courseID,insID);
	}

	@Override
	public WeekFeedbackDTO getByStudentIDAndCourseIDAndInstructorAndQuesID(
			Integer id, Integer courseID, Integer insID, Integer quesID) {
		return service.getByStudentIDAndCourseIDAndInstructorAndQuesID(id,courseID,insID,quesID);
	}

	@Override
	public WeekFeedbackDTO getByQuestIDAndStudentIDAndCourseID(Integer quesID,
			Integer stID, Integer courseId) {
		return service.getByQuestIDAndStudentIDAndCourseID(quesID,stID,courseId);
	}

	@Override
	public WeekFeedbackDTO getById(Integer id) {
		return service.getById(id);
	}

	@Override
	public WeekFeedbackDTO add(WeekFeedbackDTO form) {
		return service.add(form);
	}

	@Override
	public WeekFeedbackDTO update(WeekFeedbackDTO form) {
		return service.update(form);
	}

	@Override
	public boolean delete(WeekFeedbackDTO form) {
		return service.delete(form);
	}

	@Override
	public WeekFeedbackDTO getByQuestIDAndStudentID(Integer id, Integer stID) {
		return service.getByQuestIDAndStudentID(id,stID);
	}

	@Override
	public List<WeekFeedbackDTO> getAnswresByQuestionIDAndCourseIDAndInstructorID(
			Integer courseID, Integer insID, Integer quesID) {
		return service.getAnswresByQuestionIDAndCourseIDAndInstructorID( courseID,  insID,  quesID);
	}

	@Override
	public List<WeekFeedbackDTO> getAnswresByQuesIDAndCourseID(
			Integer courseID, Integer quesID) {
		return service.getAnswresByQuesIDAndCourseID( courseID,  quesID);
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseID(Integer courseID) {
		return service.getByCourseID( courseID);
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseIDAndInsID(Integer courseId,
			Integer insID) {
		return service.getByCourseIDAndInsID( courseId,insID);
	}

	@Override
	public List<WeekFeedbackDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID) {
		return service.getByQuestionIDAndCourseIDAndInsIDAndStudentId( questionID,  courseID,  insID,
				 studentID);
	}


	@Override
	public List<WeekFeedbackDTO> getByCourseIDAndInsIDAndStudentIdAndWeek(
			Integer courseID, Integer insID, Integer studentID, Integer week) {
		return service.getByCourseIDAndInsIDAndStudentIdAndWeek( courseID,  insID,  studentID,  week);
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseIDAndWeek(Integer courseID, Integer week) {
		return service.getByCourseIDAndWeek( courseID,week);
	}

	@Override
	public List<CoursesPercentageDTO> getPercentageofCourse(SemesterWeeksDTO week) {
		return service.getPercentageofCourse( week);
	}

	@Override
	public List<WeekFeedbackResultsDTO> getResultsDetails(Integer courseId, Integer insID, Integer weekID) {
		return service.getResultsDetails(courseId, insID,  weekID);
	}

	@Override
	public Integer getTotalNumOfParticipatedStudents(SemesterWeeksDTO week) {
		return service.getTotalNumOfParticipatedStudents(week);
	}

	@Override
	public boolean sendEmail(CoursesPercentageDTO course , SemesterWeeksDTO week, List<WeekFeedbackResultsDTO> details) {
		return service.sendEmail(course,week,details);
	}



}
