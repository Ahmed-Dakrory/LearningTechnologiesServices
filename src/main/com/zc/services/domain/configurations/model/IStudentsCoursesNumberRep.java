/**
 * 
 */
package main.com.zc.services.domain.configurations.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IStudentsCoursesNumberRep {
	public List<StudentsCoursesNumber> getAll();
	public StudentsCoursesNumber getByCourseID(Integer id);
	public StudentsCoursesNumber getById (Integer id);
	public StudentsCoursesNumber add(StudentsCoursesNumber courseStudent);
	public StudentsCoursesNumber update(StudentsCoursesNumber courseStudent);
	public boolean remove(StudentsCoursesNumber obj); 
}
