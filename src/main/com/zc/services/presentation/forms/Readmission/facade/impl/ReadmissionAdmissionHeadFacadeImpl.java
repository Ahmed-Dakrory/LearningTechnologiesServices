/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.readmission.services.IAdmissionHeadReadmissionService;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IreadmissionAdmissionHeadFacade;

/**
 * @author omnya
 *
 */
@Service("ReadmissionAdmissionHeadFacadeImpl")
public class ReadmissionAdmissionHeadFacadeImpl implements IreadmissionAdmissionHeadFacade{

	@Autowired
	IAdmissionHeadReadmissionService service;
	@Override
	public List<ReadmissionDTO> getPendingFormsOfAdmissionHead() {
		
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<ReadmissionDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}

	
}
