/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfConcentration.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */

public interface IDeclarationOfConcentrationResultsFacade {
public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(Integer concentrationID);
public List<IntendedMajorSurveyDTO> getAllReults();
}
