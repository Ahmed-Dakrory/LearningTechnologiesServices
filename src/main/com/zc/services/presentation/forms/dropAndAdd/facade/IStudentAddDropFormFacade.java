/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface IStudentAddDropFormFacade {

	public DropAddFormDTO addForm(DropAddFormDTO dto);
	public DropAddFormDTO getByID(Integer id);
	public List<DropAddFormDTO> getPendingFormsOfStudent(Integer studentId);
	public List<DropAddFormDTO> getArchievedFormsOfStudent(Integer studentId);

	public List<CoursesDTO> getAllCourses();
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id);
}
