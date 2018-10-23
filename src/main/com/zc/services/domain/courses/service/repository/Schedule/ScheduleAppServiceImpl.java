/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.Schedule;





import java.util.List;
import main.com.zc.services.domain.courses.model.schedule.IScheduleAppService;
import main.com.zc.services.domain.courses.model.schedule.Schedule;
import main.com.zc.services.domain.courses.model.schedule.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("ScheduleFacadeImpl")
public class ScheduleAppServiceImpl implements IScheduleAppService{

	@Autowired
	ScheduleRepository scheduleRepository;
	
	
	@Override
	public List<Schedule> getAll() {
		try{
			List<Schedule> so=scheduleRepository.getAll();
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Schedule> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<Schedule> so=scheduleRepository.getByCourseId(id);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public Schedule addSchedule(Schedule so) {
		try{
			Schedule so2=scheduleRepository.addSchedule(so);
			return so2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(Schedule so) {
		// TODO Auto-generated method stub
		try{
			scheduleRepository.delete(so);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public Schedule getById(int id) {
		// TODO Auto-generated method stub
		try{
			Schedule so=scheduleRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


