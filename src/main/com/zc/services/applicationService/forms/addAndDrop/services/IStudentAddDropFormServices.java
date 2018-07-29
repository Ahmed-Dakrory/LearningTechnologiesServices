/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services;

import java.util.List;

import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface IStudentAddDropFormServices {

	public DropAddFormDTO addForm(DropAddFormDTO dto);
	public List<DropAddFormDTO> getPendingFormsOfStudent(Integer studentId);
	public List<DropAddFormDTO> getArchievedFormsOfStudent(Integer studentId);
	public List<CoursesDTO> getAllCourses();
	public DropAddFormDTO getByID(Integer id);
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id);
}
