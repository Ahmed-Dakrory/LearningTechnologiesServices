/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.List;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatActionsSharedService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatActionsSharedFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("ICourseRepeatActionsSharedFacade")
public class CourseRepeatActionsSharedFacadeImpl implements ICourseRepeatActionsSharedFacade{

	
	@Autowired
	ICourseRepeatActionsSharedService service;
	
	@Override
	public CourseRepeatDTO getByID(Integer id) {
		
			return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		service.addComment(dto, instructorID);
	}

	@Override
	public CourseRepeatDTO forwardPetition(CourseRepeatDTO dto) {
		return service.forwardPetition(dto);
	}

	@Override
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto) {
		return service.updateStatusOfForm(dto);
	}

	@Override
	public CourseRepeatDTO update(CourseRepeatDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
