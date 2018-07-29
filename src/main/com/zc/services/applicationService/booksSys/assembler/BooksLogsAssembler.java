/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.assembler;

import main.com.zc.services.domain.booksSys.model.Book;
import main.com.zc.services.domain.booksSys.model.BooksLogs;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;

/**
 * @author omnya
 *
 */
public class BooksLogsAssembler {

	public BooksLogsDTO toDTO(BooksLogs obj)
	{
		BooksLogsDTO dto=new BooksLogsDTO();
		dto.setId(obj.getId());
		dto.setDate(obj.getDate());
		dto.setAction(obj.getAction());
		dto.setUser(obj.getUser());
	
		try
		{
			BookDTO book=new  BookDTO();
			book.setId(obj.getBook().getId());
			book.setName(obj.getBook().getName());
			dto.setBook(book);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dto;
		
	}
	public BooksLogs toEntity(BooksLogsDTO dto)
	{
		BooksLogs obj=new BooksLogs();
		obj.setId(dto.getId());
		obj.setDate(dto.getDate());
		obj.setAction(dto.getAction());
		obj.setUser(dto.getUser());
	
		try
		{
			Book book=new  Book();
			book.setId(dto.getBook().getId());
			book.setName(dto.getBook().getName());
			obj.setBook(book);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return obj;
	
}}
