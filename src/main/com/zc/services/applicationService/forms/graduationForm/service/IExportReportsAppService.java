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
public interface IExportReportsAppService {
	public void export(List<GraduationInformationDTO> lst);
}
