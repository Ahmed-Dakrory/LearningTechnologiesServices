/**
 * 
 */
package main.com.zc.services.presentation.survey.courseFeedback.dto;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author Omnya Alaa
 *
 */
public class CoursesDTO {
private int id;
private String name;
private main.com.zc.services.presentation.users.dto.InstructorDTO coordinator;
private Integer year;
private SemesterEnum semester;
//for course evaluation to remove from list
private boolean statusRemove;
private Integer instructorsNum;
private Boolean hideCourseEval;
public CoursesDTO() {
	super();
}
public CoursesDTO(int id, String name) {
	super();
	this.id = id;
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public main.com.zc.services.presentation.users.dto.InstructorDTO  getCoordinator() {
	return coordinator;
}
public void setCoordinator(main.com.zc.services.presentation.users.dto.InstructorDTO  coordinator) {
	this.coordinator = coordinator;
}
public Integer getYear() {
	return year;
}
public void setYear(Integer year) {
	this.year = year;
}
public SemesterEnum getSemester() {
	return semester;
}
public void setSemester(SemesterEnum semester) {
	this.semester = semester;
}
public boolean isStatusRemove() {
	return statusRemove;
}
public void setStatusRemove(boolean statusRemove) {
	this.statusRemove = statusRemove;
}
public Integer getInstructorsNum() {
	return instructorsNum;
}
public void setInstructorsNum(Integer instructorsNum) {
	this.instructorsNum = instructorsNum;
}
public Boolean getHideCourseEval() {
	return hideCourseEval;
}
public void setHideCourseEval(Boolean hideCourseEval) {
	this.hideCourseEval = hideCourseEval;
}


}
