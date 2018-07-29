/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IskipMajorHeadCoursesRep {

	public SkipMajorHeadCourses add(SkipMajorHeadCourses form);

	public boolean remove(Integer id);

	public SkipMajorHeadCourses update(SkipMajorHeadCourses form);

	public List<SkipMajorHeadCourses> getAll();

	public SkipMajorHeadCourses getById(Integer id);

	public SkipMajorHeadCourses getByCourseID(Integer id);
}
