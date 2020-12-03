/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ques_choose;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("instructor_all_survey_ques_chooseFacadeImpl")
public class instructor_all_survey_ques_chooseAppServiceImpl implements Iinstructor_all_survey_ques_chooseAppService{

	@Autowired
	instructor_all_survey_ques_chooseRepository instructor_all_survey_ques_chooseDataRepository;
	
	
	@Override
	public List<instructor_all_survey_ques_choose> getAll() {
		try{
			List<instructor_all_survey_ques_choose> course=instructor_all_survey_ques_chooseDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public instructor_all_survey_ques_choose addinstructor_all_survey_ques_choose(instructor_all_survey_ques_choose data) {
		try{
			instructor_all_survey_ques_chooseDataRepository.addinstructor_all_survey_ques_choose(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(instructor_all_survey_ques_choose data)throws Exception {
		// TODO Auto-generated method stub
		try{
			instructor_all_survey_ques_chooseDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public instructor_all_survey_ques_choose getById(int id) {
		// TODO Auto-generated method stub
		try{
			instructor_all_survey_ques_choose so=instructor_all_survey_ques_chooseDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	



	@Override
	public List<instructor_all_survey_ques_choose> getAllByQuesId(int quesId) {
		try{
			List<instructor_all_survey_ques_choose> course=instructor_all_survey_ques_chooseDataRepository.getAllByQuesId(quesId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


