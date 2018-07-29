/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.service;

import java.util.List;

import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface ITAJuniorProgramServiceStudent {

	public List<TAJuniorProgramDTO>  getPendingRequestsOfStudent(Integer studentID);
	public List<TAJuniorProgramDTO>  getArchievedRequestsOfStudent(Integer studentID);
	public TAJuniorProgramDTO submitRequest(TAJuniorProgramDTO dto);

	public TAJuniorProgramDTO getByID(Integer id) ;
	
	
}
