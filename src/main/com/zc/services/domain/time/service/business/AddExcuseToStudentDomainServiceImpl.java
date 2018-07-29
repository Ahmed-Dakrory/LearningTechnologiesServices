/**
 * 
 */
package main.com.zc.services.domain.time.service.business;

import java.util.Calendar;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.time.model.ISeminarTimesRepository;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.SeminarTimes;
import main.com.zc.services.domain.time.model.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Omnya Alaa
 *
 */
@Service
public class AddExcuseToStudentDomainServiceImpl implements IAddExcuseToStudentDomainService {

	
	@Autowired
	ITimeRepository timeRep;
	@Autowired
	ISeminarTimesRepository seminarRep;
	@Override
	public Time addExcuseToStudentInAttendance(Time time) {
		time.setFirstIn(time.getExpectedIn());
		time.setFirstOut(time.getExpectedOut());
		time.setAttendanceStatus(Constants.ATTENDANCE_STATUS_EXCUSE);
		
		return timeRep.update(time);
	}

	@Override
	public SeminarTimes addExcuseToStudentInSeminar(SeminarTimes time) {
		Calendar cal=Calendar.getInstance();
		
		time.setAttendTime(cal);
		time.setAttendanceStatus(Constants.ATTENDANCE_STATUS_EXCUSE);
		return seminarRep.update(time);
	}

}
