/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.addAndDrop.services.IInstructorAddDropFormServices;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IInstructorAddDropFormFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("InstructorAddDropFormFacadeImpl")
public class InstructorAddDropFormFacadeImpl implements IInstructorAddDropFormFacade {

	@Autowired
	IInstructorAddDropFormServices insService;
	@Override
	public List<DropAddFormDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return insService.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<DropAddFormDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return insService.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) {
		
		return insService.updateStatusOfForm(dto);
	}

	@Override
	public List<DropAddFormDTO> getPendingFormsOfDean() {
		
		return insService.getPendingFormsOfDean();
	}

	@Override
	public List<DropAddFormDTO> getArchievedFormsOfDean() {
		
		return insService.getArchievedFormsOfDean();
	}
	
	@Override
	public void addComment(Integer id, String comment) {
		
		insService.addComment(id, comment);
	}

	@Override
	public DropAddFormDTO getByID(Integer id) {
		
		return insService.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return insService.fillInsLst();
	}

	@Override
	public DropAddFormDTO forwardPetition(DropAddFormDTO dto) {
		
		return insService.forwardPetition(dto);
	}

}
