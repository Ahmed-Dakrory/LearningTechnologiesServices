/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.booksSys.assembler.BooksLogsAssembler;
import main.com.zc.services.applicationService.booksSys.service.IBooksLogsService;
import main.com.zc.services.domain.booksSys.model.BooksLogs;
import main.com.zc.services.domain.booksSys.model.IBooksLogsRepository;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class BooksLogsServiceImpl implements IBooksLogsService{

	@Autowired
	IBooksLogsRepository rep;
	BooksLogsAssembler assem=new BooksLogsAssembler();
	@Override
	public BooksLogsDTO add(BooksLogsDTO obj) {
		try{
			BooksLogs log=new BooksLogs();
			log=assem.toEntity(obj);
			log=rep.add(log);
			obj=assem.toDTO(log);
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
	public BooksLogsDTO update(BooksLogsDTO obj) {
		try{
			BooksLogs log=new BooksLogs();
			log=assem.toEntity(obj);
			log=rep.update(log);
			obj=assem.toDTO(log);
			return obj;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
			
		}
	}

	@Override
	public List<BooksLogsDTO> getAll() {
		try{
			List<BooksLogsDTO> booksDTO=new ArrayList<BooksLogsDTO>();
			List<BooksLogs> books=new ArrayList<BooksLogs>();
			books=rep.getAll();
			for(int i=0;i<books.size();i++)
			{
				BooksLogsDTO dto=new BooksLogsDTO();
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
	public BooksLogsDTO getById(Integer id) {
		try
		{
			BooksLogs entity=new BooksLogs();
			entity=rep.getById(id);
			BooksLogsDTO dto=new BooksLogsDTO();
			dto=assem.toDTO(entity);
			return dto;
		}
		catch(Exception ex){
			return null;
		}
	}

	@Override
	public List<BooksLogsDTO> getByUserName(String user) {
		try{
			List<BooksLogsDTO> booksDTO=new ArrayList<BooksLogsDTO>();
			List<BooksLogs> books=new ArrayList<BooksLogs>();
			books=rep.getByUserName(user);
			for(int i=0;i<books.size();i++)
			{
				BooksLogsDTO dto=new BooksLogsDTO();
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
	public List<BooksLogsDTO> getByBookID(Integer id) {
		
		try{
			List<BooksLogsDTO> booksDTO=new ArrayList<BooksLogsDTO>();
			List<BooksLogs> books=new ArrayList<BooksLogs>();
			books=rep.getByBookID(id);
			for(int i=0;i<books.size();i++)
			{
				BooksLogsDTO dto=new BooksLogsDTO();
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

}
