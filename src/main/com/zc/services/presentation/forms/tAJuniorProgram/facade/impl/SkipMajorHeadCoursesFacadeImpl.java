/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.ISkipMajorHeadCoursesService;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.SkipMajorHeadCoursesDTO;
import main.com.zc.services.presentation.forms.tAJuniorProgram.facade.ISkipMajorHeadCoursesFacade;

/**
 * @author omnya
 *
 */
@Service("ISkipMajorHeadCoursesFacade")
public class SkipMajorHeadCoursesFacadeImpl implements ISkipMajorHeadCoursesFacade{

	@Autowired
	ISkipMajorHeadCoursesService service;
	
	@Override
	public SkipMajorHeadCoursesDTO add(SkipMajorHeadCoursesDTO form) {
		
		return service.add(form);
	}

	@Override
	public boolean remove(Integer id) {
		
		return service.remove(id);
	}

	@Override
	public SkipMajorHeadCoursesDTO update(SkipMajorHeadCoursesDTO form) {
		
		return service.update(form);
	}

	@Override
	public List<SkipMajorHeadCoursesDTO> getAll() {
		
		return service.getAll();
	}

	@Override
	public SkipMajorHeadCoursesDTO getById(Integer id) {
		
		return service.getById(id);
	}

	@Override
	public SkipMajorHeadCoursesDTO getByCourseID(Integer id) {
		return service.getByCourseID(id);
	}

	@Override
	public boolean checkCourse(Integer courseID) {
		SkipMajorHeadCoursesDTO dto= getById(courseID);
		if(dto!=null)
		{
			if(dto.getId()!=null)
			{
				return true;
			}
			else return false;
		}
		else 
		return false;
	}

}
