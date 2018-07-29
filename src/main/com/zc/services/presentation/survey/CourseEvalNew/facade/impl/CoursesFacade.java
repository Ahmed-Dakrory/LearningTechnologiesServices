package main.com.zc.services.presentation.survey.CourseEvalNew.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.configuration.services.ICoursesService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IStudentAddDropFormServices;
import main.com.zc.services.applicationService.shared.service.IStudentsService;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalAnswersAppService;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICoursesFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("ICoursesEvalFacade")
public class CoursesFacade implements ICoursesFacade {

	@Autowired
	IStudentAddDropFormServices studentService;
	@Autowired
	ICourseEvalQuestionsAppService questionsAppService;
	@Autowired
	ICourseEvalAnswersAppService answersAppService;		
	@Autowired
	ICoursesService coursesService;
	@Autowired
	IStudentsService studentCourseService;
	
	@Override
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id) {
		
		return studentService.getAllInstructorsOfCourse(id);
	}

	@Override
	public List<CourseEvalInsQuestionsDTO> getAllInstructorQuestions(int category) {		
		
		return questionsAppService.getInstSectionQuestions(category);
	}

	@Override
	public List<CourseEvalAnswersDTO> getEmpQuestAns(Integer empId,
			Integer courseId, Integer studentId) {

		return answersAppService.getEvalAnsByCourseIdAndEmpIdAndStudentId(courseId, empId, studentId);
	}

	@Override
	public InstructorDTO getInstructorById(Integer id) {
	
		return questionsAppService.getInsByID(id);
	}

	@Override
	public CoursesDTO getCourseById(Integer id) {
		
		return coursesService.getCourseById(id);
	}

	@Override
	public void saveInstructorEval(List<CourseEvalAnswersDTO> answers,Integer type) {
		
		for (CourseEvalAnswersDTO courseEvalAnswersDTO : answers) {
			
			answersAppService.addInstAns(courseEvalAnswersDTO,type);
		}
	}
	
	@Override
	public void saveInstructorEvalUpdate(List<CourseEvalAnswersDTO> answers,Integer type) {
		/*
		 * (Ahmed Dakrory)
		 * @see main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep#deleteAllcourseData(main.com.zc.services.domain.courseEval.model.CourseEvalAnswers)
		 */
		answersAppService.deleteAllcourseData(answers.get(0));
		for (CourseEvalAnswersDTO courseEvalAnswersDTO : answers) {
			
			answersAppService.addInstAns(courseEvalAnswersDTO,type);
		}
	}

	@Override
	public CourseEvalQuestionsDTO getQuestionById(Integer id) {
		
		return questionsAppService.getById(id);
	}

	@Override
	public void deleteAns(CourseEvalAnswersDTO form) {
		
		answersAppService.delete(form);
	}

	@Override
	public List<CoursesDTO> getAllCourses() {
		
		return coursesService.getAllCourses();
	}
	
}
