/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.course_replacement_form.services.IAdmissionHeadcourse_replacement_formService;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formAdmissionHeadFacade;

/**
 * @author omnya
 *
 */
@Service("course_replacement_formAdmissionHeadFacadeImpl")
public class course_replacement_formAdmissionHeadFacadeImpl implements Icourse_replacement_formAdmissionHeadFacade{

	@Autowired
	IAdmissionHeadcourse_replacement_formService service;
	@Override
	public List<course_replacement_formDTO> getPendingFormsOfAdmissionHead() {
		
		return service.getPendingFormsOfAdmissionHead();
	}

	@Override
	public List<course_replacement_formDTO> getArchievedFormsOfAdmissionHead() {
		
		return service.getArchievedFormsOfAdmissionHead();
	}

	@Override
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}

	
}
