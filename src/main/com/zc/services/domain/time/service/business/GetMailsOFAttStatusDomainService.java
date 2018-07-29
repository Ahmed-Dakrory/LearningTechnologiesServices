/**
 * 
 */
package main.com.zc.services.domain.time.service.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.person.model.Student;
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
public class GetMailsOFAttStatusDomainService implements IGetMailsOFAttStatusDomainService {

	@Autowired
    ITimeRepository timeRep;	

	@Override
	public List<Student> getMailListByAttStatus(String status) {
		List<Student> personsList=new  ArrayList<Student>();
		try
		{
			List<Time> times=timeRep.getAllByStatus(status);
			for(int i=0;i<times.size();i++)
			{
				Student per=times.get(i).getPerson();
				personsList.add(per);
			}
		}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return personsList;
	}

	

	@Override
	public List<Student> getMailListByAttStatusAndDate(String status,
			Calendar date) {
		List<Student> personsList=new  ArrayList<Student>();
		try
		{
			List<Time> times=timeRep.getAllByStatusAndDate(status, date);
			for(int i=0;i<times.size();i++)
			{
				Student per=times.get(i).getPerson();
				personsList.add(per);
			}
		}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return personsList;
	}



	@Override
	public List<Time> getTimesListByAttStatusAndDate(String status,
			Calendar date) {
		List<Time> TimesList=new  ArrayList<Time>();
		try
		{
			TimesList=timeRep.getAllByStatusAndDate(status, date);
			
		}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return TimesList;
	}



	@Override
	public List<Student> getMailListByListOfTimes(List<Time> timesList) {
		List<Student> personsList=new  ArrayList<Student>();
		try
		{
			
			for(int i=0;i<timesList.size();i++)
			{
				Student per=timesList.get(i).getPerson();
				personsList.add(per);
			}
		}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return personsList;
	}



	@Override
	public List<Student> getMailListByListOfSeminarTimes(
			List<SeminarTimes> SeminarTimesList) {
		List<Student> personsList=new  ArrayList<Student>();
		try
		{
			
			for(int i=0;i<SeminarTimesList.size();i++)
			{
				Student per=SeminarTimesList.get(i).getPerson();
				personsList.add(per);
			}
		}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return personsList;
	}



	@Override
	public List<Time> getTimesListByDate(Calendar date) {
		List<Time> TimesList=new  ArrayList<Time>();
		try
		{
			TimesList=timeRep.getAllByAttendanceDate(date);
			
		}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return TimesList;
	}
	}





