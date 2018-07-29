/**
 * 
 */
package main.com.zc.services.domain.configurations.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ICourseStudentRep {

	public List<CourseStudent> getAll();
	public List<CourseStudent> getByStudentID(Integer id);
	public List<CourseStudent> getByStudentIDAndSemesterAndYear(Integer id,Integer semester, Integer year); 
	public List<CourseStudent> getByCourseID(Integer id);
	public CourseStudent getByStudentIDAndByCourseID(Integer studentID,Integer courseId); 
	public CourseStudent getById (Integer id);
	public CourseStudent add(CourseStudent courseStudent);
	public boolean remove(CourseStudent obj); 
	public List<CourseStudent> getBySemesterAndYear(Integer semester,Integer year);
	public List<CourseStudent> getByFacultyIDAndSemesterAndYear(Integer facultyID,Integer semester,Integer year);
	boolean delete(Integer semester, Integer year);
	public boolean removelst(List<CourseStudent> isbefore);
}
