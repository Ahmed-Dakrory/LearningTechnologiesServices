/**
 * 
 */
package main.com.zc.services.domain.courses.model.relatedTopics;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface RelatedTopicsRepository {

	public List<RelatedTopics> getAll();
	public List<RelatedTopics> getByCourseId(int id);
	public RelatedTopics addRelatedTopics(RelatedTopics relatedTopics);
}
