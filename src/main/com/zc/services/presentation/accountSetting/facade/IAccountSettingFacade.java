package main.com.zc.services.presentation.accountSetting.facade;

import main.com.zc.services.applicationService.accountSetting.exceptions.ChangePasswordException;
import main.com.zc.services.presentation.accountSetting.dto.AccountSettingDTO;

public interface IAccountSettingFacade {

	AccountSettingDTO getAccountSetting();

	void save(AccountSettingDTO accountSettingDTO);

	void changePassword(AccountSettingDTO accountSettingDTO) throws ChangePasswordException;
}
