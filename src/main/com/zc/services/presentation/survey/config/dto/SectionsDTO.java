/**
 * 
 */
package main.com.zc.services.presentation.survey.config.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class SectionsDTO {

	private Integer id;
	private String name;
	private Date date;
	List<CourseEvalQuestionsDTO> questions=new ArrayList<CourseEvalQuestionsDTO>();
	private CoursesDTO course=new CoursesDTO();
	private String title;
	private Integer exportStyle;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<CourseEvalQuestionsDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<CourseEvalQuestionsDTO> questions) {
		this.questions = questions;
	}
	public CoursesDTO getCourse() {
		return course;
	}
	public void setCourse(CoursesDTO course) {
		this.course = course;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getExportStyle() {
		return exportStyle;
	}
	public void setExportStyle(Integer exportStyle) {
		this.exportStyle = exportStyle;
	}
	
	
	
	
}
