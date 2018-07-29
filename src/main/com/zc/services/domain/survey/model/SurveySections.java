/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "SurveySections.getAll", query = "SELECT s FROM SurveySections s"),
	@NamedQuery(name = "SurveySections.getById", query = "from SurveySections s where s.id = :id "),
	@NamedQuery(name = "SurveySections.getBySectionId", query = "from SurveySections s "
			+ "where s.section.id = :id "),
	@NamedQuery(name = "SurveySections.getBySurveyId", query = "from SurveySections s "
			+ "where s.survey.id = :id "),
	@NamedQuery(name = "SurveySections.getGeneralBySurveyId", query = "from SurveySections s "
			+ "where s.survey.id = :id AND s.section.course is null"),
	@NamedQuery(name = "SurveySections.getCourseSectionsBySurveyID", query = "from SurveySections s "
			+ "where s.survey.id = :surveyId AND s.section.course.id= :courseID"), 
	@NamedQuery(name = "SurveySections.getBySectionIdAndSurveyID", query = "from SurveySections s "
			+ "where s.survey.id = :id and s.section.id = :sectionID")
	})



@Table(name="survey_sections")
@Entity
public class SurveySections {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "SECTION_ID")
	private Section section;
	
	@ManyToOne
	@JoinColumn(name = "SURVEY_ID")
	private Survey survey;
	
	@Column(name = "CREATION_DATE")
	private Date date;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Section getSection() {
		return section;
	}
	public void setSection(Section section) {
		this.section = section;
	}
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
}
