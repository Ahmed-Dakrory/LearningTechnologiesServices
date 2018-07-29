/**
 * 
 */
package main.com.zc.services.presentation.shared.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public interface ICouresFacade {
	public List<CoursesDTO> getCoursesByStudentID(Integer studentId);
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(Integer studentId,Integer semester,Integer year);
	public List<CoursesDTO> getCoursesBySemesterAndYear(Integer semester, Integer year);
	public List<CoursesDTO> getAll();
	public CoursesDTO getcourseById(Integer id);
	public CoursesDTO getcourseByNameAndSemesterAndYear(String name,Integer semester,Integer year);
	
}
