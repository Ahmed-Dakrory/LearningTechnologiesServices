/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services;
import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorReadmissionService {

	public List<ReadmissionDTO> getPendingFormsOfInstructor(Integer insID);
	public List<ReadmissionDTO> getArchievedFormsOfInstructor(Integer insID);
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto);
	public List<ReadmissionDTO> getPendingFormsOfDean();
	public List<ReadmissionDTO> getArchievedFormsOfDean();
	public ReadmissionDTO getByID(Integer id);
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public ReadmissionDTO forwardPetition(ReadmissionDTO dto);
}
