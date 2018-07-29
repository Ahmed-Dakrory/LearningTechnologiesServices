/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatAdmissionDeptService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatAdmissionDeptFacade;

/**
 * @author omnya
 *
 */
@Service("ICourseRepeatAdmissionDeptFacade")
public class CourseRepeatAdmissionDeptFacadeImpl implements ICourseRepeatAdmissionDeptFacade{

	@Autowired
	ICourseRepeatAdmissionDeptService service;
	@Override
	public CourseRepeatDTO updateRequest(CourseRepeatDTO dto) {
		
		return service.updateRequest(dto);
	}

	@Override
	public CourseRepeatDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent() {
		
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent() {
		
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);
		
	}
	@Override
	public List<CourseRepeatDTO> getAuditingPet() {
		
		return service.getAuditingPet();
	}
}
