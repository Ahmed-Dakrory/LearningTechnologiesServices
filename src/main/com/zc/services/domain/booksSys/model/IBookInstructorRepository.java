/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IBookInstructorRepository {

	public BookInstructor add(BookInstructor obj);
	public boolean remove(Integer id);
	public BookInstructor update(BookInstructor obj);
	public List<BookInstructor> getAll();
	public BookInstructor getById(Integer id);
	public List<BookInstructor> getByInsID(Integer id);
	public List<BookInstructor>getByBarCodeAndInsID(String barCode, Integer studentID);
	public List<BookInstructor> getByBarCode(String barCode);
}
