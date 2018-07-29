/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdmissionHAddDropFormService;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAdmissionHAddDropFormFacade;

/**
 * @author omnya.alaa
 *
 */
@Service("AdmissionHAddDropFormFacadeImpl")
public class AdmissionHAddDropFormFacadeImpl implements IAdmissionHAddDropFormFacade{

	@Autowired
	IAdmissionHAddDropFormService appService;
	@Override
	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead() {
		
		return appService.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead() {
		return appService.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) {
		return appService.updateStatusOfForm(dto);
	}
	
	@Override
	public void addComment(Integer id, String comment) {
		
		appService.addComment(id, comment);
	}

}
