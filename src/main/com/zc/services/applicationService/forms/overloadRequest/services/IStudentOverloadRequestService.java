/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services;

import java.util.List;

import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IStudentOverloadRequestService {

	public List<OverloadRequestDTO>  getPendingRequestsOfStudent(Integer studentID);
	public List<OverloadRequestDTO>  getArchievedRequestsOfStudent(Integer studentID);
	public OverloadRequestDTO submitRequest(OverloadRequestDTO dto);
	public List<CoursesDTO> getAllCourses();
	public OverloadRequestDTO getByID(Integer id) ;
}
