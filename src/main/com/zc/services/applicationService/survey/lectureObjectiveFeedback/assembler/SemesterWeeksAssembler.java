/**
 * 
 */
package main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.assembler;

import java.util.Calendar;
import java.util.Date;

import main.com.zc.services.domain.lectureObjectiveFeedback.model.SemesterWeeks;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;

/**
 * @author omnya
 *
 */
public class SemesterWeeksAssembler {
	public SemesterWeeksDTO toDTO(SemesterWeeks week)
	{
		SemesterWeeksDTO dto=new SemesterWeeksDTO();
		dto.setId(week.getId());
		Date tempStart=week.getStartDate().getTime();
		Date tempEnd=week.getEndDate().getTime();
		dto.setStartDate(tempStart);
		dto.setEndDate(tempEnd);
		dto.setName(week.getName());
		dto.setSemester(week.getSemester());
		dto.setYear(week.getYear());
		dto.setActive(week.getActive());
		return dto;
	}
	public SemesterWeeks toEntity(SemesterWeeksDTO dto)
	{
		SemesterWeeks week=new SemesterWeeks();
		week.setId(dto.getId());
		Calendar tempStart=Calendar.getInstance();
		Calendar tempEnd=Calendar.getInstance();
		tempStart.setTime(dto.getStartDate());
		tempEnd.setTime(dto.getEndDate());
		
		week.setEndDate(tempEnd);
		week.setStartDate(tempStart);
		week.setName(dto.getName());
		week.setSemester(dto.getSemester());
		week.setYear(dto.getYear());
		week.setActive(dto.getActive());
		return week;
	}
}
