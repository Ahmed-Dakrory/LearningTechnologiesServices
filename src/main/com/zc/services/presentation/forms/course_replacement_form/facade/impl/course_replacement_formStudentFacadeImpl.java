/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.course_replacement_form.services.IStudentcourse_replacement_formService;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.Icourse_replacement_formStudentFacade;

/**
 * @author omnya.alaa
 *
 */
@Service("Icourse_replacement_formStudentFacade")
public class course_replacement_formStudentFacadeImpl implements Icourse_replacement_formStudentFacade{

	@Autowired
	IStudentcourse_replacement_formService appService;

	@Override
	public course_replacement_formDTO add(course_replacement_formDTO dto) {
		
		return appService.add(dto);
	}

	@Override
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		return appService.getPendingPetitionsOfstuent(studentID);
	}

	@Override
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		return appService.getArchievedPetitionsOfstuent(studentID);
	}



	@Override
	public course_replacement_formDTO getByID(Integer id) {
		
		return appService.getByID(id);
	}
}
