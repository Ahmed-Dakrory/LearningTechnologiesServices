/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;


/**
 * @author omnya
 *
 */
public interface Icourse_replacement_formAdmissionHeadFacade {
	public List<course_replacement_formDTO> getPendingFormsOfAdmissionHead();
	public List<course_replacement_formDTO> getArchievedFormsOfAdmissionHead();
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto);
	public void addComment(Integer id, String comment);
}
