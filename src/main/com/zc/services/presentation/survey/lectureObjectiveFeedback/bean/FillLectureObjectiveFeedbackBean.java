/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.shared.facade.ICouresFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.ISemesterWeeksFacade;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.IWeekFeedbackFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.IInstructorFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean
public class FillLectureObjectiveFeedbackBean {

	private List<CoursesDTO> coursesLst;
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	
	@ManagedProperty("#{ICouresFacade}")
   	private ICouresFacade coursesFacade; 
	
	private List<SemesterWeeksDTO> weeks;
	private List<InstructorDTO> insLst;
	@ManagedProperty("#{ISemesterWeeksFacade}")
   	private ISemesterWeeksFacade weeksFacade; 
	
	@ManagedProperty("#{IInstructorFacade}")
   	private IInstructorFacade insFacade; 
	
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade quesFacade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
	
	@ManagedProperty("#{IWeekFeedbackFacade}")
	private IWeekFeedbackFacade ansFacade;
	
	FormsStatusDTO form;
	
	private SemesterWeeksDTO selectedWeek;
	private CoursesDTO selectedCourse;
	private InstructorDTO selectedIns;
	private List<CourseEvalQuestionsDTO> questions;
	private List<CourseEvalAnswersDTO> answers;
	private boolean disableSubmitSurvey;
	
