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
public interface IJuniorTAFacadeInstructor {

	public List<TAJuniorProgramDTO> getPendingFormsOfInstructor(Integer insID);
	public List<TAJuniorProgramDTO> getArchievedFormsOfInstructor(Integer insID);
	public List<TAJuniorProgramDTO> getPendingFormsOfPA(Integer insID);
	public List<TAJuniorProgramDTO> getArchievedFormsOfPA(Integer insID);
	public TAJuniorProgramDTO updateStatusOfForm(TAJuniorProgramDTO dto);
	public List<TAJuniorProgramDTO> getPendingFormsOfDean();
	public List<TAJuniorProgramDTO> getArchievedFormsOfDean();
	public TAJuniorProgramDTO getByID(Integer id);
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public List<InstructorDTO> fillInsLst();
	
	public List<TAJuniorProgramDTO> getByCourseID(Integer id);
	
}
