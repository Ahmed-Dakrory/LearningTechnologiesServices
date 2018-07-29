/**
 * 
 */
package main.com.zc.services.presentation.configuration.dto;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class StudentCourseDTO {

	private Integer id;
	private StudentDTO student;
	private CoursesDTO course;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
}