	@PostConstruct
	public void init(){
		weeks=new ArrayList<SemesterWeeksDTO>();
		coursesLst=new ArrayList<CoursesDTO>();
		form=formStatus.getById(11);
		 fillQuestions();
		 fillCoursesList();
		fillWeeks();
		selectedWeek=new SemesterWeeksDTO();
		if(weeks!=null)
			if(weeks.size()>0)
		selectedWeek= weeks.get(0);
		selectedCourse=new CoursesDTO();
		insLst=new ArrayList<InstructorDTO>();
		selectedIns=new InstructorDTO();
		
	}
	public void fillCoursesList()
	{
		//coursesLst=coursesFacade.getAllCourses();
		coursesLst=new ArrayList<CoursesDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{	
		if(authentication.getName().startsWith("s-")||authentication.getName().startsWith("S-")||StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			
			{
		coursesLst=coursesFacade.getCoursesBySemesterAndYear(form.getSemester().getId(), form.getYear());

			}}
		}
	public void fillInstructorsLst()
	{
		insLst=new ArrayList<InstructorDTO>();
		insLst=insFacade.getByCourseID(getSelectedCourse().getId());
		}
	
	public void fillWeeks(){
		weeks=new ArrayList<SemesterWeeksDTO>();
		weeks=weeksFacade.getActiveBySemesterAndyear(form.getSemester().getId(), form.getYear());
	}
	public void startSurvey(){
		fillQuestions();
		try{
			InstructorDTO temp=getInsDataFacade.getInsByPersonID(getSelectedIns().getId());
			selectedIns=temp;
			CoursesDTO course=new CoursesDTO();
			course=coursesFacade.getcourseById(getSelectedCourse().getId());
			selectedCourse=course;
			updateCourseList(selectedCourse.getId(),getSelectedIns().getId());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}
	public void fillQuestions()
	{
		questions=new ArrayList<CourseEvalQuestionsDTO>();
		questions=quesFacade.getBySectionID(QuestionsCategory.LECTURE_OBJECTIVES_FEEDBACK.getID());
		answers = new ArrayList<CourseEvalAnswersDTO>();
	}
	 public void updateCourseList(Integer courseId,Integer insId)
	    {
		 disableSubmitSurvey=false;
	    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
	    		PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
					
	    		List<WeekFeedbackDTO> answers=ansFacade.getByCourseIDAndInsIDAndStudentIdAndWeek
	    				(courseId, insId, student.getId(), getSelectedWeek().getId());
	    			
	    		if(answers!=null)
	    		{
	    			if(answers.size()>0)
	    				for(int k=0;k<questions.size();k++)
						{
	    					for(int i=0;i<answers.size();i++)
	    					{
	    						if(answers.get(i).getQuestion().getId().equals(questions.get(k).getId()))
	    						{
	    							questions.get(k).setSelection(answers.get(i).getSelections());
	    							disableSubmitSurvey=true;
	    						}
	    					}
						}
	    		}
	    		
					}
				}
	    	
				
	    	
	    
			
	    
	 
	public void endSurvey(){
		
		try{
    	for(int i=0;i<questions.size();i++)
    	{
    		if(questions.get(i).getSelection()!=0){
    		WeekFeedbackDTO ans=new WeekFeedbackDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse().getId());
    		ans.setCourse(course);
    		ans.setQuestion(questions.get(i));
			ans.setSelections(questions.get(i).getSelection());
			ans.setInstructor(getSelectedIns());
			ans.setWeek(getSelectedWeek());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				student.setMail(person.getEmail());
				ans.setStudent(student);
				}
			
    		ansFacade.add(ans);
    	
    		}
    		
    	}
    	init();
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Lecture's feedback has been submitted successfully");
		FacesContext.getCurrentInstance().getExternalContext().redirect("settingsPage.xhtml?-faces-redirect=true");
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void saveAns(int select, CourseEvalQuestionsDTO ques)
	{
		
      
        	 for(int i=0;i<questions.size();i++)
     		{
     			if(questions.get(i).getId()==ques.getId())
     			{
     				questions.get(i).setSelection(select);
     				break;
     			}
     		}
        	
		 }
	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}
	public ICouresFacade getCoursesFacade() {
		return coursesFacade;
	}
	public void setCoursesFacade(ICouresFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}
	public List<SemesterWeeksDTO> getWeeks() {
		return weeks;
	}
	public void setWeeks(List<SemesterWeeksDTO> weeks) {
		this.weeks = weeks;
	}
	public List<InstructorDTO> getInsLst() {
		return insLst;
	}
	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}
	public ISemesterWeeksFacade getWeeksFacade() {
		return weeksFacade;
	}
	public void setWeeksFacade(ISemesterWeeksFacade weeksFacade) {
		this.weeksFacade = weeksFacade;
	}
	public IInstructorFacade getInsFacade() {
		return insFacade;
	}
	public void setInsFacade(IInstructorFacade insFacade) {
		this.insFacade = insFacade;
	}
	public FormsStatusDTO getForm() {
		return form;
	}
	public void setForm(FormsStatusDTO form) {
		this.form = form;
	}
	public SemesterWeeksDTO getSelectedWeek() {
		return selectedWeek;
	}
	public void setSelectedWeek(SemesterWeeksDTO selectedWeek) {
		this.selectedWeek = selectedWeek;
	}
	public CoursesDTO getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(CoursesDTO selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public InstructorDTO getSelectedIns() {
		return selectedIns;
	}
	public void setSelectedIns(InstructorDTO selectedIns) {
		this.selectedIns = selectedIns;
	}
	public ICourseEvalQuestionsFacade getQuesFacade() {
		return quesFacade;
	}
	public void setQuesFacade(ICourseEvalQuestionsFacade quesFacade) {
		this.quesFacade = quesFacade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public List<CourseEvalQuestionsDTO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<CourseEvalQuestionsDTO> questions) {
		this.questions = questions;
	}
	public List<CourseEvalAnswersDTO> getAnswers() {
		return answers;
	}
	public void setAnswers(List<CourseEvalAnswersDTO> answers) {
		this.answers = answers;
	}
	public IWeekFeedbackFacade getAnsFacade() {
		return ansFacade;
	}
	public void setAnsFacade(IWeekFeedbackFacade ansFacade) {
		this.ansFacade = ansFacade;
	}
	public boolean isDisableSubmitSurvey() {
		return disableSubmitSurvey;
	}
	public void setDisableSubmitSurvey(boolean disableSubmitSurvey) {
		this.disableSubmitSurvey = disableSubmitSurvey;
	}

	
}
