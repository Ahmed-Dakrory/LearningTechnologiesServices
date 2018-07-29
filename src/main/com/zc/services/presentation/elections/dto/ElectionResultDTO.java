/**
 * 
 */
package main.com.zc.services.presentation.elections.dto;

import java.util.List;

import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class ElectionResultDTO 
{
private int id;
private int studentID;
private BaseDTO president;
private BaseDTO vice;
private List<BaseDTO> activities;
private List<BaseDTO> services;
private List<BaseDTO> academic;
private BaseDTO activitiesPresident;
private BaseDTO servicesPresident;
private BaseDTO academicPresident;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public int getStudentID() {
	return studentID;
}
public void setStudentID(int studentID) {
	this.studentID = studentID;
}
public BaseDTO getPresident() {
	return president;
}
public void setPresident(BaseDTO president) {
	this.president = president;
}
public BaseDTO getVice() {
	return vice;
}
public void setVice(BaseDTO vice) {
	this.vice = vice;
}
public BaseDTO getActivitiesPresident() {
	return activitiesPresident;
}
public void setActivitiesPresident(BaseDTO activitiesPresident) {
	this.activitiesPresident = activitiesPresident;
}
public BaseDTO getServicesPresident() {
	return servicesPresident;
}
public void setServicesPresident(BaseDTO servicesPresident) {
	this.servicesPresident = servicesPresident;
}
public BaseDTO getAcademicPresident() {
	return academicPresident;
}
public void setAcademicPresident(BaseDTO academicPresident) {
	this.academicPresident = academicPresident;
}
public void setActivities(List<BaseDTO> activities) {
	this.activities = activities;
}
public void setServices(List<BaseDTO> services) {
	this.services = services;
}
public void setAcademic(List<BaseDTO> academic) {
	this.academic = academic;
}
public List<BaseDTO> getActivities() {
	return activities;
}
public List<BaseDTO> getServices() {
	return services;
}
public List<BaseDTO> getAcademic() {
	return academic;
}


}
