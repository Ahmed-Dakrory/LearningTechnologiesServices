/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IBookCopiesRep {


	public BookCopies add(BookCopies copy);
	public boolean remove(Integer id);
	public BookCopies update(BookCopies copy);
	public List<BookCopies> getAll();
	public List<BookCopies> getAllFree();
	public List<BookCopies> getAllHeld();
	public List<BookCopies>getByCourseID(Integer id);
	public List<BookCopies>getByBookID(Integer id);
	public BookCopies getById(Integer id);
	public BookCopies getByBarCode(String code);
	
}
