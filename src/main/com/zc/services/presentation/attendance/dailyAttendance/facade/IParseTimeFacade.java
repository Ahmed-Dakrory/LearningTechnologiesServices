/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.facade;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
public interface IParseTimeFacade {
	public List<DailyAttDataDTO> parse(InputStream input) throws IOException,
	ConventionProblem ;
	
	public List<DailyAttDataDTO> newParse(InputStream input) throws IOException,
	ConventionProblem ;
}
