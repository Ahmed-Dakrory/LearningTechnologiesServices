/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatStudentService;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.CourseRepeat.facade.ICourseRepeatStudentFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
@Service("CourseRepeatStudentFacadeImpl")
public class CourseRepeatStudentFacadeImpl implements ICourseRepeatStudentFacade {

	@Autowired
	ICourseRepeatStudentService service;
	@Override
	public CourseRepeatDTO add(CourseRepeatDTO dto) {
		
		return service.add(dto);
	}

	@Override
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		return service.getPendingPetitionsOfstuent(studentID);
	}

	@Override
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		return service.getArchievedPetitionsOfstuent(studentID);
	}


	@Override
	public List<CoursesDTO> getAllCourses() {
		
		return service.getAllCourses();
	}

	@Override
	public CourseRepeatDTO getByID(Integer id) {
		
		return service.getByID(id);
	}


}
