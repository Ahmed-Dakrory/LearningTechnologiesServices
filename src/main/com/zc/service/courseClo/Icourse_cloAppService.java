/**
 * 
 */
package main.com.zc.service.courseClo;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Icourse_cloAppService {

	public List<course_clo> getAll();
	public List<course_clo> getAllByYearAndSemestar(String year,String semestar);
	public course_clo getAllByYearAndSemestarAndCourseCode(String year,String semestar,String courseCode);
	public course_clo addcourse_clo(course_clo data);
	public course_clo getById(int id);
	public boolean delete(course_clo data)throws Exception;
}
