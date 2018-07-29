/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.changeMajor.services.IInstructorChangeMajorService;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IchangeMajorInsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("ChangeMajorInsFacadeImpl")
public class ChangeMajorInsFacadeImpl implements IchangeMajorInsFacade{

	@Autowired
	IInstructorChangeMajorService service;
	@Override
	public List<ChangeMajorDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<ChangeMajorDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto) {
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<ChangeMajorDTO> getPendingFormsOfDean() {
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<ChangeMajorDTO> getArchievedFormsOfDean() {
		return service.getArchievedFormsOfDean();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}

	@Override
	public ChangeMajorDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public ChangeMajorDTO forwardPetition(ChangeMajorDTO dto) {
		
		return service.forwardPetition(dto);
	}
}
