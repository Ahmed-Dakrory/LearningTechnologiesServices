/**
 * 
 */
package main.com.zc.service.instructor_survey_ans;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("instructor_survey_ansFacadeImpl")
public class instructor_survey_ansAppServiceImpl implements Iinstructor_survey_ansAppService{

	@Autowired
	instructor_survey_ansRepository instructor_survey_ansDataRepository;
	
	
	@Override
	public List<instructor_survey_ans> getAll() {
		try{
			List<instructor_survey_ans> course=instructor_survey_ansDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public instructor_survey_ans addinstructor_survey_ans(instructor_survey_ans data) {
		try{
			instructor_survey_ansDataRepository.addinstructor_survey_ans(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(instructor_survey_ans data)throws Exception {
		// TODO Auto-generated method stub
		try{
			instructor_survey_ansDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public instructor_survey_ans getById(int id) {
		// TODO Auto-generated method stub
		try{
			instructor_survey_ans so=instructor_survey_ansDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_survey_ans> getAllByCourse(int courseId) {
		try{
			List<instructor_survey_ans> course=instructor_survey_ansDataRepository.getAllByCourse(courseId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<instructor_survey_ans> getAllByCourseAndInstructor(int courseId, int instructorId) {
		try{
			List<instructor_survey_ans> course=instructor_survey_ansDataRepository.getAllByCourseAndInstructor(courseId, instructorId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_survey_ans>  getAllByCourseAndInstructorAndStudent(int courseId, int instructorId, int studentId)  {
		// TODO Auto-generated method stub
				try{
					List<instructor_survey_ans>  so=instructor_survey_ansDataRepository.getAllByCourseAndInstructorAndStudent(courseId,  instructorId,  studentId);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	

	
	
	
}
		
		

	
		
	


