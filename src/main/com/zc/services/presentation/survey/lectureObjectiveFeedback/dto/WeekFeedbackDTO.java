/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto;

import java.util.Calendar;
import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.SemesterWeeks;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class WeekFeedbackDTO {

	private Integer id;
	
	private Integer Selections;
	
	private String comment;
	
	private Calendar submissionDate;
	
	private CourseEvalQuestionsDTO question;
	
	
	private StudentDTO student;

	private InstructorDTO instructor;
	
	private CoursesDTO course;
	
	private SemesterWeeksDTO week;

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

	public StudentDTO getStudent() {
		return student;
	}

	public CourseEvalQuestionsDTO getQuestion() {
		return question;
	}

	public void setQuestion(CourseEvalQuestionsDTO question) {
		this.question = question;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public InstructorDTO getInstructor() {
		return instructor;
	}

	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}

	public CoursesDTO getCourse() {
		return course;
	}

	public void setCourse(CoursesDTO course) {
		this.course = course;
	}

	public SemesterWeeksDTO getWeek() {
		return week;
	}

	public void setWeek(SemesterWeeksDTO week) {
		this.week = week;
	}


	
	
	
}
