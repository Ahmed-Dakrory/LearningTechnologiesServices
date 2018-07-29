/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeConcentrationInsService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationInsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IChangeConcentrationInsFacade")
public class ChangeConcentrationInsFacadeImpl implements IChangeConcentrationInsFacade{

	@Autowired
	IChangeConcentrationInsService service;
	@Override
	public List<ChangeConcentrationDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<ChangeConcentrationDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public ChangeConcentrationDTO getByID(Integer id) {
		return service.getByID(id);
	}

	

	@Override
	public List<InstructorDTO> fillInsLst() {
		return service.fillInsLst();
	}

	@Override
	public ChangeConcentrationDTO forwardPetition(ChangeConcentrationDTO dto) {
		return service.forwardPetition(dto);
	}

}
