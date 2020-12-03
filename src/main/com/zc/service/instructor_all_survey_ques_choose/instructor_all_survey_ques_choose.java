package main.com.zc.service.instructor_all_survey_ques_choose;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.service.instructor_all_survey_ques.instructor_all_survey_ques;




/**
 * 
 * @author Ahmed.Dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="instructor_all_survey_ques_choose.getAll",
		     query="SELECT c FROM instructor_all_survey_ques_choose c "
		     )
	,
	@NamedQuery(name="instructor_all_survey_ques_choose.getById",
	query = "from instructor_all_survey_ques_choose d where d.id = :id "
			)
	,
	@NamedQuery(name="instructor_all_survey_ques_choose.getAllByQuesId",
	query = "from instructor_all_survey_ques_choose d where d.quesId.id = :quesId"
			)
	
			
	
})
 
@Entity
@Table(name = "instructor_all_survey_ques_choose")
public class instructor_all_survey_ques_choose {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	
	
	@ManyToOne
	@JoinColumn(name = "quesId")
	private instructor_all_survey_ques quesId;
	
	

	@Column(name = "answerName")
	private String answerName;
	
	

	@Column(name = "answerValue")
	private Integer answerValue;
	
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}






	public instructor_all_survey_ques getQuesId() {
		return quesId;
	}



	public void setQuesId(instructor_all_survey_ques quesId) {
		this.quesId = quesId;
	}



	public String getAnswerName() {
		return answerName;
	}



	public void setAnswerName(String answerName) {
		this.answerName = answerName;
	}



	public Integer getAnswerValue() {
		return answerValue;
	}



	public void setAnswerValue(Integer answerValue) {
		this.answerValue = answerValue;
	}


	

	
	
}
