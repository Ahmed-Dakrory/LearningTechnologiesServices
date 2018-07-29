/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeInsService;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeInsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IIncompleteGradeInsFacade")
public class IncompleteGradeInsFacadeImpl implements IIncompleteGradeInsFacade {

	@Autowired
	IIncompleteGradeInsService service;
	@Override
	public List<IncompleteGradeDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public List<IncompleteGradeDTO> getPendingFormsOfPA(Integer insID) {
		
		return service.getPendingFormsOfPA(insID);
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedFormsOfPA(Integer insID) {
		
		return service.getArchievedFormsOfPA(insID);
	}

	@Override
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<IncompleteGradeDTO> getPendingFormsOfDean() {
		
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedFormsOfDean() {
		
		return service.getArchievedFormsOfDean();
	}

	@Override
	public IncompleteGradeDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		service.addComment(dto,instructorID);
		
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public IncompleteGradeDTO forwardPetition(IncompleteGradeDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public IncompleteGradeDTO update(IncompleteGradeDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
