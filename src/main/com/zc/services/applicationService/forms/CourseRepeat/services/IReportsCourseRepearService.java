/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;

/**
 * @author omnya
 *
 */
public interface IReportsCourseRepearService {
	public List<CourseRepeatDTO> getOldSummer(Integer year);
	public List<CourseRepeatDTO> getOldSpring(Integer year);
	public List<CourseRepeatDTO> getOldFall(Integer year);
	public void generateExcelByList(List<CourseRepeatDTO> lst);
}
