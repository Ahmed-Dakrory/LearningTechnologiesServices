/**
 * 
 */
package main.com.zc.services.applicationService.attendance.seminarAttendance.service;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.presentation.attendance.seminarAttendance.dto.SeminarAttendanceDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IAddSeminarTimesToDBAPPService {

	public int addSeminarTimes(List<SeminarAttendanceDTO> attendedStudents,Calendar cal);
}
