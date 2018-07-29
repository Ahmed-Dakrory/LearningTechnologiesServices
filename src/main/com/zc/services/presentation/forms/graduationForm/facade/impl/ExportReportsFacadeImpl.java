/**
 * 
 */
package main.com.zc.services.presentation.forms.graduationForm.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.graduationForm.service.IExportReportsAppService;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;
import main.com.zc.services.presentation.forms.graduationForm.facade.IExportReportsFacade;

/**
 * @author omnya
 *
 */
@Service("IExportReportsFacade")
public class ExportReportsFacadeImpl implements IExportReportsFacade{

	@Autowired
	IExportReportsAppService service;
	@Override
	public void export(List<GraduationInformationDTO> lst) {
		service.export(lst);
		
	}

}
