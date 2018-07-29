/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.academicPetition.services.IAcademicPetitionActionsService;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.PetitionsActions;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAcademicPetitionActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya 
 *
 */
@Service("IAcademicPetitionActionsFacade")
public class AcademicPetitionActionsFacadeImpl implements IAcademicPetitionActionsFacade {

	@Autowired
	IAcademicPetitionActionsService service;
	@Override
	public CoursePetitionDTO getByID(Integer id) {
		
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
	public CoursePetitionDTO forwardPetition(CoursePetitionDTO dto) {
		
		return service.forwardPetition(dto);
	}
	@Override
	public CoursePetitionDTO updateStatusOfForm(CoursePetitionDTO dto) {
		return service.updateStatusOfForm(dto);
	}
	@Override
	public CoursePetitionDTO update(CoursePetitionDTO dto) {
		return service.update(dto);
	}

	
}
