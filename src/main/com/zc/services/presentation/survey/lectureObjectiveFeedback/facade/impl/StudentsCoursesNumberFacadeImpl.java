/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.configuration.services.IStudentsCoursesNumberAppService;
import main.com.zc.services.presentation.configuration.dto.StudentsCoursesNumberDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.IStudentsCoursesNumberFacade;

/**
 * @author omnya
 *
 */
@Service("IStudentsCoursesNumberFacade")
public class StudentsCoursesNumberFacadeImpl implements IStudentsCoursesNumberFacade{

	@Autowired
	IStudentsCoursesNumberAppService service;

	@Override
	public List<StudentsCoursesNumberDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public StudentsCoursesNumberDTO getByCourseID(Integer id) {
		return service.getByCourseID(id);
	}

	@Override
	public StudentsCoursesNumberDTO getById(Integer id) {
		return service.getById(id);
	}

	@Override
	public StudentsCoursesNumberDTO add(StudentsCoursesNumberDTO courseStudent) {
		return service.add(courseStudent);
	}

	@Override
	public StudentsCoursesNumberDTO update(StudentsCoursesNumberDTO courseStudent) {
		return service.update(courseStudent);
	}

	@Override
	public boolean remove(StudentsCoursesNumberDTO obj) {
		return service.remove(obj);
	}
	

}
