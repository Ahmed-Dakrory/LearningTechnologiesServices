/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services;

import java.util.List;

import main.com.zc.services.presentation.configuration.dto.StudentsCoursesNumberDTO;

/**
 * @author omnya
 *
 */
public interface IStudentsCoursesNumberAppService {

	public List<StudentsCoursesNumberDTO> getAll();
	public StudentsCoursesNumberDTO getByCourseID(Integer id);
	public StudentsCoursesNumberDTO getById (Integer id);
	public StudentsCoursesNumberDTO add(StudentsCoursesNumberDTO courseStudent);
	public StudentsCoursesNumberDTO update(StudentsCoursesNumberDTO courseStudent);
	public boolean remove(StudentsCoursesNumberDTO obj); 
	
}
