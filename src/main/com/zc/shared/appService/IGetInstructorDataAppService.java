/**
 * 
 */
package main.com.zc.shared.appService;

import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IGetInstructorDataAppService {
	public InstructorDTO getInsByMail(String mail);
	public main.com.zc.services.presentation.users.dto.InstructorDTO getInsByMailNew(String mail);
	public boolean isCouurseCoordinator(String mail);
	public boolean isPA(String mail);
	public  main.com.zc.services.presentation.users.dto.InstructorDTO getInsByPersonID(Integer id);
}
