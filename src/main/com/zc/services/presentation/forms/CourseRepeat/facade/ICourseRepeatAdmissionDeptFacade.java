/**
 * 
 */
package main.com.zc.services.presentation.forms.CourseRepeat.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;

/**
 * @author omnya
 *
 */
public interface ICourseRepeatAdmissionDeptFacade {

	public CourseRepeatDTO updateRequest(CourseRepeatDTO dto);
	public CourseRepeatDTO getByID(Integer id);
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent();
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent();
	public void addComment(Integer id, String comment);
	public List<CourseRepeatDTO> getAuditingPet();
}
