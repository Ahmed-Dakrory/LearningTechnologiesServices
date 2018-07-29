/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services;

import java.util.List;

import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

/**
 * @author omnya.alaa
 *
 */
public interface IAdmissionHOverloadRequestService {

	public List<OverloadRequestDTO> getPendingForms();
	public List<OverloadRequestDTO> getArchievedForms();
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto);
	public void addComment(Integer id, String comment);
}
	
