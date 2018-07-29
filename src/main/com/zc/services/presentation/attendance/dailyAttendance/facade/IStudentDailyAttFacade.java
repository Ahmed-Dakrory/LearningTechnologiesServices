/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.facade;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
public interface IStudentDailyAttFacade {

	public List<DailyAttDataDTO>getStudentAttByFileNo(int fileNO); 
	public DailyAttDataDTO getStudentAttByDate(int fileNo,Calendar cal);
	public boolean petitionRequest(DailyAttDataDTO dto);
	public List<DailyAttDataDTO> getPetitions(String status);
}
