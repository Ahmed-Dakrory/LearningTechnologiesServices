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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="CoursesInstructorsConfigBean")
public class CoursesInstructorsConfigBean {

	private List<BaseDTO> semesterLst;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<Integer> yearLst;
	private List<Integer> newCourseYearLst;
	private List<CoursesDTO> coursesLst;
	private Integer selectedCourseID;
	private List<InstructorDTO> instructors;
	private List<InstructorDTO> tas;
	private List<InstructorDTO> allIns;
	private List<InstructorDTO> allTas;
	private CoursesDTO deletedCourse;
	private CoursesDTO detailedCourse;
	private List<InstructorDTO> instructorsOfCourse;
	private Integer selectedInstructor;
	private Integer selectedTA;
	private Integer selectedCoordinator;
	private String newCourseName;
	private Integer newSelectedCoordinator;
	private Integer newCourseYear;
	private Integer newCourseSemester;
	private List<CoursesDTO> filteredCoursesLst;
	@ManagedProperty("#{StudentAcademicPetFacadeImpl}")
	private IStudentAcademicPetFacade facade;
	
	@ManagedProperty("#{ICourseInstructorFacade}")
	private ICourseInstructorFacade courseInsFacade;
	@PostConstruct
	public void init()
	{
		fillSemesterLst();
		fillIns();
		instructorsOfCourse=new ArrayList<InstructorDTO>();
		newCourseYearLst=new ArrayList<Integer>();
		filteredCoursesLst=new ArrayList<CoursesDTO>();
		for(int i=2013;i<2031;i++)
		{
			newCourseYearLst.add(i);
		}
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
		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		for(int i=0;i<coursesLst.size();i++)
		{
			instructors=courseInsFacade.getAllInstructorsByCourseId(coursesLst.get(i).getId());
			tas=courseInsFacade.getAllTAsByCourseId(coursesLst.get(i).getId());
			coursesLst.get(i).setInstructorsNum(instructors.size());
		}
		
	}
	public void fillIns()
	{
		allIns=courseInsFacade.getAllInstructors();
	}
	public void fillTas()
	{
		allTas=courseInsFacade.getAllTas();
	}
	public void preDelete(){
		setDeletedCourse(getDetailedCourse());
		RequestContext.getCurrentInstance().execute("delDlg.show();");
	}
	public void deleteCourse()
	{
		try{
      boolean b=courseInsFacade.deleteCourse(deletedCourse);
      if(b)
      {
    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Deleted successfully!");
    		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
    		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("coursesAndInstructors.xhtml");
	    			
	    		
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
	public void deleteSelectedCourse(CoursesDTO course)
	{
		try{
		      boolean b=courseInsFacade.deleteCourse(course);
		      if(b)
		      {
		    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Deleted successfully!");
		    		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		    		try {
			    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			    		StringBuffer url=origRequest.getRequestURL();
			    			FacesContext.getCurrentInstance().getExternalContext().redirect
							("coursesAndInstructors.xhtml");
			    			
			    		
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
	
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		CoursesDTO selectedDTO=(CoursesDTO) event.getObject();
	  		setDetailedCourse(selectedDTO);
	  		
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseDetails.xhtml");
	    			instructors=courseInsFacade.getAllInstructorsByCourseId(selectedDTO.getId());
	    		    tas=courseInsFacade.getAllTAsByCourseId(selectedDTO.getId());
	    		    setSelectedInstructor(null);
	    		    setSelectedTA(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String backToCourses()
	{
		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
		return "coursesAndInstructors.xhtml?faces-redirect=true";
	}
	public void deleteInstructorFromCourse(InstructorDTO instructor){
		 boolean b=courseInsFacade.deleteInstructorFromCourse(instructor, getDetailedCourse());
	      if(b)
	      {
	    		JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Deleted successfully!");
	    		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
	    		detailedCourse=courseInsFacade.getCourseById(getDetailedCourse().getId());
	    		try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("courseDetails.xhtml");
		    			instructors=courseInsFacade.getAllInstructorsByCourseId(detailedCourse.getId());
		    			tas=courseInsFacade.getAllTAsByCourseId(detailedCourse.getId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	      }
	      else 
	      {
	    	  RequestContext.getCurrentInstance().execute("delDlg.hide();");
	    	  JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Can't delete this course because there are assigned petitions");
	    	  detailedCourse=courseInsFacade.getCourseById(getDetailedCourse().getId());
	    		try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("courseDetails.xhtml");
		    			instructors=courseInsFacade.getAllInstructorsByCourseId(detailedCourse.getId());
		    			tas=courseInsFacade.getAllTAsByCourseId(detailedCourse.getId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
	}
	
	public void addInsToCourse()
	{
		boolean b=courseInsFacade.addInstructorToCourse(getSelectedInstructor(), getDetailedCourse().getId());
		if(b)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added successfully!");
    		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
    		detailedCourse=courseInsFacade.getCourseById(getDetailedCourse().getId());
    		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseDetails.xhtml");
	    			instructors=courseInsFacade.getAllInstructorsByCourseId(detailedCourse.getId());
	    			tas=courseInsFacade.getAllTAsByCourseId(detailedCourse.getId());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failure");
		}
	}
	public void addTAToCourse()
	{
		boolean b=courseInsFacade.addInstructorToCourse(getSelectedTA(), getDetailedCourse().getId());
		if(b)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added successfully!");
    		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
    		detailedCourse=courseInsFacade.getCourseById(getDetailedCourse().getId());
    		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("courseDetails.xhtml");
	    			tas=courseInsFacade.getAllTAsByCourseId(detailedCourse.getId());
	    			instructors=courseInsFacade.getAllInstructorsByCourseId(detailedCourse.getId());
		    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failure");
		}
	}
	public void editCourseCoordinator(){
		try
		{
			InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedCoordinator());
			
			getDetailedCourse().setCoordinator(ins);
			CoursesDTO dto=courseInsFacade.editCourseCoordinator(getDetailedCourse());
			if(dto!=null){
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Edited successfully!");
	    		coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
	    		detailedCourse=courseInsFacade.getCourseById(getDetailedCourse().getId());
	    		try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("courseDetails.xhtml");
		    			tas=courseInsFacade.getAllTAsByCourseId(detailedCourse.getId());
		    			instructors=courseInsFacade.getAllInstructorsByCourseId(detailedCourse.getId());
			    		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failure");
			}
		}
		catch(Exception ex)
		{
			
		}
		
	}
	public void addNewCourse()
	{
		try
		{
			CoursesDTO dto=new CoursesDTO();
			dto.setName(getNewCourseName());
			dto.setYear(getNewCourseYear());
			if(getNewCourseSemester()==0)
			{
				dto.setSemester(SemesterEnum.Fall);	
			}
			else if(getNewCourseSemester()==1)
			{
				dto.setSemester(SemesterEnum.Spring);
			}
			else if(getNewCourseSemester()==2)
			{
				dto.setSemester(SemesterEnum.Summer);
			}
			
			InstructorDTO ins=new InstructorDTO();
			ins.setId(getNewSelectedCoordinator());
			dto.setCoordinator(ins);
			 dto=courseInsFacade.addNewCourse(dto);
			if(dto!=null){
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added successfully!");
	    		setSelectedSemester(getNewCourseSemester());
	    		setSelectedYear(getNewCourseYear());
				coursesLst=facade.getAllCoursesBySemesterAndYear(getSelectedSemester(), getSelectedYear());
	    		
	    		//detailedCourse=courseInsFacade.getCourseById(getDetailedCourse().getId());
	    		/*try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("courseDetails.xhtml");
		    			instructors=courseInsFacade.getAllInstructorsByCourseId(detailedCourse.getId());
		    		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
			else 
			{
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "Failure");
			}
		}
		catch(Exception ex)
		{
			
		}
		
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
	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public Integer getSelectedCourseID() {
		return selectedCourseID;
	}
	public void setSelectedCourseID(Integer selectedCourseID) {
		this.selectedCourseID = selectedCourseID;
	}
	public IStudentAcademicPetFacade getFacade() {
		return facade;
	}
	public void setFacade(IStudentAcademicPetFacade facade) {
		this.facade = facade;
	}
	public ICourseInstructorFacade getCourseInsFacade() {
		return courseInsFacade;
	}
	public void setCourseInsFacade(ICourseInstructorFacade courseInsFacade) {
		this.courseInsFacade = courseInsFacade;
	}
	public List<InstructorDTO> getInstructors() {
		return instructors;
	}
	public void setInstructors(List<InstructorDTO> instructors) {
		this.instructors = instructors;
	}
	public CoursesDTO getDeletedCourse() {
		return deletedCourse;
	}
	public void setDeletedCourse(CoursesDTO deletedCourse) {
		this.deletedCourse = deletedCourse;
	}
	public CoursesDTO getDetailedCourse() {
		return detailedCourse;
	}
	public void setDetailedCourse(CoursesDTO detailedCourse) {
		this.detailedCourse = detailedCourse;
	}
	public List<InstructorDTO> getInstructorsOfCourse() {
		return instructorsOfCourse;
	}
	public void setInstructorsOfCourse(List<InstructorDTO> instructorsOfCourse) {
		this.instructorsOfCourse = instructorsOfCourse;
	}
	public List<InstructorDTO> getAllIns() {
		fillIns();		
		return allIns;
	}
	public void setAllIns(List<InstructorDTO> allIns) {
		this.allIns = allIns;
	}
	public Integer getSelectedInstructor() {
		return selectedInstructor;
	}
	public void setSelectedInstructor(Integer selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}
	public Integer getSelectedCoordinator() {
		return selectedCoordinator;
	}
	public void setSelectedCoordinator(Integer selectedCoordinator) {
		this.selectedCoordinator = selectedCoordinator;
	}
	public String getNewCourseName() {
		return newCourseName;
	}
	public void setNewCourseName(String newCourseName) {
		this.newCourseName = newCourseName;
	}
	public Integer getNewSelectedCoordinator() {
		return newSelectedCoordinator;
	}
	public void setNewSelectedCoordinator(Integer newSelectedCoordinator) {
		this.newSelectedCoordinator = newSelectedCoordinator;
	}
	public Integer getNewCourseYear() {
		return newCourseYear;
	}
	public void setNewCourseYear(Integer newCourseYear) {
		this.newCourseYear = newCourseYear;
	}
	public Integer getNewCourseSemester() {
		return newCourseSemester;
	}
	public void setNewCourseSemester(Integer newCourseSemester) {
		this.newCourseSemester = newCourseSemester;
	}
	public List<Integer> getNewCourseYearLst() {
		return newCourseYearLst;
	}
	public void setNewCourseYearLst(List<Integer> newCourseYearLst) {
		this.newCourseYearLst = newCourseYearLst;
	}
	public List<CoursesDTO> getFilteredCoursesLst() {
		return filteredCoursesLst;
	}
	public void setFilteredCoursesLst(List<CoursesDTO> filteredCoursesLst) {
		this.filteredCoursesLst = filteredCoursesLst;
	}
	public List<InstructorDTO> getTas() {
		return tas;
	}
	public void setTas(List<InstructorDTO> tas) {
		this.tas = tas;
	}
	public Integer getSelectedTA() {
		return selectedTA;
	}
	public void setSelectedTA(Integer selectedTA) {
		this.selectedTA = selectedTA;
	}
	public List<InstructorDTO> getAllTas() {
		fillTas();		
		return allTas;
	}
	public void setAllTas(List<InstructorDTO> allTas) {
		this.allTas = allTas;
	}
	
	
}
