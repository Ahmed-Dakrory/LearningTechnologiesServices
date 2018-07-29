/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.changeMajor.services.IAdmissionDeptChangeMajorService;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IChangeMajorAdmissionDeptFacade;

/**
 * @author omnya.alaa
 *
 */
@Service("ChangeMajorAdmissionDeptFacadeImpl")
public class ChangeMajorAdmissionDeptFacadeImpl implements IChangeMajorAdmissionDeptFacade {

	@Autowired
	IAdmissionDeptChangeMajorService service;
	@Override
	public ChangeMajorDTO updateRequest(ChangeMajorDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent() {
	
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}
}
