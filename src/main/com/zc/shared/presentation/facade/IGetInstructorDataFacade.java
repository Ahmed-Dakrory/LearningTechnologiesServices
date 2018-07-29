/**
 * 
 */
package main.com.zc.shared.presentation.facade;

import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IGetInstructorDataFacade {

	public InstructorDTO getInsByMail(String mail);
}
