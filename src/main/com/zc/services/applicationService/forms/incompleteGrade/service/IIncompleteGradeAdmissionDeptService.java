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
public interface IIncompleteGradeAdmissionDeptService {
	public IncompleteGradeDTO updateRequest(IncompleteGradeDTO dto);
	public IncompleteGradeDTO getByID(Integer id);
	public List<IncompleteGradeDTO> getPendingPetitionsOfstuent();
	public List<IncompleteGradeDTO> getArchievedPetitionsOfstuent();
	public void addComment(Integer id, String comment);
}
