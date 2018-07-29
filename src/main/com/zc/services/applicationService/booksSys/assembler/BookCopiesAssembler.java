/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.assembler;

import java.util.Calendar;
import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.domain.booksSys.model.BookCopies;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;

/**
 * @author omnya
 *
 */
public class BookCopiesAssembler {
	public BookCopiesDTO toDTO(BookCopies book)
	{
		BookCopiesDTO dto=new BookCopiesDTO();
		dto.setId(book.getId());
	    dto.setBarCode(book.getBarCode());
	    dto.setStatus(book.getStatus());
		try{
		Calendar cal=Calendar.getInstance();
		cal.setTime(book.getAddedDate());	
		dto.setAddedDate(cal);
		}
		catch(Exception ex){
			
		}
		try{
			Calendar cal=Calendar.getInstance();
			cal.setTime(book.getLastOper());	
			dto.setLastOper(cal);
			}
			catch(Exception ex){
				
			}
		
		try
		{
			BookDTO bookDTO=new  BookDTO();
			BookAssembler bookAssme=new BookAssembler();
			bookDTO=bookAssme.toDTO(book.getBook());
		 dto.setBook(bookDTO);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dto;
		
	}
	public BookCopies toEntity(BookCopiesDTO dto)
	{
		BookCopies book=new BookCopies();
		book.setId(dto.getId());
		book.setBarCode(dto.getBarCode());
		book.setStatus(dto.getStatus());
		try{
		
			book.setAddedDate(dto.getAddedDate().getTime());
		}
		catch(Exception ex){
			
		}
		try{
			book.setLastOper(dto.getLastOper().getTime());
			}
			catch(Exception ex){
				
			}
		
		try
		{
			Book  bookOb=new  Book();
			bookOb.setId(dto.getBook().getId());
		 book.setBook(bookOb);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return book;
}}
