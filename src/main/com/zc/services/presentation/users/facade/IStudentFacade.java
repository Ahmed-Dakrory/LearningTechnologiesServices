/**
 * 
 */
package main.com.zc.services.presentation.users.facade;

import java.util.List;

import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */

public interface IStudentFacade {
	public List<StudentDTO> getAll();
	
	public StudentDTO getById(Integer id);
	
}
