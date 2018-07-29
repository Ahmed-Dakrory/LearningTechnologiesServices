/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author omnya
 *
 */
public interface IAdmissionDeptAcademicPetService {

	public List<CoursePetitionDTO> getPendingPet();
	public List<CoursePetitionDTO> getOldPet();
	public CoursePetitionDTO update(CoursePetitionDTO dto);
	
	public void addComment(CoursePetitionDTO dto,Integer instructorID);
	public List<CoursePetitionDTO> getOldSummer(Integer year);
	public List<CoursePetitionDTO> getOldSpring(Integer year);
	public List<CoursePetitionDTO> getOldFall(Integer year);
	List<CoursePetitionDTO> getAuditingPet();
}
