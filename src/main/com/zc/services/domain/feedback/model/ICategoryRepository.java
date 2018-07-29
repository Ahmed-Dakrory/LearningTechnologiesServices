/**
 * 
 */
package main.com.zc.services.domain.feedback.model;

import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface ICategoryRepository {

	public List<FeedbackCategory> getAll();
	public FeedbackCategory getByID(int id);

}
