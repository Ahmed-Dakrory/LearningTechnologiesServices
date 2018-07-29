/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public interface IStudentAcademicPetFacade {
	public List<CoursePetitionDTO> getPendingPetOfStudent(Integer studentId);
	public List<CoursePetitionDTO> getOldPetOfStudent(Integer studentId);
	public CoursePetitionDTO submit(CoursePetitionDTO dto);
	public List<CoursesDTO> getAllCourses();
	public List<CoursesDTO> getAllCoursesBySemesterAndYear(Integer semester, Integer year);
	public CoursePetitionDTO getById(Integer id);
}
