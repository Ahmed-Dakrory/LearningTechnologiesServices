/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.facade.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalAnswersAppService;
import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("ICourseEvalAnswersFacade")
public class CourseEvalAnswersFacadeImpl implements ICourseEvalAnswersFacade{

	@Autowired
	ICourseEvalAnswersAppService service;
	@Autowired
	ICoursesRepository coursesRep;
	@Autowired
	ICourseStudentRep courseStudentRep;
		@Override
	public List<CourseEvalAnswersDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public List<CourseEvalAnswersDTO> getByCategoryID(Integer id) {
		
		return service.getByCategoryID(id);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestID(Integer id) {
		
		return service.getByQuestID(id);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByStudentID(Integer id) {
		
		return service.getByStudentID(id);
	}

	@Override
	public CourseEvalAnswersDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public CourseEvalAnswersDTO add(CourseEvalAnswersDTO form,Integer surveyType) {
		
		return service.add(form,surveyType);
	}

	@Override
	public CourseEvalAnswersDTO update(CourseEvalAnswersDTO form) {
		
		return service.update(form);
	}

	@Override
	public boolean delete(CourseEvalAnswersDTO form) {
		
		return service.delete(form);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseID(Integer id,
			Integer courseID,Integer type) {
		return service.getByStudentIDAndCourseID( id, courseID ,type) ;
	}

	@Override
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseIDAndInstructor(
			Integer id, Integer courseID, Integer insID) {
		
		return service.getByStudentIDAndCourseIDAndInstructor( id, courseID,insID) ;
	}

	@Override
	public CoursesDTO getCourseById(Integer id) {
		CoursesDTO coursesDTO=new CoursesDTO();
		try{
			Courses course=coursesRep.getById(id);
			coursesDTO.setId(course.getId());
			coursesDTO.setName(course.getName());
			return coursesDTO;
		}
		catch(Exception ex)
		{
			return null;
		}
	}

	@Override
	public List<CourseEvalQuestionsDTO> getFacEvalByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getFacEvalByCourseIDAndInstructorID(courseID, insID);
		
	}

	@Override
	public List<CourseEvalQuestionsDTO> getCourseByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getCourseByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getCourseAssignByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getCourseAssignByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getTAByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getTAByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getLabFacByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getLabFacByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getLabExpByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getLabExpByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getOthersTextByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getOthersTextByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getOthersRateByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		return service.getOthersRateByCourseIDAndInstructorID(courseID, insID);
	}

	@Override
	public List<CoursesDTO> getCoursesByInsID(Integer insID) {
		
		return service.getCoursesByInsID(insID);
	}

	@Override
	public List<String> getCommentsByCategoryIDAndCourseIDAndInstructorID(
			Integer courseID, Integer insID, Integer categoryID) {
		
		return service.getCommentsByCategoryIDAndCourseIDAndInstructorID(courseID, insID, categoryID);
	}

	@Override
	public List<String> getCommentsByCategoryIDAndCourseID(Integer courseID,
			Integer categoryID) {
		return service.getCommentsByCategoryIDAndCourseID(courseID, categoryID);
	}

	@Override
	public List<CoursesDTO> getAllCourses() {
		List<CoursesDTO> coursesDTOs=new ArrayList<CoursesDTO>();
		try{
			List<Courses> courses=coursesRep.getAll();
			for(int i=0;i<courses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(courses.get(i).getId());
				course.setName(courses.get(i).getName());
				coursesDTOs.add(course);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return coursesDTOs;
	}

	@Override
	public List<StudentDTO> getTotalStudentsOfCourse(Integer id,Integer SurveyID) {
		
		return service.getTotalStudentsOfCourse(id,SurveyID);
	}

	@Override
	public Integer getStudentsNumberOfCourse(Integer courseID) {
		try{
		List<CourseStudent> students=courseStudentRep.getByCourseID(courseID);
		return students.size();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public List<CourseEvalAnswersDTO> getByCourseID(Integer courseID) {
		
		return service.getByCourseID(courseID);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseID(
			Integer questionID, Integer courseID) {
		
		return service.getByQuestionIDAndCourseID(questionID, courseID);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsID(
			Integer questionID, Integer courseID, Integer insID) {
		return service.getByQuestionIDAndCourseIDAndInsID(questionID, courseID, insID);
	}


	@Override
	public List<CourseEvalAnswersDTO> getByCourseIDAndInsID(Integer courseID,
			Integer insID) {
		
		return service.getByCourseIDAndInsID(courseID,
				insID);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID) {
	
		return service.getByQuestionIDAndCourseIDAndInsIDAndStudentId(
				questionID, courseID, insID,
				studentID);
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndStudentId(
			Integer questionID, Integer courseID, Integer studentID) {
		return service.getByQuestionIDAndCourseIDAndStudentId(
				questionID, courseID,
				studentID);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getCourseEvalDynamic(Integer courseID,
			Integer category) {
		
		return service.getCourseEvalDynamic(courseID, category);
	}

	@Override
	public List<CourseEvalInsQuestionsDTO> getInstCourseEvalDynamic(
			Integer courseID, Integer category, Integer instId) {
		
		return service.getInstCourseEvalDynamic(courseID, category, instId);
	}

	@Override
	public List<CoursesDTO> getCoursesByYearAndSemester(Integer semester,
			Integer year) {		
		
		List<CoursesDTO> coursesDTOs=new ArrayList<CoursesDTO>();
		try{
			List<Courses> courses=coursesRep.getByYearAndSemester(semester, year);
			for(int i=0;i<courses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(courses.get(i).getId());
				course.setName(courses.get(i).getName());
				coursesDTOs.add(course);
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return coursesDTOs;
	}

	@Override
	public boolean removeDuplicatesFromCourses() {
		
		return service.removeDuplicatesFromCourses();
	}

	@Override
	public boolean removeDuplicatesFromIns() {
		return service.removeDuplicatesFromIns();
	}

}