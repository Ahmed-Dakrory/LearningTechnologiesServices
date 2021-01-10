/**
 * 
 */
package main.com.zc.service.clo_survey_ans;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("clo_survey_ansFacadeImpl")
public class clo_survey_ansAppServiceImpl implements Iclo_survey_ansAppService{

	@Autowired
	clo_survey_ansRepository clo_survey_ansDataRepository;
	
	
	@Override
	public List<clo_survey_ans> getAll() {
		try{
			List<clo_survey_ans> course=clo_survey_ansDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public clo_survey_ans addclo_survey_ans(clo_survey_ans data) {
		try{
			clo_survey_ansDataRepository.addclo_survey_ans(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(clo_survey_ans data)throws Exception {
		// TODO Auto-generated method stub
		try{
			clo_survey_ansDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public clo_survey_ans getById(int id) {
		// TODO Auto-generated method stub
		try{
			clo_survey_ans so=clo_survey_ansDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	@Override
	public List<clo_survey_ans> getAllByCourseId(int courseId) {
		try{
			List<clo_survey_ans> course=clo_survey_ansDataRepository.getAllByCourseId(courseId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	
	
	@Override
	public clo_survey_ans getByStudentIdAndCourseId(int idStudent, int courseId) {
		try{
			clo_survey_ans course=clo_survey_ansDataRepository.getByStudentIdAndCourseId(idStudent, courseId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<clo_survey_ans> getAllByCourseIdGroupByStudent(int courseId) {
		try{
			List<clo_survey_ans> course=clo_survey_ansDataRepository.getAllByCourseIdGroupByStudent(courseId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


