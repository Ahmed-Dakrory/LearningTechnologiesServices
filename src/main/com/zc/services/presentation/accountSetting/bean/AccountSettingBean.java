/**
 * 
 */
package main.com.zc.services.presentation.accountSetting.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.accountSetting.exceptions.ChangePasswordException;
import main.com.zc.services.presentation.accountSetting.dto.AccountSettingDTO;
import main.com.zc.services.presentation.accountSetting.facade.IAccountSettingFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

@ManagedBean
@ViewScoped
public class AccountSettingBean {
	@ManagedProperty("#{accountSettingFacadeImpl}")
	private IAccountSettingFacade accountSettingFacade;
	private AccountSettingDTO accountSettingDTO;

	@PostConstruct
	public void init() {
		
			accountSettingDTO = accountSettingFacade.getAccountSetting();
			 StringBuffer url = (StringBuffer) ((HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest())
						.getRequestURL();
			if(url.toString().contains("mailNotificationSetting.xhtml")&& !accountSettingDTO.getInstructorAccount())
			{
				accountSettingDTO=null;
			JavaScriptMessagesHandler
				.RegisterErrorMessage(null, "Not Allowed ");
			}
		
	}

	public void save() {
		try {
			accountSettingFacade.save(accountSettingDTO);//
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,
					"Mail Setting saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
			JavaScriptMessagesHandler
					.RegisterErrorMessage(null, "System Error");
		}
	}

	public String savePassword(){
		try {
			if (! this.accountSettingDTO.getNewPassword().equals(this.accountSettingDTO.getConfirmPassword())) {
				JavaScriptMessagesHandler.RegisterErrorMessage("",
						"Confirm Password doesn't match New Password !");
				return "";
			} else {
						accountSettingFacade.changePassword(accountSettingDTO);
				
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,
						"Password Changed Successfully");
				return "";
			}
		
	} catch (ChangePasswordException e) {
		e.printStackTrace();
		JavaScriptMessagesHandler.RegisterErrorMessage("",e.getMessage());
		return "";
	}
	catch (Exception ex) {
		ex.printStackTrace();
		JavaScriptMessagesHandler.RegisterErrorMessage("","System Error !");
		return "";
	}}
	public IAccountSettingFacade getAccountSettingFacade() {
		return accountSettingFacade;
	}

	public void setAccountSettingFacade(
			IAccountSettingFacade accountSettingFacade) {
		this.accountSettingFacade = accountSettingFacade;
	}

	public AccountSettingDTO getAccountSettingDTO() {
		return accountSettingDTO;
	}

	public void setAccountSettingDTO(AccountSettingDTO accountSettingDTO) {
		this.accountSettingDTO = accountSettingDTO;
	}

}
