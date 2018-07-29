/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IAdmissionDeptAddDropFacade {
	public List<DropAddFormDTO> getPendingFormsOfAdmissionDept();
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionDept();
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
	public void addComment(Integer id, String comment);

}
