/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.incompleteGrade.service.IReportsIncompleteService;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadeIncompleteImpl")
public class ReportsFacadeIncompleteImpl implements IReportsFacade{
	@Autowired
	IReportsIncompleteService service;
	
	@Override
	public List<IncompleteGradeDTO> getOldSummer(Integer year) {
		return service.getOldSummer(year);
	}

	@Override
	public List<IncompleteGradeDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<IncompleteGradeDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<IncompleteGradeDTO> lst) {
		service.generateExcelByList(lst);
		
	}

}
