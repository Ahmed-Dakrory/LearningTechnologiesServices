/**
 * 
 */
package main.com.zc.services.applicationService.attendance.petitions.service.impl;

import java.util.Calendar;

import main.com.zc.services.applicationService.attendance.petitions.service.IEditTimeOfDailyAttAppService;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;
import main.com.zc.services.presentation.attendance.shared.dto.TimeDailyAttDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 * 
 */

@Service
public class EditTimeOfDailyAttAppServiceImpl implements
		IEditTimeOfDailyAttAppService {

	@Autowired
	ITimeRepository timeRep;

	@Override
	public TimeDailyAttDTO editTimesExcusesToAttendance(DailyAttPetDto dao,
			Calendar date) {
		// Return the time by student file No and date
		try {
			Time time = timeRep.getTimeByFileNoAndDate(dao.getFileNo(), date);
            Calendar cal=Calendar.getInstance();
			time.setFirstIn(cal);
			time.setFirstOut(cal);
			time.setAttendanceStatus(dao.getExcuse());

			Time updatedTime = timeRep.update(time);
			TimeDailyAttDTO updatedDao = new TimeDailyAttDTO(
					updatedTime.getId(), updatedTime.getAttendanceStatus(),
					updatedTime.getDate(), updatedTime.getSendMailStatus());
			return updatedDao;
		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}
	}

	@Override
	public void editSendMailStatusToAttendance(DailyAttPetDto dao,
			Calendar date) {
		// TODO Auto-generated method stub
		try {
			Time time = timeRep.getTimeByFileNoAndDate(dao.getFileNo(), date);
			time.setSendMailStatus("Edit Mail Of Attendance Was Sent");

			Time updatedTime = timeRep.update(time);
			TimeDailyAttDTO updatedDao = new TimeDailyAttDTO(
					updatedTime.getId(), updatedTime.getAttendanceStatus(),
					updatedTime.getDate(), updatedTime.getSendMailStatus());
			
		} catch (Exception e) {

			e.printStackTrace();
			

		}
	}

}
