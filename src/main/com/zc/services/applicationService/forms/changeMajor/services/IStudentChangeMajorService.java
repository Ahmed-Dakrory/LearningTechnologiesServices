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
public interface IStudentChangeMajorService {
	
	public ChangeMajorDTO add(ChangeMajorDTO dto);
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent(Integer studentID);
	
	public ChangeMajorDTO getByID(Integer id);

}
