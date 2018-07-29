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
public interface IIncompleteGradeAdmissionHeadFacade {
	public List<IncompleteGradeDTO> getPendingFormsOfAdmissionHead();
	public List<IncompleteGradeDTO> getArchievedFormsOfAdmissionHead();
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto);
	public IncompleteGradeDTO getById(Integer id);
	public void addComment(Integer id, String comment);
}
