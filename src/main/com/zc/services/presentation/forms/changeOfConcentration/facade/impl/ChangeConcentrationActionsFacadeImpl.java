/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeConcentrationActionsService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IChangeConcentrationActionsFacade")
public class ChangeConcentrationActionsFacadeImpl implements IChangeConcentrationActionsFacade {

	@Autowired
	IChangeConcentrationActionsService service;
	@Override
	public ChangeConcentrationDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		 service.addComment(dto, instructorID);
		
	}

	@Override
	public ChangeConcentrationDTO forwardPetition(ChangeConcentrationDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public ChangeConcentrationDTO update(ChangeConcentrationDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
