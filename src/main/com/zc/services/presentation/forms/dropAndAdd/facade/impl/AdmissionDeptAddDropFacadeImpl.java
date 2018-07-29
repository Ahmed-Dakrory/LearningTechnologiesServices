/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdmissionDeptAddDropFormService;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAdmissionDeptAddDropFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service("AdmissionDeptAddDropFacadeImpl")
public class AdmissionDeptAddDropFacadeImpl implements IAdmissionDeptAddDropFacade {

	@Autowired
	IAdmissionDeptAddDropFormService appService;

	@Override
	public List<DropAddFormDTO> getPendingFormsOfAdmissionDept() {
		
		return appService.getPendingFormsOfAdmissionDept();
	}

	@Override
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionDept() {
		
		return appService.getArchievedFormsOfAdmissionDept();
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
