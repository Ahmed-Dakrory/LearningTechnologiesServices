/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IFormsStatusRepository {

	public FormsStatus add(FormsStatus form);

	public boolean remove(FormsStatus status);

	public FormsStatus update(FormsStatus form);

	public List<FormsStatus> getAll();

	public FormsStatus getById(Integer id);
	
	
}
