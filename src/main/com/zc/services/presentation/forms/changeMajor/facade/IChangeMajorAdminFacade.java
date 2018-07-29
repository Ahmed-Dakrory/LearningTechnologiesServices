/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author momen
 *
 */
public interface IChangeMajorAdminFacade {

	public ChangeMajorDTO updateRequest(ChangeMajorDTO dto);
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent();
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent();
	public void notifyNextUser(ChangeMajorDTO dto);
}
