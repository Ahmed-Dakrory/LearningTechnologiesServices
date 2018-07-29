/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service;

import java.util.List;

import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;

/**
 * @author omnya
 *
 */
public interface IBookInstructorService {

	public BookInstructorDTO add(BookInstructorDTO obj);
	public boolean remove(Integer id);
	public BookInstructorDTO update(BookInstructorDTO obj);
	public List<BookInstructorDTO> getAll();
	public BookInstructorDTO getById(Integer id);
	public List<BookInstructorDTO> getByInsID(Integer id);
	public List<BookInstructorDTO>getByBarCodeAndInsID(String barCode, Integer studentID);
	public List<BookInstructorDTO> getByBarCode(String barCode);
	
}
