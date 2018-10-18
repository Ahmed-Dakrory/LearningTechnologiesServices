/**
 * 
 */
package main.com.zc.services.domain.courses.service.repository.books;





import java.util.List;

import main.com.zc.services.domain.courses.model.books.CourseBookRepository;
import main.com.zc.services.domain.courses.model.books.CourseBooks;
import main.com.zc.services.domain.courses.model.books.IBookCourseAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dakrory
 *
 */
@Service("BookCourseFacadeImpl")
public class CourseBookAppServiceImpl implements IBookCourseAppService{

	@Autowired
	CourseBookRepository courseBookRepository;
	
	
	@Override
	public List<CourseBooks> getAll() {
		try{
			List<CourseBooks> books=courseBookRepository.getAll();
			
			return books;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<CourseBooks> getByCourseId(int id) {
		// TODO Auto-generated method stub
				try{
					List<CourseBooks> books=courseBookRepository.getByCourseId(id);
					
					return books;
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						return null;
					}
	}

	@Override
	public CourseBooks addCourseBook(CourseBooks book) {
		try{
			CourseBooks courseBooks=courseBookRepository.addbookForCourse(book);
			return courseBooks;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(CourseBooks data) {
		// TODO Auto-generated method stub
		try{
			courseBookRepository.delete(data);
			return true;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
	}

	@Override
	public CourseBooks getById(int id) {
		// TODO Auto-generated method stub
		try{
			CourseBooks objData=courseBookRepository.getById(id);
			
			return objData;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return null;
			}
	}
}
		
		

	
		
	


