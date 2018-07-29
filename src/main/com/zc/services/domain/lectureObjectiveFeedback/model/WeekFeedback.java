/**
 * 
 */
package main.com.zc.services.domain.lectureObjectiveFeedback.model;

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

import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;

/**
 * @author omnya
 *
 */
@NamedQueries({

	@NamedQuery(name = "WeekFeedback.getAll", query = "SELECT d FROM WeekFeedback d "),
	@NamedQuery(name = "WeekFeedback.getById", query = "from WeekFeedback d where d.id = :id"),
	@NamedQuery(name = "WeekFeedback.getByQuestID", query = "from WeekFeedback d where d.question.id = :id "),
	@NamedQuery(name = "WeekFeedback.getByStudentID", query = "from WeekFeedback d where d.student.id = :id "),
	@NamedQuery(name = "WeekFeedback.getByStudentIDAndCourseID", query = "from WeekFeedback d where d.student.id = :id AND d.course.id = :courseID "),
	@NamedQuery(name = "WeekFeedback.getByStudentIDAndCourseIDAndInstructor", query = "from WeekFeedback d where d.student.id = :id AND d.course.id = :courseID"
			+ " AND d.instructor = :insID ORDER BY d.id ASC "),
	@NamedQuery(name = "WeekFeedback.getByStudentIDAndCourseIDAndInstructorAndQuesID", query = "from WeekFeedback d where d.student.id = :id AND d.course.id = :courseID"
					+ " AND d.instructor = :insID AND d.question.id = :quesID"),
	@NamedQuery(name = "WeekFeedback.getByQuestIDAndStudentID", query = "from WeekFeedback d where d.question.id = :id and d.student.id = :stID"),
	@NamedQuery(name = "WeekFeedback.getByQuestIDAndStudentIDAndCourseID", query = "from WeekFeedback d where d.question.id = :quesID and d.student.id = :stID"
			+ " AND d.course.id = :courseId"),
	@NamedQuery(name = "WeekFeedback.getAnswresByQuestionIDAndCourseIDAndInstructorID", query = "from WeekFeedback d where d.course.id = :courseID"
					+ " AND d.instructor = :insID AND d.question.id = :quesID "),
	@NamedQuery(name = "WeekFeedback.getCommentsByCategoryIDAndCourseIDAndInstructorID", query = "from WeekFeedback d where d.course.id = :courseID"
							+ " AND d.instructor = :insID AND d.question.category = :categoryID"),
	@NamedQuery(name = "WeekFeedback.getCommentsByCategoryIDAndCourseID", query = "from WeekFeedback d where d.course.id = :courseID"
									+ " AND d.question.category = :categoryID"),
	@NamedQuery(name = "WeekFeedback.getAnswresByQuesIDAndCourseID", query ="from WeekFeedback d where d.course.id = :courseID"
									+ " AND d.question.id = :quesID" ),
    @NamedQuery(name = "WeekFeedback.getDistintStudents", query ="select distinct d.student from WeekFeedback d where d.course.id = :courseID "
											),
	@NamedQuery(name = "WeekFeedback.getByCourseID", query ="from WeekFeedback d where d.course.id = :courseId"
														),
    @NamedQuery(name = "WeekFeedback.getByCourseIDAndInsID", query ="from WeekFeedback d where d.course.id = :courseId and instructor.id = :insID"
																),
	@NamedQuery(name = "WeekFeedback.getByCourseIDAndInsIDAndStudentIdAndWeek", query ="from WeekFeedback d where d.course.id = :courseID "
			+ "and d.instructor.id = :insID and d.student.id = :studentID and d.week.id  = :week"
																			),
	@NamedQuery(name = "WeekFeedback.getByCourseIDAndWeek", query ="from WeekFeedback d where d.course.id = :courseID "
			+ " AND d.week.id  = :week"
																			),
	
	@NamedQuery(name = "WeekFeedback.getDistinctStudentByCourseIDandWeekAndIns", 
	query ="select distinct d.student from WeekFeedback d where d.course.id = :courseID "
			+ " AND d.week.id  = :week AND d.instructor.id = :insID"
																			),
	@NamedQuery(name = "WeekFeedback.getDistinctStudentByCourseIDandWeekAndInsAndQuesAndSelection", 
	query ="select distinct d.student from WeekFeedback d where d.course.id = :courseID "
			+ " AND d.week.id  = :week AND d.instructor.id = :insID AND d.question.id= :quesId AND d.Selections= :selectionID"
																			),
	@NamedQuery(name = "WeekFeedback.getTotalNumOfParticipatedStudents", 
			query ="select distinct d.student from WeekFeedback d where"
					+ "  d.week.id= :week"
																			)
/*	select distinct m.msgFrom 
    from Message m
    WHERE m.msgTo = ? 
    AND m.msgCheck = "0"*/
	}
 )


@Entity
@Table(name = "weeks_feedback")
public class WeekFeedback {


	
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
	
	@ManyToOne
	@JoinColumn(name = "WEEK_ID")
	private SemesterWeeks week;

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

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
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

	public SemesterWeeks getWeek() {
		return week;
	}

	public void setWeek(SemesterWeeks week) {
		this.week = week;
	}
	
	
}
