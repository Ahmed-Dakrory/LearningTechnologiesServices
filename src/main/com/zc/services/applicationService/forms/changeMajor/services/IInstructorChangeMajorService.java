/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services;
import java.util.List;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorChangeMajorService {

	public List<ChangeMajorDTO> getPendingFormsOfInstructor(Integer insID);
	public List<ChangeMajorDTO> getArchievedFormsOfInstructor(Integer insID);
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto);
	public List<ChangeMajorDTO> getPendingFormsOfDean();
	public List<ChangeMajorDTO> getArchievedFormsOfDean();
	public ChangeMajorDTO getByID(Integer id);
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public ChangeMajorDTO forwardPetition(ChangeMajorDTO dto);
}
