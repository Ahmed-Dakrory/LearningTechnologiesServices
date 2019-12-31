/**
 * 
 */
package main.com.zc.services.presentation.booksSys.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.booksSys.service.IBookInstructorService;
import main.com.zc.services.applicationService.booksSys.service.IBookStudentService;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.booksSys.facade.IBookReservationFacade;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("IBookReservationFacade")
public class BookReservationFacadeImpl implements IBookReservationFacade{

	@Autowired
	IBookInstructorService insService;
	@Autowired
	IBookStudentService studentService;
	@Autowired
	private ICourseInstructorFacade insFacde;
	@Override
	public BookInstructorDTO add(BookInstructorDTO obj) {
		
		return insService.add(obj);
	}

	@Override
	public BookStudentDTO add(BookStudentDTO obj) {
		
		return studentService.add(obj);
	}

	@Override
	public BookStudentDTO update(BookStudentDTO obj) {
		
		return studentService.update(obj);
	}

	@Override
	public BookInstructorDTO update(BookInstructorDTO obj) {
		
		return insService.update(obj);
	}

	@Override
	public List<BookStudentDTO> getAllStudents() {
		
		return studentService.getAll();
	}

	@Override
	public BookStudentDTO getByIdStudents(Integer id) {
		
		return studentService.getById(id);
	}

	@Override
	public List<BookInstructorDTO> getAllIns() {
		
		return insService.getAll();
	}

	@Override
	public BookInstructorDTO getByIdIns(Integer id) {
		
		return insService.getById(id);
	}

	@Override
	public List<BookStudentDTO> getByBarCodeAndStudentID(String barCode,
			Integer studentID) {
		
		return studentService.getByBarCodeAndStudentID(barCode, studentID);
	}

	@Override
	public List<BookStudentDTO> getByBarCodeStudent(String barCode) {
	
		return studentService.getByBarCode(barCode);
	}

	@Override
	public List<BookInstructorDTO> getByBarCodeAndInsID(String barCode,
			Integer studentID) {
		
		return insService.getByBarCodeAndInsID(barCode, studentID);
	}

	@Override
	public List<BookInstructorDTO> getByBarCodeIns(String barCode) {
		
		return insService.getByBarCode(barCode);
	}

	@Override
	public List<BookStudentDTO> getByStudentID(Integer id) {
		
		return studentService.getByStudentID(id);
	}

	@Override
	public List<BookInstructorDTO> getByInsID(Integer id) {
		
		return insService.getByInsID(id);
	}

	@Override
	public List<InstructorDTO> getAllInstructors() {
		
		return insFacde.getAllInstructors();
	}

	@Override
	public List<InstructorDTO> getAllTAs() {
		
		return insFacde.getAllTas();
	}

}
