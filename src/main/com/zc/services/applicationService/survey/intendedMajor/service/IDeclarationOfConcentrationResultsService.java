/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
public interface IDeclarationOfConcentrationResultsService {

	public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(Integer concentrationID);
	public List<IntendedMajorSurveyDTO> getAllReults();
}
