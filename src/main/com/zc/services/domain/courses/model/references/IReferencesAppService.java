/**
 * 
 */
package main.com.zc.services.domain.courses.model.references;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IReferencesAppService {

	public List<References> getAllReferences();
	public List<References> getByCourseId(int id);
	public References addReference(References reference);
	
}
