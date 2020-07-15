/**
 * 
 */
package main.com.zc.service.academic_advisingInstructorStudents;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("aa_instructor_studentsFacadeImpl")
public class aa_instructor_studentsAppServiceImpl implements Iaa_instructor_studentsAppService{

	@Autowired
	aa_instructor_studentsRepository aa_instructor_studentsDataRepository;
	
	
	@Override
	public List<aa_instructor_students> getAll() {
		try{
			List<aa_instructor_students> course=aa_instructor_studentsDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public aa_instructor_students addaa_instructor_students(aa_instructor_students data) {
		try{
			aa_instructor_studentsDataRepository.addaa_instructor_students(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(aa_instructor_students data)throws Exception {
		// TODO Auto-generated method stub
		try{
			aa_instructor_studentsDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public aa_instructor_students getById(int id) {
		// TODO Auto-generated method stub
		try{
			aa_instructor_students so=aa_instructor_studentsDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}










	@Override
	public List<aa_instructor_students> getAllByYearAndSemester(String year, String semester) {
		try{
			List<aa_instructor_students> course=aa_instructor_studentsDataRepository.getAllByYearAndSemester(year,semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_students> getByInstructorIdAndYearAndSemester(int id, String year, String semester) {
		try{
			List<aa_instructor_students> course=aa_instructor_studentsDataRepository.getByInstructorIdAndYearAndSemester(id, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public aa_instructor_students getByStudentIdAndYearAndSemester(int id, String year, String semester) {
		try{
			aa_instructor_students course=aa_instructor_studentsDataRepository.getByStudentIdAndYearAndSemester(id, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public aa_instructor_students getByStudentEmailAndYearAndSemester(String mail, String year, String semester) {
		try{
			aa_instructor_students course=aa_instructor_studentsDataRepository.getByStudentEmailAndYearAndSemester(mail, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


