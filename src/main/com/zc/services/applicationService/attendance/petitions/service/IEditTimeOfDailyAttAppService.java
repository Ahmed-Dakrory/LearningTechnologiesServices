package main.com.zc.services.applicationService.attendance.petitions.service;

import java.util.Calendar;

import main.com.zc.services.presentation.attendance.petitions.dto.DailyAttPetDto;
import main.com.zc.services.presentation.attendance.shared.dto.TimeDailyAttDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface IEditTimeOfDailyAttAppService {
	public TimeDailyAttDTO editTimesExcusesToAttendance(DailyAttPetDto dao,Calendar date);
	public void editSendMailStatusToAttendance(DailyAttPetDto dao,Calendar date);
}
