/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.List;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author Omnya Alaa
 *
 */
public interface ICoursesRepository {
	public int add(Courses course);

	public boolean remove(Courses course);

	public Courses update(Courses course);

	public List<Courses> getAll();

	public Courses getById(int id);
	
	public Courses getByName(String name);
	
	public List<Courses>getByCourseCoordinatorID(String mail);
	public List<Courses>getByYearAndSemester(Integer year ,Integer semester);
	public Courses getByNameAndYearAndSemester(String name,  Integer year ,Integer semester);
}
