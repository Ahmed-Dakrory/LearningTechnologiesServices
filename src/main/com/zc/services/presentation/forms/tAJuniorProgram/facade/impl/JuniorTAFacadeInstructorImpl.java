/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.IJuniorTAServiceInstructor;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.IJuniorTAFacadeInstructor;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IJuniorTAFacadeInstructor")
public class JuniorTAFacadeInstructorImpl implements IJuniorTAFacadeInstructor{

	@Autowired
	IJuniorTAServiceInstructor service;
	@Override
	public List<TAJuniorProgramDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<TAJuniorProgramDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public List<TAJuniorProgramDTO> getPendingFormsOfPA(Integer insID) {
		
		return service.getPendingFormsOfPA(insID);
	}

	@Override
	public List<TAJuniorProgramDTO> getArchievedFormsOfPA(Integer insID) {
		return service.getArchievedFormsOfPA(insID);
	}

	@Override
	public TAJuniorProgramDTO updateStatusOfForm(TAJuniorProgramDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<TAJuniorProgramDTO> getPendingFormsOfDean() {
		
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<TAJuniorProgramDTO> getArchievedFormsOfDean() {
		
		return service.getArchievedFormsOfDean();
	}

	@Override
	public TAJuniorProgramDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		service.addComment(dto, instructorID);
		
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public List<TAJuniorProgramDTO> getByCourseID(Integer id) {
		
		return service.getByCourseID(id);
	}


}
