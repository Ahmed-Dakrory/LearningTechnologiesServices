/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IOverloadRequestActionsSharedFacade {
	public OverloadRequestDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) ;
	public OverloadRequestDTO forwardPetition(OverloadRequestDTO dto);
	public OverloadRequestDTO updateStatusOfForm(OverloadRequestDTO dto) ;
	public OverloadRequestDTO update(OverloadRequestDTO dto);
}
