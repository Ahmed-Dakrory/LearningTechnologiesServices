package main.com.zc.services.applicationService.accountSetting;

import main.com.zc.services.applicationService.accountSetting.exceptions.ChangePasswordException;
import main.com.zc.services.presentation.accountSetting.dto.AccountSettingDTO;

public interface IAccountSettingAppService {
	
	public  AccountSettingDTO getCurrentInstructor() ;

	public void save(AccountSettingDTO accountSettingDTO);

	public void changePassword(AccountSettingDTO accountSettingDTO)throws ChangePasswordException;

}
