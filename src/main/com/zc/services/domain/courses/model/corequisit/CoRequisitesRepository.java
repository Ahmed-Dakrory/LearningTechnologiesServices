/**
 * 
 */
package main.com.zc.services.domain.courses.model.corequisit;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface CoRequisitesRepository {

	public List<CoRequisites> getAll();
	public List<CoRequisites> getByCourseId(int id);
	public CoRequisites addCoRequisite(CoRequisites coRequisites);
}
