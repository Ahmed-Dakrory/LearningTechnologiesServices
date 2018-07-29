/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.service;

import java.util.List;

import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IJuniorTAServiceInstructor {

	public List<TAJuniorProgramDTO> getPendingFormsOfInstructor(Integer insID);
	public List<TAJuniorProgramDTO> getArchievedFormsOfInstructor(Integer insID);
	public List<TAJuniorProgramDTO> getPendingFormsOfPA(Integer insID);
	public List<TAJuniorProgramDTO> getArchievedFormsOfPA(Integer insID);
	public TAJuniorProgramDTO updateStatusOfForm(TAJuniorProgramDTO dto);
	public List<TAJuniorProgramDTO> getPendingFormsOfDean();
	public List<TAJuniorProgramDTO> getArchievedFormsOfDean();
	public TAJuniorProgramDTO getByID(Integer id);
	public void addComment(PetitionsActionsDTO dto, Integer instructorID);
	public List<InstructorDTO> fillInsLst();
	public List<TAJuniorProgramDTO>getPendingByDeanDashboard();
	public List<TAJuniorProgramDTO>getPendingByCoordDashboard(Integer id);
	public List<TAJuniorProgramDTO>getPendingByPADashboard(Integer id);
	public List<TAJuniorProgramDTO> getByCourseID(Integer id);
	
}
