/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.facade;

import java.util.List;

import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IGeneratePDFFacade {

	public List<BaseDTO> getAllCourses();
	public List<BaseDTO> getCoursesByYearAndSem(Integer year, Integer sem);
	public List<BaseDTO> getInstructorsOfCourse(Integer courseID);
}
