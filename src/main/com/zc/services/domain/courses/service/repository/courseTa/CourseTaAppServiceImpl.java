/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.courseTa;
import java.util.List;

import main.com.zc.services.domain.courses.model.courseTa.CourseTa;
import main.com.zc.services.domain.courses.model.courseTa.CourseTaRepository;
import main.com.zc.services.domain.courses.model.courseTa.ICourseTaAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("courseTaFacadeImpl")
public class CourseTaAppServiceImpl implements ICourseTaAppService{

	@Autowired
	CourseTaRepository courseTaRepository;
	
	
	@Override
	public List<CourseTa> getAllCourseTas() {
		try{
			List<CourseTa> references=courseTaRepository.getAll();
			
			return references;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseTa> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<CourseTa> courseTas=courseTaRepository.getByCourseId(id);
					
					return courseTas;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public CourseTa addCourseTa(CourseTa courseTa) {
		try{
			CourseTa courseTa2=courseTaRepository.addCourseTa(courseTa);
			return courseTa2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


