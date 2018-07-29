/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IStudentOverloadRequestFacade {

	public List<OverloadRequestDTO>  getPendingRequestsOfStudent(Integer studentID);
	public List<OverloadRequestDTO>  getArchievedRequestsOfStudent(Integer studentID);
	public OverloadRequestDTO submitRequest(OverloadRequestDTO dto);
	public OverloadRequestDTO getByID(Integer id);
	public List<CoursesDTO> getAllCourses();
	//public List<MajorDTO> getAllMajors();
	
}
