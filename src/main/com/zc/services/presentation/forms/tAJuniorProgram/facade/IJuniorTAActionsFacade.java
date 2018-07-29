/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IJuniorTAActionsFacade {

	public TAJuniorProgramDTO getByID(Integer id);

	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) ;

	public TAJuniorProgramDTO updateStatusOfForm(TAJuniorProgramDTO dto) ;

	public TAJuniorProgramDTO updateRequest(TAJuniorProgramDTO dto);
	public boolean approvedBefore(Integer formID , Integer formType, Integer insID);
}
