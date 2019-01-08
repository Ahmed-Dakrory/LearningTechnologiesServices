/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IInstructorFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="InstructorsBean")
public class InstructorsBean {

	private List<InstructorDTO> instructors;
	private InstructorDTO selectedIns;
	private InstructorDTO detailedInstructor;
	private List<CoursesDTO> courses;
	private String addedInsName;
	private String addedInsMail;
	private Integer selectedrole;
	@ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade facade; 
	

	@ManagedProperty("#{IInstructorFacade}")
	private IInstructorFacade insFacade;
	
	
	
	
	
	@PostConstruct
	public void init()
	{
		instructors=new ArrayList<InstructorDTO>();
		fillInsList();
		
	}
	public void fillInsList()
	{
		instructors=facade.getAllEmp();
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		InstructorDTO selectedDTO=(InstructorDTO) event.getObject();
	  		setDetailedInstructor(selectedDTO);
	  		
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("instructorDetails.xhtml");
	    			
	    			courses=facade.getCoursesOfInstructor(getDetailedInstructor().getId());
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void preAddInstructor()
	{
		//RequestContext.getCurrentInstance().reset(":addInsForm");
		setAddedInsMail(null);
		setAddedInsName(null);
		RequestContext.getCurrentInstance().execute("addInsDlg.show();");
		/*FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add(":addInsForm");*/
	}
	public void addIns(){
		InstructorDTO ins =new InstructorDTO();
		ins.setMail(getAddedInsMail());
		ins.setName(getAddedInsName());
		ins.setEmpType(getSelectedrole());
		InstructorDTO dto=facade.addIns(ins);
		/*
		 * A7med Dakrory
		 * Adding Instructor to the database
		 */
		insFacade.update(ins);
		if(dto!=null)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added Successfully");
			fillInsList();
			RequestContext.getCurrentInstance().execute("addInsDlg.hide();");
			setSelectedIns(dto);
		}
		else
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error");
		}
		
	}
	public List<InstructorDTO> getInstructors() {
		return instructors;
	}
	public void setInstructors(List<InstructorDTO> instructors) {
		this.instructors = instructors;
	}
	public InstructorDTO getDetailedInstructor() {
		return detailedInstructor;
	}
	public void setDetailedInstructor(InstructorDTO detailedInstructor) {
		this.detailedInstructor = detailedInstructor;
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
	public String getAddedInsName() {
		return addedInsName;
	}
	public void setAddedInsName(String addedInsName) {
		this.addedInsName = addedInsName;
	}
	public String getAddedInsMail() {
		return addedInsMail;
	}
	public void setAddedInsMail(String addedInsMail) {
		this.addedInsMail = addedInsMail;
	}
	public InstructorDTO getSelectedIns() {
		return selectedIns;
	}
	public void setSelectedIns(InstructorDTO selectedIns) {
		this.selectedIns = selectedIns;
	}
	public Integer getSelectedrole() {
		return selectedrole;
	}
	public void setSelectedrole(Integer selectedrole) {
		this.selectedrole = selectedrole;
	}
	public IInstructorFacade getInsFacade() {
		return insFacade;
	}
	public void setInsFacade(IInstructorFacade insFacade) {
		this.insFacade = insFacade;
	}
	
}
