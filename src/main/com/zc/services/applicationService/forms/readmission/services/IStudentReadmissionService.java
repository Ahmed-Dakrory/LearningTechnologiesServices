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
public interface IStudentReadmissionService {
	
	public ReadmissionDTO add(ReadmissionDTO dto);
	public List<ReadmissionDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent(Integer studentID);
	
	public ReadmissionDTO getByID(Integer id);

}
