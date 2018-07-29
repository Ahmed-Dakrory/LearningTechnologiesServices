/**
 * 
 */
package main.com.zc.shared.appService.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import main.com.zc.services.applicationService.moodleService.IMoodleIntegrationAppService;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.Data;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.IDataRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.LoginData;
import main.com.zc.services.presentation.survey.courseFeedback.dto.InstructorDTO;
import main.com.zc.shared.appService.ILoginAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

/**
 * @author Omnya Alaa
 *
 */
@Service("loginAppServiceImpl")
public class LoginAppServiceImpl implements ILoginAppService {

	@Autowired
	ILoginDataRepository loginStaffRep;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	IDataRepository dataRep;
	@Autowired
	IStudentRepository studentRepository;
	@Autowired
	IMoodleIntegrationAppService  moodleIntegrationAppService;
	@Override
	public LoginStaffDTO checkLogin(String mail, String password) {
		LoginData loginStaff = loginStaffRep.getByMail(mail);
		if (loginStaff.getPassword().equals(password)) {
			LoginStaffDTO dao = new LoginStaffDTO(loginStaff.getId(),
					loginStaff.getName(), loginStaff.getMail(),
					loginStaff.getPassword());
			return dao;
		} else {
			return null;
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LoginStaffDTO addInsLogin(String name, String mail, String pass,
			byte[] image) throws Exception {
		try {
			//if (mail.toLowerCase().startsWith("s-")) {
				//if (image != null) {
				//	Data data = studentRepository.getPersonByMail(mail)
					//		.getData();
				//	data.setStudentImage(image);
				//	dataRep.update(data);
				//} else {
				//	throw new Exception("Student Must Add Image");

				//}
			//}//
			LoginData loginData=new LoginData();
			loginData.setName(name);
			loginData.setMail(mail);
			loginData.setPassword(pass);
			int id = loginStaffRep.add(loginData);
			LoginStaffDTO dao = new LoginStaffDTO(id, loginStaffRep.getById(id)
					.getName(), loginStaffRep.getById(id).getMail(),
					loginStaffRep.getById(id).getPassword());
			return dao;
		} catch (ConstraintViolationException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/* *
	 * @see main.com.zc.shared.appService.ILoginAppService#checkInstuctors(java.lang.String)
	 */
	@Override
	public boolean checkInstuctors(String mail) { 
		try {
			Employee instructor = insRep.getByMail(mail);
			if (instructor != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public InstructorDTO getCourseInsByInsMail(String mail) {
		// TODO Auto-generated method stub
		Courses_Instructors courseIns = courseInsRep.getByInstructorMail(mail);
		InstructorDTO dao = new InstructorDTO(courseIns.getId(), courseIns
				.getInstructor().getId(), courseIns.getInstructor().getName(),
				courseIns.getCourse().getId(), courseIns.getCourse().getName());
		return dao;
	}

	@Override
	public LoginStaffDTO checkRegisteryOFMail(String mail) {
		try {
			LoginData login = loginStaffRep.getByMail(mail);
			LoginStaffDTO dao = new LoginStaffDTO(login.getId(),
					login.getName(), login.getMail(), login.getPassword());

			return dao;
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	@Override
	public boolean checkStudents(String mail) {
		try {
			Data student = dataRep.getByMail(mail);
			if (student != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public byte[] getStudentImage(String mail) {
		try {
			return studentRepository.getPersonByMail(mail).getData()
					.getStudentImage();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public byte[] saveImage(String mail,byte[] image)
	{
		try {
			if (image != null) {
				Data data = studentRepository.getPersonByMail(mail)
						.getData();
				data.setStudentImage(image);
					 dataRep.update(data);
				 return image;
			} else {
				throw new Exception("Student Must Add Image");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void changePassword(final String mail, final String newPass) {
	LoginData loginDatag = loginStaffRep.getByMail(mail);
	loginDatag.setPassword(newPass);
	loginDatag=loginStaffRep.update(loginDatag);
	try{
		Runnable t=new Runnable() {
			@Override
		public void run() {
			moodleIntegrationAppService.updateMoodleUserPassword(mail, newPass);
		}
	};
		t.run(); 
	}catch(Exception e){
		System.out.println(e);
	}
	}

	@Override
	public void changeAllPasswords() {
		List<LoginData> logins=new ArrayList<LoginData>();
		logins=loginStaffRep.getAll();
		try{
		for(int i=0;i<logins.size();i++)
		{
			System.out.println("done for "+logins.get(i).getMail());
			System.out.println("Old  "+logins.get(i).getPassword());
			/*String newPassword=new  Md5PasswordEncoder().encodePassword(logins.get(i).getPassword(), logins.get(i).getMail());
			*/logins.get(i).setPassword(logins.get(i).getPassword());
			loginStaffRep.update(logins.get(i));
			System.out.println("new : "+logins.get(i).getPassword());
			
			
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
				
		
	}
}
