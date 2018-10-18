/**
 * 
 */
package main.com.zc.services.domain.courses.model.grades;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IGradeAppService {

	public List<Grade> getAllRelatedTopics();
	public List<Grade> getByCourseId(int id);
	public Grade addGrade(Grade grade);
	public Grade getById(int id);
	public boolean delete(Grade grade);
	
}
