/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfConcentration.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IDeclarationOfConcentrationFacade {

	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public List<BaseDTO> getAllConcentrations();
	public List<BaseDTO> getConcentrationsByMajor(Integer major);
	public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(Integer concentrationID);
	public List<IntendedMajorSurveyDTO> getAllReults();
	public List<IntendedMajorSurveyDTO> getAllByConcentrationIDAndYearAndSemester(
			Integer id, Integer year, Integer semester);
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(Integer year, Integer semester);
	public BaseDTO getConcentrationById(Integer id);
	
}
