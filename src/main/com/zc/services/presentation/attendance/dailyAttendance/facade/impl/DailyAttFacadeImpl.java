/**
 * 
 */
package main.com.zc.services.presentation.attendance.dailyAttendance.facade.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IAddPersonDailyAttAPPService;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IEditStudentDailyAttendanceAppService;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IGetDailyAttAppService;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IOverwriteAttendanceAppService;
import main.com.zc.services.applicationService.attendance.dailyAttendance.services.ISendMailsAppService;
import main.com.zc.services.applicationService.attendance.seminarAttendance.service.ISendSeminarAttendanceMailsAPPService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IDailyAttFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("dailyAttFacadeImpl")
public class DailyAttFacadeImpl implements IDailyAttFacade{

	@Autowired
	IAddPersonDailyAttAPPService savePersonDailyAttAppService;
	@Autowired
	IOverwriteAttendanceAppService overwriteAppService;
	@Autowired
	IStudentRepository personRep;
	@Autowired
	ISendMailsAppService sendEmails;
	@Autowired
	IGetDailyAttAppService getDailyAttInfo;
	@Autowired
	IEditStudentDailyAttendanceAppService editDailyAtt;
	@Override
	public int addStudentData(List<DailyAttDataDTO> list) {
		
		return savePersonDailyAttAppService.addStudentData(list);
	}
	@Override
	public DailyAttDataDTO getStudentData(DailyAttDataDTO dto) {
		
		return savePersonDailyAttAppService.getStudentData(dto);
	}
	@Override
	public List<DailyAttDataDTO> getAllStudentData() 
	{
		
		try 
		{
		List<DailyAttDataDTO> allStudents=new ArrayList<DailyAttDataDTO>();
		List<Student> studentsLst=personRep.getAll();
		
		for(int i=0;i<studentsLst.size();i++)
		{
			DailyAttDataDTO dto=new DailyAttDataDTO();
			dto.setPersonId(studentsLst.get(i).getFileNo());
			dto.setStudentName(studentsLst.get(i).getData().getNameInEnglish());
			dto.setFirst_in(null);
			dto.setLast_out(null);
			allStudents.add(dto);
		}
		
		
		return allStudents;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean  sendMailsOfAttendanceToStudents(Calendar cal) {
		try
		{
			sendEmails.sendMailsOfAttendanceToStudents(cal);
			return true;
		}
		catch(RuntimeException ex)
		{
			ex.printStackTrace();
			return false;
		}

	}
	@Override
	public List<DailyAttDataDTO> getTimesListByAttStatusAndDate(String status,
			Calendar date) {
		
		return getDailyAttInfo.getTimesListByAttStatusAndDate(status, date);
	}
	@Override
	public boolean sendMailsOfAttendanceToSpecialTypeOfStudents(Calendar day,
			String status) {
		try
		{
			sendEmails.sendMailsOfAttendanceToSpecialTypeOfStudents(day, status);
			return true;
		}
		catch(RuntimeException ex)
		{
			ex.printStackTrace();
			return false;
		}

		
	}
	@Override
	public List<DailyAttDataDTO> getTimesListByDate(Calendar date) {
		return getDailyAttInfo.getTimesListByDate(date);
	}
	@Override
	public boolean editDailyAttendance(DailyAttDataDTO dto) {
		return editDailyAtt.editDailyAttendance(dto);
		
	}
	@Override
	public boolean deleteOldAttendance(Calendar cal) {
		
		return overwriteAppService.deleteOldAttendance(cal);
	}
	@Override
	public boolean  sendEditAttendanceTimesMailConformation(DailyAttDataDTO dto) {
		return sendEmails.sendEditAttendanceTimesMailConformation(dto);
		
	}
	@Override
	public List<DailyAttDataDTO> getAllStudentByStatus(String status) {
		List<DailyAttDataDTO> attList=getDailyAttInfo.getStudentsWithPetitions(status);
		return attList;
	}
	@Override
	public List<DailyAttDataDTO> getStudentsByExcuseStatus(String status) {
		// TODO Auto-generated method stub
		return getDailyAttInfo.getStudentsByExcuseStatus(status);
	}
	@Override
	public boolean refuseEditAttendance(DailyAttDataDTO dto) {
		return editDailyAtt.refuseExcuse(dto);
		
	}
	@Override
	public boolean sendRefuseEditMail(DailyAttDataDTO dto) {
		
		return sendEmails.sendRefuseEditAtt(dto);
	}
	@Override
	public boolean approveEditAttendance(DailyAttDataDTO dto) {
		return editDailyAtt.approveExcuse(dto);
	}
	@Override
	public boolean sendApproveEditMail(DailyAttDataDTO dto) {
		return sendEmails.sendApproveEditAtt(dto);
	}
	@Override
	public boolean sendCustomEditMail(DailyAttDataDTO dto, String msg) {
		return sendEmails.sendCustomEditEmailToStudent(dto, msg);
	}

}
