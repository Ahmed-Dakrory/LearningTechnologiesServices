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
public interface IIncompleteGradeAdminService {

	public List<IncompleteGradeDTO> getPendingForms();
	public List<IncompleteGradeDTO> getArchievedForms();
    public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto);
	public IncompleteGradeDTO getById(Integer id);
	//public void addComment(Integer id, String comment);
	
}
