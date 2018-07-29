/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdmissionHeadService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdmissionHeadFacade;

/**
 * @author omnya
 *
 */
@Service("ICourseRepeatAdmissionHeadFacade")
public class CourseRepeatAdmissionHeadFacadeImpl implements ICourseRepeatAdmissionHeadFacade {

	@Autowired
	ICourseRepeatAdmissionHeadService service;
	@Override
	public List<CourseRepeatDTO> getPendingFormsOfAdmissionHead() {
		
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<CourseRepeatDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public CourseRepeatDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);
		
	}

}
