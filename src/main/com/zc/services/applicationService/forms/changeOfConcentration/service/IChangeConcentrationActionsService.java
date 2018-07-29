/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.service;

import java.util.List;

import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IChangeConcentrationActionsService {
	public ChangeConcentrationDTO getByID(Integer id);
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public ChangeConcentrationDTO forwardPetition(ChangeConcentrationDTO dto);
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto);
	public List<InstructorDTO> fillInsLst();
	public ChangeConcentrationDTO update(ChangeConcentrationDTO dto);
}
