/**
 * 
 */
package main.com.zc.service.instructor_survey_ques;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("instructor_survey_quesFacadeImpl")
public class instructor_survey_quesAppServiceImpl implements Iinstructor_survey_quesAppService{

	@Autowired
	instructor_survey_quesRepository instructor_survey_quesDataRepository;
	
	
	@Override
	public List<instructor_survey_ques> getAll() {
		try{
			List<instructor_survey_ques> course=instructor_survey_quesDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public instructor_survey_ques addinstructor_survey_ques(instructor_survey_ques data) {
		try{
			instructor_survey_quesDataRepository.addinstructor_survey_ques(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(instructor_survey_ques data)throws Exception {
		// TODO Auto-generated method stub
		try{
			instructor_survey_quesDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public instructor_survey_ques getById(int id) {
		// TODO Auto-generated method stub
		try{
			instructor_survey_ques so=instructor_survey_quesDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	@Override
	public List<instructor_survey_ques> getAllByYearAndSemestar(int year, int semestar) {
		try{
			List<instructor_survey_ques> course=instructor_survey_quesDataRepository.getAllByYearAndSemestar(year, semestar);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_survey_ques> getAllByYearAndSemestarAndCategory(int year, int semestar, int category) {
		// TODO Auto-generated method stub
				try{
					List<instructor_survey_ques> so=instructor_survey_quesDataRepository.getAllByYearAndSemestarAndCategory(year,  semestar,  category);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	

	
	
	
}
		
		

	
		
	


