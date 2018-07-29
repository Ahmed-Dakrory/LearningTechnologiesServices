/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.overloadRequest.services.IInsOverloadRequestService;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IInsOverloadRequestFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("InsOverloadRequestFacadeImpl")
public class InsOverloadRequestFacadeImpl implements IInsOverloadRequestFacade{

	@Autowired
	IInsOverloadRequestService service;
	@Override
	public List<OverloadRequestDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<OverloadRequestDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public OverloadRequestDTO updateStatusOfForm(OverloadRequestDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<OverloadRequestDTO> getPendingFormsOfDean() {
		
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<OverloadRequestDTO> getArchievedFormsOfDean() {
		
		return service.getArchievedFormsOfDean();
	}

	@Override
	public List<OverloadRequestDTO> getPendingFormsOfProvost() {
		
		return service.getPendingFormsOfProvost();
	}

	@Override
	public List<OverloadRequestDTO> getArchievedFormsOfProvost() {
		
		return service.getArchievedFormsOfProvost();
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);		
	}

	@Override
	public OverloadRequestDTO getById(Integer id) {
		
		return service.gteById(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public OverloadRequestDTO forwardPetition(OverloadRequestDTO dto) {
		
		return service.forwardPetition(dto);
	}

}
