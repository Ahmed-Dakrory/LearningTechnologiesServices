package main.com.zc.services.presentation.configuration.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

@SessionScoped
@ManagedBean(name="StudentsBean")
public class StudentsBean {

	private List<StudentDTO> students;
	private StudentDTO detailedStudent;
	private List<CoursesDTO> courses;
	private List<BaseDTO> semesterLst;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<Integer> yearLst;
	private List<CoursesDTO> addedCoursesLst;
	private Integer selectedCourseID;
	@ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade facade; 
	
	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade coursesFacade;
	
	@PostConstruct
	public void init()
	{
		students=new ArrayList<StudentDTO>();
		fillStudentsList();
		
	}
	public void fillStudentsList()
	{
		students=facade.getAllStudents();
	}
	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<Integer>();
		 setSelectedYear(null);
		 setSelectedCourseID(null);
		for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	public void fillCourseLst(AjaxBehaviorEvent event)
	{
		addedCoursesLst=coursesFacade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		
		
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		StudentDTO selectedDTO=(StudentDTO) event.getObject();
	  		setDetailedStudent(selectedDTO);
	  		
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("StudentDetails.xhtml");
	    			//instructors=courseInsFacade.getAllInstructorsByCourseId(selectedDTO.getId());
	    			courses=facade.getCoursesOfStudent(getDetailedStudent().getId());
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void preDelete(){}
	public void deleteCourse(CoursesDTO course)
	{
		try{
		      boolean b=facade.deleteCourseForStudent(course.getId(),getDetailedStudent().getId());
		      if(b)
		      {
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Deleted successfully!");
		    		courses=facade.getCoursesOfStudent(getDetailedStudent().getId());
		    		try {
			    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			    		StringBuffer url=origRequest.getRequestURL();
			    			FacesContext.getCurrentInstance().getExternalContext().redirect
							("StudentDetails.xhtml");
			    			
			    		
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		      }
		      else 
		      {
		    	  RequestContext.getCurrentInstance().execute("delDlg.hide();");
		    	  JavaScriptMessagesHandler.RegisterErrorMessage(null, "Can't delete this course because there are assigned instructors or petitions");
		      }
				}
				catch(Exception ex)
				{
					  RequestContext.getCurrentInstance().execute("delDlg.hide();");
			    	  JavaScriptMessagesHandler.RegisterErrorMessage(null, "Can't delete this course because there are assigned instructors or petitions");
				}
	}
	public void preAddCourse()
	{
		RequestContext.getCurrentInstance().reset("dlgForm");
		setSelectedCourseID(null);
		setSelectedSemester(null);
		setSelectedYear(null);
		fillSemesterLst();
		RequestContext.getCurrentInstance().execute("addCourseDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("dlgForm");
	}
	public List<StudentDTO> getStudents() {
		fillStudentsList();
		return students;
	}
	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}
	public IStudentCourseFacade getFacade() {
		return facade;
	}
	public void setFacade(IStudentCourseFacade facade) {
		this.facade = facade;
	}
	public StudentDTO getDetailedStudent() {
		return detailedStudent;
	}
	public void setDetailedStudent(StudentDTO detailedStudent) {
		this.detailedStudent = detailedStudent;
	}
	public List<CoursesDTO> getCourses() {
		return courses;
	}
	public void setCourses(List<CoursesDTO> courses) {
		this.courses = courses;
	}
	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}
	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}
	public Integer getSelectedSemester() {
		return selectedSemester;
	}
	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}
	public Integer getSelectedYear() {
		return selectedYear;
	}
	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}
	public List<Integer> getYearLst() {
		return yearLst;
	}
	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}
	public List<CoursesDTO> getAddedCoursesLst() {
		return addedCoursesLst;
	}
	public void setAddedCoursesLst(List<CoursesDTO> addedCoursesLst) {
		this.addedCoursesLst = addedCoursesLst;
	}
	public Integer getSelectedCourseID() {
		return selectedCourseID;
	}
	public void setSelectedCourseID(Integer selectedCourseID) {
		this.selectedCourseID = selectedCourseID;
	}
	public IStudentAcademicPetFacade getCoursesFacade() {
		return coursesFacade;
	}
	public void setCoursesFacade(IStudentAcademicPetFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}
	
}
