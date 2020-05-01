/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.course_replacement_form.services.IAdmissionDeptcourse_replacement_formService;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formAdmissionDeptFacade;


/**
 * @author omnya.alaa
 *
 */
@Service("course_replacement_formAdmissionDeptFacadeImpl")
public class course_replacement_formAdmissionDeptFacadeImpl implements Icourse_replacement_formAdmissionDeptFacade {

	@Autowired
	IAdmissionDeptcourse_replacement_formService service;
	@Override
	public course_replacement_formDTO updateRequest(course_replacement_formDTO dto) {
	
		return service.updateRequest(dto);
	}

	@Override
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent() {
	
		return service.getPendingPetitionsOfstuent();
	}

	@Override
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent() {
	
		return service.getArchievedPetitionsOfstuent();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}
}
