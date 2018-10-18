/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.courseIns;
import java.util.List;

import main.com.zc.services.domain.courses.model.courseIns.CourseInSyllabus;
import main.com.zc.services.domain.courses.model.courseIns.CourseInsSyllabusRepository;
import main.com.zc.services.domain.courses.model.courseIns.ICourseInsSyllabusAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("courseInsSyllabusFacadeImpl")
public class CourseInsAppServiceImpl implements ICourseInsSyllabusAppService{

	@Autowired
	CourseInsSyllabusRepository courseInsSyllabusRepository;
	
	
	@Override
	public List<CourseInSyllabus> getAll() {
		try{
			List<CourseInSyllabus> references=courseInsSyllabusRepository.getAll();
			
			return references;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseInSyllabus> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<CourseInSyllabus> courseInSyllabus=courseInsSyllabusRepository.getByCourseId(id);
					
					return courseInSyllabus;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public CourseInSyllabus addCourseIns(CourseInSyllabus courseInSyllabus) {
		try{
			CourseInSyllabus courseInSyllabus2=courseInsSyllabusRepository.addCourseIns(courseInSyllabus);
			return courseInSyllabus2;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
	
	@Override
	public boolean delete(CourseInSyllabus data) {
		// TODO Auto-generated method stub
		try{
			courseInsSyllabusRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public CourseInSyllabus getById(int id) {
		// TODO Auto-generated method stub
		try{
			CourseInSyllabus objData=courseInsSyllabusRepository.getById(id);
			
			return objData;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
	
}
		
		

	
		
	


