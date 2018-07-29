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
public interface IIncompleteGradeAdminFacade {

	public List<IncompleteGradeDTO> getPendingForms();
	public List<IncompleteGradeDTO> getArchievedForms();
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto);
	public void notifyNextUser(IncompleteGradeDTO dto);
	
}
