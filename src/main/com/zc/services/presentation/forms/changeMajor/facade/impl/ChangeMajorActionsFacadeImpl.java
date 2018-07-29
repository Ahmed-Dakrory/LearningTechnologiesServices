/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeMajor.services.IChangeMajorActionsService;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 * @author Eman
 */
@Service("IChangeMajorActionsFacade")
public class ChangeMajorActionsFacadeImpl implements IChangeMajorActionsFacade{

	@Autowired
	IChangeMajorActionsService service;
	@Override
	public ChangeMajorDTO getByID(Integer id) {
		
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
	public ChangeMajorDTO forwardPetition(ChangeMajorDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public ChangeMajorDTO update(ChangeMajorDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
