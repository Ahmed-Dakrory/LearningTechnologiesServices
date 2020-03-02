/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;


/**
 * @author momen
 *
 */
public interface IReadmissionAdminFacade {

	public ReadmissionDTO updateRequest(ReadmissionDTO dto);
	public List<ReadmissionDTO> getPendingPetitionsOfstuent();
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent();
	public void notifyNextUser(ReadmissionDTO dto);
}
