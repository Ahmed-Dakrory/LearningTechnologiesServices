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
public interface Icourse_replacement_formAdmissionDeptFacade {

	public course_replacement_formDTO updateRequest(course_replacement_formDTO dto);
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent();
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent();
	public void addComment(Integer id, String comment);
	
}
