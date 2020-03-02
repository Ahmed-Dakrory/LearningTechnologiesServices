/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.readmission.services.IAdmissionDeptReadmissionService;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReadmissionAdmissionDeptFacade;


/**
 * @author omnya.alaa
 *
 */
@Service("ReadmissionAdmissionDeptFacadeImpl")
public class ReadmissionAdmissionDeptFacadeImpl implements IReadmissionAdmissionDeptFacade {

	@Autowired
	IAdmissionDeptReadmissionService service;
	@Override
	public ReadmissionDTO updateRequest(ReadmissionDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<ReadmissionDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent() {
	
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}
}
