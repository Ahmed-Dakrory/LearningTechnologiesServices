/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ISectionRepository {
	public Section add(Section section);
	public Section update(Section section);
	public Section getById(Integer id);
	public List<Section> getAll();
	public boolean delete(Section section);
	public Section getByIdAndCourseID(Integer id,Integer cID);
	public Section getByCourseID(int id);
}
