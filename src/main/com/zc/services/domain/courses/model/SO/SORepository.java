/**
 * 
 */
package main.com.zc.services.domain.courses.model.SO;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface SORepository {

	public List<SO> getAll();
	public List<SO> getByCourseId(int id);
	public SO addSO(SO clo);
}
