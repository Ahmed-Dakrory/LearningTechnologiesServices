/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IOverloadRequestActionsSharedService;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IOverloadRequestActionsSharedFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IOverloadRequestActionsSharedFacade")
public class OverloadRequestActionsSharedFacadeImpl implements IOverloadRequestActionsSharedFacade{

	@Autowired
	IOverloadRequestActionsSharedService service;
	@Override
	public OverloadRequestDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		service.addComment(dto, instructorID);
		
	}

	@Override
	public OverloadRequestDTO forwardPetition(OverloadRequestDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public OverloadRequestDTO updateStatusOfForm(OverloadRequestDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public OverloadRequestDTO update(OverloadRequestDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
