/**
 * 
 */
package main.com.zc.services.domain.courses.model.clo;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface CLORepository {

	public List<CLO> getAll();
	public List<CLO> getByCourseId(int id);
	public CLO addCLO(CLO clo);
	public CLO getById(int id);
	public boolean delete(CLO clo);
}
