/**
 * 
 */
package main.com.zc.services.applicationService.attendance.seminarAttendance.service;

import java.util.List;

import main.com.zc.services.presentation.attendance.seminarAttendance.dto.SeminarAttendanceDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IParseSeminarAttendanceFromSystemAppService {

	public List<SeminarAttendanceDTO> parseSeminarAtt(String fileName);
}
