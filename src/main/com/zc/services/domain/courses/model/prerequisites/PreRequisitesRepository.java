/**
 * 
 */
package main.com.zc.services.domain.courses.model.prerequisites;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface PreRequisitesRepository {

	public List<PreRequisites> getAll();
	public List<PreRequisites> getByCourseId(int id);
	public PreRequisites addPreRequisites(PreRequisites preRequisite);
}
