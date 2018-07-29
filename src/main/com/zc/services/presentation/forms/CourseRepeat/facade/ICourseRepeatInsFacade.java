/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseRepeatInsFacade {
	public List<CourseRepeatDTO> getPendingFormsOfInstructor(Integer insID);
	public List<CourseRepeatDTO> getArchievedFormsOfInstructor(Integer insID);
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto);
	public List<CourseRepeatDTO> getPendingFormsOfDean();
	public List<CourseRepeatDTO> getArchievedFormsOfDean();
	public CourseRepeatDTO getByID(Integer id);
	public void addComment(Integer id, String comment);
	public List<InstructorDTO> fillInsLst();
	public CourseRepeatDTO forwardPetition(CourseRepeatDTO dto);
}
