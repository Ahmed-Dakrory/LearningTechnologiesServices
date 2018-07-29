/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("ICourseEvalQuestionsFacade")
public class CourseEvalQuestionsFacadeImpl implements ICourseEvalQuestionsFacade{

	@Autowired
	ICourseEvalQuestionsAppService service;
	@Override
	public List<CourseEvalQuestionsDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public List<CourseEvalQuestionsDTO> getByCategoryID(Integer id) {
		
		return service.getByCategoryID(id);
	}

	@Override
	public CourseEvalQuestionsDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public CourseEvalQuestionsDTO add(CourseEvalQuestionsDTO form) {
		
		return service.add(form);
	}

	@Override
	public CourseEvalQuestionsDTO update(CourseEvalQuestionsDTO form) {
		
		return service.update(form);
	}

	@Override
	public boolean delete(CourseEvalQuestionsDTO form) {
		
		return service.delete(form);
	}

	@Override
	public InstructorDTO getInsByID(Integer id) {
		
		return service.getInsByID(id);
	}

	@Override
	public List<CoursesDTO> getCoursesByStudentID(Integer studentId) {
		
		return service.getCoursesByStudentID(studentId);
	}

	@Override
	public List<CourseEvalQuestionsDTO> getBySectionID(Integer id) {
		return service.getBySectionID(id);
	}

	@Override
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(
			Integer studentId, Integer semester, Integer year) {
		return service. getCoursesByStudentIDAndSemesterAndYear(
				 studentId,  semester,  year);
	}

	@Override
	public boolean deleteSelection(ScaleSelectionsDTO selection) {
		return service.deleteSelection(selection);
				 
	}

	@Override
	public List<CourseEvalQuestionsDTO> getByCourseID(int id) {
		return service.getByCourseID(id);
	}

}
