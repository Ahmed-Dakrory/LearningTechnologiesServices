/**
 * 
 */
package main.com.zc.services.domain.courses.model.books;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface CourseBookRepository {

	public List<CourseBooks> getAll();
	public List<CourseBooks> getByCourseId(int id);
	public CourseBooks addbookForCourse(CourseBooks book);
	public CourseBooks getById(int id);
	public boolean delete(CourseBooks courseBooks);
}
