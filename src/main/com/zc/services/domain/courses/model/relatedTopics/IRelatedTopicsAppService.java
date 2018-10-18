/**
 * 
 */
package main.com.zc.services.domain.courses.model.relatedTopics;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IRelatedTopicsAppService {

	public List<RelatedTopics> getAllRelatedTopics();
	public List<RelatedTopics> getByCourseId(int id);
	public RelatedTopics addRelatedTopics(RelatedTopics relatedTopics);
	public RelatedTopics getById(int id);
	public boolean delete(RelatedTopics relatedTopics);
}
