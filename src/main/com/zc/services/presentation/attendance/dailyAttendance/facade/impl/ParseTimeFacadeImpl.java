/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.facade.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IParseTimeAppService;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IParseTimeFacade;

/**
 * @author omnya
 *
 */
@Service("parseTimeFacadeImpl")
public class ParseTimeFacadeImpl implements IParseTimeFacade{

	@Autowired
	IParseTimeAppService parseDailyTimeAppService;
	
	@Override
	public List<DailyAttDataDTO> parse(InputStream input) throws IOException,
			ConventionProblem {
		
		return parseDailyTimeAppService.parse(input);
	}

	@Override
	public List<DailyAttDataDTO> newParse(InputStream input) throws IOException,
			ConventionProblem {
		// TODO Auto-generated method stub
		 	return parseDailyTimeAppService.newParse(input);
	}

}
