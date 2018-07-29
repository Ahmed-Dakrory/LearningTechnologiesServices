/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services;

import java.util.List;

import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;

/**
 * @author momen
 *
 */
public interface IAdminAddDropFormService {

	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead();
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead();
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
}
