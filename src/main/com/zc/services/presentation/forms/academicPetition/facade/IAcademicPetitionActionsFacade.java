/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IAcademicPetitionActionsFacade {

	public CoursePetitionDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public CoursePetitionDTO forwardPetition(CoursePetitionDTO dto);
	public CoursePetitionDTO updateStatusOfForm(CoursePetitionDTO dto);
	public CoursePetitionDTO update(CoursePetitionDTO dto);
}
