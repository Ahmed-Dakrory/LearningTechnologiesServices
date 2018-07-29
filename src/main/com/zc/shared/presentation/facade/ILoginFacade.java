/**
 * 
 */
package main.com.zc.shared.presentation.facade;

import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface ILoginFacade {
	public LoginStaffDTO checkLogin(String mail, String password) ;
	public boolean checkInstuctors(String mail);
	public boolean checkStudents(String mail);
	InstructorDTO getCourseInsByInsMail(String mail);
	public LoginStaffDTO checkRegisteryOFMail(String mail);
	public void changeAllPasswords();
	public boolean checkProfileCompleted(String mail);
}
