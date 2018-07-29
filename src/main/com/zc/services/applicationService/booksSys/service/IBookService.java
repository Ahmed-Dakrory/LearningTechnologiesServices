/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service;

import java.util.List;

import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public interface IBookService {

	public BookDTO add(BookDTO book);
	public boolean remove(Integer id);
	public BookDTO update(BookDTO book);
	public List<BookDTO> getAll();
	public BookDTO getById(Integer id);
	public List<BookDTO> getPendingBook();
	public List<BookDTO> getConfirmedBook();
	public List<BooksLogsDTO> getLogsOfBook(Integer bookID) ;
/*	public List<BookStudentDTO> getStudentsOfBook(Integer bookID);
	public List<BookInstructorDTO> getInstructorsOfBook(Integer bookID);*/
}
