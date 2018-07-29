/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.List;

/**
 * @author Omnya
 *
 */
public interface IDataRepository {
	
	public int add(Data data);
	public List<Data> getAll();
   public boolean delete(int id);
   public int update(Data data);
   public Data getByMail(String mail);
}
