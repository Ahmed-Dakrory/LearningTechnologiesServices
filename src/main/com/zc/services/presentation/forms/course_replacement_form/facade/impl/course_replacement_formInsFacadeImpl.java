/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.course_replacement_form.services.IInstructorcourse_replacement_formService;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formInsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("course_replacement_formInsFacadeImpl")
public class course_replacement_formInsFacadeImpl implements Icourse_replacement_formInsFacade{

	@Autowired
	IInstructorcourse_replacement_formService service;
	@Override
	public List<course_replacement_formDTO> getPendingFormsOfInstructor(Integer insID) {
		
		return service.getPendingFormsOfInstructor(insID);
	}

	@Override
	public List<course_replacement_formDTO> getArchievedFormsOfInstructor(Integer insID) {
		
		return service.getArchievedFormsOfInstructor(insID);
	}

	@Override
	public course_replacement_formDTO updateStatusOfForm(course_replacement_formDTO dto) {
		return service.updateStatusOfForm(dto);
	}

	@Override
	public List<course_replacement_formDTO> getPendingFormsOfDean() {
		return service.getPendingFormsOfDean();
	}

	@Override
	public List<course_replacement_formDTO> getArchievedFormsOfDean() {
		return service.getArchievedFormsOfDean();
	}

	@Override
	public void addComment(Integer id, String comment) {
		
		service.addComment(id, comment);
	}

	@Override
	public course_replacement_formDTO getByID(Integer id) {
		
		return service.getByID(id);
	}

	@Override
	public List<InstructorDTO> fillInsLst() {
		
		return service.fillInsLst();
	}

	@Override
	public course_replacement_formDTO forwardPetition(course_replacement_formDTO dto) {
		
		return service.forwardPetition(dto);
	}

	@Override
	public List<course_replacement_formDTO> getPendingFormsOfDeanAcademics() {
		// TODO Auto-generated method stub
		return service.getPendingFormsOfDeanOfAcademic();
	}

	
}
