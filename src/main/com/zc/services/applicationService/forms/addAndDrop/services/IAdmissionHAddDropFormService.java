/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services;

import java.util.List;

import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IAdmissionHAddDropFormService {

	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead();
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead();
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
	public void addComment(Integer id, String comment);
}
