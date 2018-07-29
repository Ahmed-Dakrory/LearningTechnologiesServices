/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.assembler;

import java.util.Calendar;

import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class BookAssembler {

	public BookDTO toDTO(Book book)
	{
		BookDTO dto=new BookDTO();
		dto.setId(book.getId());
		dto.setOriginalCopies(book.getOriginalCopies());
		dto.setRemaingCopies(book.getRemaingCopies());
		dto.setStatus(book.getStatus());
		dto.setName(book.getName());
		try{
		Calendar cal=Calendar.getInstance();
		cal.setTime(book.getFromDate());	
		dto.setFromDate(cal);
		}
		catch(Exception ex){
			
		}
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(book.getLastDate());	
			dto.setLastDate(cal);
			}
			catch(Exception ex){
				
			}
		
		try
		{
			CoursesDTO course=new  CoursesDTO();
			course.setId(book.getCourse().getId());
			course.setName(book.getCourse().getName());
			course.setYear(book.getCourse().getYear());
			course.setSemester(book.getCourse().getSemester());
			dto.setCourse(course);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dto;
		
	}
	public Book toEntity(BookDTO dto)
	{
		Book book=new Book();
		book.setId(dto.getId());
		book.setName(dto.getName());
		book.setOriginalCopies(dto.getOriginalCopies());
		book.setRemaingCopies(dto.getRemaingCopies());
		book.setStatus(dto.getStatus());
		try{
		book.setFromDate(dto.getFromDate().getTime());
		}
		catch(Exception ex){
			
		}
		try{
			book.setLastDate(dto.getLastDate().getTime());
			}
			catch(Exception ex){
				
			}
		try{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			course.setName(dto.getCourse().getName());
			course.setYear(dto.getCourse().getYear());
			course.setSemester(dto.getCourse().getSemester());
			book.setCourse(course);
		}
		catch(Exception ex){
			
		}
		return book;
		
	}
	
}

