/**
 * 
 */
package main.com.zc.service.academic_advisingInstructorsDates;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("aa_instructor_dateFacadeImpl")
public class aa_instructor_dateAppServiceImpl implements Iaa_instructor_dateAppService{

	@Autowired
	aa_instructor_dateRepository aa_instructor_dateDataRepository;
	
	
	@Override
	public List<aa_instructor_date> getAll() {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public aa_instructor_date addaa_instructor_date(aa_instructor_date data) {
		try{
			aa_instructor_dateDataRepository.addaa_instructor_date(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(aa_instructor_date data)throws Exception {
		// TODO Auto-generated method stub
		try{
			aa_instructor_dateDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public aa_instructor_date getById(int id) {
		// TODO Auto-generated method stub
		try{
			aa_instructor_date so=aa_instructor_dateDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}










	@Override
	public List<aa_instructor_date> getAllByYearAndSemester(String year, String semester) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getAllByYearAndSemester(year,semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByInstructorIdAndYearAndSemester(int id, String year, String semester) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByInstructorIdAndYearAndSemester(id, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByActionAndYearAndSemester(String state, String year, String semester) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByActionAndYearAndSemester(state, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getAllAvailableByInstructorIdAndYearAndSemester(int id, String year,
			String semester) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getAllAvailableByInstructorIdAndYearAndSemester(id, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public aa_instructor_date getByInstructorIdAndStudentIdAndYearAndSemester(int idInstructor, int idStudent,
			String year, String semester) {
		try{
			aa_instructor_date course=aa_instructor_dateDataRepository.getByInstructorIdAndStudentIdAndYearAndSemester(idInstructor,idStudent, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByStudentIdAndYearAndSemester(int id, String year, String semester) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByStudentIdAndYearAndSemester(id, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByActionAndInstructorAndYearAndSemester(String state, int idInstructor,
			String year, String semester) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByActionAndInstructorAndYearAndSemester(state,idInstructor, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


