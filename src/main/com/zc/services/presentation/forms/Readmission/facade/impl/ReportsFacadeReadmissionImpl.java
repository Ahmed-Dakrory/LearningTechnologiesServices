/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.readmission.services.IReportsServiceReadmission;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.forms.Readmission.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadeReadmissionImpl")
public class ReportsFacadeReadmissionImpl implements IReportsFacade{

	@Autowired
	IReportsServiceReadmission service;
	@Override
	public List<ReadmissionDTO> getOldSummer(Integer year) {
		return service.getOldSummer(year);
	}

	@Override
	public List<ReadmissionDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<ReadmissionDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<ReadmissionDTO> lst) {
		service.generateExcelByList(lst);
		
	}



}
