/**
 * 
 */
package main.com.zc.shared.appService.impl;


import main.com.zc.services.domain.person.model.ILoginDataRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.LoginData;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.shared.appService.ILoginSecurityAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Omnya Alaa
 *
 */
@Service("loginSecurityAppServiceImpl")
public class LoginSecurityAppServiceImpl implements ILoginSecurityAppService {

	@Autowired
	ILoginDataRepository loginStaffRep;
	@Autowired
	IStudentRepository perRep;
	@Override
	public LoginStaffDTO getUserByMail(String mail) {
	
		try{
		LoginData loginStaff=loginStaffRep.getByMail(mail);
		LoginStaffDTO dao=new LoginStaffDTO(loginStaff.getId(),
				loginStaff.getName(),loginStaff.getMail(), loginStaff.getPassword());
		if(loginStaff.getMail().startsWith("S-")||loginStaff.getMail().startsWith("s-"))
		{
		Student per=perRep.getPersonByMail(loginStaff.getMail());
		dao.setFileNo(per.getFileNo());
		}
		return dao;
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		return null;
	}
	}

}
