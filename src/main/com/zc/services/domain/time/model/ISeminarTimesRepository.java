/**
 * 
 */
package main.com.zc.services.domain.time.model;

import java.util.Calendar;
import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface ISeminarTimesRepository {
public int add(SeminarTimes time);
public SeminarTimes update(SeminarTimes time);
public SeminarTimes getById(int id);
public List<SeminarTimes> getAll();
public boolean remove(int id);
public SeminarTimes getSeminarTimesByPersonID(int id);
public List<SeminarTimes> getAllByStatus(String status);
public List<SeminarTimes> getAllByStatusAndDate(String status,Calendar date);
public SeminarTimes getSeminarTimesByFileNoAndDate(int fileNo,Calendar date);
public List<SeminarTimes> getAllAttendanceByFileNo(int fileNo);
}
