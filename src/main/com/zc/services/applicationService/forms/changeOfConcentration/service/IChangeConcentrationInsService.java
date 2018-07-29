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
public interface IChangeConcentrationInsService {

	public List<ChangeConcentrationDTO> getPendingFormsOfInstructor(Integer insID);
	public List<ChangeConcentrationDTO> getArchievedFormsOfInstructor(Integer insID);
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto);
	public ChangeConcentrationDTO getByID(Integer id);
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public List<InstructorDTO> fillInsLst();
	public ChangeConcentrationDTO forwardPetition(ChangeConcentrationDTO dto);
	
	
	
}
