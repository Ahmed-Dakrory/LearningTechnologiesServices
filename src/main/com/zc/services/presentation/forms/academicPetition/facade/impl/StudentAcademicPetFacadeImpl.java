/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.academicPetition.services.IStudentAcademicPetitionService;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
@Service("StudentAcademicPetFacadeImpl")
public class StudentAcademicPetFacadeImpl implements IStudentAcademicPetFacade{

	@Autowired
	IStudentAcademicPetitionService service;
	@Override
	public List<CoursePetitionDTO> getPendingPetOfStudent(Integer studentId) {
		
		return service.getPendingPetOfStudent(studentId);
	}

	@Override
	public List<CoursePetitionDTO> getOldPetOfStudent(Integer studentId) {
	
		return service.getOldPetOfStudent(studentId);
	}

	@Override
	public CoursePetitionDTO submit(CoursePetitionDTO dto) {
		
		return service.submit(dto);
	}

	@Override
	public List<CoursesDTO> getAllCourses() {
		
		return service.getAllCourses();
	}

	@Override
	public CoursePetitionDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public List<CoursesDTO> getAllCoursesBySemesterAndYear(Integer semester,
			Integer year) {
		
		return service.getAllCoursesBySemesterAndYear(semester, year);
	}

}
