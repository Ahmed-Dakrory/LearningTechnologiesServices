/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfMinor.facade.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfMinorResultsService;
import main.com.zc.services.presentation.survey.declarationOfMinor.facade.IDeclarationOfMinorResultsFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
@Service("IDeclarationOfMinorResultsFacade")
public class DeclarationOfMinorResultsFacadeImpl implements IDeclarationOfMinorResultsFacade{

	@Autowired
	IDeclarationOfMinorResultsService service;
	@Override
	public List<IntendedMajorSurveyDTO> getResultsByMinorID(
			Integer concentrationID) {
		
		return service.getResultsByMinorID(concentrationID);
	}
	@Override
	public List<IntendedMajorSurveyDTO> getAllReults() {
		
		return service.getAllReults();
	}

}
