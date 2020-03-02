/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author Dakrory
 * @author Dakrory
 */
public interface IReadmissionActionsFacade {

	public ReadmissionDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public ReadmissionDTO forwardPetition(ReadmissionDTO dto);
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto);
	public ReadmissionDTO update(ReadmissionDTO dto);
	
	
}
