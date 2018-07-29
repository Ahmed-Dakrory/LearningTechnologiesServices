package main.com.zc.shared.appService;

import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

public interface ILoginAppService {
public LoginStaffDTO checkLogin(String mail,String password);
public LoginStaffDTO addInsLogin(String name , String mail , String pass, byte[] image) throws Exception;
/**
 *  this method is for check if the inserted mail is real instructor or not
 * @param mail
 * @return boolean
 */
public boolean checkInstuctors(String mail); 
public boolean checkStudents(String mail);
public InstructorDTO getCourseInsByInsMail(String mail);
public LoginStaffDTO checkRegisteryOFMail(String mail);// Check if the user already registered or not 
byte[] getStudentImage(String mail);
byte[] saveImage(String mail, byte[] image);
public void changePassword(String mail , String newPass) ;
public void changeAllPasswords();
}
