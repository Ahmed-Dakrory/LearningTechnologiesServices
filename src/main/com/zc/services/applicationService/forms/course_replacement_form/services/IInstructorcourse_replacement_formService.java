/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services;
import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorcourse_replacement_formService {

	public List<course_replacement_formDTO> getPendingFormsOfInstructor(Integer insID);
	public List<course_replacement_formDTO> getArchievedFormsOfInstructor(Integer insID);
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto);
	public List<course_replacement_formDTO> getPendingFormsOfDean();
	public List<course_replacement_formDTO> getPendingFormsOfDeanOfAcademic();
	public List<course_replacement_formDTO> getArchievedFormsOfDean();
	public course_replacement_formDTO getByID(Integer id);
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public course_replacement_formDTO forwardPetition(course_replacement_formDTO dto);
}
