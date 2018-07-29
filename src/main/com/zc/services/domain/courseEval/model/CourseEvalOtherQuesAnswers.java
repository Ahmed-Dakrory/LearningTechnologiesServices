/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import main.com.zc.services.domain.person.model.Student;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "CourseEvalOtherQuesAnswers.getAll", query = "SELECT d FROM CourseEvalOtherQuesAnswers d "),
	@NamedQuery(name = "CourseEvalOtherQuesAnswers.getById", query = "from CourseEvalOtherQuesAnswers d where d.id = :id"),
	@NamedQuery(name = "CourseEvalOtherQuesAnswers.getByCategoryID", query = "from CourseEvalOtherQuesAnswers d where d.question.category = :id "),
	@NamedQuery(name = "CourseEvalOtherQuesAnswers.getByQuestID", query = "from CourseEvalOtherQuesAnswers d where d.question.id = :id "),
	@NamedQuery(name = "CourseEvalOtherQuesAnswers.getByStudentID", query = "from CourseEvalOtherQuesAnswers d where d.student.id = :id "),
	@NamedQuery(name = "CourseEvalOtherQuesAnswers.getByQuestIDAndStudentID", query = "from CourseEvalOtherQuesAnswers d where d.question.id = :id and d.student.id = :stID"),
	}
 )
@Entity
@Table(name = "course_eval_other_ques")
public class CourseEvalOtherQuesAnswers {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	
	@ManyToOne
	@JoinColumn(name = "QUES_ID")
	private CourseEvalQuestions question;
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;
	
	@Column(name="TEXT")
	private String text;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CourseEvalQuestions getQuestion() {
		return question;
	}

	public void setQuestion(CourseEvalQuestions question) {
		this.question = question;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
