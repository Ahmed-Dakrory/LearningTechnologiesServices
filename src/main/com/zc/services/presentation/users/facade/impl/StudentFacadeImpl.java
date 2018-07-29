/**
 * 
 */
package main.com.zc.services.presentation.users.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.IStudentsService;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IStudentFacade;

/**
 * @author omnya
 *
 */
@Service("IStudentFacade")
public class StudentFacadeImpl implements IStudentFacade{

	@Autowired
	IStudentsService service;
	@Override
	public List<StudentDTO> getAll() {
		
		return service.getAll();
	}
	@Override
	public StudentDTO getById(Integer id) {
		
		return service.getById(id);
	}

}
