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
public interface IAdmissionDeptAddDropFormService {

	public List<DropAddFormDTO> getPendingFormsOfAdmissionDept();
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionDept();
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
	public void addComment(Integer id, String comment);
}
