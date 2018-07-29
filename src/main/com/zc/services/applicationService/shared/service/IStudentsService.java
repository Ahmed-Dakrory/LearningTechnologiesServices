/**
 * 
 */
package main.com.zc.services.applicationService.shared.service;

import java.util.List;

import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public interface IStudentsService {
	
	public List<StudentDTO> getAll();
	
	public StudentDTO getById(Integer id);

}
