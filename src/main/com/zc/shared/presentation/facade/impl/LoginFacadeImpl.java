/**
 * 
 */
package main.com.zc.shared.presentation.facade.impl;

import main.com.zc.services.applicationService.persons.service.IStudentProfileService;
import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;
import main.com.zc.shared.appService.ILoginAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;
import main.com.zc.shared.presentation.facade.ILoginFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service("loginFacadeImpl")
public class LoginFacadeImpl implements ILoginFacade {

	@Autowired
	ILoginAppService loginStaffAppService;
	@Autowired
	IStudentProfileService studentProfileService;
	
	@Override
	public LoginStaffDTO checkLogin(String mail, String password) {
		// TODO Auto-generated method stub
		return loginStaffAppService.checkLogin(mail, password);
	}
	@Override
	public boolean checkInstuctors(String mail) {
		// TODO Auto-generated method stub
		return loginStaffAppService.checkInstuctors(mail);
	}
	@Override
	public InstructorDTO getCourseInsByInsMail(String mail) {
		// TODO Auto-generated method stub
		return loginStaffAppService.getCourseInsByInsMail(mail);
	}
	@Override
	public LoginStaffDTO checkRegisteryOFMail(String mail) {
		// TODO Auto-generated method stub
		return loginStaffAppService.checkRegisteryOFMail(mail);
	}
	@Override
	public boolean checkStudents(String mail) {
		// TODO Auto-generated method stub
		return loginStaffAppService.checkStudents(mail);
	}
	@Override
	public void changeAllPasswords() {
		loginStaffAppService.changeAllPasswords();
		
	}
	@Override
	public boolean checkProfileCompleted(String mail) {
		
		return studentProfileService.checkStudentProfile(mail);
	}

}
