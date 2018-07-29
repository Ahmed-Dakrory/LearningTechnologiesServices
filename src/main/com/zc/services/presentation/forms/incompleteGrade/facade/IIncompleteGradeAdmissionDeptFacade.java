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
public interface IIncompleteGradeAdmissionDeptFacade {
	public IncompleteGradeDTO updateRequest(IncompleteGradeDTO dto);
	public IncompleteGradeDTO getByID(Integer id);
	public List<IncompleteGradeDTO> getPendingPetitions();
	public List<IncompleteGradeDTO> getArchievedPetitions();
	public void addComment(Integer id, String comment);
}
