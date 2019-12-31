/**
 * 
 */
package main.com.zc.services.presentation.booksSys.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.booksSys.service.IBookCopiesAppService;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.services.presentation.booksSys.facade.IBookCopiesFacade;

/**
 * @author omnya
 *
 */
@Service("IBookCopiesFacade")
public class BookCopiesFacadeImpl implements  IBookCopiesFacade {

	@Autowired
	IBookCopiesAppService service;
	@Override
	public BookCopiesDTO add(BookCopiesDTO copy) {
		
		return service.add(copy);
	}

	@Override
	public boolean remove(Integer id) {
		
		return service.remove(id);
	}

	@Override
	public BookCopiesDTO update(BookCopiesDTO copy) {
		
		return service.update(copy);
	}

	@Override
	public List<BookCopiesDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public List<BookCopiesDTO> getByCourseID(Integer id) {
		
		return service.getByCourseID(id);
	}

	@Override
	public List<BookCopiesDTO> getByBookID(Integer id) {
		
		return service.getByBookID(id);
	}

	@Override
	public BookCopiesDTO getById(Integer id) {
		
		return service.getById(id);
		
	}

	@Override
	public BookCopiesDTO getByBarCode(String code) {
		
		return service.getByBarCode(code);
	}

	@Override
	public List<BookCopiesDTO> getAllFree() {
		// TODO Auto-generated method stub

		return service.getAllFree();
	}
	
	@Override
	public List<BookCopiesDTO> getAllHeld() {
		// TODO Auto-generated method stub

		return service.getAllHeld();
	}

}
