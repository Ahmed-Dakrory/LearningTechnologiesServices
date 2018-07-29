/**
 * 
 */
package main.com.zc.services.applicationService.shared.service;

import java.util.ArrayList;

/**
 * @author omnya
 *
 */
public interface ISendEmailAppService {
	public boolean sendMail(ArrayList<String> emailLstTO,ArrayList<String> emailLstCC,ArrayList<String> emailLstBCC, String content, String subject) ;
}
