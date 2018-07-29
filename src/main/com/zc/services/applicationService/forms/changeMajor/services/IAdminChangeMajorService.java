/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services;

import java.util.List;

import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author momen
 *
 */
public interface IAdminChangeMajorService {

	public ChangeMajorDTO updateRequest(ChangeMajorDTO dto);
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent();
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent();
	
}
