/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IChangeConcentrationInsFacade {
	public List<ChangeConcentrationDTO> getPendingFormsOfInstructor(Integer insID);
	public List<ChangeConcentrationDTO> getArchievedFormsOfInstructor(Integer insID);
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto);
	public ChangeConcentrationDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public ChangeConcentrationDTO forwardPetition(ChangeConcentrationDTO dto);
}
