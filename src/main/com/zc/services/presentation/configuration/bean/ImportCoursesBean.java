/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.model.UploadedFile;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="ImportCoursesBean")
public class ImportCoursesBean {

	
	private UploadedFile file;
	private String statusMessage;
	private boolean saveMood;
	private List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
	private List<CoursesDTO> list;
	private boolean resultText;
	@ManagedProperty("#{IStudentCourseFacade}")
	   	private IStudentCourseFacade facade; 
	
	@PostConstruct
	public void init()
	{
		courses=new ArrayList<CoursesDTO>();
		list=new ArrayList<CoursesDTO>();
		/*filteredStudentCourseLst=new ArrayList<StudentCourseDTO>();
		selectedstudentCourseLst=new StudentCourseDTO();*/
		
	}
	
	public String parseFile()
	{
		 InputStream inputStream = null;
		 try {
			inputStream=file.getInputstream();
			list=facade.parseCoursesFile(inputStream);
			 
		    Map<String, CoursesDTO> map = new LinkedHashMap();
		    for (CoursesDTO ays : list) {
		      map.put(ays.getName(), ays);
		    }
		    list.clear();
		    list.addAll(map.values());
		    courses=facade.getNewCourses(list);
		    //for(int i=0;i<courses.size();i++) {
		    	//System.out.println("Ahmed: "+String.valueOf(courses.get(i).getName()));
		    	//System.out.println("Ahmed: "+String.valueOf(courses.get(i).getCourseCoordinator().getMail()));
		    //}
			System.out.println("Size : "+list.size());
			resultText=true;
			//courses=facade.saveCourses(list);
			//System.out.println("Added Courses size is "+courses.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	}
	public void saveCourse()
	{
		courses=facade.saveCourses(list);
		if(courses.size()!=0)
		{
			 JavaScriptMessagesHandler.RegisterNotificationMessage(null, courses.size()+" course(s) has(have) been saved Successfully"); 
		}
		System.out.println("Added Courses size is "+courses.size());
	}

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

	public boolean isSaveMood() {
		return saveMood;
	}

	public void setSaveMood(boolean saveMood) {
		this.saveMood = saveMood;
	}

	public List<CoursesDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CoursesDTO> courses) {
		this.courses = courses;
	}

	public IStudentCourseFacade getFacade() {
		return facade;
	}

	public void setFacade(IStudentCourseFacade facade) {
		this.facade = facade;
	}

	public List<CoursesDTO> getList() {
		return list;
	}

	public void setList(List<CoursesDTO> list) {
		this.list = list;
	}

	public boolean isResultText() {
		return resultText;
	}

	public void setResultText(boolean resultText) {
		this.resultText = resultText;
	}
	
	
	
}
