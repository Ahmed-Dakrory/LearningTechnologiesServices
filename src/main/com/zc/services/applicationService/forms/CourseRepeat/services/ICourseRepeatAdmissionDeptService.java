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
public interface ICourseRepeatAdmissionDeptService {


	public CourseRepeatDTO updateRequest(CourseRepeatDTO dto);
	public CourseRepeatDTO getByID(Integer id);
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent();
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent();
	public void addComment(Integer id, String comment);
	List<CourseRepeatDTO> getAuditingPet();
	int getPendingAndAuditingCourseRepeateAdmissionDept();
	
}
