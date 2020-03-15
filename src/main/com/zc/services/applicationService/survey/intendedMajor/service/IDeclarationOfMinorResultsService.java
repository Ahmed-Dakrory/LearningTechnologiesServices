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
public interface IDeclarationOfMinorResultsService {

	public List<IntendedMajorSurveyDTO> getResultsByMinorID(Integer minorID);
	public List<IntendedMajorSurveyDTO> getAllReults();
}
