/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ITAJuniorProgramRepository {

	public TAJuniorProgram add(TAJuniorProgram form);

	public boolean remove(Integer id);

	public TAJuniorProgram update(TAJuniorProgram form);

	public List<TAJuniorProgram> getAll();

	public TAJuniorProgram getById(Integer id);
	
	public List<TAJuniorProgram> getByStudentID(Integer id);
	
	public List<TAJuniorProgram> getByCourseCoordniatorPending(Integer id);
	public List<TAJuniorProgram> getByCourseCoordniatorOld(Integer id);

	public List<TAJuniorProgram> getPendingByPA(Integer id);
	public List<TAJuniorProgram> getOldByPA(Integer id);
	
	public List<TAJuniorProgram> getPendingByDean();
	public List<TAJuniorProgram> getOldByDean();
	
}
