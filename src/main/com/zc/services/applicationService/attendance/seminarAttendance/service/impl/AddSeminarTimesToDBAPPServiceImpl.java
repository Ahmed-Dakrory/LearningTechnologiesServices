/**
 * 
 */
package main.com.zc.services.applicationService.attendance.seminarAttendance.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.applicationService.attendance.seminarAttendance.service.IAddSeminarTimesToDBAPPService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.time.model.ISeminarTimesRepository;
import main.com.zc.services.domain.time.model.SeminarTimes;
import main.com.zc.services.presentation.attendance.seminarAttendance.dto.SeminarAttendanceDTO;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class AddSeminarTimesToDBAPPServiceImpl implements IAddSeminarTimesToDBAPPService{

	@Autowired
	ISeminarTimesRepository seminarTimeRep;
	/*@Autowired
	IStudentRepository studentRep;*/
	@Autowired
	IStudentRepository personRep;
	@Override
	public int addSeminarTimes(List<SeminarAttendanceDTO> attendedStudents ,Calendar cal) {
	
		int size=0;
		//List<Students> allStudent=studentRep.getAll();
		List<Student> allstudents=personRep.getAll();
		List<SeminarAttendanceDTO> lostPeople=new ArrayList<SeminarAttendanceDTO>();
		List<Student> absents=new ArrayList<Student>();
		boolean found=false;
		for(int i=0;i<allstudents.size();i++)
		{
			for(int j=0;j<attendedStudents.size();j++)
			{
				if(allstudents.get(i).getFileNo()==attendedStudents.get(j).getFileNo()){
					found=true;
					break;
				}
			}
			if(found==false)
			{
				absents.add(allstudents.get(i));
			}
			found=false;
		}
		
		// Loop on attended students and add them to DB
		for(int i=0;i<attendedStudents.size();i++)
		{
			Student per=personRep.getPersonByFileNo(attendedStudents.get(i).getFileNo());
			if(per!=null){
				SeminarTimes seminarTimes=new SeminarTimes(cal,attendedStudents.get(i).getTime()
						,per, 
						Constants.ATTENDANCE_STATUS_ATTENDED);
				try{
				seminarTimeRep.add(seminarTimes);
				size++;
				}
				catch(HibernateException ex)
				{
					System.out.println(attendedStudents.get(i)+" Duplicate");
				}
		
			}
				else 
				{
					lostPeople.add(attendedStudents.get(i));
				}	
		}
		
		//Loop in absents and add them to DB
		for(int i=0;i<absents.size();i++){
			Student per=personRep.getPersonByFileNo(absents.get(i).getFileNo());
			if(per!=null){
			SeminarTimes seminarTimes=new SeminarTimes(cal,null, 
					per, Constants.ATTENDANCE_STATUS_ABSENCE);
					
			try{
				seminarTimeRep.add(seminarTimes);
				size++;
				}
			catch(HibernateException ex)
			{
				System.out.println(absents.get(i)+" Duplicate");
			}
		
			
		}
			else
			{
				SeminarAttendanceDTO dao=new SeminarAttendanceDTO();
				dao.setDate(cal);
				dao.setFileNo(absents.get(i).getFileNo());
				
				lostPeople.add(dao);
				}	
		}
		return size;
	}

}
