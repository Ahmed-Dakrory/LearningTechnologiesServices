/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.grades;





import java.util.List;
import main.com.zc.services.domain.courses.model.grades.Grade;
import main.com.zc.services.domain.courses.model.grades.GradesRepository;
import main.com.zc.services.domain.courses.model.grades.IGradeAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("GradeFacadeImpl")
public class GradeAppServiceImpl implements IGradeAppService{

	@Autowired
	GradesRepository gradesRepository;
	
	
	@Override
	public List<Grade> getAllRelatedTopics() {
		try{
			List<Grade> grades=gradesRepository.getAll();
			
			return grades;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<Grade> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<Grade> grades=gradesRepository.getByCourseId(id);
					
					return grades;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public Grade addGrade(Grade grade) {
		try{
			Grade grade2=gradesRepository.addGrade(grade);
			return grade2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public Grade getById(int id) {
		try{
			Grade grades=gradesRepository.getById(id);
			
			return grades;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(Grade grade) {
		// TODO Auto-generated method stub
		try{
			boolean ok=gradesRepository.delete(grade);
			
			return ok;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}
	
}
		
		

	
		
	


