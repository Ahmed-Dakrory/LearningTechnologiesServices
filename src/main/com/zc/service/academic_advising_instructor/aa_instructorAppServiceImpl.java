/**
 * 
 */
package main.com.zc.service.academic_advising_instructor;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("aa_instructorFacadeImpl")
public class aa_instructorAppServiceImpl implements Iaa_instructorAppService{

	@Autowired
	aa_instructorRepository aa_instructorDataRepository;
	
	
	@Override
	public List<aa_instructor> getAll() {
		try{
			List<aa_instructor> course=aa_instructorDataRepository.getAll();
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	

	@Override
	public aa_instructor addaa_instructor(aa_instructor data) {
		try{
			aa_instructorDataRepository.addaa_instructor(data);
				return data;
			
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}


	@Override
	public boolean delete(aa_instructor data)throws Exception {
		// TODO Auto-generated method stub
		try{
			aa_instructorDataRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				throw ex;
			}
	}

	@Override
	public aa_instructor getById(int id) {
		// TODO Auto-generated method stub
		try{
			aa_instructor so=aa_instructorDataRepository.getById(id);
			
			return so;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}










	@Override
	public List<aa_instructor> getAllByYearAndSemester(int year, String semester) {
		try{
			List<aa_instructor> course=aa_instructorDataRepository.getAllByYearAndSemester(year,semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	@Override
	public aa_instructor getByMailAndYearAndSemester(String mail, int year, String semester) {
		try{
			aa_instructor course=aa_instructorDataRepository.getByMailAndYearAndSemester(mail, year, semester);
			
			return course;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}



	

	
	
	
}
		
		

	
		
	


