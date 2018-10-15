/**
 * 
 */
package main.com.zc.services.presentation.booksSys.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.booksSys.service.IBookService;
import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;
import main.com.zc.services.presentation.booksSys.facade.IBooksFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("IBooksFacade")
public class BooksFacadeImpl implements IBooksFacade{

	@Autowired
	IBookService service;
	@Override
	public BookDTO add(BookDTO book) {
		
		return service.add(book);
	}

	@Override
	public boolean remove(Integer id) {
		
		return service.remove(id);
	}

	@Override
	public BookDTO update(BookDTO book) {
		
		return service.update(book);
	}

	@Override
	public List<BookDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public BookDTO getById(Integer id) {
	
		return service.getById(id);
	}

	@Override
	public List<BookDTO> getPendingBook() {
		
		return service.getPendingBook();
	}

	@Override
	public List<BookDTO> getConfirmedBook() {
		
		return service.getConfirmedBook();
	}

	@Override
	public List<BooksLogsDTO> getLogsOfBook(Integer bookID) {
		
		return service.getLogsOfBook(bookID);
	}

	@Override
	public List<Book> getCoursesBookWithCourseId(int id) {
		// TODO Auto-generated method stub
		return service.getCoursesBookWithCourseId(id);
	}

/*	@Override
	public List<BookStudentDTO> getStudentsOfBook(Integer bookID) {
		return service.getStudentsOfBook(bookID);
	}

	@Override
	public List<BookInstructorDTO> getInstructorsOfBook(Integer bookID) {
			return service.getInstructorsOfBook(bookID);
	}*/

	
}
