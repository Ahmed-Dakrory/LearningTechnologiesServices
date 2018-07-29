/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatInsService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatInsFacade;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("ICourseRepeatInsFacade")
public class CourseRepeatInsFacadeImpl implements ICourseRepeatInsFacade{

	@Autowired
	ICourseRepeatInsService service;
	@Override
	public List<CourseRepeatDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<CourseRepeatDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<CourseRepeatDTO> getPendingFormsOfDean() {
		
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<CourseRepeatDTO> getArchievedFormsOfDean() {
		
		return service.getArchievedFormsOfDean();
	}

	@Override
	public CourseRepeatDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);
		
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public CourseRepeatDTO forwardPetition(CourseRepeatDTO dto) {
		
		return service.forwardPetition(dto);
	}

}
