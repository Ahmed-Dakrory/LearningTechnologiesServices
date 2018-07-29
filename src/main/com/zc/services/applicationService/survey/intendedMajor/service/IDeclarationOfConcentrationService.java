/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IDeclarationOfConcentrationService {

	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public List<BaseDTO> getAllConcentrations();
	public List<BaseDTO> getConcentrationsByMajorID(Integer majorID);
	public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(Integer concentrationID);
	public List<IntendedMajorSurveyDTO> getAllReults();
	public List<IntendedMajorSurveyDTO> getAllByConcentrationIDAndYearAndSemester(
			Integer concentrationID, Integer year, Integer semester);
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(Integer year, Integer semester);
	public BaseDTO getConcentrationById(Integer id);
}
