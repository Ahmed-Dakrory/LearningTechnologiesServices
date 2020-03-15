/**
 * 
 */
package main.com.zc.services.presentation.survey.intendedMajor.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.service.IAdminIntenedMajorService;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.survey.intendedMajor.facade.IAdminIntenedMajorFacade;

/**
 * @author omnya
 *
 */
@Service("IAdminIntenedMajorFacade")
public class AdminIntenedMajorFacadeImpl implements IAdminIntenedMajorFacade {

	@Autowired
	IAdminIntenedMajorService service;
	@Override
	public List<IntendedMajorSurveyDTO> getbyMajorID(Integer id) {
		
		return service.getbyMajorID(id);
	}
	@Override
	public List<IntendedMajorSurveyDTO> getAll() {
		
		return service.getAll();
	}
	@Override
	public List<IntendedMajorSurveyDTO> getbyMajorIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		
		return service.getbyMajorIDAndYearAndSemester(id, year, semester);
	}
	@Override
	public List<IntendedMajorSurveyDTO> getByYearAndSemester(Integer year,
			Integer semester) {
		
		return service.getByYearAndSemester(year, semester);
	}
	@Override
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto) {
		// TODO Auto-generated method stub
		return service.update(dto);
	}

}
