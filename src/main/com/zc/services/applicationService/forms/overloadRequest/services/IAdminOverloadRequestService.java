/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services;

import java.util.List;

import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

/**
 * @author momen
 *
 */
public interface IAdminOverloadRequestService {

	public List<OverloadRequestDTO> getPendingForms();
	public List<OverloadRequestDTO> getArchievedForms();
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto);
}
	
