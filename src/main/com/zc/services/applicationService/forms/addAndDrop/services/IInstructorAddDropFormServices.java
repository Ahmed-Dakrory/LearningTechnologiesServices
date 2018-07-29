/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services;

import java.util.List;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorAddDropFormServices {
	public List<DropAddFormDTO> getPendingFormsOfInstructor(Integer insID);
	public List<DropAddFormDTO> getArchievedFormsOfInstructor(Integer insID);
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto);
	public List<DropAddFormDTO> getPendingFormsOfDean();
	public List<DropAddFormDTO> getArchievedFormsOfDean();
	public void addComment(Integer id, String comment);
	public DropAddFormDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public DropAddFormDTO forwardPetition(DropAddFormDTO dto);
}
