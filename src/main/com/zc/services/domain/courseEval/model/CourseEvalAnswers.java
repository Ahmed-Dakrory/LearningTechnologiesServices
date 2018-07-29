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

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "CourseEvalAnswers.getAll", query = "SELECT d FROM CourseEvalAnswers d "),
	@NamedQuery(name = "CourseEvalAnswers.getById", query = "from CourseEvalAnswers d where d.id = :id"),
	@NamedQuery(name = "CourseEvalAnswers.getByCategoryID", query = "from CourseEvalAnswers d where d.question.category.id = :id "),
	@NamedQuery(name = "CourseEvalAnswers.getByQuestID", query = "from CourseEvalAnswers d where d.question.id = :id "),
	@NamedQuery(name = "CourseEvalAnswers.getByStudentID", query = "from CourseEvalAnswers d where d.student.id = :id "),
	@NamedQuery(name = "CourseEvalAnswers.getByStudentIDAndCourseID", query = "from CourseEvalAnswers d where d.student.id = :id AND d.course.id = :courseID And d.type = :type"),
	@NamedQuery(name = "CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructor", query = "from CourseEvalAnswers d where d.student.id = :id AND d.course.id = :courseID"
			+ " AND d.instructor = :insID ORDER BY d.id ASC "),
	@NamedQuery(name = "CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructorAndQuesID", query = "from CourseEvalAnswers d where d.student.id = :id AND d.course.id = :courseID"
					+ " AND d.instructor = :insID AND d.question.id = :quesID"),
	@NamedQuery(name = "CourseEvalAnswers.getByQuestIDAndStudentID", query = "from CourseEvalAnswers d where d.question.id = :id and d.student.id = :stID"),
	@NamedQuery(name = "CourseEvalAnswers.getByQuestIDAndStudentIDAndCourseID", query = "from CourseEvalAnswers d where d.question.id = :quesID and d.student.id = :stID"
			+ " AND d.course.id = :courseId"),
	@NamedQuery(name = "CourseEvalAnswers.getAnswresByQuestionIDAndCourseIDAndInstructorID", query = "from CourseEvalAnswers d where d.course.id = :courseID"
					+ " AND d.instructor = :insID AND d.question.id = :quesID "),
	@NamedQuery(name = "CourseEvalAnswers.getCommentsByCategoryIDAndCourseIDAndInstructorID", query = "from CourseEvalAnswers d where d.course.id = :courseID"
							+ " AND d.instructor = :insID AND d.question.category.id = :categoryID"),
	@NamedQuery(name = "CourseEvalAnswers.getCommentsByCategoryIDAndCourseID", query = "from CourseEvalAnswers d where d.course.id = :courseID"
									+ " AND d.question.category.id = :categoryID"),
	@NamedQuery(name = "CourseEvalAnswers.getAnswresByQuesIDAndCourseID", query ="from CourseEvalAnswers d where d.course.id = :courseID"
									+ " AND d.question.id = :quesID" ),
    @NamedQuery(name = "CourseEvalAnswers.getDistintStudents", query ="select distinct d.student from CourseEvalAnswers d where d.course.id = :courseID and d.type= :surveyID"
											),
	@NamedQuery(name = "CourseEvalAnswers.getByCourseID", query ="from CourseEvalAnswers d where d.course.id = :courseId"
														),
    @NamedQuery(name = "CourseEvalAnswers.getByCourseIDAndInsID", query ="from CourseEvalAnswers d where d.course.id = :courseId and instructor.id = :insID"
																),
	@NamedQuery(name = "CourseEvalAnswers.getByQuestionIDAndCourseIDAndInsIDAndStudentId", query ="from CourseEvalAnswers d where d.course.id = :courseID "
			+ "and d.instructor.id = :insID and d.question.id = :questionID and d.student.id = :studentID"),
	@NamedQuery(name = "CourseEvalAnswers.getByStudentIDAndCourseIDAndInstructorAndQuesIDAndAnsID",
			query ="from CourseEvalAnswers d where d.course.id = :course "
					+ "and d.instructor.id = :ins and d.question.id = :ques " +
					"and d.student.id = :student and d.Selections = :ans"),
	@NamedQuery(name = "CourseEvalAnswers.getByQuestionIDAndCourseIDAndStudentIdAndAns",
			query ="from CourseEvalAnswers d where d.course.id = :course "
					+ "and d.question.id = :ques " +
					"and d.student.id = :student and d.Selections = :ans")
				
	}	
 )
@Entity
@Table(name = "course_eval_answers")
public class CourseEvalAnswers {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name="SELECTION")
	private Integer Selections;
	
	@Column(name="COMMENT")
	private String comment;
	
	@Column(name="SUB_DATE")
	private Calendar submissionDate;
	
	@ManyToOne
	@JoinColumn(name = "QUES_ID")
	private CourseEvalQuestions question;
	
	
	@ManyToOne
	@JoinColumn(name = "STUDENT_ID")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "INSTRUCTOR_ID")
	private Employee instructor;
	
	@ManyToOne
	@JoinColumn(name = "COURSE_ID")
	private Courses course;
	
	@Column(name="TYPE")
	private Integer type;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSelections() {
		return Selections;
	}

	public void setSelections(Integer selections) {
		Selections = selections;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Employee getInstructor() {
		return instructor;
	}

	public void setInstructor(Employee instructor) {
		this.instructor = instructor;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}

	public Calendar getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	
}
