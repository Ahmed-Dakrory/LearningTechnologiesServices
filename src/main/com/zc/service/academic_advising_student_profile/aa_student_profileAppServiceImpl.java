/**
 * 
 */
package main.com.zc.service.academic_advising_student_profile;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("aa_student_profileFacadeImpl")
public class aa_student_profileAppServiceImpl implements Iaa_student_profileAppService{

	@Autowired
	aa_student_profileRepository aa_student_profileDataRepository;
	
	
	@Override
	public List<aa_student_profile> getAll() {
		try{
			List<aa_student_profile> course=aa_student_profileDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public aa_student_profile addaa_student_profile(aa_student_profile data) {
		try{
			aa_student_profileDataRepository.addaa_student_profile(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(aa_student_profile data)throws Exception {
		// TODO Auto-generated method stub
		try{
			aa_student_profileDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public aa_student_profile getById(int id) {
		// TODO Auto-generated method stub
		try{
			aa_student_profile so=aa_student_profileDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}










	@Override
	public List<aa_student_profile> getAllByYearAndSemester(int year, String semester) {
		try{
			List<aa_student_profile> course=aa_student_profileDataRepository.getAllByYearAndSemester(year,semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public aa_student_profile getByMailAndYearAndSemester(String mail, int year, String semester) {
		try{
			aa_student_profile course=aa_student_profileDataRepository.getByMailAndYearAndSemester(mail, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


