/**
 * 
 */
package main.com.zc.services.presentation.booksSys.facade;

import java.util.List;

import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;

/**
 * @author omnya
 *
 */
public interface IBookCopiesFacade {

	public BookCopiesDTO add(BookCopiesDTO copy);
	public boolean remove(Integer id);
	public BookCopiesDTO update(BookCopiesDTO copy);
	public List<BookCopiesDTO> getAll();
	public List<BookCopiesDTO> getAllFree();
	public List<BookCopiesDTO> getAllHeld();
	public List<BookCopiesDTO>getByCourseID(Integer id);
	public List<BookCopiesDTO>getByBookID(Integer id);
	public BookCopiesDTO getById(Integer id);
	public BookCopiesDTO getByBarCode(String code);
	
	
}
