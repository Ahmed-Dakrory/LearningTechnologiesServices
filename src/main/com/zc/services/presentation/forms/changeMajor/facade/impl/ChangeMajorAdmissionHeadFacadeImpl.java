/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.changeMajor.services.IAdmissionHeadChangeMajorService;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IchangeMajorAdmissionHeadFacade;

/**
 * @author omnya
 *
 */
@Service("ChangeMajorAdmissionHeadFacadeImpl")
public class ChangeMajorAdmissionHeadFacadeImpl implements IchangeMajorAdmissionHeadFacade{

	@Autowired
	IAdmissionHeadChangeMajorService service;
	@Override
	public List<ChangeMajorDTO> getPendingFormsOfAdmissionHead() {
		
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<ChangeMajorDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public ChangeMajorDTO updateStatusOfForm(ChangeMajorDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}
}
