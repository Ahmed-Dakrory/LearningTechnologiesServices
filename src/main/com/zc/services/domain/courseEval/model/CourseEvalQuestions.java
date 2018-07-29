/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.domain.survey.model.Section;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "CourseEvalQuestions.getAll", query = "SELECT d FROM CourseEvalQuestions d "),
	@NamedQuery(name = "CourseEvalQuestions.getById", query = "from CourseEvalQuestions d where d.id = :id"),
	@NamedQuery(name = "CourseEvalQuestions.getByCategoryID", query = "from CourseEvalQuestions d where d.category.id =:id "),
	}
 )

@Entity
@Table(name = "course_eval_questions")
public class CourseEvalQuestions {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="TEXT")
	private String text;
	
	//@Column(name="CATEGORY")
	//private QuestionsCategory category;
	@ManyToOne
	@JoinColumn(name="CATEGORY")
	private Section category;
	
	//@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	//@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "SCALE_TYPE_ID")
	@ManyToOne(cascade=javax.persistence.CascadeType.ALL)  
	private ScaleType scaleType;
	
	
	@Column(name="CREATION_DATE")
	private Date date;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

/*	public QuestionsCategory getCategory() {
		return category;
	}

	public void setCategory(QuestionsCategory category) {
		this.category = category;
	}*/
	@ManyToOne(cascade=javax.persistence.CascadeType.ALL)  
	public ScaleType getScaleType() {
		return scaleType;
	}

	public void setScaleType(ScaleType scaleType) {
		this.scaleType = scaleType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Section getCategory() {
		return category;
	}

	public void setCategory(Section category) {
		this.category = category;
	}


	
}
