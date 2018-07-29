package main.com.zc.services.presentation.survey.intendedMajor.facade.impl;

import java.util.List;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IAdminOfficialMajorService;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.survey.intendedMajor.facade.IAdminOfficialMajorFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("IAdminOfficialMajorFacade")
public class AdminOfficialMajorFacadeImpl implements IAdminOfficialMajorFacade {

@Autowired
IAdminOfficialMajorService service;
	
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
		
		return service.getByYearAndSemester(year,semester);
	}

}
