/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IReadmissionStudentFacade {

	
	public ReadmissionDTO add(ReadmissionDTO dto);
	public ReadmissionDTO getByID(Integer id);
	public List<ReadmissionDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent(Integer studentID);
	
}
