/**
 * 
 */
package main.com.zc.services.presentation.forms.graduationForm.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.graduationForm.service.IGraduationInformationAppService;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;
import main.com.zc.services.presentation.forms.graduationForm.facade.IGraduationInformationFacade;

/**
 * @author omnya
 *
 */
@Service("IGraduationInformationFacade")
public class GraduationInformationFacadeImpl implements IGraduationInformationFacade{

	@Autowired
	IGraduationInformationAppService service;
	@Override
	public GraduationInformationDTO add(GraduationInformationDTO form) {
	
	return service.add(form);
	}

	@Override
	public boolean remove(GraduationInformationDTO form) {
		return service.remove(form);
	}

	@Override
	public GraduationInformationDTO update(GraduationInformationDTO form) {
		return service.update(form);
	}

	@Override
	public List<GraduationInformationDTO> getAll() {
		return service.getAll();
	}

	@Override
	public List<GraduationInformationDTO> getFormByStudentID(Integer studentID) {
		return service.getFormByStudentID(studentID);
	}

	@Override
	public GraduationInformationDTO getById(Integer id) {
		return service.getById(id);
	}

	@Override
	public GraduationInformationDTO getFormByStudentIDAndSemesterAndYear(Integer studentID, Integer year,
			Integer semester) {
		return service.getFormByStudentIDAndSemesterAndYear(studentID,year,semester);
	}

	@Override
	public List<GraduationInformationDTO> getFormBySemesterAndYear(Integer year, Integer semester) {
		return service.getFormBySemesterAndYear(year,semester);
	}

}
