/**
 * 
 */
package main.com.zc.services.presentation.survey.intendedMajor.facade;

import java.util.List;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
public interface IAdminIntenedMajorFacade {
	public List<IntendedMajorSurveyDTO> getbyMajorID(Integer id);
	public List<IntendedMajorSurveyDTO> getAll();
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto);
	public List<IntendedMajorSurveyDTO> getbyMajorIDAndYearAndSemester(Integer id, Integer year, Integer semester);
	public List<IntendedMajorSurveyDTO> getByYearAndSemester(Integer year, Integer semester);
}
