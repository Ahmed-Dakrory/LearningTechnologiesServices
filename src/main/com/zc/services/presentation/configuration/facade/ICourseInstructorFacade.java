/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface ICourseInstructorFacade {

	public List<InstructorDTO> getAllInstructors();
	public List<InstructorDTO> getAllTas();
	public boolean deleteCourse(CoursesDTO course);
	public List<InstructorDTO> getAllInstructorsByCourseId(Integer courseID);
	public List<InstructorDTO> getAllTAsByCourseId(Integer courseID);
	public boolean deleteInstructorFromCourse(InstructorDTO dto, CoursesDTO course);
	public CoursesDTO getCourseById(Integer courseID);
	public boolean addInstructorToCourse(Integer insID, Integer courseID);
	public CoursesDTO editCourseCoordinator(CoursesDTO dto);
	public CoursesDTO addNewCourse(CoursesDTO dto);
}
