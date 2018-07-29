/**
 * 
 */
package main.com.zc.services.presentation.forms.emails.service;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ICheckNewMails {

	void notifyNextStepOwner(List<String> recipMAil, String name, String content,String pettionTitle);
}
