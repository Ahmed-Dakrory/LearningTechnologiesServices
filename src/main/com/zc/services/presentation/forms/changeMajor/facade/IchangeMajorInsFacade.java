/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IchangeMajorInsFacade {

	public List<ChangeMajorDTO> getPendingFormsOfInstructor(Integer insID);
	public List<ChangeMajorDTO> getArchievedFormsOfInstructor(Integer insID);
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto);
	public ChangeMajorDTO getByID(Integer id);
	public List<ChangeMajorDTO> getPendingFormsOfDean();
	public List<ChangeMajorDTO> getArchievedFormsOfDean();
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public ChangeMajorDTO forwardPetition(ChangeMajorDTO dto);
}
