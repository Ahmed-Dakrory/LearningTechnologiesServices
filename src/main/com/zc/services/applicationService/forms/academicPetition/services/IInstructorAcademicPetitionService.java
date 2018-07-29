/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IInstructorAcademicPetitionService {

	public List<CoursePetitionDTO> getPendingPetOfInstructor(Integer insId);
	public List<CoursePetitionDTO> getOldPetOfInstructor(Integer insId);
	public CoursePetitionDTO update(CoursePetitionDTO dto);
	public List<CoursePetitionDTO> getPendingPetOfDean();
	public List<CoursePetitionDTO> getOldPetOfDean();
	public CoursePetitionDTO getByID(Integer id);
	public void addComment(CoursePetitionDTO dto ,Integer personID);
	public List<InstructorDTO> fillInsLst();
	public CoursePetitionDTO forwardPetition(CoursePetitionDTO dto);
}
