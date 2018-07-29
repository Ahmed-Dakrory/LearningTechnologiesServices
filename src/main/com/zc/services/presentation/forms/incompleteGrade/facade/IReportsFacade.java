/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;

/**
 * @author omnya
 *
 */
public interface IReportsFacade {

	public List<IncompleteGradeDTO> getOldSummer(Integer year);
	public List<IncompleteGradeDTO> getOldSpring(Integer year);
	public List<IncompleteGradeDTO> getOldFall(Integer year);
	public void generateExcelByList(List<IncompleteGradeDTO> lst);
}
