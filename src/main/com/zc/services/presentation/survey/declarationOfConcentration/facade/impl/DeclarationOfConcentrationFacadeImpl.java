/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfConcentration.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfConcentrationService;
import main.com.zc.services.presentation.survey.declarationOfConcentration.facade.IDeclarationOfConcentrationFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service("IDeclarationOfConcentrationFacade")
public class DeclarationOfConcentrationFacadeImpl implements IDeclarationOfConcentrationFacade{

	@Autowired
	IDeclarationOfConcentrationService service;
	@Override
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto) {
		return service.submit(dto);
		
	}

	@Override
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto) {
		
		return service.update(dto);
	}

	@Override
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID) {
		
		return service.getByStudentID(studentID);
	}

	@Override
	public List<BaseDTO> getAllConcentrations() {
		
		return service.getAllConcentrations();
	}

	@Override
	public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(
			Integer concentrationID) {
		
		return service.getResultsByConcentrationID(concentrationID);
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllReults() {
		return service.getAllReults();
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllByConcentrationIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		
		return service.getAllByConcentrationIDAndYearAndSemester(id, year, semester);
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(
			 Integer year, Integer semester) {
		return service.getAllByYearAndSemester(year, semester);
	}

	@Override
	public List<BaseDTO> getConcentrationsByMajor(Integer major) {
		return service.getConcentrationsByMajorID(major);
	}

	@Override
	public BaseDTO getConcentrationById(Integer id) {
		return service.getConcentrationById(id);
	}



}
