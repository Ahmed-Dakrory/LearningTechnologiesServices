/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAddDropActionsService;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAddDropActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IAddDropActionsFacade")
public class AddDropActionsFacadeImpl implements IAddDropActionsFacade {

	@Autowired
	IAddDropActionsService service;
	@Override
	public DropAddFormDTO getByID(Integer id) {
		
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
	public DropAddFormDTO forwardPetition(DropAddFormDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public DropAddFormDTO update(DropAddFormDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
