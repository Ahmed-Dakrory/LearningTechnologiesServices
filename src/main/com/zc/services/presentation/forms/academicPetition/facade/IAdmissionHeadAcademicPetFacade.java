/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author omnya
 *
 */
public interface IAdmissionHeadAcademicPetFacade {

	public List<CoursePetitionDTO> getPendingPet();
	public List<CoursePetitionDTO> getOldPet();
	public CoursePetitionDTO update(CoursePetitionDTO dto);
	
	public void addComment(Integer id, String comment);
}
