/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;

/**
 * @author omnya.alaa
 *
 */
public interface Icourse_replacement_formStudentFacade {

	
	public course_replacement_formDTO add(course_replacement_formDTO dto);
	public course_replacement_formDTO getByID(Integer id);
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent(Integer studentID);
	
}
