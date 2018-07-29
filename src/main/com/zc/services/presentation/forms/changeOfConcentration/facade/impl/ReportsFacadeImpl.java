/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IReportsService;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("IReportsFacade")
public class ReportsFacadeImpl implements IReportsFacade{

	@Autowired
	IReportsService service;
	@Override
	public void generateExcelByList(List<ChangeConcentrationDTO> lst) {
		service.generateExcelByList(lst);
		
	}

}
