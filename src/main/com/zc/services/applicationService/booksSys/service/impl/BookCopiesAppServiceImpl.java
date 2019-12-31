/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.booksSys.assembler.BookCopiesAssembler;
import main.com.zc.services.applicationService.booksSys.service.IBookCopiesAppService;
import main.com.zc.services.domain.booksSys.model.BookCopies;
import main.com.zc.services.domain.booksSys.model.BookInstructor;
import main.com.zc.services.domain.booksSys.model.BookStudent;
import main.com.zc.services.domain.booksSys.model.IBookCopiesRep;
import main.com.zc.services.domain.booksSys.model.IBookInstructorRepository;
import main.com.zc.services.domain.booksSys.model.IBookStudentRepository;
import main.com.zc.services.domain.shared.enumurations.BookActionEnum;
import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@Service
public class BookCopiesAppServiceImpl implements IBookCopiesAppService{

	@Autowired
	IBookCopiesRep rep;
	@Autowired
	IBookStudentRepository studentBookRep;
	@Autowired
	IBookInstructorRepository insBookRep;
	BookCopiesAssembler assem=new BookCopiesAssembler();
	@Override
	public BookCopiesDTO add(BookCopiesDTO copy) {
		try{
		BookCopies copies=new BookCopies();
		copies=assem.toEntity(copy);
		BookCopies addedCopy=rep.add(copies);
		BookCopiesDTO dto=new BookCopiesDTO();
		dto=assem.toDTO(addedCopy);
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean remove(Integer id) {
		
		return false;
	}

	@Override
	public BookCopiesDTO update(BookCopiesDTO copy) {
		try{
		BookCopies copies=new BookCopies();
		copies=assem.toEntity(copy);
		BookCopies addedCopy=rep.update(copies);
		BookCopiesDTO dto=new BookCopiesDTO();
		dto=assem.toDTO(addedCopy);
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BookCopiesDTO> getAll() {
		List<BookCopies> copies=new ArrayList<BookCopies>();
		List<BookCopiesDTO> dtos=new ArrayList<BookCopiesDTO>();
		try{
			copies=rep.getAll();
		for(int i=0;i<copies.size();i++)
		{
			BookCopiesDTO dto=assem.toDTO(copies.get(i));
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<BookCopiesDTO> getByCourseID(Integer id) {
		
		List<BookCopies> copies=new ArrayList<BookCopies>();
		List<BookCopiesDTO> dtos=new ArrayList<BookCopiesDTO>();
		try{
			copies=rep.getByCourseID(id);
		for(int i=0;i<copies.size();i++)
		{
			BookCopiesDTO dto=assem.toDTO(copies.get(i));
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BookCopiesDTO> getByBookID(Integer id) {
		
		List<BookCopies> copies=new ArrayList<BookCopies>();
		List<BookCopiesDTO> dtos=new ArrayList<BookCopiesDTO>();
		try{
			copies=rep.getByBookID(id);
		for(int i=0;i<copies.size();i++)
		{
			BookCopiesDTO dto=assem.toDTO(copies.get(i));
			try{
			if( dto.getStatus().equals(BookStatusEnum.HELD))
			{
			List<BookStudent> students=new ArrayList<BookStudent>();
			students=studentBookRep.getByBarCode(dto.getBarCode());
			if(students!=null)
			{
				if(students.size()>0)
				{
					if(students.get(0).getAction().equals(BookActionEnum.RESERVE))
					{
						PersonDataDTO person=new PersonDataDTO();
						person.setEmail(students.get(0).getStudent().getData().getMail());
						person.setNameInEng(students.get(0).getStudent().getData().getNameInEnglish());
						dto.setLastPerson(person);
					}
				}
			}
			List<BookInstructor> instructors=new ArrayList<BookInstructor>();
			instructors=insBookRep.getByBarCode(dto.getBarCode());
			if(instructors!=null)
			{
				if(instructors.size()>0)
				{
					if(instructors.get(0).getAction().equals(BookActionEnum.RESERVE))
					{
						PersonDataDTO person=new PersonDataDTO();
						person.setEmail(instructors.get(0).getInstructor().getMail());
						person.setNameInEng(instructors.get(0).getInstructor().getName());
						dto.setLastPerson(person);
					}
				}
			}
			
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public BookCopiesDTO getById(Integer id) {
		BookCopies copy=new BookCopies();
	
		BookCopiesDTO dto=new BookCopiesDTO();
		try{
			copy=rep.getById(id);
			dto=assem.toDTO(copy);
	
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public BookCopiesDTO getByBarCode(String code) {
		
		BookCopies copy=new BookCopies();
		
		BookCopiesDTO dto=new BookCopiesDTO();
		try{
			copy=rep.getByBarCode(code);
			dto=assem.toDTO(copy);
	
		return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BookCopiesDTO> getAllFree() {
		List<BookCopies> copies=new ArrayList<BookCopies>();
		List<BookCopiesDTO> dtos=new ArrayList<BookCopiesDTO>();
		try{
			copies=rep.getAllFree();
		for(int i=0;i<copies.size();i++)
		{
			BookCopiesDTO dto=assem.toDTO(copies.get(i));
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<BookCopiesDTO> getAllHeld() {
		List<BookCopies> copies=new ArrayList<BookCopies>();
		List<BookCopiesDTO> dtos=new ArrayList<BookCopiesDTO>();
		try{
			copies=rep.getAllHeld();
		for(int i=0;i<copies.size();i++)
		{
			BookCopiesDTO dto=assem.toDTO(copies.get(i));
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

}
