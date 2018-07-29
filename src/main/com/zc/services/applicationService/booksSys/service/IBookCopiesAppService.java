/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service;

import java.util.List;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;

/**
 * @author omnya
 *
 */
public interface IBookCopiesAppService {

	public BookCopiesDTO add(BookCopiesDTO copy);
	public boolean remove(Integer id);
	public BookCopiesDTO update(BookCopiesDTO copy);
	public List<BookCopiesDTO> getAll();
	public List<BookCopiesDTO>getByCourseID(Integer id);
	public List<BookCopiesDTO>getByBookID(Integer id);
	public BookCopiesDTO getById(Integer id);
	public BookCopiesDTO getByBarCode(String code);
	
	
	
}
