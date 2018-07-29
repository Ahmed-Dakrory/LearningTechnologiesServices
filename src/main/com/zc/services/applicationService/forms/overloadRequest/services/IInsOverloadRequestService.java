/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services;

import java.util.List;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInsOverloadRequestService {

	public List<OverloadRequestDTO> getPendingFormsOfInstructor(Integer insID);
	public List<OverloadRequestDTO> getArchievedFormsOfInstructor(Integer insID);
	public OverloadRequestDTO updateStatusOfForm(OverloadRequestDTO dto);
	public List<OverloadRequestDTO> getPendingFormsOfDean();
	public List<OverloadRequestDTO> getArchievedFormsOfDean();
	public List<OverloadRequestDTO> getPendingFormsOfProvost();
	public List<OverloadRequestDTO> getArchievedFormsOfProvost();
	public void addComment(Integer id, String comment);
	public OverloadRequestDTO gteById(Integer id);
	public List<InstructorDTO> fillInsLst();
	public OverloadRequestDTO forwardPetition(OverloadRequestDTO dto);
}
