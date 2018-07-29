/**
 * 
 */
package main.com.zc.shared.appService;

import main.com.zc.shared.presentation.dto.LoginStaffDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface ILoginSecurityAppService {
	public LoginStaffDTO getUserByMail(String mail);
}
