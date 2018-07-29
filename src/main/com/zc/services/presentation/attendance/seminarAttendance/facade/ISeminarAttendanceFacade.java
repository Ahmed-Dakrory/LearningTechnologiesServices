/**
 * 
 */
package main.com.zc.services.presentation.attendance.seminarAttendance.facade;

import java.util.Calendar;


/**
 * @author Omnya Alaa
 *
 */
public interface ISeminarAttendanceFacade {

	public void addDataToDB(String filePath, Calendar cal);
}
