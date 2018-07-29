/**
 * 
 */
package main.com.zc.services.presentation.accountSetting.facade.impl;

import main.com.zc.services.applicationService.accountSetting.IAccountSettingAppService;
import main.com.zc.services.applicationService.accountSetting.exceptions.ChangePasswordException;
import main.com.zc.services.presentation.accountSetting.dto.AccountSettingDTO;
import main.com.zc.services.presentation.accountSetting.facade.IAccountSettingFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("accountSettingFacadeImpl")
public class AccountSettingFacadeImpl implements IAccountSettingFacade {

	@Autowired
	IAccountSettingAppService accountSettingAppService;

	@Override
	public AccountSettingDTO getAccountSetting()  {
		return accountSettingAppService.getCurrentInstructor();
	}

	@Override
	public void save(AccountSettingDTO accountSettingDTO) {
		 accountSettingAppService.save(accountSettingDTO);
			
	}

	@Override
	public void changePassword(AccountSettingDTO accountSettingDTO) throws ChangePasswordException
			{
		 accountSettingAppService.changePassword(accountSettingDTO);
			
	}
	
	
	
}