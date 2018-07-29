/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.configuration.services.ICoursesService;
import main.com.zc.services.presentation.configuration.facade.ICoursesFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
@Service("ICoursesFacade")
public class CoursesFacadeImpl implements ICoursesFacade{

	@Autowired
	ICoursesService service;
	@Override
	public List<CoursesDTO> getCoursesByYearAndSemester(Integer year,
			Integer semester) {
		return service.getCoursesByYearAndSemester(year, semester);
	}

}
