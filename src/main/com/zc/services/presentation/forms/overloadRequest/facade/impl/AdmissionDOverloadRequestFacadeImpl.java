/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdmissionDOverloadRequestService;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdmissionDOverloadRequestFacade;

/**
 * @author omnya.alaa
 *
 */
@Service("AdmissionDOverloadRequestFacadeImpl")
public class AdmissionDOverloadRequestFacadeImpl implements IAdmissionDOverloadRequestFacade {

	@Autowired
	IAdmissionDOverloadRequestService service;
	@Override
	public List<OverloadRequestDTO> getPendingForms() {
		
		return service.getPendingForms();
	}

	@Override
	public List<OverloadRequestDTO> getArchievedForms() {
		
		return service.getArchievedForms();
	}

	@Override
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto) {
		
		return service.updateStatus(dto);
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);		
	}
	
	@Override
	public List<OverloadRequestDTO> getAuditingPet() {
		
		return service.getAuditingPet();
	}
}
