/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;

/**
 * @author omnya
 *
 */
public interface ICourseRepeatAdmissionHeadService {

	public List<CourseRepeatDTO> getPendingFormsOfAdmissionHead();
	public List<CourseRepeatDTO> getArchievedFormsOfAdmissionHead();
	public CourseRepeatDTO updateStatusOfForm(CourseRepeatDTO dto);
	public CourseRepeatDTO getById(Integer id);
	public void addComment(Integer id, String comment);
	
}
