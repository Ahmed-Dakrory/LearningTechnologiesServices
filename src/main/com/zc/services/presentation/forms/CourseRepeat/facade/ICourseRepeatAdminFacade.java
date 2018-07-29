/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author momen
 *
 */
public interface ICourseRepeatAdminFacade {

	public List<CourseRepeatDTO> getPendingFormsOfAdmissionHead();
	public List<CourseRepeatDTO> getArchievedFormsOfAdmissionHead();
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto);
	public CourseRepeatDTO getById(Integer id);
	public void addComment(Integer id, String comment);
	public void notifyNextUser(CourseRepeatDTO dto);
}
