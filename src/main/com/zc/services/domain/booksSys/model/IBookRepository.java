/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IBookRepository {

	public Book add(Book book);
	public boolean remove(Integer id);
	public Book update(Book book);
	public List<Book> getAll();
	public Book getById(Integer id);
	public List<Book> getPendingBook();
	public List<Book> getConfirmedBook();
	
	
}
