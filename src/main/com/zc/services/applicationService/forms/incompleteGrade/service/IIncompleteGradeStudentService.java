/**
 * 
 */
package main.com.zc.services.applicationService.forms.incompleteGrade.service;

import java.util.List;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface IIncompleteGradeStudentService {

	public IncompleteGradeDTO add(IncompleteGradeDTO dto);
	public List<IncompleteGradeDTO> getPendingPetitionsOfstuent(Integer studentID);
	public List<IncompleteGradeDTO> getArchievedPetitionsOfstuent(Integer studentID);
	public List<CoursesDTO> getAllCourses();
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id);
	public IncompleteGradeDTO getByID(Integer id);
	
	
}
