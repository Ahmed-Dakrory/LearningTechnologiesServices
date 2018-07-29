/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IOverwriteAttendanceAppService;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;

/**
 * @author omnya
 *
 */
@Service
public class OverwriteAttendanceAppServiceImpl implements IOverwriteAttendanceAppService {

	@Autowired
	ITimeRepository timeRep;
	@Override
	public boolean deleteOldAttendance(Calendar cal) {
		try
		{
	List< Time> times=timeRep.getAllByAttendanceDate(cal);
	boolean deleted=true;
	for(int i=0;i<times.size();i++)
	{
		deleted=deleted&&timeRep.remove(times.get(i).getId());
	}
	
		return deleted;
	}

catch(Exception ex)
		{
	ex.printStackTrace();
	return false;
	}
		}
}
