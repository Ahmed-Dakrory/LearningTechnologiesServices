/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 * @author Eman
 */
public interface IChangeMajorActionsFacade {

	public ChangeMajorDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public ChangeMajorDTO forwardPetition(ChangeMajorDTO dto);
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto);
	public ChangeMajorDTO update(ChangeMajorDTO dto);
	
	
}
