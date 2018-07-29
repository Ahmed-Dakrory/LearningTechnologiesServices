/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.configuration.services.IFormsStatusAppService;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service("IFormsStatusFacade")
public class FormsStatusFacadeImpl implements IFormsStatusFacade{

   @Autowired
   IFormsStatusAppService service;
   
	@Override
	public FormsStatusDTO add(FormsStatusDTO form) {
		
		return service.add(form);
	}

	@Override
	public boolean remove(FormsStatusDTO dto) {
		
		return service.remove(dto);
	}

	@Override
	public FormsStatusDTO update(FormsStatusDTO form) {
		
		return service.update(form);
	}

	@Override
	public List<FormsStatusDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public FormsStatusDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public FormsStatusDTO addLevelsToForm(FormsStatusDTO dto) {
		
		return service.addLevelsToForm(dto);
	}

	@Override
	public FormsStatusDTO updateYearAndSemesters(FormsStatusDTO dto) {
		
		return service.updateYearAndSemesters(dto);
	}

	
}
