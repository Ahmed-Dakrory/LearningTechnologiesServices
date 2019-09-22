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

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@ManagedBean(name="CourseEvalStudentBean")
@SessionScoped
public class CourseEvalStudentBean {

	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade facade;
	
	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{StudentAddDropFormFacadeImpl}")
	private IStudentAddDropFormFacade coursesFacade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	private List<CourseEvalQuestionsDTO> facultyEvalQues;
	private List<CourseEvalQuestionsDTO> courseEvalCourseQues;
	private List<CourseEvalQuestionsDTO> courseEvalAssignQues;
	private List<CourseEvalQuestionsDTO> teachAsisQues;
	private List<CourseEvalQuestionsDTO> labEvalFacQues;
	private List<CourseEvalQuestionsDTO> labEvalExpQues;
	private List<CourseEvalQuestionsDTO> othersQuesText;
	private List<CourseEvalQuestionsDTO> othersQuesRate;
	private List<List<CourseEvalQuestionsDTO>> facultyinstructorsList;
	private List<CoursesDTO> coursesLst=new ArrayList<CoursesDTO>();
    private List<InstructorDTO>insLst=new ArrayList<InstructorDTO>();
    private List<BaseDTO> selections=new ArrayList<BaseDTO>();
    private List<BaseDTO> yesOrNoSelections=new ArrayList<BaseDTO>();
    private List<BaseDTO> greaterScaleSelection=new ArrayList<BaseDTO>();
    private Integer selectedCourse;
    private Integer selectedIns;
    private InstructorDTO selectedInsDTO;
    private List<InstructorDTO>updatedInsLst=new ArrayList<InstructorDTO>();
    List<Integer> notLabCourses=new ArrayList<Integer>();
    List<Integer> hiddenCourses=new ArrayList<Integer>();
    String facultyComment;
    String taComment;
    List<BaseDTO> previewInstructors=new ArrayList<BaseDTO>();
    private Integer selectedPreviewInstructor;
    private String courseName;
    private boolean notlab;
	@PostConstruct
    public void init()
    
