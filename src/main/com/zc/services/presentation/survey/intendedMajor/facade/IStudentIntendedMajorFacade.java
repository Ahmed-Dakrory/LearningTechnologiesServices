/**
 * 
 */
package main.com.zc.services.presentation.survey.intendedMajor.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
public interface IStudentIntendedMajorFacade {
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public List<MajorDTO> getMajors();
}
