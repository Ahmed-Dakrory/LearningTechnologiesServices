/**
 * 
 */
package main.com.zc.services.presentation.attendance.LectureAttenance.facade;

import java.util.List;

import main.com.zc.services.applicationService.attendance.dailyAttendance.exceptions.ConventionProblem;
import main.com.zc.services.presentation.attendance.LectureAttenance.dto.LectureAttendanceDTO;

import org.primefaces.model.UploadedFile;

/**
 * @author omnya
 *
 */
public interface IParseLectureAttendanceFacade {

	public List<LectureAttendanceDTO> parse(UploadedFile file) throws ConventionProblem;
}
