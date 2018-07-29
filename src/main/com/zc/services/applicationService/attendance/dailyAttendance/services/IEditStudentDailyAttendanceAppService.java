/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services;

import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
public interface IEditStudentDailyAttendanceAppService {

	public boolean editDailyAttendance(DailyAttDataDTO dto);
	public boolean petitionRequest(DailyAttDataDTO dto);
	
	public boolean refuseExcuse(DailyAttDataDTO dto);
	public boolean approveExcuse(DailyAttDataDTO dto);
}
