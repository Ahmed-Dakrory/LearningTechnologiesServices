/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public interface ICoursesFacade {

	List<CoursesDTO> getCoursesByYearAndSemester(Integer year, Integer semester);
}
