/**
 * 
 */
package main.com.zc.services.applicationService.survey.config;

import java.util.List;

import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveyDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveySectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;

/**
 * @author omnya
 *
 */
public interface ISurveyService {
	public List<SurveyDTO> getSurveys();
	
	public SurveyDTO addSurvey(SurveyDTO survey);

	public List<SectionsDTO> getSections();

	public SectionsDTO addSection(SectionsDTO section);

	public List<SurveyDTO> getSurveysBySectionID(Integer id);

	public boolean saveSurveysSections(List<SurveySectionsDTO> lst);

	public List<SurveyDTO> getOtherSurveysBySectionID(Integer id);

	public boolean deleteSurvey(Integer survey, Integer section);
	
	public List<SurveySectionsDTO> getSectionsBySurveyID(Integer id);

	public boolean addSurveySection(Integer survey, Integer section);

	public boolean deleteSection(Integer id);
	
	public SectionsDTO getByIdAndCourseID(Integer id, Integer cID);

	public List<ScaleTypeDTO> getScales();

	public ScaleTypeDTO getByName(String name);

	public List<SurveySectionsDTO> getGeneralSectionsBySurveyID(Integer id);

	public List<SurveySectionsDTO> getCourseSectionsBySurveyID(Integer surveyID,Integer courseID);
}
