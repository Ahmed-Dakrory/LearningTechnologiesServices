/**
 * 
 */
package main.com.zc.services.domain.model.heads;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface IHeadsAppService {

	public List<Heads> getAll();
	public List<Heads> getByAllNotHidden();
	public List<Heads> getByEmployeeId(int id);
	public Heads addHead(Heads data);
	public Heads getById(int id);
	public Heads getByType(int type);
	public boolean delete(Heads data);
}
