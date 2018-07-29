/**
 * 
 */
package main.com.zc.services.presentation.survey.courseFeedback.dto;

/**
 * @author Omnya Alaa
 *
 */
public class InstructorDTO {
private int id;// id of Course_instructor table
private int insID;
private String insName;
private int courseId;
private String courseName;





public InstructorDTO() {
	super();
}



public InstructorDTO(int id, int insID, String insName, int courseId,
		String courseName) {
	super();
	this.id = id;
	this.insID = insID;
	this.insName = insName;
	this.courseId = courseId;
	this.courseName = courseName;
}



public int getInsID() {
	return insID;
}
public void setInsID(int insID) {
	this.insID = insID;
}
public String getInsName() {
	return insName;
}
public void setInsName(String insName) {
	this.insName = insName;
}


public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}



public int getCourseId() {
	return courseId;
}



public void setCourseId(int courseId) {
	this.courseId = courseId;
}



public String getCourseName() {
	return courseName;
}



public void setCourseName(String courseName) {
	this.courseName = courseName;
}

}
