/**
 * 
 */
package main.com.zc.services.presentation.survey.DeclarationOfMajor.facade;

import java.util.List;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface IStudentDeclarationOfMajorFacade {

	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public IntendedMajorSurveyDTO getByStudentIDAndYearAndSemester(Integer studentID);
	
	
}
