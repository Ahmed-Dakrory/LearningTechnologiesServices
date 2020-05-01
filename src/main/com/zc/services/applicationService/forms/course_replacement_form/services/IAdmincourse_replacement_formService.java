/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;


/**
 * @author momen
 *
 */
public interface IAdmincourse_replacement_formService {

	public course_replacement_formDTO updateRequest(course_replacement_formDTO dto);
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent();
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent();
	
}
