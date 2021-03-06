/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;


/**
 * @author omnya
 *
 */
public interface IreadmissionAdmissionHeadFacade {
	public List<ReadmissionDTO> getPendingFormsOfAdmissionHead();
	public List<ReadmissionDTO> getArchievedFormsOfAdmissionHead();
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto);
	public void addComment(Integer id, String comment);
}
