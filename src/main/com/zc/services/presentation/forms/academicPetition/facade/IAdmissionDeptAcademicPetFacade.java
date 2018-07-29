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
public interface IAdmissionDeptAcademicPetFacade {

	public List<CoursePetitionDTO> getPendingPet();
	public List<CoursePetitionDTO> getOldPet();
	public CoursePetitionDTO update(CoursePetitionDTO dto);
	
	public void addComment(Integer id, String comment);
	public List<CoursePetitionDTO> getOldSummer(Integer year);
	public List<CoursePetitionDTO> getOldSpring(Integer year);
	public List<CoursePetitionDTO> getOldFall(Integer year);
	public List<CoursePetitionDTO> getAuditingPet();
}
