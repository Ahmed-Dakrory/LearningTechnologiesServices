/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfMinor.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IDeclarationOfMinorFacade {

	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public List<BaseDTO> getAllMinors();
	public List<BaseDTO> getMinorsByMajor(Integer major);
	public List<IntendedMajorSurveyDTO> getResultsByMinorID(Integer minorID);
	public List<IntendedMajorSurveyDTO> getAllReults();
	public List<IntendedMajorSurveyDTO> getAllByMinorIDAndYearAndSemester(
			Integer id, Integer year, Integer semester);
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(Integer year, Integer semester);
	public BaseDTO getMinorById(Integer id);
	
}
