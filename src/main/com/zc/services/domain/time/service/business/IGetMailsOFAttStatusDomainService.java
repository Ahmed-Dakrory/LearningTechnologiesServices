/**
 * 
 */
package main.com.zc.services.domain.time.service.business;

import java.util.Calendar;
import java.util.List;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.time.model.SeminarTimes;
import main.com.zc.services.domain.time.model.Time;


/**
 * @author Omnya Alaa
 *
 */
public interface IGetMailsOFAttStatusDomainService {
public List<Student> getMailListByAttStatus(String status);
public List<Student> getMailListByAttStatusAndDate(String status,Calendar date);
public List<Time> getTimesListByAttStatusAndDate(String status,Calendar date);
public List<Time> getTimesListByDate(Calendar date);
public List<Student> getMailListByListOfTimes(List<Time> timesList);
public List<Student> getMailListByListOfSeminarTimes(List<SeminarTimes> SeminarTimesList);

}
