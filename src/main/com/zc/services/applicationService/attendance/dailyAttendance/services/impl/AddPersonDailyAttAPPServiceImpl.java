/**
 * 
 */
package main.com.zc.services.applicationService.attendance.dailyAttendance.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.attendance.dailyAttendance.services.IAddPersonDailyAttAPPService;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.time.model.ITimeRepository;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.attendance.dailyAttendance.dto.DailyAttDataDTO;
import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IDailyAttFacade;
import main.com.zc.services.presentation.attendance.shared.dto.TimeDailyAttDTO;

/**
 * @author omnya
 *
 */
@Service
public class AddPersonDailyAttAPPServiceImpl implements IAddPersonDailyAttAPPService{

	@Autowired
	IStudentRepository personRep;
	@Autowired
	ITimeRepository timeRep;
	@Autowired
	IDailyAttFacade getAllStudentsData;
	@Override
	public int addStudentData(List<DailyAttDataDTO> list) {
		List<DailyAttDataDTO> lost=new ArrayList<DailyAttDataDTO>();
		try{
		for(int i=0;i<list.size();i++)
		{
			if(personRep.getPersonByFileNo(list.get(i).getPersonId())!=null)
		{  
				Time time = null;
			
		 if(list.get(i).getFirst_in()==null&&list.get(i).getLast_out()==null)
			{
				 time=new Time(list.get(i).getDate(), list.get(i).getExpected_in(), list.get(i).getExpected_out(), list.get(i).getFirst_in(), list.get(i).getLast_out(),"Absence",null,personRep.getPersonByFileNo(list.get(i).getPersonId()));
					
			}
		/* else if((list.get(i).getFirst_in().compareTo(list.get(i).getLast_out())==0)&&
				 list.get(i).getFirst_in()!=null&&list.get(i).getLast_out()!=null
				 ) 
		 {
			 time=new Time(list.get(i).getDate(), list.get(i).getExpected_in(), list.get(i).getExpected_out(), list.get(i).getFirst_in(), list.get(i).getLast_out(),"Scanned once",null,personRep.getPersonByFileNo(list.get(i).getPersonId()));
		 }*/
		 else if((list.get(i).getFirst_in()!=null&&list.get(i).getLast_out()==null)) 
		 {
			 time=new Time(list.get(i).getDate(), list.get(i).getExpected_in(), list.get(i).getExpected_out(), list.get(i).getFirst_in(), list.get(i).getLast_out(),"Scanned once",null,personRep.getPersonByFileNo(list.get(i).getPersonId()));
		 }
		 else 
		 {
			 time=new Time(list.get(i).getDate(), list.get(i).getExpected_in(), list.get(i).getExpected_out(), list.get(i).getFirst_in(), list.get(i).getLast_out(),"Attended",null,personRep.getPersonByFileNo(list.get(i).getPersonId()));
			 
		 }
		 try{
			 timeRep.add(time);
				}
				catch(HibernateException ex)
				{
					System.out.println(list.get(i)+" Duplicate");
				}
		}
		
		else// there is no emails for this students 
		{
			lost.add(list.get(i));
		}}
		for(int i=0;i<lost.size();i++)
		System.out.println("Size of lost is >>>> :"+lost.get(i).getPersonId());
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lost.size();
	}
	
	
	
	@Override
	public DailyAttDataDTO getStudentData(DailyAttDataDTO dto) {
		try{
			
			
		Student per=personRep.getPersonByFileNo(dto.getPersonId());
		dto.setStudentName(per.getData().getNameInEnglish());
		 if(dto.getFirst_in()==null&&dto.getLast_out()==null)
			{
			 dto.setStudentStatus("Absence");
					
			}
	
		 else if((dto.getFirst_in()!=null&&dto.getLast_out()==null)) 
		 {
			 dto.setStudentStatus("Scanned once");
			
		 }
		 else 
		 {
			 dto.setStudentStatus("Attended");
			
			 
		 }
		return dto;
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}



	@Override
	public List<DailyAttDataDTO> getAllStudentData() {
	
		return getAllStudentsData.getAllStudentData();
	}

	
	
}
