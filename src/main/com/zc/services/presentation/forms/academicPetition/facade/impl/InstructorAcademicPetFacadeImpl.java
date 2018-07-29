/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.academicPetition.services.IInstructorAcademicPetitionService;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IInstructorAcademicPetFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("InstructorAcademicPetFacadeImpl")
public class InstructorAcademicPetFacadeImpl implements IInstructorAcademicPetFacade{

	@Autowired
	IInstructorAcademicPetitionService service;
	@Override
	public List<CoursePetitionDTO> getPendingPetOfInstructor(Integer insId) {
		
		return service.getPendingPetOfInstructor(insId);
	}

	@Override
	public List<CoursePetitionDTO> getOldPetOfInstructor(Integer insId) {
		
		return service.getOldPetOfInstructor(insId);
	}

	@Override
	public CoursePetitionDTO update(CoursePetitionDTO dto) {
		
		return service.update(dto);
	}

	@Override
	public List<CoursePetitionDTO> getPendingPetOfDean() {
	
		return service.getPendingPetOfDean();
	}

	@Override
	public List<CoursePetitionDTO> getOldPetOfDean() {
		
		return service.getOldPetOfDean();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		//service.addComment(id, comment);
	}

	@Override
	public CoursePetitionDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public CoursePetitionDTO forwardPetition(CoursePetitionDTO dto) {
		
		return service.forwardPetition(dto);
	}

}
