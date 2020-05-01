/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;

/**
 * @author omnya
 *
 */
public interface IReportsFacade {
	public List<course_replacement_formDTO> getOldSummer(Integer year);
	public List<course_replacement_formDTO> getOldSpring(Integer year);
	public List<course_replacement_formDTO> getOldFall(Integer year);
	public void generateExcelByList(List<course_replacement_formDTO> lst);
}
