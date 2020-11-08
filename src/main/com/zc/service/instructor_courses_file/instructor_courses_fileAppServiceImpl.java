/**
 * 
 */
package main.com.zc.service.instructor_courses_file;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("instructor_courses_fileFacadeImpl")
public class instructor_courses_fileAppServiceImpl implements Iinstructor_courses_fileAppService{

	@Autowired
	instructor_courses_fileRepository instructor_courses_fileDataRepository;
	
	
	@Override
	public List<instructor_courses_file> getAll() {
		try{
			List<instructor_courses_file> course=instructor_courses_fileDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public instructor_courses_file addinstructor_courses_file(instructor_courses_file data) {
		try{
			instructor_courses_fileDataRepository.addinstructor_courses_file(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(instructor_courses_file data)throws Exception {
		// TODO Auto-generated method stub
		try{
			instructor_courses_fileDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public instructor_courses_file getById(int id) {
		// TODO Auto-generated method stub
		try{
			instructor_courses_file so=instructor_courses_fileDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}







	@Override
	public List<instructor_courses_file> getAllByInstructorEmailAndYearAndSemester(String email, String year,
			String semester) {
		try{
			List<instructor_courses_file> course=instructor_courses_fileDataRepository.getAllByInstructorEmailAndYearAndSemester(email, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}





	

	
	
	
}
		
		

	
		
	


