/**
 * 
 */
package main.com.zc.services.domain.courses.model.references;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface ReferencesRepository {

	public List<References> getAll();
	public List<References> getByCourseId(int id);
	public References addReference(References reference);
	public References getById(int id);
	public boolean delete(References references);
}
