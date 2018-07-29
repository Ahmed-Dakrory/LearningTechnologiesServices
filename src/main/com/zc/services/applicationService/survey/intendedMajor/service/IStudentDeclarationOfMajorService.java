/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
public interface IStudentDeclarationOfMajorService {

	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID);
	public IntendedMajorSurveyDTO getByStudentIDAndYearAndSemester(
			Integer studentID);
	
}
