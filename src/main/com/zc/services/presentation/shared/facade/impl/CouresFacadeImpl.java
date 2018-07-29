/**
 * 
 */
package main.com.zc.services.presentation.shared.facade.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.ICouresAppService;
import main.com.zc.services.presentation.shared.facade.ICouresFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
@Service("ICouresFacade")
public class CouresFacadeImpl implements ICouresFacade {

	@Autowired
	ICouresAppService service;
	@Override
	public List<CoursesDTO> getCoursesByStudentID(Integer studentId) {
		return service.getCoursesByStudentID(studentId);
	}

	@Override
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(Integer studentId, Integer semester, Integer year) {
		return service.getCoursesByStudentIDAndSemesterAndYear(studentId,semester,year);
	}

	@Override
	public List<CoursesDTO> getCoursesBySemesterAndYear(Integer semester, Integer year) {
		return service.getCoursesBySemesterAndYear(semester,year);
		
	}

	@Override
	public List<CoursesDTO> getAll() {
		return service.getAll();
	}

	@Override
	public CoursesDTO getcourseById(Integer id) {
		return service.getcourseById(id);
	}


	@Override
	public CoursesDTO getcourseByNameAndSemesterAndYear(String name, Integer semester, Integer year) {
		return service.getcourseByNameAndSemesterAndYear( name,  semester,  year);
	}

}
