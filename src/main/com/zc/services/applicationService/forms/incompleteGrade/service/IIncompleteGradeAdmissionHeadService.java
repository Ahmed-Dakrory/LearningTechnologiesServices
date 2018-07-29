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
public interface IIncompleteGradeAdmissionHeadService {
	public List<IncompleteGradeDTO> getPendingFormsOfAdmissionHead();
	public List<IncompleteGradeDTO> getArchievedFormsOfAdmissionHead();
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto);
	public IncompleteGradeDTO getById(Integer id);
	public void addComment(IncompleteGradeDTO dto,Integer instructorID);
	
}
