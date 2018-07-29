/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.CourseRepeat.services.IReportsCourseRepearService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.IReportsFacade;

/**
 * @author omnya
 *
 */
@Service("ReportsFacadeCourseRepeatImpl")
public class ReportsFacadeCourseRepeatImpl implements IReportsFacade{

	@Autowired
	IReportsCourseRepearService  service;
	@Override
	public List<CourseRepeatDTO> getOldSummer(Integer year) {
		return service.getOldSummer(year);
	}

	@Override
	public List<CourseRepeatDTO> getOldSpring(Integer year) {
		return service.getOldSpring(year);
	}

	@Override
	public List<CourseRepeatDTO> getOldFall(Integer year) {
		return service.getOldFall(year);
	}

	@Override
	public void generateExcelByList(List<CourseRepeatDTO> lst) {
		service.generateExcelByList(lst);
		
	}

}
