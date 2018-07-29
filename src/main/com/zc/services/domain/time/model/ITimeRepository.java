/**
 * 
 */
package main.com.zc.services.domain.time.model;

import java.util.Calendar;
import java.util.List;

/**
 * @author Omnya
 *
 */
public interface ITimeRepository {
	public Time getById(int id);
	public List<Time> getAll();
	public boolean remove(int id);
	public Time update(Time time);
	public int add(Time time);
	public Time getTimeByPersonID(int id);
	public List<Time> getAllByStatus(String status);
	public List<Time> getAllByExcuseStatus(String excuseStatus);
	public List<Time> getAllByStatusAndDate(String status,Calendar date);
	public Time getTimeByFileNoAndDate(int fileNo,Calendar date);
	public List<Time> getAllAttendanceByFileNo(int fileNo);
	public List<Time> getAllAttendanceByFileNoAndStatus(int fileNo,String status);
	public List<Time> getAllByAttendanceDate(Calendar date);
	public Time getAllByAttendanceFileNoAndDate(Calendar date,int fileNo);
}
