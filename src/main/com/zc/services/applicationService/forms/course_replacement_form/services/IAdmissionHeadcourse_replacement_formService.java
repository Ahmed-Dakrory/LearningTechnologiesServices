/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;


/**
 * @author omnya
 *
 */
public interface IAdmissionHeadcourse_replacement_formService {

	public List<course_replacement_formDTO> getPendingFormsOfAdmissionHead();
	public List<course_replacement_formDTO> getArchievedFormsOfAdmissionHead();
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto);
	
	public void addComment(Integer id, String comment);
}