	{
		try{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
		if(authentication.getName().startsWith("s-")||authentication.getName().startsWith("S-")||StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			
			{
		previewInstructors=new ArrayList<BaseDTO>();
		
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
		hiddenCourses.add(369);
		hiddenCourses.add(380);
		hiddenCourses.add(363);
		hiddenCourses.add(377);
		hiddenCourses.add(373);
		hiddenCourses.add(370);
		hiddenCourses.add(375);
		hiddenCourses.add(371);
		hiddenCourses.add(374);
		hiddenCourses.add(368);
		
		for(int i=0;i<notLabCourses.size();i++)
		{
			if(getSelectedCourse()==notLabCourses.get(i));
			setNotlab(false);
		}
		//fillfacultyEvanQues();
		fillCoursesList();
		updateCourseList();
		selections=new ArrayList<BaseDTO>();
		selections.add(new BaseDTO(1, "Strongly Disagree"));
		selections.add(new BaseDTO(2, "Disagree"));
		selections.add(new BaseDTO(3, "Neutral"));
		selections.add(new BaseDTO(4, "Agree"));
		selections.add(new BaseDTO(5, "Strongly Agree"));
		selections.add(new BaseDTO(6, "N/A"));
		
		yesOrNoSelections=new ArrayList<BaseDTO>();
		yesOrNoSelections.add(new BaseDTO(7, "Yes"));
		yesOrNoSelections.add(new BaseDTO(8, "No"));
		
		greaterScaleSelection=new ArrayList<BaseDTO>();
		greaterScaleSelection.add(new BaseDTO(9,"Much Less"));
		greaterScaleSelection.add(new BaseDTO(10,"Less"));
		greaterScaleSelection.add(new BaseDTO(11,"Same"));
		greaterScaleSelection.add(new BaseDTO(12,"Greater"));
		greaterScaleSelection.add(new BaseDTO(13,"Much Greater"));
		
		facultyEvalQues=new ArrayList<CourseEvalQuestionsDTO>();
		courseEvalCourseQues=new ArrayList<CourseEvalQuestionsDTO>();
		courseEvalAssignQues=new ArrayList<CourseEvalQuestionsDTO>();
		teachAsisQues=new ArrayList<CourseEvalQuestionsDTO>();
		labEvalFacQues=new ArrayList<CourseEvalQuestionsDTO>();
		labEvalExpQues=new ArrayList<CourseEvalQuestionsDTO>();
		othersQuesText=new ArrayList<CourseEvalQuestionsDTO>();
		othersQuesRate=new ArrayList<CourseEvalQuestionsDTO>();
		setSelectedCourse(null);
		setSelectedIns(null);
		facultyinstructorsList=new ArrayList<List<CourseEvalQuestionsDTO>>();
		}}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void fillCoursesList()
	{
		//coursesLst=coursesFacade.getAllCourses();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
		if(authentication.getName().startsWith("s-")||authentication.getName().startsWith("S-")||StringUtils.isNumeric(authentication.getName().substring(0, 4)))
			
			{
			PersonDataDTO student=studentDataFacade.getPersonByPersonMail(authentication.getName());
		coursesLst=facade.getCoursesByStudentID(student.getId());
		for(int i=0;i<coursesLst.size();i++)
		{
			for(int j=0;j<hiddenCourses.size();j++)
			{
				if(coursesLst.get(i).getId()==hiddenCourses.get(j))
				{
					coursesLst.remove(i);
					break;
					
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
	public void fillfacultyEvalQues()
	{
		facultyEvalQues=facade.getByCategoryID(1);
	}
	public void fillCourseEvalCourseQues()
	{
		courseEvalCourseQues=facade.getByCategoryID(QuestionsCategory.Course_Eval_course.getValue());
	}
	public void fillCourseEvalAssignQues()
	{
		courseEvalAssignQues=facade.getByCategoryID(QuestionsCategory.Course_Eval_assign.getValue());
	}
	public void fillTeachAsisQues()
	{
		teachAsisQues=facade.getByCategoryID(QuestionsCategory.Teach_Asis.getValue());
	}
	public void fillLabEvalFacQues()
	{
		labEvalFacQues=facade.getByCategoryID(QuestionsCategory.Lab_Eval_Fac.getValue());
	}
	public void fillOthersQues()
	{
		othersQuesText=facade.getByCategoryID(QuestionsCategory.Other_Ques_Text.getValue());
		othersQuesRate=facade.getByCategoryID(QuestionsCategory.Rate.getValue());
	}
	public void fillLabEvalExpQues()
	{
		labEvalExpQues=facade.getByCategoryID(QuestionsCategory.Lab_Eval_Exp.getValue());
	}
	public void startSurvey()
	{
		courseName=ansFacade.getCourseById(getSelectedCourse()).getName();
		// Fill pages 
		fillfacultyEvalQues();
		fillCourseEvalCourseQues();
		fillCourseEvalAssignQues();
		fillTeachAsisQues();
		fillLabEvalFacQues();
		fillLabEvalExpQues();
		fillOthersQues();
		selectedInsDTO=facade.getInsByID(getSelectedIns());
		setFacultyComment(null);
		setTaComment(null);
		previewInstructors=new ArrayList<BaseDTO>();
	}
    public void submit()
    {
    	
    	
    	// Faculty rating questions
    	for(int i=0;i<getFacultyinstructorsList().size();i++){
    	for(int j=0;j<getFacultyinstructorsList().get(i).size();j++)
    	{
    		if(getFacultyinstructorsList().get(i).get(j).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    		ans.setComment(getFacultyinstructorsList().get(i).get(j).getAnsText());
    	
			
			ans.setInstructor(getFacultyinstructorsList().get(i).get(j).getInstructor());
			
			ans.setQuestion(getFacultyinstructorsList().get(i).get(j));
			ans.setSelections(getFacultyinstructorsList().get(i).get(j).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				/*StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		ansFacade.add(ans,1);
    		}
    	}}
    	}
    	//Course Evaluation _course
    	for(int i=0;i<courseEvalCourseQues.size();i++)
    	{
    		if(courseEvalCourseQues.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    		/*InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(courseEvalCourseQues.get(i).getAnsText());
			ans.setQuestion(courseEvalCourseQues.get(i));
			ans.setSelections(courseEvalCourseQues.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				/*StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}}
    	}
    	//Course Evaluation _Assign
      	for(int i=0;i<courseEvalAssignQues.size();i++)
    	{
    		if(courseEvalAssignQues.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    		/*InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(courseEvalAssignQues.get(i).getAnsText());
			ans.setQuestion(courseEvalAssignQues.get(i));
			ans.setSelections(courseEvalAssignQues.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				/*StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
			
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}
    	}	}
      	
      //TA Eval
      	for(int i=0;i<teachAsisQues.size();i++)
    	{
    		if(teachAsisQues.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    	/*	InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(teachAsisQues.get(i).getAnsText());
			ans.setQuestion(teachAsisQues.get(i));
			ans.setSelections(teachAsisQues.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
			/*	StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}}
    	}
        //Lab_Eval_Fac
      	for(int i=0;i<labEvalFacQues.size();i++)
    	{
    		if(labEvalFacQues.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    		/*InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(labEvalFacQues.get(i).getAnsText());
			ans.setQuestion(labEvalFacQues.get(i));
			ans.setSelections(labEvalFacQues.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				/*StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}}
    	}
      	 //Lab_Eval_Exp
      	for(int i=0;i<labEvalExpQues.size();i++)
    	{
    		if(labEvalExpQues.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    	/*	InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(labEvalExpQues.get(i).getAnsText());
			ans.setQuestion(labEvalExpQues.get(i));
			ans.setSelections(labEvalExpQues.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				/*StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}}
    	}
   	 //Other ques text
      	for(int i=0;i<othersQuesText.size();i++)
    	{
    		if(!othersQuesText.get(i).getAnsText().trim().equals("")){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    	/*	InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(othersQuesText.get(i).getAnsText());
			ans.setQuestion(othersQuesText.get(i));
			ans.setSelections(othersQuesText.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
			/*	StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}}
    	}
      //Other ques rate
      	for(int i=0;i<othersQuesRate.size();i++)
    	{
    		if(othersQuesRate.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    	/*	InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			ans.setInstructor(ins);*/
			ans.setComment(othersQuesRate.get(i).getAnsText());
			ans.setQuestion(othersQuesRate.get(i));
			ans.setSelections(othersQuesRate.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
					PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
					StudentDTO student=new StudentDTO();
					student.setId(person.getId());
				//StudentDTO student=new StudentDTO();
					//student.setId(1);
					student.setMail(person.getEmail());
					ans.setStudent(student);
				
			
    		//ansFacade.add(ans);
					ansFacade.add(ans,1);
    		}}
    	}
      	updateCourseList();
      	
      	init();
      	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("choicesPage.xhtml?-faces-redirect=true");
		
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Course Evaluation Done Successfully For Selected Course");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
    }
	public void saveAns(int select, CourseEvalQuestionsDTO ques)
	{
	
		for(int i=0;i<facultyEvalQues.size();i++)
		{
			if(facultyEvalQues.get(i).getId()==ques.getId())
			{
				facultyEvalQues.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<courseEvalCourseQues.size();i++)
		{
			if(courseEvalCourseQues.get(i).getId()==ques.getId())
			{
				courseEvalCourseQues.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<courseEvalAssignQues.size();i++)
		{
			if(courseEvalAssignQues.get(i).getId()==ques.getId())
			{
				courseEvalAssignQues.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<teachAsisQues.size();i++)
		{
			if(teachAsisQues.get(i).getId()==ques.getId())
			{
				teachAsisQues.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<labEvalFacQues.size();i++)
		{
			if(labEvalFacQues.get(i).getId()==ques.getId())
			{
				labEvalFacQues.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<labEvalExpQues.size();i++)
		{
			if(labEvalExpQues.get(i).getId()==ques.getId())
			{
				labEvalExpQues.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<othersQuesText.size();i++)
		{
			if(othersQuesText.get(i).getId()==ques.getId())
			{
				othersQuesText.get(i).setSelection(select);
				break;
			}
		}
		for(int i=0;i<othersQuesRate.size();i++)
		{
			if(othersQuesRate.get(i).getId()==ques.getId())
			{
				othersQuesRate.get(i).setSelection(select);
				break;
			}
		}
	}
	/*public void saveTextToAns(String ans,CourseEvalQuestionsDTO ques )
	{
		for(int i=0;i<othersQuesText.size();i++)
		{
			if(othersQuesText.get(i).getId()==ques.getId())
			{
				othersQuesText.get(i).setAnsText(ans);
				break;
			}
		}
	}*/
	public void saveTextToAns(String ans)
	{
		for(int i=0;i<facultyEvalQues.size();i++)
		{
			facultyEvalQues.get(i).setAnsText(getFacultyComment());
		}
		
	}
	public void saveTextToAnsTA(String ans)
	
	{
		for(int i=0;i<teachAsisQues.size();i++)
		{
			teachAsisQues.get(i).setAnsText(getTaComment());
		}
		
		
	}
    public void updateCourseList()
    {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
    	List<CoursesDTO> copies=new  ArrayList<CoursesDTO>();
    	for(int i=0;i<coursesLst.size();i++)
    	{
    
    		
				CoursesDTO course=coursesLst.get(i);
    		PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
			StudentDTO student=new StudentDTO();
			student.setId(person.getId());
    		List<CourseEvalAnswersDTO> answers=ansFacade.getByStudentIDAndCourseID(student.getId(),course.getId(),1);
    		if(answers.size()==0)
    		{
    			copies.add(coursesLst.get(i));
    		}
			}
    	
    	coursesLst=copies;
		}
    }
  
    public void preSubmitCurFaculty()
    {
    	addInstructorTofacultyList();
    	RequestContext.getCurrentInstance().reset("insForm:insPalnel");
    	 updateInstructorLst();
    	 if(getUpdatedInsLst().size()==0||getInsLst().size()==1)
    	 {
    		 //redirect
    		 	try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("courseEval.xhtml?-faces-redirect=true");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	 }
    	 RequestContext.getCurrentInstance().execute("insDLG.show();");
    	 
    }
    public void submitCurFaculty()
    {

    	// Faculty rating questions
    	for(int i=0;i<facultyEvalQues.size();i++)
    	{
    		if(facultyEvalQues.get(i).getSelection()!=0){
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse());
    		ans.setCourse(course);
    		ans.setComment(facultyEvalQues.get(i).getAnsText());
    		InstructorDTO ins=new InstructorDTO();
			ins.setId(getSelectedIns());
			
			ans.setInstructor(ins);
			
			ans.setQuestion(facultyEvalQues.get(i));
			ans.setSelections(facultyEvalQues.get(i).getSelection());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				/*StudentDTO student=new StudentDTO();
					student.setId(1);*/
					student.setMail(person.getEmail());
					ans.setStudent(student);
				}
			
    		//ansFacade.add(ans);
			ansFacade.add(ans,1);
    		}
    		
    	}
    	
   	/* RequestContext.getCurrentInstance().execute("insDLG.hide();");
 	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("facultyEval.xhtml?-faces-redirect=true");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
    	
    }
    
    public void updateInstructorLst()
    {
    	List<InstructorDTO> copies=new  ArrayList<InstructorDTO>();
    	for(int i=0;i<insLst.size();i++)
    	{
    		boolean exist=false;
    		for(int j=0;j<getFacultyinstructorsList().size();j++)
    		{
    		
    				/*if(getFacultyinstructorsList().get(j).get(0).getSelection()!=0)
    				{*/
    					InstructorDTO ins=insLst.get(i);
    					if(getFacultyinstructorsList().get(j).get(0).getInstructor().getId().equals(ins.getId()))
    					{
    						exist=true;
    						
    						break;
    					}
    					
        			//}
    				
    		}
    		if(!exist)
    		{
    			copies.add(insLst.get(i));
    		}
  
    	}
    	updatedInsLst=copies;
    	
    	// get from DB
    	/*List<InstructorDTO> copies=new  ArrayList<InstructorDTO>();
    	for(int i=0;i<insLst.size();i++)
    	{
    		InstructorDTO ins=insLst.get(i);
    		List<CourseEvalAnswersDTO> answers=ansFacade.getByStudentIDAndCourseIDAndInstructor(1,getSelectedCourse(), ins.getId());
    		if(answers.size()==0)
    		{
    			copies.add(insLst.get(i));
    		}
    	}
    	updatedInsLst=copies;*/
    }
    public void startSurveyForAnotherIns()
    {
    	setFacultyComment(null);
    	selectedInsDTO=facade.getInsByID(getSelectedIns());
    	try {
    		fillfacultyEvalQues();
    		setFacultyComment(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("facultyEval.xhtml?-faces-redirect=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void nextOfTeachingAssistant(){
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
    	
			FacesContext.getCurrentInstance().getExternalContext().redirect("otherQues.xhtml?-faces-redirect=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	else 
    	{
    		try {
    	    	
    			FacesContext.getCurrentInstance().getExternalContext().redirect("labEval.xhtml?-faces-redirect=true");
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    public void addInstructorTofacultyList()
    {
    	// check that there is at least one selection to add this to the list
    	boolean existed=false;
    
    	for(int i=0;i<getFacultyEvalQues().size();i++)
    	{
    		if(getFacultyEvalQues().get(i).getSelection()!=0)
    		{
    			existed=true;
    		
    			break;
    		}
    	}
    	if(existed)
    	{
    		InstructorDTO ins =new InstructorDTO();
    		ins.setId(getSelectedIns());
    		for(int i=0;i<getFacultyEvalQues().size();i++)
    		{
    			getFacultyEvalQues().get(i).setInstructor(ins);
    		}
    		if(getFacultyinstructorsList().size()==0)
    		{
    			getFacultyinstructorsList().add(getFacultyEvalQues());
    		}
    		else {
    			boolean insExistedBefore=false;
    			int index=0;
    	for(int i=0;i<getFacultyinstructorsList().size();i++)
    	{
    		if(getFacultyinstructorsList().get(i).get(0).getInstructor().getId()==getFacultyEvalQues().get(0).getInstructor().getId())
    		{
    			insExistedBefore=true;
    		index=i;
    			break;
    		}
    		
    			
    	}
    	if(insExistedBefore)
    	{
    		facultyinstructorsList.set(index,getFacultyEvalQues());
    		
    	}
    	else 
    		facultyinstructorsList.add(getFacultyEvalQues());
    		}
    	
    	}
    }
    public void previewQuestion()
    {
    	if(getFacultyinstructorsList().size()>1)
    	{
    	// fill list of instructors 
    	for(int i=0;i<getFacultyinstructorsList().size();i++)
    	{
    		InstructorDTO dto=getInsDataFacade.getInsByPersonID(getFacultyinstructorsList().get(i).get(0).getInstructor().getId());
    		previewInstructors.add(new BaseDTO(getFacultyinstructorsList().get(i).get(0).getInstructor().getId(),dto.getName()));
    		
    	}
    	setFacultyEvalQues(getFacultyinstructorsList().get(getFacultyinstructorsList().size()-1));
    	setFacultyComment(getFacultyinstructorsList().get(getFacultyinstructorsList().size()-1).get(0).getAnsText());
    	setSelectedPreviewInstructor(getFacultyinstructorsList().get(getFacultyinstructorsList().size()-1).get(0).getInstructor().getId());
    	}
    	else if(getFacultyinstructorsList().size()==1)
    	{
    		setFacultyEvalQues(getFacultyinstructorsList().get(getFacultyinstructorsList().size()-1));
        	setFacultyComment(getFacultyinstructorsList().get(getFacultyinstructorsList().size()-1).get(0).getAnsText());
        	setSelectedPreviewInstructor(getFacultyinstructorsList().get(getFacultyinstructorsList().size()-1).get(0).getInstructor().getId());
    	}
    	// forward to the first page
    
      	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("facultyEval.xhtml?-faces-redirect=true");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void previewInstructor()
    {
    	Integer ins=getSelectedPreviewInstructor();
    	if(ins!=null)
    	{
    		for(int i=0;i<getFacultyinstructorsList().size();i++)
    				{
    			     if(getFacultyinstructorsList().get(i).get(0).getInstructor().getId()==ins)
    			     {
    			    	 setFacultyEvalQues(getFacultyinstructorsList().get(i));

    			    		selectedInsDTO=getFacultyinstructorsList().get(i).get(0).getInstructor();
    			    		InstructorDTO returnedIns=getInsDataFacade.getInsByPersonID(selectedInsDTO.getId());
    			    		selectedInsDTO.setName(returnedIns.getName());
    			    		setFacultyComment(getFacultyinstructorsList().get(i).get(0).getAnsText());
    			    	 break;
    			     }
    				}
    	}
    }
    
    public ICourseEvalQuestionsFacade getFacade() {
		return facade;
	}
	public void setFacade(ICourseEvalQuestionsFacade facade) {
		this.facade = facade;
	}
	public List<CourseEvalQuestionsDTO> getFacultyEvalQues() {
		return facultyEvalQues;
	}
	public void setFacultyEvalQues(List<CourseEvalQuestionsDTO> facultyEvalQues) {
		this.facultyEvalQues = facultyEvalQues;
	}
	public IStudentAddDropFormFacade getCoursesFacade() {
		return coursesFacade;
	}
	public void setCoursesFacade(IStudentAddDropFormFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}
	public List<CoursesDTO> getCoursesLst() {
		 fillCoursesList();
		updateCourseList();
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public List<InstructorDTO> getInsLst() {
		
		return insLst;
	}
	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}
	public Integer getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(Integer selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public Integer getSelectedIns() {
		return selectedIns;
	}
	public void setSelectedIns(Integer selectedIns) {
		this.selectedIns = selectedIns;
	}
	public List<BaseDTO> getSelections() {
		return selections;
	}
	public void setSelections(List<BaseDTO> selections) {
		this.selections = selections;
	}
	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}
	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public List<CourseEvalQuestionsDTO> getCourseEvalCourseQues() {
		return courseEvalCourseQues;
	}
	public void setCourseEvalCourseQues(
			List<CourseEvalQuestionsDTO> courseEvalCourseQues) {
		this.courseEvalCourseQues = courseEvalCourseQues;
	}
	public List<CourseEvalQuestionsDTO> getCourseEvalAssignQues() {
		return courseEvalAssignQues;
	}
	public void setCourseEvalAssignQues(
			List<CourseEvalQuestionsDTO> courseEvalAssignQues) {
		this.courseEvalAssignQues = courseEvalAssignQues;
	}
	public List<CourseEvalQuestionsDTO> getTeachAsisQues() {
		return teachAsisQues;
	}
	public void setTeachAsisQues(List<CourseEvalQuestionsDTO> teachAsisQues) {
		this.teachAsisQues = teachAsisQues;
	}
	public List<CourseEvalQuestionsDTO> getLabEvalFacQues() {
		return labEvalFacQues;
	}
	public void setLabEvalFacQues(List<CourseEvalQuestionsDTO> labEvalFacQues) {
		this.labEvalFacQues = labEvalFacQues;
	}
	public List<CourseEvalQuestionsDTO> getLabEvalExpQues() {
		return labEvalExpQues;
	}
	public void setLabEvalExpQues(List<CourseEvalQuestionsDTO> labEvalExpQues) {
		this.labEvalExpQues = labEvalExpQues;
	}
	public List<CourseEvalQuestionsDTO> getOthersQuesText() {
		return othersQuesText;
	}
	public void setOthersQuesText(List<CourseEvalQuestionsDTO> othersQuesText) {
		this.othersQuesText = othersQuesText;
	}
	public List<CourseEvalQuestionsDTO> getOthersQuesRate() {
		return othersQuesRate;
	}
	public void setOthersQuesRate(List<CourseEvalQuestionsDTO> othersQuesRate) {
		this.othersQuesRate = othersQuesRate;
	}
	public InstructorDTO getSelectedInsDTO() {
		return selectedInsDTO;
	}
	public void setSelectedInsDTO(InstructorDTO selectedInsDTO) {
		this.selectedInsDTO = selectedInsDTO;
	}
	public List<InstructorDTO> getUpdatedInsLst() {
		return updatedInsLst;
	}
	public void setUpdatedInsLst(List<InstructorDTO> updatedInsLst) {
		this.updatedInsLst = updatedInsLst;
	}
	public List<Integer> getNotLabCourses() {
		return notLabCourses;
	}
	public void setNotLabCourses(List<Integer> notLabCourses) {
		this.notLabCourses = notLabCourses;
	}
	public String getFacultyComment() {
		return facultyComment;
	}
	public void setFacultyComment(String facultyComment) {
		this.facultyComment = facultyComment;
	}
	public List<List<CourseEvalQuestionsDTO>> getFacultyinstructorsList() {
		return facultyinstructorsList;
	}
	public void setFacultyinstructorsList(
			List<List<CourseEvalQuestionsDTO>> facultyinstructorsList) {
		this.facultyinstructorsList = facultyinstructorsList;
	}
	public String getTaComment() {
		return taComment;
	}
	public void setTaComment(String taComment) {
		this.taComment = taComment;
	}
	public List<BaseDTO> getPreviewInstructors() {
		return previewInstructors;
	}
	public void setPreviewInstructors(List<BaseDTO> previewInstructors) {
		this.previewInstructors = previewInstructors;
	}
	public Integer getSelectedPreviewInstructor() {
		return selectedPreviewInstructor;
	}
	public void setSelectedPreviewInstructor(Integer selectedPreviewInstructor) {
		this.selectedPreviewInstructor = selectedPreviewInstructor;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	public List<BaseDTO> getYesOrNoSelections() {
		return yesOrNoSelections;
	}
	public void setYesOrNoSelections(List<BaseDTO> yesOrNoSelections) {
		this.yesOrNoSelections = yesOrNoSelections;
	}
	public List<BaseDTO> getGreaterScaleSelection() {
		return greaterScaleSelection;
	}
	public void setGreaterScaleSelection(List<BaseDTO> greaterScaleSelection) {
		this.greaterScaleSelection = greaterScaleSelection;
	}

	
	
}
