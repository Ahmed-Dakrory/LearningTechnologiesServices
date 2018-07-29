/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author omnya
 *
 */
public interface IchangeMajorAdmissionHeadFacade {
	public List<ChangeMajorDTO> getPendingFormsOfAdmissionHead();
	public List<ChangeMajorDTO> getArchievedFormsOfAdmissionHead();
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto);
	public void addComment(Integer id, String comment);
}
