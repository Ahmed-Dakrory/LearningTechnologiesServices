/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseRepeatActionsSharedFacade {

	public CourseRepeatDTO getByID(Integer id);
	public List<InstructorDTO> fillInsLst();
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) ;
	public CourseRepeatDTO forwardPetition(CourseRepeatDTO dto);
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto) ;
	public CourseRepeatDTO update(CourseRepeatDTO dto) ;

	
}
