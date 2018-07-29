/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorAddDropFormFacade {

	public List<DropAddFormDTO> getPendingFormsOfInstructor(Integer insID);
	public List<DropAddFormDTO> getArchievedFormsOfInstructor(Integer insID);
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
	public DropAddFormDTO getByID(Integer id);
	public List<DropAddFormDTO> getPendingFormsOfDean();
	public List<DropAddFormDTO> getArchievedFormsOfDean();
	
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public DropAddFormDTO forwardPetition(DropAddFormDTO dto);
}
