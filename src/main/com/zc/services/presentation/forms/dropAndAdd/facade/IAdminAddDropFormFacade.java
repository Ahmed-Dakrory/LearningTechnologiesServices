/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;

/**
 * @author momen
 *
 */
public interface IAdminAddDropFormFacade {
	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead();
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead();
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
	public void notifyNextUser(DropAddFormDTO dto);
}
