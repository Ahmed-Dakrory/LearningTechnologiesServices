/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdmissionHOverloadRequestService;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IAdmissionHOverloadRequestFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service("AdmissionHOverloadRequestFacadeImpl")
public class AdmissionHOverloadRequestFacadeImpl implements IAdmissionHOverloadRequestFacade{

	@Autowired
	IAdmissionHOverloadRequestService service;
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

}
