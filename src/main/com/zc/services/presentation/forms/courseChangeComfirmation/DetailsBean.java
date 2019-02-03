/**
 * 
 */
package main.com.zc.services.presentation.forms.courseChangeComfirmation;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.forms.courseChangeComfirmation.implementation.CCCAppServiceImpl;
import main.com.zc.services.presentation.shared.facade.impl.CouresFacadeImpl;
import main.com.zc.services.presentation.shared.facade.impl.MajorsFacadeImpl;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.impl.StudentFacadeImpl;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DetailsBeanChangeCourseComfirmation")
@ViewScoped
public class DetailsBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
    
    @ManagedProperty("#{IStudentFacade}")
    private StudentFacadeImpl studentFacadeImpl;
    

    @ManagedProperty("#{IStudentProfileFacade}")
	private StudentProfileFacadeImpl facade;
    
    @ManagedProperty("#{IMajorsFacade}")
	private MajorsFacadeImpl majorfacade;
    
    @ManagedProperty("#{ICouresFacade}")
	private CouresFacadeImpl coursesfacade;
    
    @ManagedProperty("#{CCCFacadeImpl}")
	private CCCAppServiceImpl cccAppServiceImpl;
    
	
    private StudentDTO student;
    private MajorDTO major;
    private List<CoursesDTO> coursesDTOsTaken;
    private List<CoursesDTO> coursesDTOsAll;
    private List<CCC> courseChangeComfirmations;
    private int studentId;
    

    
    private int stateOfReq;
    private int courseTakenId;
    private int courseRelativeId;
    private CCC newCourseComfirmation;
    
    
	@PostConstruct
	public void init()
	{
HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		
		try{

			Integer id=Integer.parseInt(origRequest.getParameter("id"));
			
				if(id!=null){
					studentId=id;
				}
				
			}
		catch(Exception ex){
			 
		}
		
		student=studentFacadeImpl.getById(studentId);
		newCourseComfirmation=new CCC();
		courseChangeComfirmations=cccAppServiceImpl.getByStudentId(studentId);
		student.setStudentProfileDTO(facade.getCurrentPRofileByStudentID(studentId));
		major=majorfacade.getById(student.getStudentProfileDTO().getMajor().getId());
		//coursesDTOsTaken=coursesfacade.getCoursesByStudentID(student.getId());
		coursesDTOsTaken=coursesfacade.getAll();
		coursesDTOsAll=coursesfacade.getAll();
		System.out.println("Ahmed Dakrory: "+String.valueOf(major.getType()));
	
	}
	
	
	public void addCCC(){
		stateOfReq=0;
		Courses courseTaken= new Courses();
		courseTaken.setId(courseTakenId);
		Courses courseRelative= new Courses();
		courseRelative.setId(courseRelativeId);
		Student student=new Student();
		student.setId(studentId);
		newCourseComfirmation.setStudent(student);
		newCourseComfirmation.setStateStep(stateOfReq);
		newCourseComfirmation.setCourseOld(courseTaken);
		newCourseComfirmation.setNewCourse(courseRelative);
		newCourseComfirmation.setMajorId(major.getId());
		newCourseComfirmation.setType(major.getType());
		newCourseComfirmation.setAction(0);
		newCourseComfirmation.setDate(Calendar.getInstance());
		newCourseComfirmation.setUpdateDate(Calendar.getInstance());
		cccAppServiceImpl.addCCC(newCourseComfirmation);
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Course comfirmation requist was added successfully");

		courseChangeComfirmations=cccAppServiceImpl.getByStudentId(studentId);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addCourseComfirmationDlg.hide();");
		newCourseComfirmation=new CCC();
	}
	
	public void deleteCCC(int Id){
		/*preRequisitFacade.delete(preRequisitFacade.getById(Id));
		courseSyllabusCollection.setPre_Requisites(preRequisitFacade.getByCourseId(courseId));
		*/
		cccAppServiceImpl.delete(cccAppServiceImpl.getById(Id));
		courseChangeComfirmations=cccAppServiceImpl.getByStudentId(studentId);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Course comfirmation requist was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public String getCourseCountAsString(int countAs) {
		switch(countAs){
		case 0:
			return "Major Req";
		case 1:
			return "Humanities";
		case 2:
			return "Major Elective";
		case 3:
			return "Collateral Req";
			
		}
		return "";
	}
	
	public String getMajorType(int type) {
		switch(type){
		case 1:
			return "Science";
		case 2:
			return "Engineering";
			
		}
		return "";
	}

	
	
	
	
	
	
	
	
	
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public StudentFacadeImpl getStudentFacadeImpl() {
		return studentFacadeImpl;
	}
	public void setStudentFacadeImpl(StudentFacadeImpl studentFacadeImpl) {
		this.studentFacadeImpl = studentFacadeImpl;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public StudentProfileFacadeImpl getFacade() {
		return facade;
	}
	
	public void setFacade(StudentProfileFacadeImpl facade) {
		this.facade = facade;
	}

	public MajorsFacadeImpl getMajorfacade() {
		return majorfacade;
	}

	public void setMajorfacade(MajorsFacadeImpl majorfacade) {
		this.majorfacade = majorfacade;
	}

	public CouresFacadeImpl getCoursesfacade() {
		return coursesfacade;
	}

	public void setCoursesfacade(CouresFacadeImpl coursesfacade) {
		this.coursesfacade = coursesfacade;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	public List<CoursesDTO> getCoursesDTOsTaken() {
		return coursesDTOsTaken;
	}

	public void setCoursesDTOsTaken(List<CoursesDTO> coursesDTOsTaken) {
		this.coursesDTOsTaken = coursesDTOsTaken;
	}

	public List<CoursesDTO> getCoursesDTOsAll() {
		return coursesDTOsAll;
	}

	public void setCoursesDTOsAll(List<CoursesDTO> coursesDTOsAll) {
		this.coursesDTOsAll = coursesDTOsAll;
	}

	public CCCAppServiceImpl getCccAppServiceImpl() {
		return cccAppServiceImpl;
	}
	
	public void setCccAppServiceImpl(CCCAppServiceImpl cccAppServiceImpl) {
		this.cccAppServiceImpl = cccAppServiceImpl;
	}

	public List<CCC> getCourseChangeComfirmations() {
		return courseChangeComfirmations;
	}

	public void setCourseChangeComfirmations(List<CCC> courseChangeComfirmations) {
		this.courseChangeComfirmations = courseChangeComfirmations;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}


	public int getStateOfReq() {
		return stateOfReq;
	}


	public void setStateOfReq(int stateOfReq) {
		this.stateOfReq = stateOfReq;
	}


	public CCC getNewCourseComfirmation() {
		return newCourseComfirmation;
	}


	public void setNewCourseComfirmation(CCC newCourseComfirmation) {
		this.newCourseComfirmation = newCourseComfirmation;
	}


	public int getCourseTakenId() {
		return courseTakenId;
	}


	public void setCourseTakenId(int courseTakenId) {
		this.courseTakenId = courseTakenId;
	}


	public int getCourseRelativeId() {
		return courseRelativeId;
	}


	public void setCourseRelativeId(int courseRelativeId) {
		this.courseRelativeId = courseRelativeId;
	}

}
