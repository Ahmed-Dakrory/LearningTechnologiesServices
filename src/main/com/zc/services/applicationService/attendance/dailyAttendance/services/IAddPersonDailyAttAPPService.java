/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services;

import java.util.List;

import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
public interface IAddPersonDailyAttAPPService {
	public int addStudentData(List<DailyAttDataDTO> list); // return the number of missed students
	
	public DailyAttDataDTO getStudentData(DailyAttDataDTO dto);
	public List<DailyAttDataDTO> getAllStudentData();
}
