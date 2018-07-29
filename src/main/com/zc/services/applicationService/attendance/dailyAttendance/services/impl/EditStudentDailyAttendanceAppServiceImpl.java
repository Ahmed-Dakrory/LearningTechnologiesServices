/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IEditStudentDailyAttendanceAppService;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
@Service
public class EditStudentDailyAttendanceAppServiceImpl implements IEditStudentDailyAttendanceAppService{

	@Autowired
	ITimeRepository timeRep;
	@Override
	public boolean editDailyAttendance(DailyAttDataDTO dto) {
		try
		{
		Time time=timeRep.getTimeByFileNoAndDate(dto.getPersonId(), dto.getDate());
		if(time.getAttendanceStatus().equals(Constants.ATTENDANCE_STATUS_ABSENCE))
		{
			time.setFirstIn(time.getExpectedIn());
			time.setFirstOut(time.getExpectedOut());
			time.setExcuse(dto.getExcuse());
			time.setExcuseStatus(dto.getExcuseStatus());
			timeRep.update(time);
		}
		else if(time.getAttendanceStatus().equals(Constants.ATTENDANCE_STATUS_SCANNED_ONCE))
		{
			time.setAttendanceStatus(dto.getStudentStatus());
			timeRep.update(time);
		}
		return true;
		}
		catch(Exception ex)
		{
		return false;
		}
	}
	@Override
	public boolean  petitionRequest(DailyAttDataDTO dto) {
		try
		{
		Time time=timeRep.getTimeByFileNoAndDate(dto.getPersonId(), dto.getDate());
		
		
			time.setExcuse(dto.getExcuse());
			time.setExcuseStatus(Constants.ATTENDANCE_STATUS_WAITING_RESPONSE);
			timeRep.update(time);
			return true;
		}
		
		
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}
	@Override
	public boolean refuseExcuse(DailyAttDataDTO dto) {
		try
		{
		Time time=timeRep.getTimeByFileNoAndDate(dto.getPersonId(), dto.getDate());
		
		
			
			time.setExcuseStatus(Constants.ATTENDANCE_STATUS_REFUSE_EXCUSE);
			timeRep.update(time);
			return true;
		}
		
		
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}
	@Override
	public boolean approveExcuse(DailyAttDataDTO dto) {
		try
		{
		Time time=timeRep.getTimeByFileNoAndDate(dto.getPersonId(), dto.getDate());
		
		
			
			time.setExcuseStatus(Constants.ATTENDANCE_STATUS_APPROVE_EXCUSE);
			timeRep.update(time);
			return true;
		}
		
		
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		return false;
		}
	}

}
