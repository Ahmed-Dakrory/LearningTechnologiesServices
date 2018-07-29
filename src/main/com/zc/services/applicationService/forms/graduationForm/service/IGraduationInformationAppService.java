/**
 * 
 */
package main.com.zc.services.applicationService.forms.graduationForm.service;

import java.util.List;

import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;

/**
 * @author omnya
 *
 */
public interface IGraduationInformationAppService {
	public GraduationInformationDTO add(GraduationInformationDTO form) ;
	public boolean remove(GraduationInformationDTO form);
	public GraduationInformationDTO update(GraduationInformationDTO form);
	public List<GraduationInformationDTO> getAll();
	public List<GraduationInformationDTO> getFormByStudentID(Integer studentID);
	public GraduationInformationDTO getById(Integer id);
	public GraduationInformationDTO getFormByStudentIDAndSemesterAndYear(Integer studentID, Integer year,
			Integer semester);
	public  List<GraduationInformationDTO> getFormBySemesterAndYear(Integer year, Integer semester);
}
