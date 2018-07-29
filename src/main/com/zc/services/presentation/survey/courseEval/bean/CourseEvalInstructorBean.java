/**
 * 
 */
package main.com.zc.services.presentation.survey.courseEval.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.PieChartModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;

/**
 * @author omnya
 *
 */
@ManagedBean(name="CourseEvalInstructorBean")
@SessionScoped
public class CourseEvalInstructorBean {

	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	@ManagedProperty("#{StudentAddDropFormFacadeImpl}")
	private IStudentAddDropFormFacade coursesFacade;
	
	List<CourseEvalQuestionsDTO> facultyEvalAnswers;
	List<CourseEvalQuestionsDTO> courseEvalCourseAnswers;
	List<CourseEvalQuestionsDTO> courseEvalAssignAnswers;
	List<CourseEvalQuestionsDTO> labExpAnswers;
	List<CourseEvalQuestionsDTO> labFacAnswers;
	List<CourseEvalQuestionsDTO> othersTextEvalAnswers;
	List<CourseEvalQuestionsDTO> othersRateEvalAnswers;
	List<CourseEvalQuestionsDTO> teachingAsisEvalAnswers;
	private List<InstructorDTO>insLst=new ArrayList<InstructorDTO>();
	List<Integer> notLabCourses=new ArrayList<Integer>();
	List<Integer> hiddenCourses=new ArrayList<Integer>();
	List<String> facultyComments=new ArrayList<String>();
	List<String> taComments=new ArrayList<String>();
	List<CoursesDTO> courses;
	private InstructorDTO loggedInInstructor;
	private Integer selectedCourse;
	private boolean notlab;
	private Integer selectedIns;
	private String courseName;
	
	@PostConstruct
	public void init()
	{
		facultyEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		courseEvalCourseAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		courseEvalAssignAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		labExpAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		labFacAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		othersTextEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		othersRateEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		teachingAsisEvalAnswers=new ArrayList<CourseEvalQuestionsDTO>();
		courses=new ArrayList<CoursesDTO>();
		
		notLabCourses=new ArrayList<Integer>();
		notLabCourses.add(60);
		notLabCourses.add(120);
		notLabCourses.add(145);
		notLabCourses.add(94);
		notLabCourses.add(119);
		notLabCourses.add(41);
		notLabCourses.add(33);
		notLabCourses.add(126);
		notLabCourses.add(108);
		notLabCourses.add(109);
		notLabCourses.add(110);
		notLabCourses.add(111);
		notLabCourses.add(125);
		notLabCourses.add(27);
		notLabCourses.add(3);
		notLabCourses.add(118);
		notLabCourses.add(92);
		notLabCourses.add(148);
		notLabCourses.add(117);
		notLabCourses.add(116);
		notLabCourses.add(149);
		notLabCourses.add(23);
		notLabCourses.add(129);
		notLabCourses.add(128);
		notLabCourses.add(19);
		notLabCourses.add(149);
		

		hiddenCourses.add(133);
		hiddenCourses.add(127);
		hiddenCourses.add(134);
		hiddenCourses.add(141);
		hiddenCourses.add(139);
		hiddenCourses.add(140);
		hiddenCourses.add(135);
		hiddenCourses.add(136);
		hiddenCourses.add(137);
		
		for(int i=0;i<notLabCourses.size();i++)
		{
			if(getSelectedCourse()==notLabCourses.get(i))
			{
			setNotlab(false);
			break;
			}
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
			{
			loggedInInstructor=getInsDataFacade.getInsByPersonMail(authentication.getName());
			//courses=ansFacade.getCoursesByInsID(loggedInInstructor.getId());
			courses=ansFacade.getAllCourses();
			
			for(int i=0;i<courses.size();i++)
			{
				for(int j=0;j<hiddenCourses.size();j++)
				{
					if(courses.get(i).getId()==hiddenCourses.get(j))
					{
						courses.remove(i);
						
					}
				}
			}
			
		}}
		}
	
