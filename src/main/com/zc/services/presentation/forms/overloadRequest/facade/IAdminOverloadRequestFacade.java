/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

/**
 * @author momen
 *
 */
public interface IAdminOverloadRequestFacade {

	public List<OverloadRequestDTO> getPendingForms();
	public List<OverloadRequestDTO> getArchievedForms();
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto);
	public void notifyNextUser(OverloadRequestDTO dto);
}
