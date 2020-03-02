/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;


/**
 * @author omnya.alaa
 *
 */
public interface IAdmissionDeptReadmissionService {

	public ReadmissionDTO updateRequest(ReadmissionDTO dto);
	public List<ReadmissionDTO> getPendingPetitionsOfstuent();
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent();
	
	public void addComment(Integer id, String comment);
}
