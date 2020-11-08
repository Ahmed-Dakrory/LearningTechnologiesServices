/**
 * 
 */
package main.com.zc.service.instructor_courses_file;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iinstructor_courses_fileAppService {


	public List<instructor_courses_file> getAll();
	public List<instructor_courses_file> getAllByInstructorEmailAndYearAndSemester(String email,String year,String semester);
	public instructor_courses_file getById(int id);
	public instructor_courses_file addinstructor_courses_file(instructor_courses_file data);
	public boolean delete(instructor_courses_file data)throws Exception;
}