	 public void fillInstructorsLst()
		{
			insLst=new ArrayList<InstructorDTO>();
			insLst=coursesFacade.getAllInstructorsOfCourse(getSelectedCourse());
		}
	public void startView()
	{
		courseName=ansFacade.getCourseById(getSelectedCourse()).getName();
		
		facultyEvalAnswers=ansFacade.getFacEvalByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
		facultyComments=ansFacade.getCommentsByCategoryIDAndCourseIDAndInstructorID(getSelectedCourse(),getSelectedIns(),1);
		
	}
	public void courseEvalView()
	{
		courseEvalAssignAnswers=ansFacade.getCourseAssignByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
		courseEvalCourseAnswers=ansFacade.getCourseByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("courseEvalIns.xhtml?-faces-redirect=true");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void taView()
	{
		teachingAsisEvalAnswers=ansFacade.getTAByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
		taComments=ansFacade.getCommentsByCategoryIDAndCourseID(getSelectedCourse(),4);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("teachingAssistantIns.xhtml?-faces-redirect=true");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void labView()
	{
    	boolean existed = false;
    	for(int i=0;i<notLabCourses.size();i++)
    	{
    		if(notLabCourses.get(i)==getSelectedCourse())
    		{
    			existed=true;
    		
    			break;
    		}
    	}
    	if(existed)
    		
    	{
    	try {
    		othersTextEvalAnswers=ansFacade.getOthersTextByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
    		othersRateEvalAnswers=ansFacade.getOthersRateByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
			FacesContext.getCurrentInstance().getExternalContext().redirect("otherQuesIns.xhtml?-faces-redirect=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	else 
    	{
    		labExpAnswers=ansFacade.getLabExpByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
    		labFacAnswers=ansFacade.getLabFacByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
    		try {
    			FacesContext.getCurrentInstance().getExternalContext().redirect("labEvalIns.xhtml?-faces-redirect=true");
    		
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
		
	}
	public void othersView()
	{
		othersTextEvalAnswers=ansFacade.getOthersTextByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
		othersRateEvalAnswers=ansFacade.getOthersRateByCourseIDAndInstructorID(getSelectedCourse(), getSelectedIns());
		try {
    		
			FacesContext.getCurrentInstance().getExternalContext().redirect("otherQuesIns.xhtml?-faces-redirect=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}

	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	

	public List<CoursesDTO> getCourses() {
		return courses;
	}

	public void setCourses(List<CoursesDTO> courses) {
		this.courses = courses;
	}

	public InstructorDTO getLoggedInInstructor() {
		return loggedInInstructor;
	}

	public void setLoggedInInstructor(InstructorDTO loggedInInstructor) {
		this.loggedInInstructor = loggedInInstructor;
	}

	public Integer getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(Integer selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public List<Integer> getNotLabCourses() {
		return notLabCourses;
	}

	public void setNotLabCourses(List<Integer> notLabCourses) {
		this.notLabCourses = notLabCourses;
	}

	public boolean isNotlab() {
		boolean exist=true;
		for(int i=0;i<notLabCourses.size();i++)
		{
			if(getSelectedCourse().equals(notLabCourses.get(i)))
			{
				exist= false;
			
			break;
			}
		}
		return exist;
	}

	public void setNotlab(boolean notlab) {
		this.notlab = notlab;
	}

	public List<Integer> getHiddenCourses() {
		return hiddenCourses;
	}

	public void setHiddenCourses(List<Integer> hiddenCourses) {
		this.hiddenCourses = hiddenCourses;
	}

	public List<CourseEvalQuestionsDTO> getFacultyEvalAnswers() {
		return facultyEvalAnswers;
	}

	public void setFacultyEvalAnswers(
			List<CourseEvalQuestionsDTO> facultyEvalAnswers) {
		this.facultyEvalAnswers = facultyEvalAnswers;
	}

	public List<CourseEvalQuestionsDTO> getCourseEvalCourseAnswers() {
		return courseEvalCourseAnswers;
	}

	public void setCourseEvalCourseAnswers(
			List<CourseEvalQuestionsDTO> courseEvalCourseAnswers) {
		this.courseEvalCourseAnswers = courseEvalCourseAnswers;
	}

	public List<CourseEvalQuestionsDTO> getCourseEvalAssignAnswers() {
		return courseEvalAssignAnswers;
	}

	public void setCourseEvalAssignAnswers(
			List<CourseEvalQuestionsDTO> courseEvalAssignAnswers) {
		this.courseEvalAssignAnswers = courseEvalAssignAnswers;
	}

	public List<CourseEvalQuestionsDTO> getLabExpAnswers() {
		return labExpAnswers;
	}

	public void setLabExpAnswers(List<CourseEvalQuestionsDTO> labExpAnswers) {
		this.labExpAnswers = labExpAnswers;
	}

	public List<CourseEvalQuestionsDTO> getLabFacAnswers() {
		return labFacAnswers;
	}

	public void setLabFacAnswers(List<CourseEvalQuestionsDTO> labFacAnswers) {
		this.labFacAnswers = labFacAnswers;
	}

	public List<CourseEvalQuestionsDTO> getOthersTextEvalAnswers() {
		return othersTextEvalAnswers;
	}

	public void setOthersTextEvalAnswers(
			List<CourseEvalQuestionsDTO> othersTextEvalAnswers) {
		this.othersTextEvalAnswers = othersTextEvalAnswers;
	}

	public List<CourseEvalQuestionsDTO> getOthersRateEvalAnswers() {
		return othersRateEvalAnswers;
	}

	public void setOthersRateEvalAnswers(
			List<CourseEvalQuestionsDTO> othersRateEvalAnswers) {
		this.othersRateEvalAnswers = othersRateEvalAnswers;
	}

	public List<CourseEvalQuestionsDTO> getTeachingAsisEvalAnswers() {
		return teachingAsisEvalAnswers;
	}

	public void setTeachingAsisEvalAnswers(
			List<CourseEvalQuestionsDTO> teachingAsisEvalAnswers) {
		this.teachingAsisEvalAnswers = teachingAsisEvalAnswers;
	}

	public List<String> getFacultyComments() {
		return facultyComments;
	}

	public void setFacultyComments(List<String> facultyComments) {
		this.facultyComments = facultyComments;
	}

	public List<String> getTaComments() {
		return taComments;
	}

	public void setTaComments(List<String> taComments) {
		this.taComments = taComments;
	}

	public IStudentAddDropFormFacade getCoursesFacade() {
		return coursesFacade;
	}

	public void setCoursesFacade(IStudentAddDropFormFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}

	public List<InstructorDTO> getInsLst() {
		return insLst;
	}

	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}

	public Integer getSelectedIns() {
		return selectedIns;
	}

	public void setSelectedIns(Integer selectedIns) {
		this.selectedIns = selectedIns;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	
	
	}

