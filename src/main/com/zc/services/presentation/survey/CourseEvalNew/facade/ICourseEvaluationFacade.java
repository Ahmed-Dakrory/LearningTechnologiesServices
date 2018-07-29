/**
 * 
 */
package main.com.zc.services.presentation.survey.CourseEvalNew.facade;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseEvaluationFacade {

	public List<InstructorDTO> getTasByCourseID(Integer courseID);
	public List<InstructorDTO> getInstructorsByCourseID(Integer courseID);
	
	/**
	 * @author asmaa
	 * 
	 * @return
	 */
	public List<CoursesDTO> getAllCourses();
}
