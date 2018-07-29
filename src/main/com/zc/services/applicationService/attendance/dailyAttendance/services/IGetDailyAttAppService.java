/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
public interface IGetDailyAttAppService {

	public List<DailyAttDataDTO> getTimesListByAttStatusAndDate(String status,Calendar date);
	public List<DailyAttDataDTO> getTimesListByDate(Calendar date);
	public List<DailyAttDataDTO> getStudentTimesListByFileNo(int fileNo);
	public DailyAttDataDTO getStudentAttByFileNoAndDate(int fileNo,Calendar date);
	public List<DailyAttDataDTO> getStudentsWithPetitions(String status);
	public List<DailyAttDataDTO> getStudentsByExcuseStatus(String status);
	//public List<DailyAttDataDTO> getMailListByListOfTimes(List<Time> timesList);
}
