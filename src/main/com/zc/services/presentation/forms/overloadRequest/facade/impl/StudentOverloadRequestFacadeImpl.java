/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.overloadRequest.services.IStudentOverloadRequestService;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IStudentOverloadRequestFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service("StudentOverloadRequestFacadeImpl")
public class StudentOverloadRequestFacadeImpl implements IStudentOverloadRequestFacade{

	@Autowired
	IStudentOverloadRequestService service;
	@Override
	public List<OverloadRequestDTO> getPendingRequestsOfStudent(
			Integer studentID) {
		
		return service.getPendingRequestsOfStudent(studentID);
	}

	@Override
	public List<OverloadRequestDTO> getArchievedRequestsOfStudent(
			Integer studentID) {
		
		return service.getArchievedRequestsOfStudent(studentID);
	}

	@Override
	public OverloadRequestDTO submitRequest(OverloadRequestDTO dto) {
		
		return service.submitRequest(dto);
	}

	@Override
	public List<CoursesDTO> getAllCourses() {
	
		return service.getAllCourses();
	}


	@Override
	public OverloadRequestDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

}
