/**
 * 
 */
package main.com.zc.services.domain.courses.model.courseIns;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface ICourseInsSyllabusAppService {

	public List<CourseInSyllabus> getAll();
	public List<CourseInSyllabus> getByCourseId(int id);
	public CourseInSyllabus addCourseIns(CourseInSyllabus courseIns);
	public CourseInSyllabus getById(int id);
	public boolean delete(CourseInSyllabus courseInSyllabus);
}
