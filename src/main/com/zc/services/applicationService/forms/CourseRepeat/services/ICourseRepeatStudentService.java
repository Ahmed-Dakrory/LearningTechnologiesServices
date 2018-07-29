/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services;

import java.util.List;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseRepeatStudentService {

	public CourseRepeatDTO add(CourseRepeatDTO dto);
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent(Integer studentID);

	public List<CoursesDTO> getAllCourses();
	public CourseRepeatDTO getByID(Integer id);

}
