/**
 * 
 */
package main.com.zc.services.applicationService.forms.incompleteGrade.service;

import java.util.List;

import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;

/**
 * @author omnya
 *
 */
public interface IReportsIncompleteService {

	public List<IncompleteGradeDTO> getOldSummer(Integer year);
	public List<IncompleteGradeDTO> getOldSpring(Integer year);
	public List<IncompleteGradeDTO> getOldFall(Integer year);
	public void generateExcelByList(List<IncompleteGradeDTO> lst);
	
}
