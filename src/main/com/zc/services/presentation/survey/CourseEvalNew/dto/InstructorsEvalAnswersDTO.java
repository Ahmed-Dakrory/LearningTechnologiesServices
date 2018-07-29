/**
 * 
 */
package main.com.zc.services.presentation.survey.CourseEvalNew.dto;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public class InstructorsEvalAnswersDTO {
	private Integer id;
	
	private Integer selection;
	
	private String comment;
	
	private Calendar submissionDate;
	
	private CourseEvalQuestionsDTO question;
	
	
	private InstructorDTO from;

	private InstructorDTO to;

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

	public CourseEvalQuestionsDTO getQuestion() {
		return question;
	}

	public void setQuestion(CourseEvalQuestionsDTO question) {
		this.question = question;
	}

	public InstructorDTO getFrom() {
		return from;
	}

	public void setFrom(InstructorDTO from) {
		this.from = from;
	}

	public InstructorDTO getTo() {
		return to;
	}

	public void setTo(InstructorDTO to) {
		this.to = to;
	}
	
	
	
	
	
	
}
