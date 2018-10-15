/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.booksSys.assembler.BookAssembler;
import main.com.zc.services.applicationService.booksSys.service.IBookService;
import main.com.zc.services.applicationService.booksSys.service.IBooksLogsService;
import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.domain.booksSys.model.IBookInstructorRepository;
import main.com.zc.services.domain.booksSys.model.IBookRepository;
import main.com.zc.services.domain.booksSys.model.IBookStudentRepository;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;

/**
 * @author omnya
 *
 */
@Service
public class BookServiceImpl implements IBookService{

	@Autowired
	IBookRepository rep;
	@Autowired
	IBookStudentRepository bookStudentRep;
	@Autowired
	IBookInstructorRepository bookInsRep;
	BookAssembler assem=new BookAssembler(); 
	
	@Autowired
	IBooksLogsService logsService;
	
	@Override
	public BookDTO add(BookDTO book) {
		 
		try
		{
			Book entity=new Book();
			entity=assem.toEntity(book);
			entity=rep.add(entity);
			book=assem.toDTO(entity);
			return book;
		}
		
		
		catch(Exception ex){
			
			ex.printStackTrace();
			return null;
			
		}
		
	}

	@Override
	public boolean remove(Integer id) {
		try {
			return rep.remove(id);
		} catch (Exception ex) {
				ex.printStackTrace();
				return false;
		}
	}

	@Override
	public BookDTO update(BookDTO book) {
		try
		{
			Book entity=new Book();
			entity=assem.toEntity(book);
			
			entity=rep.update(entity);
			book=assem.toDTO(entity);
			return book;
		}
		catch(Exception ex){
			return null;
		}
	
	}

	@Override
	public List<BookDTO> getAll() {
		try{
			List<BookDTO> booksDTO=new ArrayList<BookDTO>();
			List<Book> books=new ArrayList<Book>();
			books=rep.getAll();
			for(int i=0;i<books.size();i++)
			{
				BookDTO dto=new BookDTO();
				dto=assem.toDTO(books.get(i));
				booksDTO.add(dto);
				
			}
			return booksDTO;
					
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public BookDTO getById(Integer id) {
		try
		{
			Book entity=new Book();
			entity=rep.getById(id);
			BookDTO dto=new BookDTO();
			dto=assem.toDTO(entity);
			return dto;
		}
		catch(Exception ex){
			return null;
		}
	}

	@Override
	public List<BookDTO> getPendingBook() {
		try{
			List<BookDTO> booksDTO=new ArrayList<BookDTO>();
			List<Book> books=new ArrayList<Book>();
			books=rep.getPendingBook();
			for(int i=0;i<books.size();i++)
			{
				BookDTO dto=new BookDTO();
				dto=assem.toDTO(books.get(i));
				booksDTO.add(dto);
				
			}
			return booksDTO;
					
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BookDTO> getConfirmedBook() {
		try{
			List<BookDTO> booksDTO=new ArrayList<BookDTO>();
			List<Book> books=new ArrayList<Book>();
			books=rep.getConfirmedBook();
			for(int i=0;i<books.size();i++)
			{
				BookDTO dto=new BookDTO();
				dto=assem.toDTO(books.get(i));
				booksDTO.add(dto);
				
			}
			return booksDTO;
					
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BooksLogsDTO> getLogsOfBook(Integer bookID) {
		List<BooksLogsDTO> logs=logsService.getByBookID(bookID);
		
		return logs;
	}

	@Override
	public List<Book> getCoursesBookWithCourseId(int id) {
		try{
			List<Book> books=new ArrayList<Book>();
			books=rep.getCoursesBookWithCourseId(id);
			
			return books;
					
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	/*@Override
	public List<BookStudentDTO> getStudentsOfBook(Integer bookID) {
		List<BookStudentDTO> students=new ArrayList<BookStudentDTO>();
		try{
		List<BookStudent> booksStudents=new ArrayList<BookStudent>();
		booksStudents=bookStudentRep.getByBookID(bookID);
		for(int i=0;i<booksStudents.size();i++)
		{
			BookStudentDTO dto=new BookStudentDTO();
			BookStudentAssembler studentBookAssem=new BookStudentAssembler();
			dto=studentBookAssem.toDTO(booksStudents.get(i));
			dto.setId(booksStudents.get(i).getId());
			students.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return students;
	}

	@Override
	public List<BookInstructorDTO> getInstructorsOfBook(Integer bookID) {
		List<BookInstructorDTO> instructors=new ArrayList<BookInstructorDTO>();
		try{
		List<BookInstructor> booksInstructor=new ArrayList<BookInstructor>();
		booksInstructor=bookInsRep.getByBookID(bookID);
		for(int i=0;i<booksInstructor.size();i++)
		{
			BookInstructorDTO dto=new BookInstructorDTO();
			BookInstructorAssembler insAssem=new BookInstructorAssembler();
			dto=insAssem.toDTO(booksInstructor.get(i));
			dto.setId(booksInstructor.get(i).getId());
			instructors.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return instructors;
	}*/

}
