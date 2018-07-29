package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */

public interface ISurveyRepository {
	
	public Survey add(Survey survey);
	public Survey update(Survey survey);
	public Survey getById(Integer id);
	public List<Survey> getAll();
	public boolean delete(Survey survey);
}
