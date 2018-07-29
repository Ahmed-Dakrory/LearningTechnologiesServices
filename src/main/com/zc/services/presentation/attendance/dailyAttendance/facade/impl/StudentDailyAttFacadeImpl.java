/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.facade.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IEditStudentDailyAttendanceAppService;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IGetDailyAttAppService;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IStudentDailyAttFacade;

/**
 * @author omnya
 *
 */
@Service("StudentDailyAttFacadeImpl")
public class StudentDailyAttFacadeImpl implements IStudentDailyAttFacade{

	@Autowired 
	IGetDailyAttAppService dailyAttAppService;
	@Autowired
	IEditStudentDailyAttendanceAppService editStudentAtt;
	@Override
	public List<DailyAttDataDTO> getStudentAttByFileNo(int fileNO) {
		
		List<DailyAttDataDTO> timeList = dailyAttAppService.getStudentTimesListByFileNo(fileNO);
		return timeList;
	}

	@Override
	public DailyAttDataDTO getStudentAttByDate(int fileNo,Calendar cal) {
		DailyAttDataDTO dto=dailyAttAppService.getStudentAttByFileNoAndDate(fileNo, cal);
		
		return dto;
	}

	@Override
	public boolean  petitionRequest(DailyAttDataDTO dto) {
		
		return editStudentAtt.petitionRequest(dto);
	}

	@Override
	public List<DailyAttDataDTO> getPetitions(String status) {
		// TODO Auto-generated method stub
		return dailyAttAppService.getStudentsWithPetitions(status);
	}

}
