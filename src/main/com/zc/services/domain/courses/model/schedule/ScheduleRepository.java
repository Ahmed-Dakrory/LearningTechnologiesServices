/**
 * 
 */
package main.com.zc.services.domain.courses.model.schedule;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface ScheduleRepository {

	public List<Schedule> getAll();
	public List<Schedule> getByCourseId(int id);
	public Schedule addSchedule(Schedule clo);
	public Schedule getById(int id);
	public boolean delete(Schedule so);
}
