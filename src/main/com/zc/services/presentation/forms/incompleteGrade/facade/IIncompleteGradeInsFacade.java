/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IIncompleteGradeInsFacade {
	public List<IncompleteGradeDTO> getPendingFormsOfInstructor(Integer insID);
	public List<IncompleteGradeDTO> getArchievedFormsOfInstructor(Integer insID);
	public List<IncompleteGradeDTO> getPendingFormsOfPA(Integer insID);
	public List<IncompleteGradeDTO> getArchievedFormsOfPA(Integer insID);
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto);
	public List<IncompleteGradeDTO> getPendingFormsOfDean();
	public List<IncompleteGradeDTO> getArchievedFormsOfDean();
	public IncompleteGradeDTO getByID(Integer id);
	//public void addComment(PetitionsActionsDTO dto);
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public List<InstructorDTO> fillInsLst();
	public IncompleteGradeDTO forwardPetition(IncompleteGradeDTO dto);
	public IncompleteGradeDTO update(IncompleteGradeDTO dto);
}
