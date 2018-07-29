/**
 * 
 */
package main.com.zc.services.applicationService.accountSetting.exceptions;

/**
 * @author heba
 * @since Mar 10, 2015
 * @lastUpdated  Mar 10, 2015
 */
public class ChangePasswordException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ChangePasswordException(String message) {
		super(message);
	}

	public ChangePasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChangePasswordException(Throwable cause) {
		super(cause);
	}
}
