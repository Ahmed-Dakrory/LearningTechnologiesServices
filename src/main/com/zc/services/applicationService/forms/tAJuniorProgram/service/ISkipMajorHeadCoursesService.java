/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.service;

import java.util.List;

import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.SkipMajorHeadCoursesDTO;

/**
 * @author omnya
 *
 */
public interface ISkipMajorHeadCoursesService {

	public SkipMajorHeadCoursesDTO add(SkipMajorHeadCoursesDTO form);

	public boolean remove(Integer id);

	public SkipMajorHeadCoursesDTO update(SkipMajorHeadCoursesDTO form);

	public List<SkipMajorHeadCoursesDTO> getAll();

	public SkipMajorHeadCoursesDTO getById(Integer id);

	public SkipMajorHeadCoursesDTO getByCourseID(Integer id);
	
	
}
