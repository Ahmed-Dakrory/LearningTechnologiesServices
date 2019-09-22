/**
 * 
 */
package main.com.zc.services.applicationService.accountSetting.impl;

import main.com.zc.services.applicationService.accountSetting.IAccountSettingAppService;
import main.com.zc.services.applicationService.accountSetting.exceptions.ChangePasswordException;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.accountSetting.dto.AccountSettingDTO;
import main.com.zc.shared.appService.ILoginAppService;
import main.com.zc.shared.appService.ILoginSecurityAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AccountSettingAppServiceImpl implements IAccountSettingAppService {

	@Autowired
	IEmployeeRepository instructorRepository;
	@Autowired 
	IStudentRepository   studentRepository;
	@Autowired
	ILoginSecurityAppService loginSecAppService;
	@Autowired
	private ILoginAppService loginAppService;

	@Override
	public AccountSettingDTO getCurrentInstructor()  {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String mail =((User) authentication.getPrincipal())
				.getUsername();
		if(mail.equals(Constants.LTS_SYSTEM_ADMIN)){
			AccountSettingDTO accountSettingDTO = new AccountSettingDTO();
			accountSettingDTO.setInstructorAccount(false);
			return accountSettingDTO;
			}
		else if(!(mail.toLowerCase().startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))))
		{
		Employee instructor = instructorRepository
				.getByMail(mail);
		AccountSettingDTO accountSettingDTO = new AccountSettingDTO();
		accountSettingDTO.setEveryDay(instructor.getMailSetting().getId());
		accountSettingDTO.setId(instructor.getMailSetting().getId());
		accountSettingDTO.setInstructorId(instructor.getId());
		accountSettingDTO
				.setNotifyMe(instructor.getMailSetting().getNotifyMe());
		accountSettingDTO.setInstructorAccount(true);
		return accountSettingDTO;
	}else{
		Student student = studentRepository
				.getPersonByMail(mail);
		AccountSettingDTO accountSettingDTO = new AccountSettingDTO();
		accountSettingDTO.setInstructorId(student.getId());
		accountSettingDTO.setInstructorAccount(false);
		return accountSettingDTO;
		}	
	}

	@Override
	public void save(AccountSettingDTO accountSettingDTO) {

		Employee instructor = instructorRepository.getById(accountSettingDTO
				.getInstructorId());
		instructor.getMailSetting()
				.setNotifyMe(accountSettingDTO.getNotifyMe());
		if (accountSettingDTO.getNotifyMe()) {
			instructor.getMailSetting().setEveryDays(
					accountSettingDTO.getEveryDay());
		} else {
			instructor.getMailSetting().setEveryDays(0);
		}
		instructorRepository.update(instructor);
	}

	@Override
	public void changePassword(AccountSettingDTO accountSettingDTO)
			throws ChangePasswordException {
		String mail =((User)  SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal())
				.getUsername();
		LoginStaffDTO dao = loginSecAppService.getUserByMail(mail);
		
		if(!dao.getPassword().equals(new Md5PasswordEncoder().encodePassword(accountSettingDTO.getOldPassword(), dao.getMail())))
		 {
			throw new ChangePasswordException("Current password is incorrect, please retype your current password");
		}
		if(!accountSettingDTO.getNewPassword().equals(accountSettingDTO.getConfirmPassword()))
		 {
			throw new ChangePasswordException("Confirm Password doesn't match New Password !");
		}
		loginAppService.changePassword(mail, accountSettingDTO.getNewPassword());
	}}