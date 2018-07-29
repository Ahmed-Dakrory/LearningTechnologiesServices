/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author momen
 *
 */
public interface IAdmissionAdminAcademicPetService {
	public List<CoursePetitionDTO> getPendingPet();
	public List<CoursePetitionDTO> getOldPet();
	public CoursePetitionDTO update(CoursePetitionDTO dto);
}
