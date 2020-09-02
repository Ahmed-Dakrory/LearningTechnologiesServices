/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.booksSys.assembler.BookAssembler;
import main.com.zc.services.applicationService.booksSys.assembler.BookStudentAssembler;
import main.com.zc.services.applicationService.booksSys.service.IBookStudentService;
import main.com.zc.services.domain.booksSys.model.BookCopies;
import main.com.zc.services.domain.booksSys.model.BookStudent;
import main.com.zc.services.domain.booksSys.model.IBookCopiesRep;
import main.com.zc.services.domain.booksSys.model.IBookStudentRepository;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class BookStudentServiceImpl implements IBookStudentService{

	@Autowired
	IBookStudentRepository rep;
	@Autowired
	IBookCopiesRep copiesRep;
	BookStudentAssembler assem=new BookStudentAssembler();
	
	@Override
	public BookStudentDTO add(BookStudentDTO obj) {
		try
		{
			BookStudent entity=new BookStudent();
			entity=assem.toEntity(obj);
			entity=rep.add(entity);
			obj=assem.toDTO(entity);
			return obj;
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
	public BookStudentDTO update(BookStudentDTO obj) {
		try
		{
			BookStudent entity=new BookStudent();
			entity=assem.toEntity(obj);
			entity=rep.update(entity);
			obj=assem.toDTO(entity);
			try{
				BookCopies copy=copiesRep.getByBarCode(obj.getBarCode());
				BookDTO book=new BookDTO();
				BookAssembler bookAssem=new BookAssembler();
				book=bookAssem.toDTO(copy.getBook());
				obj.setBook(book);
				obj.setCondition(copy.getCondition());
			}

			catch(Exception ex){
				
			}
			return obj;
		}
		
		
		catch(Exception ex){
			
			ex.printStackTrace();
			return null;
			
		}
	}

	@Override
	public List<BookStudentDTO> getAll() {
		try{
			List<BookStudentDTO> booksDTO=new ArrayList<BookStudentDTO>();
			List<BookStudent> books=new ArrayList<BookStudent>();
			books=rep.getAll();
			for(int i=0;i<books.size();i++)
			{
				BookStudentDTO dto=new BookStudentDTO();
				dto=assem.toDTO(books.get(i));
				booksDTO.add(dto);
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
					dto.setCondition(copy.getCondition());
				}

				catch(Exception ex){
					
				}
			}
			return booksDTO;
					
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public BookStudentDTO getById(Integer id) {
		try
		{
			BookStudent entity=new BookStudent();
			entity=rep.getById(id);
			BookStudentDTO dto=new BookStudentDTO();
			dto=assem.toDTO(entity);
			try{
				BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
				BookDTO book=new BookDTO();
				BookAssembler bookAssem=new BookAssembler();
				book=bookAssem.toDTO(copy.getBook());
				dto.setBook(book);
				dto.setCondition(copy.getCondition());
			}

			catch(Exception ex){
				
			}
			return dto;
		}
		catch(Exception ex){
			return null;
		}
	}

	@Override
	public List<BookStudentDTO> getByStudentID(Integer id) {
		try{
			List<BookStudentDTO> booksDTO=new ArrayList<BookStudentDTO>();
			List<BookStudent> books=new ArrayList<BookStudent>();
			books=rep.getByStudentID(id);
			for(int i=0;i<books.size();i++)
			{
				BookStudentDTO dto=new BookStudentDTO();
				dto=assem.toDTO(books.get(i));
				
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
					dto.setCondition(copy.getCondition());
				}

				catch(Exception ex){
					
				}
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
	public List<BookStudentDTO> getByBarCode(String code) {
		try{
			List<BookStudentDTO> booksDTO=new ArrayList<BookStudentDTO>();
			List<BookStudent> books=new ArrayList<BookStudent>();
			books=rep.getByBarCode(code);
			for(int i=0;i<books.size();i++)
			{
				BookStudentDTO dto=new BookStudentDTO();
				dto=assem.toDTO(books.get(i));
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
					dto.setCondition(copy.getCondition());
				}

				catch(Exception ex){
					
				}
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
	public List<BookStudentDTO> getByBarCodeAndStudentID(String barCode,
			Integer studentID) {
		try{
			List<BookStudentDTO> booksDTO=new ArrayList<BookStudentDTO>();
			List<BookStudent> books=new ArrayList<BookStudent>();
			books=rep.getByBarCodeAndStudentID(barCode,studentID);
			for(int i=0;i<books.size();i++)
			{
				BookStudentDTO dto=new BookStudentDTO();
				dto=assem.toDTO(books.get(i));
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
					dto.setCondition(copy.getCondition());
				}

				catch(Exception ex){
					
				}
				booksDTO.add(dto);
				
			}
			return booksDTO;
					
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}
