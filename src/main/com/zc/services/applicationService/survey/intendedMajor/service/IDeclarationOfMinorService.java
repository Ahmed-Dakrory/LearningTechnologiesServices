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
public interface IDeclarationOfMinorService {

	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public List<BaseDTO> getAllMinors();
	public List<BaseDTO> getMinorsByMajorID(Integer majorID);
	public List<IntendedMajorSurveyDTO> getResultsByMinorID(Integer minorID);
	public List<IntendedMajorSurveyDTO> getAllReults();
	public List<IntendedMajorSurveyDTO> getAllByMinorIDAndYearAndSemester(
			Integer minorID, Integer year, Integer semester);
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(Integer year, Integer semester);
	public BaseDTO getMinorById(Integer id);
}
