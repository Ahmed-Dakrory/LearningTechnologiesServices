/**
 * 
 */
package main.com.zc.services.presentation.forms.shared.facade;

import java.util.ArrayList;

/**
 * @author Omnya Alaa
 *
 */
public interface ISendEmailFacade {

	public boolean sendEmail(String senderName, String senderEmail, String content ,String title);
	public boolean sendMail(ArrayList<String> emailLstTO,ArrayList<String> emailLstCC,ArrayList<String> emailLstBCC, String content, String subject) ;
}
