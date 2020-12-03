/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ques;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("instructor_all_survey_quesFacadeImpl")
public class instructor_all_survey_quesAppServiceImpl implements Iinstructor_all_survey_quesAppService{

	@Autowired
	instructor_all_survey_quesRepository instructor_all_survey_quesDataRepository;
	
	
	@Override
	public List<instructor_all_survey_ques> getAll() {
		try{
			List<instructor_all_survey_ques> course=instructor_all_survey_quesDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public instructor_all_survey_ques addinstructor_all_survey_ques(instructor_all_survey_ques data) {
		try{
			instructor_all_survey_quesDataRepository.addinstructor_all_survey_ques(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(instructor_all_survey_ques data)throws Exception {
		// TODO Auto-generated method stub
		try{
			instructor_all_survey_quesDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public instructor_all_survey_ques getById(int id) {
		// TODO Auto-generated method stub
		try{
			instructor_all_survey_ques so=instructor_all_survey_quesDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	@Override
	public List<instructor_all_survey_ques> getAllByYearAndSemestar(int year, int semestar) {
		try{
			List<instructor_all_survey_ques> course=instructor_all_survey_quesDataRepository.getAllByYearAndSemestar(year, semestar);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ques> getAllByYearAndSemestarAndCategoryAndMidtermOrFinal(int year, int semestar, int category,int mode) {
		// TODO Auto-generated method stub
				try{
					List<instructor_all_survey_ques> so=instructor_all_survey_quesDataRepository.getAllByYearAndSemestarAndCategoryAndMidtermOrFinal(year,  semestar,  category,mode);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	@Override
	public List<instructor_all_survey_ques> getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal(int year,
			int semestar, int mode) {
		// TODO Auto-generated method stub
		try{
			List<instructor_all_survey_ques> so=instructor_all_survey_quesDataRepository.getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal(year,  semestar,mode);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ques> getAllByYearAndSemestarAndMidtermOrFinalAndType(int year,
			int semestar, int mode, int type) {
		try{
			List<instructor_all_survey_ques> so=instructor_all_survey_quesDataRepository.getAllByYearAndSemestarAndMidtermOrFinalAndType(year,  semestar,mode,type);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


