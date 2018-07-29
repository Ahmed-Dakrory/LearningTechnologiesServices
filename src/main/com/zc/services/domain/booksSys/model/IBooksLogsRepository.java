/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IBooksLogsRepository {

	public BooksLogs add(BooksLogs obj);
	public boolean remove(Integer id);
	public BooksLogs update(BooksLogs obj);
	public List<BooksLogs> getAll();
	public BooksLogs getById(Integer id);
	public List<BooksLogs> getByUserName(String user);
	public List<BooksLogs> getByBookID(Integer id);
		
	
}
