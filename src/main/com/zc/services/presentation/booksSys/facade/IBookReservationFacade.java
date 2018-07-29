/**
 * 
 */
package main.com.zc.services.presentation.booksSys.facade;

import java.util.List;

import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IBookReservationFacade {
	public BookInstructorDTO add(BookInstructorDTO obj);
	public BookStudentDTO add(BookStudentDTO obj);
	public BookStudentDTO update(BookStudentDTO obj);
	public BookInstructorDTO update(BookInstructorDTO obj);
	public List<BookStudentDTO> getAllStudents();
	public BookStudentDTO getByIdStudents(Integer id);
	public List<BookInstructorDTO> getAllIns();
	public BookInstructorDTO getByIdIns(Integer id);
	public List<BookStudentDTO>getByBarCodeAndStudentID(String barCode, Integer studentID);
	public List<BookStudentDTO> getByBarCodeStudent(String barCode);
	public List<BookInstructorDTO>getByBarCodeAndInsID(String barCode, Integer studentID);
	public List<BookInstructorDTO> getByBarCodeIns(String barCode);
	public List<BookStudentDTO> getByStudentID(Integer id);
	public List<BookInstructorDTO> getByInsID(Integer id);
	public List<InstructorDTO> getAllInstructors();
	public List<InstructorDTO> getAllTAs();
}
