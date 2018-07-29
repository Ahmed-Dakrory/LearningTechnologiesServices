/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfConcentration.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfConcentrationResultsService;
import main.com.zc.services.presentation.survey.declarationOfConcentration.facade.IDeclarationOfConcentrationResultsFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
@Service("IDeclarationOfConcentrationResultsFacade")
public class DeclarationOfConcentrationResultsFacadeImpl implements IDeclarationOfConcentrationResultsFacade{

	@Autowired
	IDeclarationOfConcentrationResultsService service;
	@Override
	public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(
			Integer concentrationID) {
		
		return service.getResultsByConcentrationID(concentrationID);
	}
	@Override
	public List<IntendedMajorSurveyDTO> getAllReults() {
		
		return service.getAllReults();
	}

}
