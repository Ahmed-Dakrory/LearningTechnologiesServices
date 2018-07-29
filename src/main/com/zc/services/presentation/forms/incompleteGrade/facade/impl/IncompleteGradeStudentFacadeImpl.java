/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeStudentService;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeStudentFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IIncompleteGradeStudentFacade")
public class IncompleteGradeStudentFacadeImpl implements IIncompleteGradeStudentFacade{

	@Autowired
	IIncompleteGradeStudentService service;
	@Override
	public IncompleteGradeDTO add(IncompleteGradeDTO dto) {
		
		return service.add(dto);
	}

	@Override
	public List<IncompleteGradeDTO> getPendingPetitionsOfstuent(
			Integer studentID) {
		
		return service.getPendingPetitionsOfstuent(studentID);
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedPetitionsOfstuent(
			Integer studentID) {
		
		return service.getArchievedPetitionsOfstuent(studentID);
	}

	

	@Override
	public List<CoursesDTO> getAllCourses() {
		
		return service.getAllCourses();
	}

	@Override
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id) {
		
		return service.getAllInstructorsOfCourse(id);
	}

	@Override
	public IncompleteGradeDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

}
