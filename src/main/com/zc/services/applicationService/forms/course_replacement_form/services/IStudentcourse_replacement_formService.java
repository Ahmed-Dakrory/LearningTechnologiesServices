/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IStudentcourse_replacement_formService {
	
	public course_replacement_formDTO add(course_replacement_formDTO dto);
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent(Integer studentID);
	
	public course_replacement_formDTO getByID(Integer id);

}
