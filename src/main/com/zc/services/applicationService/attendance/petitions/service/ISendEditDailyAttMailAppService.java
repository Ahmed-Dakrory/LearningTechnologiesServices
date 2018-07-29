/**
 * 
 */
package main.com.zc.services.applicationService.attendance.petitions.service;

import java.util.Calendar;

/**
 * @author Omnya Alaa
 *
 */
public interface ISendEditDailyAttMailAppService {

	public boolean sendMailByEditedAtt(String mail,Calendar cal);
	public boolean sendMailByEditedAttTest(String mail,Calendar cal,int fileNo);
}
