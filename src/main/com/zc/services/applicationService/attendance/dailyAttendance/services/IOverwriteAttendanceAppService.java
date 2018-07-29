/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services;

import java.util.Calendar;

/**
 * @author omnya
 *
 */
public interface IOverwriteAttendanceAppService {

	
	public boolean deleteOldAttendance(Calendar cal);
	
}
