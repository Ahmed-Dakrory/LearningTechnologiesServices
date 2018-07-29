/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface ITAJuniorProgramFacadeStudent {
	public List<TAJuniorProgramDTO>  getPendingRequestsOfStudent(Integer studentID);
	public List<TAJuniorProgramDTO>  getArchievedRequestsOfStudent(Integer studentID);
	public TAJuniorProgramDTO submitRequest(TAJuniorProgramDTO dto);

	public TAJuniorProgramDTO getByID(Integer id) ;
}
