/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IGetDailyAttAppService;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.domain.time.service.business.IGetMailsOFAttStatusDomainService;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;

/**
 * @author omnya
 *
 */
@Service
public class GetDailyAttAppServiceImpl implements IGetDailyAttAppService{

	@Autowired
	IGetMailsOFAttStatusDomainService getDailyAttInfoAppService;
	
	@Autowired
	ITimeRepository timeRep;
	@Override
	public List<DailyAttDataDTO> getTimesListByAttStatusAndDate(String status,
			Calendar date) {
		List<Time> times=getDailyAttInfoAppService.getTimesListByAttStatusAndDate(status, date);
		List<DailyAttDataDTO> dailyAttDtos=new ArrayList<DailyAttDataDTO>();
		try 
		{
		for(int i=0;i<times.size();i++)
		{
			
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setDate(times.get(i).getDate());
			dto.setExpected_in(times.get(i).getExpectedIn());
			dto.setExpected_out(times.get(i).getExpectedOut());
			dto.setFirst_in(times.get(i).getFirstIn());
			dto.setLast_out(times.get(i).getFirstOut());
			dto.setPersonId(times.get(i).getPerson().getFileNo());
			dto.setStudentName(times.get(i).getPerson().getData().getNameInEnglish());
			dto.setStudentStatus(times.get(i).getAttendanceStatus());
			dto.setSentEmailStatus(times.get(i).getSendMailStatus());
			dailyAttDtos.add(dto);
			
			
		}
		return dailyAttDtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<DailyAttDataDTO> getTimesListByDate(Calendar date) {
	
		List<Time> times=getDailyAttInfoAppService.getTimesListByDate(date);
		List<DailyAttDataDTO> dailyAttDtos=new ArrayList<DailyAttDataDTO>();
		try 
		{
		for(int i=0;i<times.size();i++)
		{
			
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setDate(times.get(i).getDate());
			dto.setExpected_in(times.get(i).getExpectedIn());
			dto.setExpected_out(times.get(i).getExpectedOut());
			dto.setFirst_in(times.get(i).getFirstIn());
			dto.setLast_out(times.get(i).getFirstOut());
			dto.setPersonId(times.get(i).getPerson().getFileNo());
			dto.setStudentName(times.get(i).getPerson().getData().getNameInEnglish());
			dto.setStudentStatus(times.get(i).getAttendanceStatus());
			dto.setSentEmailStatus(times.get(i).getSendMailStatus());
			dto.setExcuse(times.get(i).getExcuse());
			dto.setExcuseStatus(times.get(i).getExcuseStatus());
			dailyAttDtos.add(dto);
			
			
		}
		return dailyAttDtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<DailyAttDataDTO> getStudentTimesListByFileNo(int fileNo) {
		List<Time> times=timeRep.getAllAttendanceByFileNo(fileNo);
		List<DailyAttDataDTO> dailyAttDtos=new ArrayList<DailyAttDataDTO>();
		try 
		{
		for(int i=0;i<times.size();i++)
		{
			
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setDate(times.get(i).getDate());
			dto.setExpected_in(times.get(i).getExpectedIn());
			dto.setExpected_out(times.get(i).getExpectedOut());
			dto.setFirst_in(times.get(i).getFirstIn());
			dto.setLast_out(times.get(i).getFirstOut());
			dto.setPersonId(times.get(i).getPerson().getFileNo());
			dto.setStudentName(times.get(i).getPerson().getData().getNameInEnglish());
			dto.setStudentStatus(times.get(i).getAttendanceStatus());		
			try
			{
				if(times.get(i).getExcuseStatus().equals(Constants.ATTENDANCE_STATUS_APPROVE_EXCUSE))
					dto.setStudentStatus("Edited to be attended");
				else
					dto.setStudentStatus(times.get(i).getAttendanceStatus());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
			
			dto.setSentEmailStatus(times.get(i).getSendMailStatus());
			dailyAttDtos.add(dto);
			
			
		}
		return dailyAttDtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public DailyAttDataDTO getStudentAttByFileNoAndDate(int fileNo,
			Calendar date) {
		try{
			Time time=timeRep.getAllByAttendanceFileNoAndDate(date, fileNo);
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setDate(time.getDate());
			dto.setExpected_in(time.getExpectedIn());
			dto.setExpected_out(time.getExpectedOut());
			dto.setFirst_in(time.getFirstIn());
			dto.setLast_out(time.getFirstOut());
			dto.setPersonId(time.getPerson().getFileNo());
			dto.setStudentName(time.getPerson().getData().getNameInEnglish());
			dto.setStudentStatus(time.getAttendanceStatus());
			dto.setSentEmailStatus(time.getSendMailStatus());
			
			return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<DailyAttDataDTO> getStudentsWithPetitions(String status) {
		List<Time> times=timeRep.getAllByStatus(status);
		List<DailyAttDataDTO> dailyAttDtos=new ArrayList<DailyAttDataDTO>();
		try 
		{
		for(int i=0;i<times.size();i++)
		{
			
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setDate(times.get(i).getDate());
			dto.setExpected_in(times.get(i).getExpectedIn());
			dto.setExpected_out(times.get(i).getExpectedOut());
			dto.setFirst_in(times.get(i).getFirstIn());
			dto.setLast_out(times.get(i).getFirstOut());
			dto.setPersonId(times.get(i).getPerson().getFileNo());
			dto.setStudentName(times.get(i).getPerson().getData().getNameInEnglish());
			dto.setStudentStatus(times.get(i).getAttendanceStatus());
			dto.setSentEmailStatus(times.get(i).getSendMailStatus());
			dto.setExcuse(times.get(i).getExcuse());
			dailyAttDtos.add(dto);
			
			
		}
		return dailyAttDtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<DailyAttDataDTO> getStudentsByExcuseStatus(String status) {
		List<Time> times=timeRep.getAllByExcuseStatus(status);
		List<DailyAttDataDTO> dailyAttDtos=new ArrayList<DailyAttDataDTO>();
		try 
		{
		for(int i=0;i<times.size();i++)
		{
			
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setDate(times.get(i).getDate());
			dto.setExpected_in(times.get(i).getExpectedIn());
			dto.setExpected_out(times.get(i).getExpectedOut());
			dto.setFirst_in(times.get(i).getFirstIn());
			dto.setLast_out(times.get(i).getFirstOut());
			dto.setPersonId(times.get(i).getPerson().getFileNo());
			dto.setStudentName(times.get(i).getPerson().getData().getNameInEnglish());
			dto.setStudentStatus(times.get(i).getAttendanceStatus());
			dto.setSentEmailStatus(times.get(i).getSendMailStatus());
			dto.setExcuse(times.get(i).getExcuse());
			dto.setExcuseStatus(times.get(i).getExcuseStatus());
			dailyAttDtos.add(dto);
			
			
		}
		return dailyAttDtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	
}
