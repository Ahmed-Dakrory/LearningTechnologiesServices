/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "InstructorsEvalAnswers.getAll", query = "SELECT d FROM InstructorsEvalAnswers d "),
	@NamedQuery(name = "InstructorsEvalAnswers.getById", query = "from InstructorsEvalAnswers d where d.id = :id"),
	@NamedQuery(name = "InstructorsEvalAnswers.getByCategoryID", query = "from InstructorsEvalAnswers d where d.question.category = :id "),
	@NamedQuery(name = "InstructorsEvalAnswers.getByQuestID", query = "from InstructorsEvalAnswers d where d.question.id = :id "),
	@NamedQuery(name = "InstructorsEvalAnswers.getByInsFromID", query = "from InstructorsEvalAnswers d where d.from.id = :id "),
	@NamedQuery(name = "InstructorsEvalAnswers.getByInsTOID", query = "from InstructorsEvalAnswers d where d.to.id = :id "),
	@NamedQuery(name = "InstructorsEvalAnswers.getByFromInsAndTOIns", query = "from InstructorsEvalAnswers d where d.from.id = :fromINS AND d.to.id = :to"
			+""),
	@NamedQuery(name = "InstructorsEvalAnswers.getByFromInsAndTOInsAndQuesID", query = "from InstructorsEvalAnswers d where d.from.id = :from AND d.to.id = :to"
					+ "  AND d.question.id = :quesID"),
	@NamedQuery(name = "InstructorsEvalAnswers.getByQuestIDAndInsFromID", query = "from InstructorsEvalAnswers d where d.question.id = :ques and d.from.id = :from"),
	@NamedQuery(name = "InstructorsEvalAnswers.getByQuestIDAndInsTOID", query = "from InstructorsEvalAnswers d where d.question.id = :ques and d.to.id = :to"),

	
	}
 )
@Entity
@Table(name = "instructors_eval")
public class InstructorsEvalAnswers {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="SELECTION")
	private Integer selection;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="SUB_DATE")
	private Calendar submissionDate;
	
	@ManyToOne
	@JoinColumn(name = "QUES_ID")
	private CourseEvalQuestions question;
	
	
	@ManyToOne
	@JoinColumn(name = "FROM_ID")
	private Employee from;

	@ManyToOne
	@JoinColumn(name = "TO_ID")
	private Employee to;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSelection() {
		return selection;
	}

	public void setSelection(Integer selection) {
		this.selection = selection;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Calendar getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}

	public CourseEvalQuestions getQuestion() {
		return question;
	}

	public void setQuestion(CourseEvalQuestions question) {
		this.question = question;
	}

	public Employee getFrom() {
		return from;
	}

	public void setFrom(Employee from) {
		this.from = from;
	}

	public Employee getTo() {
		return to;
	}

	public void setTo(Employee to) {
		this.to = to;
	}

	
	
	
	
	
	
}
