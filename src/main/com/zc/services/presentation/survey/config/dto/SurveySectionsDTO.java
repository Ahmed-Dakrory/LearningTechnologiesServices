/**
 * 
 */
package main.com.zc.services.presentation.survey.config.dto;


/**
 * @author omnya
 *
 */
public class SurveySectionsDTO {

	private SurveyDTO survey;
	private SectionsDTO section;
	private Integer id;
	public SurveyDTO getSurvey() {
		return survey;
	}
	public void setSurvey(SurveyDTO survey) {
		this.survey = survey;
	}
	public SectionsDTO getSection() {
		return section;
	}
	public void setSection(SectionsDTO section) {
		this.section = section;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
