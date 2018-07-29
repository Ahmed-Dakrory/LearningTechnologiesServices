/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.dto;

import org.primefaces.model.chart.PieChartModel;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class CourseEvalAnswersDTO {

	
	
	private Integer id;
	
	
	private Integer selections;
	
	
	
	private CourseEvalQuestionsDTO question;
	

	private StudentDTO student;

	private String text;

	private CoursesDTO course;
	
	private InstructorDTO instructor;
	
	private String comment;
	
	private PieChartModel model;
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getSelections() {
		return selections;
	}


	public void setSelections(Integer selections) {
		this.selections = selections;
	}


	public CourseEvalQuestionsDTO getQuestion() {
		return question;
	}


	public void setQuestion(CourseEvalQuestionsDTO question) {
		this.question = question;
	}


	public StudentDTO getStudent() {
		return student;
	}


	public void setStudent(StudentDTO student) {
		this.student = student;
	}


	public CoursesDTO getCourse() {
		return course;
	}


	public void setCourse(CoursesDTO course) {
		this.course = course;
	}


	public InstructorDTO getInstructor() {
		return instructor;
	}


	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public PieChartModel getModel() {
		return model;
	}


	public void setModel(PieChartModel model) {
		this.model = model;
	}
	
	
	
	
}
