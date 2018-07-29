/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ISurveySectionRepository {

	public SurveySections add(SurveySections survey);
	public SurveySections update(SurveySections survey);
	public SurveySections getById(Integer id);
	public List<SurveySections> getAll();
	public List<SurveySections> getBySectionId(Integer id);
	public List<SurveySections> getBySurveyId(Integer id);
	public boolean delete(SurveySections survey);
	public SurveySections getBySectionIdAndSurveyID(Integer survey, Integer section);
	public List<SurveySections> getGeneralBySurveyId(Integer id);
	public List<SurveySections> getCourseSectionsBySurveyID(Integer surveyId,Integer courseID);
	
	
	
}
