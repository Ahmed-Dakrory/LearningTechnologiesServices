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
	public List<aa_instructor_date> getByInstructorId(int id) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByInstructorId(id);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByAction(String state) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByAction(state);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getAllAvailableByInstructorId(int id) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getAllAvailableByInstructorId(id);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public aa_instructor_date getByInstructorIdAndStudentId(int idInstructor, int idStudent) {
		try{
			aa_instructor_date course=aa_instructor_dateDataRepository.getByInstructorIdAndStudentId(idInstructor,idStudent);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByStudentId(int id) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByStudentId(id);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public List<aa_instructor_date> getByActionAndInstructor(String state, int idInstructor) {
		try{
			List<aa_instructor_date> course=aa_instructor_dateDataRepository.getByActionAndInstructor(state,idInstructor);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


