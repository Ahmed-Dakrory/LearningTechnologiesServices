/**
 * 
 */
package main.com.zc.services.domain.courses.model.prerequisites;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IPreRequisitesAppService {

	public List<PreRequisites> getAllPreRequisites();
	public List<PreRequisites> getByCourseId(int id);
	public PreRequisites addPreRequisite(PreRequisites preRequisite);
	
}
