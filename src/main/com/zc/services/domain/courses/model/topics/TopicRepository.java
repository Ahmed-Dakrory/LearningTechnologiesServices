/**
 * 
 */
package main.com.zc.services.domain.courses.model.topics;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface TopicRepository {

	public List<Topics> getAll();
	public List<Topics> getByCourseId(int id);
	public Topics addTopic(Topics clo);
	public Topics getById(int id);
	public boolean delete(Topics clo);
}
