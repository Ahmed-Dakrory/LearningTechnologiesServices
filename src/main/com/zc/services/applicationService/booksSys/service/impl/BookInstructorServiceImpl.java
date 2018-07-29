/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.booksSys.assembler.BookAssembler;
import main.com.zc.services.applicationService.booksSys.assembler.BookInstructorAssembler;
import main.com.zc.services.applicationService.booksSys.service.IBookInstructorService;
import main.com.zc.services.domain.booksSys.model.BookCopies;
import main.com.zc.services.domain.booksSys.model.BookInstructor;
import main.com.zc.services.domain.booksSys.model.IBookCopiesRep;
import main.com.zc.services.domain.booksSys.model.IBookInstructorRepository;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class BookInstructorServiceImpl implements IBookInstructorService{

	@Autowired
	IBookInstructorRepository rep;
	@Autowired
	IBookCopiesRep copiesRep;
	BookInstructorAssembler assem=new BookInstructorAssembler();
	
	@Override
	public BookInstructorDTO add(BookInstructorDTO obj) {
		try
		{
			BookInstructor entity=new BookInstructor();
			entity=assem.toEntity(obj);
			entity=rep.add(entity);
			obj=assem.toDTO(entity);
			try{
				BookCopies copy=copiesRep.getByBarCode(obj.getBarCode());
				BookDTO book=new BookDTO();
				BookAssembler bookAssem=new BookAssembler();
				book=bookAssem.toDTO(copy.getBook());
				obj.setBook(book);
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
	public boolean remove(Integer id) {
		try {
			return rep.remove(id);
		} catch (Exception ex) {
				ex.printStackTrace();
				return false;
		}
	}

	@Override
	public BookInstructorDTO update(BookInstructorDTO obj) {
		try
		{
			BookInstructor entity=new BookInstructor();
			entity=assem.toEntity(obj);
			entity=rep.update(entity);
			obj=assem.toDTO(entity);
			try{
				BookCopies copy=copiesRep.getByBarCode(obj.getBarCode());
				BookDTO book=new BookDTO();
				BookAssembler bookAssem=new BookAssembler();
				book=bookAssem.toDTO(copy.getBook());
				obj.setBook(book);
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
	public List<BookInstructorDTO> getAll() {
		try{
			List<BookInstructorDTO> booksDTO=new ArrayList<BookInstructorDTO>();
			List<BookInstructor> books=new ArrayList<BookInstructor>();
			books=rep.getAll();
			for(int i=0;i<books.size();i++)
			{
				BookInstructorDTO dto=new BookInstructorDTO();
				dto=assem.toDTO(books.get(i));
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
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
	public BookInstructorDTO getById(Integer id) {
		try
		{
			BookInstructor entity=new BookInstructor();
			entity=rep.getById(id);
			BookInstructorDTO dto=new BookInstructorDTO();
			dto=assem.toDTO(entity);
			try{
				BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
				BookDTO book=new BookDTO();
				BookAssembler bookAssem=new BookAssembler();
				book=bookAssem.toDTO(copy.getBook());
				dto.setBook(book);
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
	public List<BookInstructorDTO> getByInsID(Integer id) {
		try{
			List<BookInstructorDTO> booksDTO=new ArrayList<BookInstructorDTO>();
			List<BookInstructor> books=new ArrayList<BookInstructor>();
			books=rep.getByInsID(id);
			for(int i=0;i<books.size();i++)
			{
				BookInstructorDTO dto=new BookInstructorDTO();
				dto=assem.toDTO(books.get(i));
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
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
	public List<BookInstructorDTO> getByBarCode(String code) {
		try{
			List<BookInstructorDTO> booksDTO=new ArrayList<BookInstructorDTO>();
			List<BookInstructor> books=new ArrayList<BookInstructor>();
			books=rep.getByBarCode(code);
			for(int i=0;i<books.size();i++)
			{
				BookInstructorDTO dto=new BookInstructorDTO();
				dto=assem.toDTO(books.get(i));
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
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
	public List<BookInstructorDTO> getByBarCodeAndInsID(String barCode,
			Integer studentID) {
		try{
			List<BookInstructorDTO> booksDTO=new ArrayList<BookInstructorDTO>();
			List<BookInstructor> books=new ArrayList<BookInstructor>();
			books=rep.getByBarCodeAndInsID(barCode,studentID);
			for(int i=0;i<books.size();i++)
			{
				BookInstructorDTO dto=new BookInstructorDTO();
				dto=assem.toDTO(books.get(i));
				try{
					BookCopies copy=copiesRep.getByBarCode(dto.getBarCode());
					BookDTO book=new BookDTO();
					BookAssembler bookAssem=new BookAssembler();
					book=bookAssem.toDTO(copy.getBook());
					dto.setBook(book);
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
