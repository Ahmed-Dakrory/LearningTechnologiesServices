/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IChangeMajorStudentFacade {

	
	public ChangeMajorDTO add(ChangeMajorDTO dto);
	public ChangeMajorDTO getByID(Integer id);
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent(Integer studentID);
	
}
