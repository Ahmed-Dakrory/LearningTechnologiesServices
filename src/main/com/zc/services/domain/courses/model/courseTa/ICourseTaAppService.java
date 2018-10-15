/**
 * 
 */
package main.com.zc.services.domain.courses.model.courseTa;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface ICourseTaAppService {

	public List<CourseTa> getAllCourseTas();
	public List<CourseTa> getByCourseId(int id);
	public CourseTa addCourseTa(CourseTa courseTa);
	
}
