/**
 * 
 */
package main.com.zc.services.presentation.users.facade;

import java.util.List;

import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IInstructorFacade {

	public List<InstructorDTO> getAllTAs();
	public List<InstructorDTO> getAllIns();
	public InstructorDTO update(InstructorDTO ins);
	public List<InstructorDTO> getByCourseID(Integer id);
}
