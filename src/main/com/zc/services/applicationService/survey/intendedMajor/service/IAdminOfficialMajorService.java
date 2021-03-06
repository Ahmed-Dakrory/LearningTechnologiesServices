/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service;

import java.util.List;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
public interface IAdminOfficialMajorService {

	public List<IntendedMajorSurveyDTO> getbyMajorID(Integer id);
	public List<IntendedMajorSurveyDTO> getbyMajorIDAndYearAndSemester(
			Integer id, Integer year, Integer semester);
	public List<IntendedMajorSurveyDTO> getAll();
	public List<IntendedMajorSurveyDTO> getByYearAndSemester(Integer year, Integer semester);
	
	
}
