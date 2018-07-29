/**
 * 
 */
package main.com.zc.services.applicationService.persons.service;

import java.util.List;

import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IInstructorAppService {

	public List<InstructorDTO> getAllTAs();
	public InstructorDTO update(InstructorDTO ins);
	public List<InstructorDTO> getAllIns();
	public List<InstructorDTO> getByCourseID(Integer id);
}
