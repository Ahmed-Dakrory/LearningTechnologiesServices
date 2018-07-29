/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.IJuniorTAActionsService;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.IJuniorTAActionsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IJuniorTAActionsFacade")
public class JuniorTAActionsFacadeImpl implements IJuniorTAActionsFacade{

	@Autowired
	IJuniorTAActionsService service;
	@Override
	public TAJuniorProgramDTO getByID(Integer id) {
		
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
	public TAJuniorProgramDTO updateStatusOfForm(TAJuniorProgramDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public TAJuniorProgramDTO updateRequest(TAJuniorProgramDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public boolean approvedBefore(Integer formID, Integer formType, Integer insID) {
		
		return service.approvedBefore(formID, formType,  insID);
	}


}
