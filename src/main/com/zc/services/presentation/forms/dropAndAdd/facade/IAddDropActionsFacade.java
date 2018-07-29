/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IAddDropActionsFacade {

	public DropAddFormDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) ;
	public DropAddFormDTO forwardPetition(DropAddFormDTO dto);
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) ;
	public DropAddFormDTO update(DropAddFormDTO dto);
	
}
