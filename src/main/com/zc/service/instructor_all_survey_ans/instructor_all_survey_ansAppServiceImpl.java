/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ans;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("instructor_all_survey_ansFacadeImpl")
public class instructor_all_survey_ansAppServiceImpl implements Iinstructor_all_survey_ansAppService{

	@Autowired
	instructor_all_survey_ansRepository instructor_all_survey_ansDataRepository;
	
	
	@Override
	public List<instructor_all_survey_ans> getAll() {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public instructor_all_survey_ans addinstructor_all_survey_ans(instructor_all_survey_ans data) {
		try{
			instructor_all_survey_ansDataRepository.addinstructor_all_survey_ans(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(instructor_all_survey_ans data)throws Exception {
		// TODO Auto-generated method stub
		try{
			instructor_all_survey_ansDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public instructor_all_survey_ans getById(int id) {
		// TODO Auto-generated method stub
		try{
			instructor_all_survey_ans so=instructor_all_survey_ansDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans> getAllByCourse(int courseId) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByCourse(courseId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<instructor_all_survey_ans> getAllByCourseAndInstructor(int courseId, int instructorId) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByCourseAndInstructor(courseId, instructorId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans>  getAllByCourseAndInstructorAndStudent(int courseId, int instructorId, int studentId)  {
		// TODO Auto-generated method stub
				try{
					List<instructor_all_survey_ans>  so=instructor_all_survey_ansDataRepository.getAllByCourseAndInstructorAndStudent(courseId,  instructorId,  studentId);
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	@Override
	public instructor_all_survey_ans getAllByCourseAndInstructorAndStudentAndQuestion(int courseId, int instructorId,
			int studentId, int questionId) {
		// TODO Auto-generated method stub
				try{
					instructor_all_survey_ans so=instructor_all_survey_ansDataRepository.getAllByCourseAndInstructorAndStudentAndQuestion(courseId, instructorId,studentId, questionId) ;
					
					return so;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}



	@Override
	public List<instructor_all_survey_ans> getAllForAllInstructorForYearAndSemester(int semester, int year) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllForAllInstructorForYearAndSemester(semester, year);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans> getAllByInstructorForYearAndSemester(int semester, int year, int instructorId) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByInstructorForYearAndSemester(semester, year, instructorId);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans> getAllByInstructorForYearAndSemesterandCategory(int semester, int year,
			int instructorId, int category) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByInstructorForYearAndSemesterandCategory(semester, year, instructorId,category);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndYearAndSemester(int courseId, int instructorId,
			int year, int semester) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByCourseAndInstructorAndYearAndSemester(courseId, instructorId, year,semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}






	@Override
	public List<instructor_all_survey_ans> getAllByInstructorForYearAndSemesterGroupbyCourseId(int instructorId, int year,
			int semester) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByInstructorForYearAndSemesterGroupbyCourseId(instructorId, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndYearAndSemesterAndCategory(int courseId,
			int instructorId, int year, int semester, int category) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByCourseAndInstructorAndYearAndSemesterAndCategory(courseId, instructorId, year,semester,category);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<instructor_all_survey_ans> getAllByCourseAndInstructorAndYearAndSemesterAndType(int courseId,
			int instructorId, int year, int semester, int type) {
		try{
			List<instructor_all_survey_ans> course=instructor_all_survey_ansDataRepository.getAllByCourseAndInstructorAndYearAndSemesterAndType(courseId, instructorId, year,semester,type);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


