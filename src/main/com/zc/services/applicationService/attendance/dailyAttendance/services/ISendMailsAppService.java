/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface ISendMailsAppService {
public void sendMailsOfAttendanceToStudents(Calendar day);
public void sendMailsOfAttendanceToSpecialTypeOfStudents(Calendar day,String status);
public void sendMailsOfAttendanceToSomeStudent(Calendar day,List<Integer> excludedFileNumbers);
public boolean sendEditAttendanceTimesMailConformation(DailyAttDataDTO dto);
public boolean sendRefuseEditAtt(DailyAttDataDTO dto);
public boolean sendApproveEditAtt(DailyAttDataDTO dto);
public boolean sendCustomEditEmailToStudent(DailyAttDataDTO dto,String msg);
//public void sendMailsToAbsence(List<Time> times,Calendar day);
//public void sendEditAttendanceTimesMailConformation(Time times);
}
