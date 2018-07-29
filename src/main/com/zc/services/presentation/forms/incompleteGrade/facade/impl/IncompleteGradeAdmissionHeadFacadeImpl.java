/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionHeadService;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeAdmissionHeadFacade;

/**
 * @author omnya
 *
 */
@Service("IIncompleteGradeAdmissionHeadFacade")
public class IncompleteGradeAdmissionHeadFacadeImpl implements IIncompleteGradeAdmissionHeadFacade {

	@Autowired
	IIncompleteGradeAdmissionHeadService service;
	@Override
	public List<IncompleteGradeDTO> getPendingFormsOfAdmissionHead() {
		
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public IncompleteGradeDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public void addComment(Integer id, String comment) {
		//service.addComment(id, comment);
		
	}

}
