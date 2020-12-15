/**
 * 
 */
package main.com.zc.service.decleration_of_track;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("decleration_of_trackFacadeImpl")
public class decleration_of_trackAppServiceImpl implements Idecleration_of_trackAppService{

	@Autowired
	decleration_of_trackRepository decleration_of_trackDataRepository;
	
	
	@Override
	public List<decleration_of_track> getAll() {
		try{
			List<decleration_of_track> course=decleration_of_trackDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public decleration_of_track adddecleration_of_track(decleration_of_track data) {
		try{
			decleration_of_trackDataRepository.adddecleration_of_track(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(decleration_of_track data)throws Exception {
		// TODO Auto-generated method stub
		try{
			decleration_of_trackDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public decleration_of_track getById(int id) {
		// TODO Auto-generated method stub
		try{
			decleration_of_track so=decleration_of_trackDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	@Override
	public List<decleration_of_track> getAllByYearAndSemestar(int year, int semestar) {
		try{
			List<decleration_of_track> course=decleration_of_trackDataRepository.getAllByYearAndSemestar(year, semestar);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}




	

	
	
	
}
		
		

	
		
	


