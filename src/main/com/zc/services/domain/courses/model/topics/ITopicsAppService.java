/**
 * 
 */
package main.com.zc.services.domain.courses.model.topics;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface ITopicsAppService {

	public List<Topics> getAll();
	public List<Topics> getByCourseId(int id);
	public Topics addTopic(Topics topic);
	public Topics getById(int id);
	public boolean delete(Topics topic);
}
