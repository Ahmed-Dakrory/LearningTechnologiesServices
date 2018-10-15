/**
 * 
 */
package main.com.zc.services.domain.courses.model.grades;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface GradesRepository {

	public List<Grade> getAll();
	public List<Grade> getByCourseId(int id);
	public Grade addGrade(Grade grade);
}
