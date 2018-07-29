/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeMajor.services.IReportsServiceChangeMajor;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.changeMajor.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadeChangeMajorImpl")
public class ReportsFacadeChangeMajorImpl implements IReportsFacade{

	@Autowired
	IReportsServiceChangeMajor service;
	@Override
	public List<ChangeMajorDTO> getOldSummer(Integer year) {
		return service.getOldSummer(year);
	}

	@Override
	public List<ChangeMajorDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<ChangeMajorDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<ChangeMajorDTO> lst) {
		service.generateExcelByList(lst);
		
	}

}
