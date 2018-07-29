/**
 * 
 */
package main.com.zc.services.presentation.users.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.persons.service.IInstructorAppService;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IInstructorFacade;

/**
 * @author Omnya Alaa
 *
 */
@Service("IInstructorFacade")
public class InstructorFacadeImpl implements IInstructorFacade{

	@Autowired
	IInstructorAppService service;
	
	@Override
	public List<InstructorDTO> getAllTAs() {
		
		return service.getAllTAs();
	}

	@Override
	public InstructorDTO update(InstructorDTO ins) {
		
		return service.update(ins);
	}

	@Override
	public List<InstructorDTO> getAllIns() {
	
		return service.getAllIns();
	}

	@Override
	public List<InstructorDTO> getByCourseID(Integer id) {
		
		return service.getByCourseID(id);
	}

}
