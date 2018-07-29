/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IReportsDropAddService;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadeAddDropImpl")
public class ReportsFacadeAddDropImpl implements IReportsFacade{

	@Autowired
	IReportsDropAddService service;
	@Override
	public List<DropAddFormDTO> getOldSummer(Integer year) {
		
		return service. getOldSummer(year);
	}

	@Override
	public List<DropAddFormDTO> getOldSpring(Integer year) {
		return service. getOldSpring(year);
	}

	@Override
	public List<DropAddFormDTO> getOldFall(Integer year) {
		return service. getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<DropAddFormDTO> lst) {
		 service. generateExcelByList(lst);
		
	}

}
