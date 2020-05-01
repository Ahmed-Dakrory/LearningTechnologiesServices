/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.course_replacement_form.services.Icourse_replacement_formActionsService;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formActionsFacade;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 * @author Eman
 */
@Service("Icourse_replacement_formActionsFacade")
public class course_replacement_formActionsFacadeImpl implements Icourse_replacement_formActionsFacade{

	@Autowired
	Icourse_replacement_formActionsService service;
	@Override
	public course_replacement_formDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public void addComment(PetitionsActionsDTO dto, Integer instructorID) {
		service.addComment(dto, instructorID);
		
	}

	@Override
	public course_replacement_formDTO forwardPetition(course_replacement_formDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto) {
		
		return service.updateStatusOfForm(dto);
	}

	@Override
	public course_replacement_formDTO update(course_replacement_formDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
