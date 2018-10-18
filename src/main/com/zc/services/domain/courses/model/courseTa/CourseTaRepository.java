/**
 * 
 */
package main.com.zc.services.domain.courses.model.courseTa;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface CourseTaRepository {

	public List<CourseTa> getAll();
	public List<CourseTa> getByCourseId(int id);
	public CourseTa addCourseTa(CourseTa courseTa);
	public CourseTa getById(int id);
	public boolean delete(CourseTa courseTa);
	
}
