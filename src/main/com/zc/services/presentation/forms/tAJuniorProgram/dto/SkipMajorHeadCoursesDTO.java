/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.dto;

import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class SkipMajorHeadCoursesDTO {

	private Integer id;
	
	private CoursesDTO course;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CoursesDTO getCourse() {
		return course;
	}

	public void setCourse(CoursesDTO course) {
		this.course = course;
	}
	
	
    
}
