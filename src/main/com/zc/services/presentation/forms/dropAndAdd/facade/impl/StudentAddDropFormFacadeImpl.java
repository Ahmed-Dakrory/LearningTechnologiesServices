/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.IStudentAddDropFormServices;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("StudentAddDropFormFacadeImpl")
public class StudentAddDropFormFacadeImpl implements IStudentAddDropFormFacade {

	@Autowired
	IStudentAddDropFormServices studentService;

	
	@Override
	public DropAddFormDTO addForm(DropAddFormDTO dto) {
		return studentService.addForm(dto);
	}

	@Override
	public List<DropAddFormDTO> getPendingFormsOfStudent(Integer studentId) {
		
		return studentService.getPendingFormsOfStudent(studentId);
	}

	@Override
	public List<DropAddFormDTO> getArchievedFormsOfStudent(Integer studentId) {
		
		return studentService.getArchievedFormsOfStudent(studentId);
	}



	@Override
	public List<CoursesDTO> getAllCourses() {
		
		return studentService.getAllCourses();
	}

	@Override
	public DropAddFormDTO getByID(Integer id) {
		
		return studentService.getByID(id);
	}

	@Override
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id) {
		
		return studentService.getAllInstructorsOfCourse(id);
	}

}
