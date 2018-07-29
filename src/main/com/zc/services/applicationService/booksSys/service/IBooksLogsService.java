/**
 * 
 */
package main.com.zc.services.applicationService.booksSys.service;

import java.util.List;

import main.com.zc.services.domain.booksSys.model.BooksLogs;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;

/**
 * @author omnya
 *
 */
public interface IBooksLogsService {

	public BooksLogsDTO add(BooksLogsDTO obj);
	public boolean remove(Integer id);
	public BooksLogsDTO update(BooksLogsDTO obj);
	public List<BooksLogsDTO> getAll();
	public BooksLogsDTO getById(Integer id);
	public List<BooksLogsDTO> getByUserName(String user);
	public List<BooksLogsDTO> getByBookID(Integer id);
	
}
