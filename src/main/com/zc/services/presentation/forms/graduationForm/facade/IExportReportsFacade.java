/**
 * 
 */
package main.com.zc.services.presentation.forms.graduationForm.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;

/**
 * @author omnya
 *
 */
public interface IExportReportsFacade {

	public void export(List<GraduationInformationDTO> lst);
}
