/**
 * 
 */
package main.com.zc.services.presentation.configuration.dto;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class StudentsCoursesNumberDTO {

	private Integer id;
	
	private CoursesDTO course;
	
	private Integer num;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public CoursesDTO getCourse() {
		return course;
	}

	public void setCourse(CoursesDTO course) {
		this.course = course;
	}
	
	
	
}
