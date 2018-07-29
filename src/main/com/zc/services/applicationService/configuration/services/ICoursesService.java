/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services;

import java.util.List;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public interface ICoursesService {

	List<CoursesDTO> getCoursesByYearAndSemester(Integer year, Integer semester);
	
	CoursesDTO getCourseById(Integer id);
	
	public List<CoursesDTO> getAllCourses();
	
	
	
}
