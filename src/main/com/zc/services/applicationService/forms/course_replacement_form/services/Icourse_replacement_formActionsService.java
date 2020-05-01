/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 * @author Eman
 */
public interface Icourse_replacement_formActionsService {
	public course_replacement_formDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) ;
	public course_replacement_formDTO forwardPetition(course_replacement_formDTO dto);
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto) ;
	public course_replacement_formDTO update(course_replacement_formDTO dto);
}
