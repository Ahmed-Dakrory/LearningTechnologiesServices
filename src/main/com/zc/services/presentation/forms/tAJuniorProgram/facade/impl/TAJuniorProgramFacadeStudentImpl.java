/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.ITAJuniorProgramServiceStudent;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ITAJuniorProgramFacadeStudent;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
@Service("ITAJuniorProgramFacadeStudent")
public class TAJuniorProgramFacadeStudentImpl implements ITAJuniorProgramFacadeStudent{

	@Autowired
	ITAJuniorProgramServiceStudent service;
	@Override
	public List<TAJuniorProgramDTO> getPendingRequestsOfStudent(
			Integer studentID) {
		
		return service.getPendingRequestsOfStudent(studentID);
	}

	@Override
	public List<TAJuniorProgramDTO> getArchievedRequestsOfStudent(
			Integer studentID) {
		
		return service.getArchievedRequestsOfStudent(studentID);
	}

	@Override
	public TAJuniorProgramDTO submitRequest(TAJuniorProgramDTO dto) {
		
		return service.submitRequest(dto);
	}



	@Override
	public TAJuniorProgramDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

}
