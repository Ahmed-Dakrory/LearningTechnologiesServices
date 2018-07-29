/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IReportsServiceOverload;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.forms.overloadRequest.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadeOverloadImpl")
public class ReportsFacadeOverloadImpl implements IReportsFacade{

	@Autowired
	IReportsServiceOverload service;
	@Override
	public List<OverloadRequestDTO> getOldSummer(Integer year) {
		return service.getOldSummer(year);
	}

	@Override
	public List<OverloadRequestDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<OverloadRequestDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<OverloadRequestDTO> lst) {
		 service.generateExcelByList(lst);
		
	}

}
