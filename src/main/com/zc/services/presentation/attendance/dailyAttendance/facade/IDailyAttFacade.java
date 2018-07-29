/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.facade;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
public interface IDailyAttFacade {
	public int addStudentData(List<DailyAttDataDTO> list);
	public DailyAttDataDTO getStudentData(DailyAttDataDTO dto);
	public List<DailyAttDataDTO> getAllStudentData();
	public boolean sendMailsOfAttendanceToStudents(Calendar cal);
	public boolean sendMailsOfAttendanceToSpecialTypeOfStudents(Calendar day,String status);
	public List<DailyAttDataDTO> getTimesListByAttStatusAndDate(String status,Calendar date);
	public List<DailyAttDataDTO> getTimesListByDate(Calendar date);
	public boolean editDailyAttendance(DailyAttDataDTO dto);
	public boolean deleteOldAttendance(Calendar cal);
	public boolean sendEditAttendanceTimesMailConformation(DailyAttDataDTO dto);
	public List<DailyAttDataDTO> getAllStudentByStatus(String status);
	public List<DailyAttDataDTO>getStudentsByExcuseStatus(String status);
	public boolean refuseEditAttendance(DailyAttDataDTO dto);
	public boolean approveEditAttendance(DailyAttDataDTO dto);
	public boolean sendRefuseEditMail(DailyAttDataDTO dto);
	public boolean sendApproveEditMail(DailyAttDataDTO dto);
	public boolean sendCustomEditMail(DailyAttDataDTO dto, String msg);
}
