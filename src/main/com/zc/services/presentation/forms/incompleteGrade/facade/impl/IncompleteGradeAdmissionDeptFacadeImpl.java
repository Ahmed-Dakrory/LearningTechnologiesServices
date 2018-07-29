/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdmissionDeptService;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IIncompleteGradeAdmissionDeptFacade;

/**
 * @author omnya
 *
 */
@Service("IIncompleteGradeAdmissionDeptFacade")
public class IncompleteGradeAdmissionDeptFacadeImpl implements IIncompleteGradeAdmissionDeptFacade{

	@Autowired
	IIncompleteGradeAdmissionDeptService service;
	@Override
	public IncompleteGradeDTO updateRequest(IncompleteGradeDTO dto) {
		
		return service.updateRequest(dto);
	}

	@Override
	public IncompleteGradeDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<IncompleteGradeDTO> getPendingPetitions() {
		
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<IncompleteGradeDTO> getArchievedPetitions() {
		
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void addComment(Integer id, String comment) {
		service.addComment(id, comment);
		
	}

}
