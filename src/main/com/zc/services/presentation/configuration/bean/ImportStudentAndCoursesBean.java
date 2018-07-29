/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import main.com.zc.services.presentation.attendance.dailyAttendance.facade.IDailyAttFacade;
import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="ImportStudentAndCoursesBean")
public class ImportStudentAndCoursesBean {

	private UploadedFile file;
	private String statusMessage;
	private List<StudentCourseDTO> studentCourseLst;
	private StudentCourseDTO selectedstudentCourseLst;
	private List<StudentCourseDTO> filteredStudentCourseLst;
	private boolean saveMood;
	
    @ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade facade; 
    
	@PostConstruct
	public void init()
	{
		studentCourseLst=new ArrayList<StudentCourseDTO>();
		filteredStudentCourseLst=new ArrayList<StudentCourseDTO>();
		selectedstudentCourseLst=new StudentCourseDTO();
		
	}
	
	
	public String parseFile()
	{
		 InputStream inputStream = null;
		 try {
			inputStream=file.getInputstream();
			List<StudentCourseDTO> list=facade.praseFile(inputStream);
			studentCourseLst=facade.saveStudents(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	}
	
	
	public void saveData(){}


	public UploadedFile getFile() {
		return file;
	}


	public void setFile(UploadedFile file) {
		this.file = file;
	}


	public String getStatusMessage() {
		return statusMessage;
	}


	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}


	public List<StudentCourseDTO> getStudentCourseLst() {
		return studentCourseLst;
	}


	public void setStudentCourseLst(List<StudentCourseDTO> studentCourseLst) {
		this.studentCourseLst = studentCourseLst;
	}


	public StudentCourseDTO getSelectedstudentCourseLst() {
		return selectedstudentCourseLst;
	}


	public void setSelectedstudentCourseLst(
			StudentCourseDTO selectedstudentCourseLst) {
		this.selectedstudentCourseLst = selectedstudentCourseLst;
	}


	public List<StudentCourseDTO> getFilteredStudentCourseLst() {
		return filteredStudentCourseLst;
	}


	public void setFilteredStudentCourseLst(
			List<StudentCourseDTO> filteredStudentCourseLst) {
		this.filteredStudentCourseLst = filteredStudentCourseLst;
	}


	public boolean isSaveMood() {
		return saveMood;
	}


	public void setSaveMood(boolean saveMood) {
		this.saveMood = saveMood;
	}


	public IStudentCourseFacade getFacade() {
		return facade;
	}


	public void setFacade(IStudentCourseFacade facade) {
		this.facade = facade;
	}
	
	
	
	
}
