/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.services.IAdmissionDeptAcademicPetService;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAdmissionDeptAcademicPetFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("AdmissionDeptAcademicPetFacadeImpl")
public class AdmissionDeptAcademicPetFacadeImpl implements IAdmissionDeptAcademicPetFacade{

	@Autowired
	IAdmissionDeptAcademicPetService service;
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
	public void addComment(Integer id, String comment) {
		
		//service.addComment(id, comment);
	}

	@Override
	public List<CoursePetitionDTO> getOldSummer(Integer year) {
		
		return service.getOldSummer(year);
	}

	@Override
	public List<CoursePetitionDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<CoursePetitionDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}
	@Override
	public List<CoursePetitionDTO> getAuditingPet() {
		
		return service.getAuditingPet();
	}
}
