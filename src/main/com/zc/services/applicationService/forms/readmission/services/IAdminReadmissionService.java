/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;


/**
 * @author momen
 *
 */
public interface IAdminReadmissionService {

	public ReadmissionDTO updateRequest(ReadmissionDTO dto);
	public List<ReadmissionDTO> getPendingPetitionsOfstuent();
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent();
	
}
