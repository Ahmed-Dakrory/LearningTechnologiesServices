/**
 * 
 */
package main.com.zc.service.courseClo;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("course_cloFacadeImpl")
public class course_cloAppServiceImpl implements Icourse_cloAppService{

	@Autowired
	course_cloRepository course_cloDataRepository;
	
	
	@Override
	public List<course_clo> getAll() {
		try{
			List<course_clo> course=course_cloDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public course_clo addcourse_clo(course_clo data) {
		try{
			course_cloDataRepository.addcourse_clo(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(course_clo data)throws Exception {
		// TODO Auto-generated method stub
		try{
			course_cloDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public course_clo getById(int id) {
		// TODO Auto-generated method stub
		try{
			course_clo so=course_cloDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	@Override
	public List<course_clo> getAllByYearAndSemestar(int year, int semestar) {
		try{
			List<course_clo> course=course_cloDataRepository.getAllByYearAndSemestar(year, semestar);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public course_clo getAllByYearAndSemestarAndCourseCode(int year, int semestar, String courseCode) {
		// TODO Auto-generated method stub
				try{
					course_clo so=course_cloDataRepository.getAllByYearAndSemestarAndCourseCode(year,  semestar,  courseCode);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	

	
	
	
}
		
		

	
		
	


