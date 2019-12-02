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
public interface course_cloRepository {

	public List<course_clo> getAll();
	public List<course_clo> getAllByYearAndSemestar(int year,int semestar);
	public course_clo getAllByYearAndSemestarAndCourseCode(int year,int semestar,String courseCode);
	public course_clo addcourse_clo(course_clo data);
	public course_clo getById(int id);
	public boolean delete(course_clo data)throws Exception;
}
