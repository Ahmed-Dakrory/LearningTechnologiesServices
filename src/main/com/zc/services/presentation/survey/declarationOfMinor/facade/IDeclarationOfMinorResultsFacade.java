/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfMinor.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */

public interface IDeclarationOfMinorResultsFacade {
public List<IntendedMajorSurveyDTO> getResultsByMinorID(Integer minorID);
public List<IntendedMajorSurveyDTO> getAllReults();
}
