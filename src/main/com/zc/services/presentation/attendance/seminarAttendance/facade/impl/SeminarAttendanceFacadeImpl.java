/**
 * 
 */
package main.com.zc.services.presentation.attendance.seminarAttendance.facade.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.seminarAttendance.service.IAddSeminarTimesToDBAPPService;
import main.com.zc.services.applicationService.attendance.seminarAttendance.service.IParseSeminarAttendanceFromSystemAppService;
import main.com.zc.services.applicationService.attendance.seminarAttendance.service.ISendSeminarAttendanceMailsAPPService;
import main.com.zc.services.presentation.attendance.seminarAttendance.dto.SeminarAttendanceDTO;
import main.com.zc.services.presentation.attendance.seminarAttendance.facade.ISeminarAttendanceFacade;

/**
 * @author Omnya Alaa
 *
 */
@Service("seminarAttendanceFacadeImpl")
public class SeminarAttendanceFacadeImpl implements ISeminarAttendanceFacade {

	@Autowired
	IParseSeminarAttendanceFromSystemAppService parseSeminarAppService;
	@Autowired
	IAddSeminarTimesToDBAPPService addSeminarTimesToDBAppSer;
	@Override
	public void addDataToDB(String filePath , Calendar cal) {
		List<SeminarAttendanceDTO> attendedStudents=parseSeminarAppService.parseSeminarAtt(filePath);
		int addItems=addSeminarTimesToDBAppSer.addSeminarTimes(attendedStudents, cal);
		System.out.println(" Done adding of "+addItems+" To DB");
		
	}

}
