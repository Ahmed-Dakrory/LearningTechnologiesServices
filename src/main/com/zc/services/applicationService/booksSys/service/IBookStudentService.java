/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service;

import java.util.List;

import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;

/**
 * @author omnya
 *
 */
public interface IBookStudentService {

	public BookStudentDTO add(BookStudentDTO obj);
	public boolean remove(Integer id);
	public BookStudentDTO update(BookStudentDTO obj);
	public List<BookStudentDTO> getAll();
	public BookStudentDTO getById(Integer id);
	public List<BookStudentDTO> getByStudentID(Integer id);
	public List<BookStudentDTO>getByBarCodeAndStudentID(String barCode, Integer studentID);
	public List<BookStudentDTO> getByBarCode(String barCode);
	
}
