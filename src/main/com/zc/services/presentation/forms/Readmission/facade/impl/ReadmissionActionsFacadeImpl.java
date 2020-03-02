/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.readmission.services.IReadmissionActionsService;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 * @author Eman
 */
@Service("IReadmissionActionsFacade")
public class ReadmissionActionsFacadeImpl implements IReadmissionActionsFacade{

	@Autowired
	IReadmissionActionsService service;
	@Override
	public ReadmissionDTO getByID(Integer id) {
		
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
	public ReadmissionDTO forwardPetition(ReadmissionDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public ReadmissionDTO update(ReadmissionDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
