/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services;

import java.util.List;

import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IAdmissionDeptChangeMajorService {

	public ChangeMajorDTO updateRequest(ChangeMajorDTO dto);
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent();
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent();
	
	public void addComment(Integer id, String comment);
}
