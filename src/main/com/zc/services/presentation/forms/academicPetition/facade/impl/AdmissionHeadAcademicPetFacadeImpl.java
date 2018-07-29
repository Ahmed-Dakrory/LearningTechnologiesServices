/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionHeadAcademicPetService;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionHeadAcademicPetFacade;

/**
 * @author omnya
 *
 */
@Service("AdmissionHeadAcademicPetFacadeImpl")
public class AdmissionHeadAcademicPetFacadeImpl implements IAdmissionHeadAcademicPetFacade {

	@Autowired
	IAdmissionHeadAcademicPetService service;
	@Override
	public List<CoursePetitionDTO> getPendingPet() {
		
		return service.getPendingPet();
	}

	@Override
	public List<CoursePetitionDTO> getOldPet() {
		
		return service.getOldPet();
	}

	@Override
	public CoursePetitionDTO update(CoursePetitionDTO dto) {
		
		return service.update(dto);
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		//service.addComment(id, comment);
	}
}
