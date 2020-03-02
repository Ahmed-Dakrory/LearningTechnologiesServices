/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.readmission.services.IInstructorReadmissionService;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionInsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("ReadmissionInsFacadeImpl")
public class ReadmissionInsFacadeImpl implements IReadmissionInsFacade{

	@Autowired
	IInstructorReadmissionService service;
	@Override
	public List<ReadmissionDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<ReadmissionDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto) {
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<ReadmissionDTO> getPendingFormsOfDean() {
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<ReadmissionDTO> getArchievedFormsOfDean() {
		return service.getArchievedFormsOfDean();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}

	@Override
	public ReadmissionDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public ReadmissionDTO forwardPetition(ReadmissionDTO dto) {
		
		return service.forwardPetition(dto);
	}

	
}
