/**
 * 
 */
package main.com.zc.services.domain.booksSys.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IBookStudentRepository {

	public BookStudent add(BookStudent obj);
	public boolean remove(Integer id);
	public BookStudent update(BookStudent obj);
	public List<BookStudent> getAll();
	public BookStudent getById(Integer id);
	public List<BookStudent> getByStudentID(Integer id);
	public List<BookStudent>getByBarCodeAndStudentID(String barCode, Integer studentID);
	public List<BookStudent> getByBarCode(String barCode);
	
}
