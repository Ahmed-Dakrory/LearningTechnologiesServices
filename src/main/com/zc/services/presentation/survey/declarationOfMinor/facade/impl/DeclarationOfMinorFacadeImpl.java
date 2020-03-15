/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfMinor.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfMinorService;
import main.com.zc.services.presentation.survey.declarationOfMinor.facade.IDeclarationOfMinorFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service("IDeclarationOfMinorFacade")
public class DeclarationOfMinorFacadeImpl implements IDeclarationOfMinorFacade{

	@Autowired
	IDeclarationOfMinorService service;
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
	public List<BaseDTO> getAllMinors() {
		
		return service.getAllMinors();
	}

	@Override
	public List<IntendedMajorSurveyDTO> getResultsByMinorID(
			Integer concentrationID) {
		
		return service.getResultsByMinorID(concentrationID);
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllReults() {
		return service.getAllReults();
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllByMinorIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		
		return service.getAllByMinorIDAndYearAndSemester(id, year, semester);
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(
			 Integer year, Integer semester) {
		return service.getAllByYearAndSemester(year, semester);
	}

	@Override
	public List<BaseDTO> getMinorsByMajor(Integer major) {
		return service.getMinorsByMajorID(major);
	}

	@Override
	public BaseDTO getMinorById(Integer id) {
		return service.getMinorById(id);
	}



}
