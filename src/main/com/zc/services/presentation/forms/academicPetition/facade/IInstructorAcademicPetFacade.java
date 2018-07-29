/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorAcademicPetFacade {

	public List<CoursePetitionDTO> getPendingPetOfInstructor(Integer insId);
	public List<CoursePetitionDTO> getOldPetOfInstructor(Integer insId);
	public CoursePetitionDTO update(CoursePetitionDTO dto);
	public CoursePetitionDTO getByID(Integer id);
	public List<CoursePetitionDTO> getPendingPetOfDean();
	public List<CoursePetitionDTO> getOldPetOfDean();
	
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public CoursePetitionDTO forwardPetition(CoursePetitionDTO dto);
}
