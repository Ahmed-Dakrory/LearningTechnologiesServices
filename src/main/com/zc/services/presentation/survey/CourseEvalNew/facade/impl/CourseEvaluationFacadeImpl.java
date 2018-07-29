package main.com.zc.services.presentation.survey.CourseEvalNew.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICourseEvaluationFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;


/**
 * @author omnya
 *
 */
@Service("ICourseEvaluationFacade")
public class CourseEvaluationFacadeImpl implements ICourseEvaluationFacade {

	@Autowired
	ICourseEvalQuestionsAppService courseEvalService;	
	
	@Override
	public List<InstructorDTO> getTasByCourseID(Integer courseID) {
		
		return courseEvalService.getTasByCourseID(courseID);
	}
	@Override
	public List<InstructorDTO> getInstructorsByCourseID(Integer courseID) {
		return courseEvalService.getInstructorsByCourseID(courseID);
	}
	@Override
	public List<CoursesDTO> getAllCourses() {
		return courseEvalService.getAllCourses();		
	}

}
