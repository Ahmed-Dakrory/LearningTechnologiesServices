/**
 * 
 */
package main.com.zc.services.applicationService.stuff.teachAsis.attendance.service;

import java.io.FileNotFoundException;
import java.util.List;

import main.com.zc.services.presentation.stuff.teachAsis.attendance.dto.TADTO;

/**
 * @author omnya
 *
 */
public interface ITAAppService {
	public TADTO add(TADTO dto);
	public List<TADTO> getAll();
	public void parseFile(String file) throws FileNotFoundException;
	

}
