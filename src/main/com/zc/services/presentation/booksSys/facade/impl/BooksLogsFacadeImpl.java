/**
 * 
 */
package main.com.zc.services.presentation.booksSys.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.booksSys.service.IBooksLogsService;
import main.com.zc.services.presentation.booksSys.dto.BooksLogsDTO;
import main.com.zc.services.presentation.booksSys.facade.IBooksLogsFacade;

/**
 * @author omnya
 *
 */
@Service("IBooksLogsFacade")
public class BooksLogsFacadeImpl implements IBooksLogsFacade{

	@Autowired
	IBooksLogsService service;
	@Override
	public BooksLogsDTO addLog(BooksLogsDTO log) {
		
		return service.add(log);
	}

}
