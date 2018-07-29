/**
 * 
 */
package main.com.zc.services.presentation.users.facade;

import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public interface IGetLoggedInInstructorData {

	public InstructorDTO getInsByPersonMail(String mail);
	public InstructorDTO getInsByPersonID(Integer id);
	public boolean isCouurseCoordinator(String mail);
	public boolean isPA(String mail);
	
}
