/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public interface ICourseRepeatStudentFacade {
	public CourseRepeatDTO add(CourseRepeatDTO dto);
	public CourseRepeatDTO getByID(Integer id);
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent(Integer studentID);
	public List<CoursesDTO> getAllCourses();
	
}
