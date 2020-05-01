/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.course_replacement_form.services.IReportsServicecourse_replacement_form;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.forms.course_replacement_form.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadecourse_replacement_formImpl")
public class ReportsFacadecourse_replacement_formImpl implements IReportsFacade{

	@Autowired
	IReportsServicecourse_replacement_form service;
	@Override
	public List<course_replacement_formDTO> getOldSummer(Integer year) {
		return service.getOldSummer(year);
	}

	@Override
	public List<course_replacement_formDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<course_replacement_formDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<course_replacement_formDTO> lst) {
		service.generateExcelByList(lst);
		
	}



}
