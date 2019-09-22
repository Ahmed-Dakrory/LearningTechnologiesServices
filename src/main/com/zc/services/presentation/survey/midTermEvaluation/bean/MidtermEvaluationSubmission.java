/**
 * 
 */
package main.com.zc.services.presentation.survey.midTermEvaluation.bean;

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
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IStudentAddDropFormFacade;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICourseEvaluationFacade;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICoursesFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@SessionScoped
@Lazy
public class MidtermEvaluationSubmission {

	private CoursesDTO selectedCourse;
	
	private List<InstructorDTO> selectedInstructorsLst;
	private List<Integer> selectedInstructorIDs;
	private List<InstructorDTO> selectedTAsLst;
	private List<CoursesDTO> coursesLst;
	private List<CourseEvalQuestionsDTO> studentEvalQuestions;
	private List<CourseEvalQuestionsDTO> courseEvalQuestions;
	private List<CourseEvalQuestionsDTO> languageOFInstructionQuestions;
	private List<CourseEvalQuestionsDTO> otherComments;
	// Instructor Section
	private Integer selectedIns;
	private Integer oldIns;
	private List<InstructorDTO> instructorsList = new ArrayList<InstructorDTO>();
	private List<CourseEvalInsQuestionsDTO> questionsList = new ArrayList<CourseEvalInsQuestionsDTO>();
	
	private List<CourseEvalAnswersDTO> instAnswersList = new ArrayList<CourseEvalAnswersDTO>();
	private List<CourseEvalAnswersDTO> taAnswersList = new ArrayList<CourseEvalAnswersDTO>();
	private List<CourseEvalAnswersDTO> labAnswersList = new ArrayList<CourseEvalAnswersDTO>();

	private String questType = "inst";	
	private int activeIndex = 0;
	
	private StudentDTO student;

	/*@Ahmed Dakrory
	 * To get the index of the selected course;
	 */
	private int IndexofTheSelectedCourse;
	
	private List<InstructorDTO>insLst=new ArrayList<InstructorDTO>();
	private List<InstructorDTO>taLst=new ArrayList<InstructorDTO>();

	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade facade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@ManagedProperty("#{ICourseEvalAnswersFacade}")
	private ICourseEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{ICoursesEvalFacade}")
	private ICoursesFacade coursesInstFacade;

	@ManagedProperty("#{StudentAddDropFormFacadeImpl}")
	private IStudentAddDropFormFacade coursesFacade;
	
	@ManagedProperty("#{ICourseEvaluationFacade}")
	private ICourseEvaluationFacade courseEvalQuesFacade;
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	
	
	@PostConstruct
	public void init(){
		fillCoursesList();
		updateCourseList();
		 hideCourse();
		//TODO will be changed after create survey settings page 
		selectedCourse=new CoursesDTO();
//		selectedCourse=coursesLst.get(0);
		selectedInstructorsLst=new ArrayList<InstructorDTO>();
		selectedInstructorIDs=new ArrayList<Integer>();
		fillStudentQuestions();
		fillCourseQuestions();
		fillLanguageQuestions();
		
		fillOtherComment();
		insLst=new ArrayList<InstructorDTO>();
		taLst=new ArrayList<InstructorDTO>();

	}
	public void fillCoursesList()
	{
		//coursesLst=coursesFacade.getAllCourses();
		coursesLst=new ArrayList<CoursesDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
		if(authentication.getName().startsWith("s-")||authentication.getName().startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4)))
			
			{
			PersonDataDTO student=studentDataFacade.getPersonByPersonMail(authentication.getName());
			FormsStatusDTO form=formStatus.getById(17);
			
			if(form.getStatus().equals(FormsStatusEnum.Active))
				coursesLst=facade.getCoursesByStudentIDAndSemesterAndYear(student.getId(), form.getSemester().getId(), form.getYear());
				//Will be deleted after submission
				//coursesLst=facade.getCoursesByStudentID(student.getId());
			}}
		
	}
    public void updateCourseList()
    {/*
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
    		List<CourseEvalAnswersDTO> answers=ansFacade.getByStudentIDAndCourseID(student.getId(),course.getId(),2);
    		if(answers.size()==0)
    		{
    			copies.add(coursesLst.get(i));
    		}
			}
    	
    	coursesLst=copies;
		}
		*/
    }
    public void hideCourse(){
    	try{
    		List<CoursesDTO> copies=new  ArrayList<CoursesDTO>();
    	for(int i=0;i<coursesLst.size();i++)
    	{
    
    		if(coursesLst.get(i).getHideCourseEval()!=null)
    		{
    			if(coursesLst.get(i).getHideCourseEval()==false)
    			{
    				copies.add(coursesLst.get(i));
    			}
    		}
    		else {
    			copies.add(coursesLst.get(i));
    		}
			}
    	coursesLst=new ArrayList<CoursesDTO>();
     	coursesLst=copies;
    	}
    	catch (Exception ex) {
    		ex.toString();
			
		}
    }
	public void fillStudentQuestions()
	{
		studentEvalQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		studentEvalQuestions=facade.getBySectionID(QuestionsCategory.Student_Eval.getID());
	}
	public void fillCourseQuestions()
	{
		courseEvalQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseEvalQuestions=facade.getBySectionID(QuestionsCategory.MIDTERM_EVALUATION_INSTRUCTOR.getID());
	}
	public void fillLanguageQuestions(){
		languageOFInstructionQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		languageOFInstructionQuestions=facade.getBySectionID(QuestionsCategory.Languag_Of_Instruction.getID());
	}
	public void fillOtherComment()
	{
		otherComments=new ArrayList<CourseEvalQuestionsDTO>();
		//otherComments=facade.getBySectionID(QuestionsCategory.Other_Comments.getID());
		otherComments=facade.getBySectionID(324);
	}
	/**
	 * @author asmaa
	 * @param select
	 * @param ques
	 * @param pageCase
	 */
	
	public void fillEmpQuestions(){
		
		student=new StudentDTO();			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			
			PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
			
			student.setId(person.getId());
			student.setMail(person.getEmail());				
		}
		
		//List<CourseEvalAnswersDTO> answers = new ArrayList<CourseEvalAnswersDTO>();
		CoursesDTO course=coursesLst.get(IndexofTheSelectedCourse);

		System.out.println("Dakrory IndexofTheSelectedCourse= "+String.valueOf(IndexofTheSelectedCourse));
		PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
		StudentDTO student=new StudentDTO();
		student.setId(person.getId());
		List<CourseEvalAnswersDTO> answers=ansFacade.getByStudentIDAndCourseID(student.getId(),course.getId(),2);

		System.out.println("Dakrory Number of te Answers= "+String.valueOf(answers.size()));
		System.out.println("Dakrory StudentId= "+String.valueOf(student.getId()));
		System.out.println("Dakrory CourseId= "+String.valueOf(course.getId()));
		switch (questType) {
		case "inst":

			if(!selectedInstructorsLst.isEmpty()) {
			
				if (selectedIns == null) 
					setSelectedIns(selectedInstructorsLst.get(0).getId());			
				
				//setQuestionsList(coursesInstFacade.getAllInstructorQuestions(10));
				//setQuestionsList(coursesInstFacade.getAllInstructorQuestions(QuestionsCategory.Instructor_Asis.getID()));			
				setQuestionsList(coursesInstFacade.getAllInstructorQuestions(319));
				//answers = instAnswersList;
			}
			
			break;
		case "ta":
			
			if(!selectedTAsLst.isEmpty()) {
			
				if (selectedIns == null) 
					setSelectedIns(selectedTAsLst.get(0).getId());
				
				//setQuestionsList(coursesInstFacade.getAllInstructorQuestions(12));
				setQuestionsList(coursesInstFacade.getAllInstructorQuestions(321));
				//answers = taAnswersList;
			}
			
			break;
		case "lab":
			selectedIns = null;
			//setQuestionsList(coursesInstFacade.getAllInstructorQuestions(13));
			setQuestionsList(coursesInstFacade.getAllInstructorQuestions(322));
			//answers = labAnswersList;
			break;
		default:
			break;
		}					
		
		setOldIns(selectedIns);					
		
		if(!answers.isEmpty() && answers != null){
			/*@ Ahmed Dakrory
			 * Set The Answers for the Course evaluation Part By 
			 * Fill the Empty Question from the last database last Answers
			 */
			for (int i=0;i< courseEvalQuestions.size();i++) {
				
				for (CourseEvalAnswersDTO courseEvalAnswersDTO : answers) {
					
					if(courseEvalQuestions.get(i).getId().equals(courseEvalAnswersDTO.getQuestion().getId())) {
						courseEvalQuestions.get(i).setSelection(courseEvalAnswersDTO.getSelections());
					}
					
				}
			}
			
			
			for (CourseEvalAnswersDTO courseEvalAnswersDTO : answers) {
				
				for(int j=0;j<otherComments.size();j++){

					//Set The comment from the last Submission
					if(otherComments.get(j).getId().equals(courseEvalAnswersDTO.getQuestion().getId())) {
						otherComments.get(j).setAnsText(courseEvalAnswersDTO.getComment());
					}	
				}
			}
			
			
			
			
			for (CourseEvalInsQuestionsDTO quest : questionsList) {
				
				List<String> strengthsSelections = new ArrayList<String>();
				List<String> concernsSelections = new ArrayList<String>();
			
				for (CourseEvalAnswersDTO courseEvalAnswersDTO : answers) {
											
					if(quest.getId().equals(courseEvalAnswersDTO.getQuestion().getId())) {
						
						if(!questType.equals("lab")){
						
							if(courseEvalAnswersDTO.getInstructor().getId().equals(selectedIns)) {
						
								List<ScaleSelectionsDTO> selectionsDto = quest.getScaleType().getSelections();																						
								
								for (ScaleSelectionsDTO scaleSelectionsDTO : selectionsDto) {
									
									if(scaleSelectionsDTO.getId().equals(courseEvalAnswersDTO.getSelections())){
										
										switch (scaleSelectionsDTO.getType().getValue()) {
										case 0:
											quest.setMainSelection(courseEvalAnswersDTO.getSelections());
											break;
										case 1:
											strengthsSelections.add(courseEvalAnswersDTO.getSelections().toString());
											if(courseEvalAnswersDTO.getComment()!=null) quest.setStrengthText(courseEvalAnswersDTO.getComment());
											break;
										case 2:
											concernsSelections.add(courseEvalAnswersDTO.getSelections().toString());
											if(courseEvalAnswersDTO.getComment()!=null) quest.setConcernText(courseEvalAnswersDTO.getComment());
											break;	
										default:
											break;
										}
									}
								}
							}
						} else {
							
							List<ScaleSelectionsDTO> selectionsDto = quest.getScaleType().getSelections();																						
							
							for (ScaleSelectionsDTO scaleSelectionsDTO : selectionsDto) {
								
								if(scaleSelectionsDTO.getId().equals(courseEvalAnswersDTO.getSelections())){
									
									switch (scaleSelectionsDTO.getType().getValue()) {
									case 0:
										quest.setMainSelection(courseEvalAnswersDTO.getSelections());
										break;
									case 1:
										strengthsSelections.add(courseEvalAnswersDTO.getSelections().toString());
										if(courseEvalAnswersDTO.getComment()!=null) quest.setStrengthText(courseEvalAnswersDTO.getComment());
										break;
									case 2:
										concernsSelections.add(courseEvalAnswersDTO.getSelections().toString());
										if(courseEvalAnswersDTO.getComment()!=null) quest.setConcernText(courseEvalAnswersDTO.getComment());
										break;	
									default:
										break;
									}
								}
							}
						}
										
						quest.setStrengthsSelection(strengthsSelections);
						quest.setConcernsSelection(concernsSelections);
					}
				}
			}
		}	
	}	
	
	public List<ScaleSelectionsDTO> getQuestionsBySelection(Integer type, List<ScaleSelectionsDTO> selections) {
		
		List<ScaleSelectionsDTO> selectionsType = new ArrayList<ScaleSelectionsDTO>(); 
		
		for (ScaleSelectionsDTO scaleSelectionsDTO : selections) {
			
			if(scaleSelectionsDTO.getType().getValue() == type) {
				
				selectionsType.add(scaleSelectionsDTO);
			}
			
		}
		
		return selectionsType;
	}
	
	public void saveAns(int select, CourseEvalQuestionsDTO ques, int pageCase)
	{
		 switch (pageCase) {
         case 1:  { //Course Eval case
        	 for(int i=0;i<courseEvalQuestions.size();i++)
     		{
     			if(courseEvalQuestions.get(i).getId()==ques.getId())
     			{
     				courseEvalQuestions.get(i).setSelection(select);
     				break;
     			}
     		}
        	 break;
         }
         case 2: { //Student case
        	 for(int i=0;i<studentEvalQuestions.size();i++)
     		{
     			if(studentEvalQuestions.get(i).getId()==ques.getId())
     			{
     				studentEvalQuestions.get(i).setSelection(select);
     				break;
     			}
     		}
        		break;
        		}
         case 6: { //Language of instruction case
        	 for(int i=0;i<languageOFInstructionQuestions.size();i++)
     		{
     			if(languageOFInstructionQuestions.get(i).getId()==ques.getId())
     			{
     				languageOFInstructionQuestions.get(i).setSelection(select);
     				break;
     			}
     		}
        		break;
        		}}
         
        
	}
	public void saveTextToAns(String ans , CourseEvalQuestionsDTO ques, int pageCase){
		 switch (pageCase) {
         case 6:  { //Course Eval case
        	 for(int i=0;i<languageOFInstructionQuestions.size();i++)
     		{
     			if(languageOFInstructionQuestions.get(i).getId()==ques.getId())
     			{
     				languageOFInstructionQuestions.get(i).setAnsText(ans);
     				break;
     			}
     		}
        	 break;
         }}
	}
	public void navigateBtnAction(int pageCase, int nav){
		switch (pageCase) {
		//to instructor
		case 1:  { 
	       	 //Redirect to Instructor page from course page 
				//redirect
			 	try {
			 		
			 		if(selectedInstructorsLst.isEmpty() && nav == 0) {navigateBtnAction(2, nav); return;}
			 		else if(selectedInstructorsLst.isEmpty() && nav == 1) {navigateBtnAction(2, nav); return;}
			 		else {
				 		submitInsEval();			 		
				 		questType = "inst";
				 		
				 		selectedIns = null;
				 		
				 		fillEmpQuestions();				 						 		
				 		
						FacesContext.getCurrentInstance().getExternalContext().redirect("instructorEval.xhtml?-faces-redirect=true");
			 		}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	 break;
	        }
		case 2:  { 
	       	 //Redirect to ta page from in page 
				//redirect
			 	try {			 					 				 	
			 		if(selectedTAsLst.isEmpty() && nav == 0) {navigateBtnAction(3, nav); return;}
			 		else if(selectedTAsLst.isEmpty() && nav == 1) {navigateBtnAction(1, nav); return;}
			 		else {
				 		submitInsEval();			 				 	
				 		questType = "ta";
				 		
				 		selectedIns = null;
				 		
				 		fillEmpQuestions();			 					 						 		
				 		
				 		FacesContext.getCurrentInstance().getExternalContext().redirect("taEval.xhtml?-faces-redirect=true");					
				 		
			 		}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	 break;
	        }
		case 3:  { 
	       	 //Redirect to Instructor page from course page 
				//redirect
			 	try {
			 					 			 		 
					submitInsEval();			 		
			 		questType = "lab";
			 		fillEmpQuestions();
			 		
			 		if(selectedInstructorsLst.isEmpty() && selectedTAsLst.isEmpty())
			 			activeIndex = 0;
			 		else if (selectedInstructorsLst.isEmpty() || selectedTAsLst.isEmpty())
			 			activeIndex = 1;
			 		else 
			 			activeIndex = 2;
			 		
					FacesContext.getCurrentInstance().getExternalContext().redirect("labEval.xhtml?-faces-redirect=true");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	       	 break;
	        }
		case 4:  { 
	       	 //Redirect to other comments  from Language of instruction page 
			try {
				submitInsEval(); // must submit lab questions tab
		 		
				SubmitTheValuesOfTheLab();
				 	
				if(selectedInstructorsLst.isEmpty() && selectedTAsLst.isEmpty())
		 			activeIndex = 1;
		 		else if (selectedInstructorsLst.isEmpty() || selectedTAsLst.isEmpty())
		 			activeIndex =2;
		 		else 
		 			activeIndex = 3;
				
				FacesContext.getCurrentInstance().getExternalContext().redirect("otherComments.xhtml?-faces-redirect=true");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       	 break;
	        }
        
		}
	}

	public void endSurvey(){
		

		//3- Submit the other comments
    	for(int i=0;i<otherComments.size();i++)
    	{
    		if(otherComments.get(i).getSelection()!=0){
    			{
    		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
    		CoursesDTO course=new CoursesDTO();
			course.setId(getSelectedCourse().getId());
    		ans.setCourse(course);
    		ans.setQuestion(otherComments.get(i));
			ans.setSelections(otherComments.get(i).getSelection());
			ans.setComment(otherComments.get(i).getAnsText());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
				
				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
				StudentDTO student=new StudentDTO();
				student.setId(person.getId());
				student.setMail(person.getEmail());
				ans.setStudent(student);
				}
			
    		//ansFacade.add(ans);
			ansFacade.add(ans,2);
    		}}
    		else 	if(otherComments.get(i).getSelection()==0&&(otherComments.get(i).getAnsText()!=null)){
    			if(!otherComments.get(i).getAnsText().trim().equals(""))
    			{
        		CourseEvalAnswersDTO ans=new CourseEvalAnswersDTO();
        		CoursesDTO course=new CoursesDTO();
    			course.setId(getSelectedCourse().getId());
        		ans.setCourse(course);
        		ans.setQuestion(otherComments.get(i));
    			ans.setSelections(otherComments.get(i).getSelection());
    			ans.setComment(otherComments.get(i).getAnsText());
    			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
    			{
    				
    				PersonDataDTO person=studentDataFacade.getPersonByPersonMail(authentication.getName());
    				StudentDTO student=new StudentDTO();
    				student.setId(person.getId());
    				student.setMail(person.getEmail());
    				ans.setStudent(student);
    				}
    			
        		//ansFacade.add(ans);
    			ansFacade.add(ans,2);
        		}
    		}
    	}
    	//navigate to settings page after submitting 
    	try {
    		init();
    		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Course'evaluation has been submitted successfully");
    		FacesContext.getCurrentInstance().getExternalContext().redirect("settings.xhtml?-faces-redirect=true");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean getOtherComment(CourseEvalInsQuestionsDTO question, int type) {
		
		String otherStrId = ""; String otherConId = "";
		
		for (ScaleSelectionsDTO scaleSelection : question.getScaleType().getSelections()) {
			
			if (scaleSelection.getName().equals("other strengths?")) {
				
				otherStrId = scaleSelection.getId().toString();
				
			}else if (scaleSelection.getName().equals("other concerns?")) {
				
				otherConId = scaleSelection.getId().toString();
			}
		}
		
		
		if (type == 1 && question.getStrengthsSelection()!=null){
			
			for (String selection : question.getStrengthsSelection()) {
										
				if(selection.equals(otherStrId)) return true;
			}
			
		}else if (type == 2 && question.getConcernsSelection()!=null){
		
			for (String selection : question.getConcernsSelection()) {
				
				if(selection.equals(otherConId)) return true;
			}
		}				
		
		return false;
	}
	
	public void addSelection(CourseEvalInsQuestionsDTO quest, Integer i) {		
		
		quest.setMainSelection(null);
		
		quest.setMainSelection(i);
	}
	
	public void addSelectionArr(CourseEvalInsQuestionsDTO quest, List<String> selections, Integer type) {
		
		if(type == 1) {
			quest.setStrengthsSelection(null);
			quest.setStrengthsSelection(selections);			
		}else {
			quest.setConcernsSelection(null);
			quest.setConcernsSelection(selections);
		}
	}
	
	public void addText(CourseEvalInsQuestionsDTO question, String text, int type) {
		
		if (type == 1) 
			question.setStrengthText(text);
		else question.setConcernText(text);
	}
	
	/**
	 * @author asmaa
	 * Save into session instructor questions page or ta questions page or lab questions page
	 */
	public void submitInsEval() {
		
		try {											
			
			List<CourseEvalAnswersDTO> answersList = new ArrayList<CourseEvalAnswersDTO>();
			List<CourseEvalAnswersDTO> oldAnswersList = new ArrayList<CourseEvalAnswersDTO>();
			
			switch (questType) {
			case "inst":
				oldAnswersList = instAnswersList;				
				break;

			case "ta":	
				oldAnswersList = taAnswersList;				
				break;
				
			default:
				break;
			}					
			
			for (CourseEvalAnswersDTO courseEvalAnswersDTO : oldAnswersList) {
				
				if(!courseEvalAnswersDTO.getInstructor().getId().equals(oldIns)) {
					
					answersList.add(courseEvalAnswersDTO);
				}
			}							
			
			for (CourseEvalInsQuestionsDTO quest : questionsList) {
				
				if(quest.getMainSelection() != null) {
				
					CourseEvalAnswersDTO answer = new CourseEvalAnswersDTO();
					if(questType != "lab")
						answer.setInstructor(coursesInstFacade.getInstructorById(oldIns));										
					answer.setQuestion(coursesInstFacade.getQuestionById(quest.getId()));
					answer.setSelections(quest.getMainSelection());
					answer.setCourse(selectedCourse);
					answer.setStudent(student);
																						
					answersList.add(answer);
				}
				
				if(quest.getStrengthsSelection()!=null) {
				
					int i = 0;
					for (String selectionDto : quest.getStrengthsSelection()) {
												
						CourseEvalAnswersDTO stAnswer = new CourseEvalAnswersDTO();
						if(questType != "lab")
							stAnswer.setInstructor(coursesInstFacade.getInstructorById(oldIns));										
						stAnswer.setQuestion(coursesInstFacade.getQuestionById(quest.getId()));
						stAnswer.setSelections(Integer.parseInt(selectionDto));
						stAnswer.setCourse(selectedCourse);
						stAnswer.setStudent(student);															
						
						if(i == quest.getStrengthsSelection().size()-1) {
							if(quest.getStrengthText() != null && quest.getStrengthText() != "")
								stAnswer.setComment(quest.getStrengthText());
						}
						
						answersList.add(stAnswer);
						
						i++;
					}
				}
				
				if(quest.getConcernsSelection() != null) {
					
					int x = 0;
					
					for (String selectionDto : quest.getConcernsSelection()) {
																	
						CourseEvalAnswersDTO stAnswer = new CourseEvalAnswersDTO();
						if(questType != "lab")
							stAnswer.setInstructor(coursesInstFacade.getInstructorById(oldIns));										
						stAnswer.setQuestion(coursesInstFacade.getQuestionById(quest.getId()));
						stAnswer.setSelections(Integer.parseInt(selectionDto));
						stAnswer.setCourse(selectedCourse);
						stAnswer.setStudent(student);				
						
						if(x == quest.getConcernsSelection().size()-1) {
							if(quest.getConcernText() != null && quest.getConcernText() != "")
								stAnswer.setComment(quest.getConcernText());
						}
						
						answersList.add(stAnswer); 
						
						x++;
					}
				
				}
								
			}
			
			switch (questType) {
			case "inst":
				instAnswersList = answersList;
				break;

			case "ta":	
				taAnswersList = answersList;
				break;
				
			case "lab":
				labAnswersList = answersList;
				break;
				
			default:
				break;
			}										
			
		} catch (Exception e) {
			
			e.printStackTrace();					
		}		
	}
	
	public void selectInstructor(int index) {
		
		submitInsEval();		
 		fillEmpQuestions();
 		
 		try {
		
 			if(index == 3)
 	 			FacesContext.getCurrentInstance().getExternalContext().redirect("instructorEval.xhtml?-faces-redirect=true");
 	 		else
 	 			FacesContext.getCurrentInstance().getExternalContext().redirect("taEval.xhtml?-faces-redirect=true");
 			
		} catch (Exception e) {
			e.printStackTrace();
		} 		 	
	}
	
	 public void fillInstructorsLst()
	{
		insLst=new ArrayList<InstructorDTO>();
		insLst=courseEvalQuesFacade.getInstructorsByCourseID(getSelectedCourse().getId());
		taLst=new ArrayList<InstructorDTO>();
		taLst=courseEvalQuesFacade.getTasByCourseID(getSelectedCourse().getId());
		
		instAnswersList = new ArrayList<CourseEvalAnswersDTO>();
		taAnswersList = new ArrayList<CourseEvalAnswersDTO>();
		labAnswersList = new ArrayList<CourseEvalAnswersDTO>();
		questionsList = new ArrayList<CourseEvalInsQuestionsDTO>();
		selectedIns = null;
	}
	 public void fillSelectedInstructorsLst(){
		 selectedInstructorsLst=new ArrayList<InstructorDTO>();
	
		 for(int i=0;i<selectedInstructorIDs.size();i++){
			 for(int j=0;j<insLst.size();j++)
			 {
				 
			 if(selectedInstructorIDs.get(i).equals(insLst.get(j).getId()))
			 {
				 
				 selectedInstructorsLst.add(insLst.get(j));
				 break;
			 }
			 }
		 }
	 }
	 public void startSurvey(){
		 
		 try {
			 selectedTAsLst=new ArrayList<InstructorDTO>();
			 for(int i=0;i<taLst.size();i++){
				 if(taLst.get(i).isSelected()){
					 selectedTAsLst.add(taLst.get(i));
				 }
			 }
			 for(int i=0;i<coursesLst.size();i++)
			 {
				 if(coursesLst.get(i).getId()==getSelectedCourse().getId())
				 {
					 IndexofTheSelectedCourse=i;
					 setSelectedCourse(coursesLst.get(i));
				 }
			 }
				fillStudentQuestions();
				fillCourseQuestions();
				fillLanguageQuestions();
			
				instAnswersList = new ArrayList<CourseEvalAnswersDTO>();
				taAnswersList = new ArrayList<CourseEvalAnswersDTO>();
				labAnswersList = new ArrayList<CourseEvalAnswersDTO>();
				questionsList = new ArrayList<CourseEvalInsQuestionsDTO>();
				selectedIns = null;
				
				fillEmpQuestions();
						
					if(getSelectedInstructorsLst().size()>0)
					{
						navigateBtnAction(1, 0);
					}
					else if(getSelectedTAsLst().size()>0)
					{
						navigateBtnAction(2, 0);
					}
					else 
						{navigateBtnAction(3, 0);
						
						}
					
			

	 }
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	 }
	 public void saveSelectionToTA(InstructorDTO ins,boolean selection){
		 ins.setSelected(selection);
	 }
	 
	 public void SubmitTheValuesOfTheInstruct(){

		 System.out.println("Ahmed Dakrory Values of type : "+questType);
			if(!instAnswersList.isEmpty())
	    		//coursesInstFacade.saveInstructorEval(instAnswersList,2);
			coursesInstFacade.saveInstructorEvalUpdate(instAnswersList,2);
	    	
	 }
	 
	 public void SubmitTheValuesOfTheTa(){

		 System.out.println("Ahmed Dakrory Values of type : "+questType);
			if(!taAnswersList.isEmpty())
	    		coursesInstFacade.saveInstructorEvalUpdate(taAnswersList,2);
	    	
	 }
	 
	 public void SubmitTheValuesOfTheLab(){

		 System.out.println("Ahmed Dakrory Values of type : "+questType);
		//3- Submit Emp Questions and lab questions
	    	
	    	if(!labAnswersList.isEmpty())
	    		coursesInstFacade.saveInstructorEvalUpdate(labAnswersList,2);
	    	
	 }
	 
	 public void chechInstEval() {
		 
		 List<CourseEvalAnswersDTO> answers = new ArrayList<CourseEvalAnswersDTO>();
		 List<InstructorDTO> inst = new ArrayList<InstructorDTO>();
		 
		 
		 submitInsEval();
		 
		 
		 if(questType.equals("inst")) {
			 
			 answers = instAnswersList;
			 inst = selectedInstructorsLst;
			 SubmitTheValuesOfTheInstruct();
			 
		 } else if(questType.equals("ta")){
			 
			 answers = taAnswersList;
			 inst = selectedTAsLst;
			 SubmitTheValuesOfTheTa();
			 
			 
		 }
		 
		 Boolean exist = false;
		 
		 for (InstructorDTO instructorDTO : inst) {
			
			 exist = false;
			 
			 for (CourseEvalAnswersDTO courseEvalAnswersDTO : answers) {
				
				if(courseEvalAnswersDTO.getInstructor().getId().equals(instructorDTO.getId())) { 
					exist = true; continue;
				}
			 }
			 
			 if(!exist) {
				 
				 RequestContext.getCurrentInstance().execute("insDLG.show();"); 
				 return;
			 }
		 }
		 
		 if(questType.equals("inst")) 
			 navigateBtnAction(2, 0);
		 else 
			 navigateBtnAction(3, 0);
	 }

	public CoursesDTO getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(CoursesDTO selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public List<InstructorDTO> getSelectedInstructorsLst() {
		return selectedInstructorsLst;
	}
	public void setSelectedInstructorsLst(List<InstructorDTO> selectedInstructorsLst) {
		this.selectedInstructorsLst = selectedInstructorsLst;
	}
	public List<InstructorDTO> getSelectedTAsLst() {
		return selectedTAsLst;
	}
	public void setSelectedTAsLst(List<InstructorDTO> selectedTAsLst) {
		this.selectedTAsLst = selectedTAsLst;
	}
	public List<CoursesDTO> getCoursesLst() {
		return coursesLst;
	}
	public void setCoursesLst(List<CoursesDTO> coursesLst) {
		this.coursesLst = coursesLst;
	}
	public ICourseEvalQuestionsFacade getFacade() {
		return facade;
	}
	public void setFacade(ICourseEvalQuestionsFacade facade) {
		this.facade = facade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public List<CourseEvalQuestionsDTO> getStudentEvalQuestions() {
		return studentEvalQuestions;
	}
	public void setStudentEvalQuestions(
			List<CourseEvalQuestionsDTO> studentEvalQuestions) {
		this.studentEvalQuestions = studentEvalQuestions;
	}
	public List<CourseEvalQuestionsDTO> getCourseEvalQuestions() {
		return courseEvalQuestions;
	}
	public void setCourseEvalQuestions(
			List<CourseEvalQuestionsDTO> courseEvalQuestions) {
		this.courseEvalQuestions = courseEvalQuestions;
	}
	public ICourseEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}
	public void setAnsFacade(ICourseEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}
	public List<CourseEvalQuestionsDTO> getLanguageOFInstructionQuestions() {
		return languageOFInstructionQuestions;
	}
	public void setLanguageOFInstructionQuestions(
			List<CourseEvalQuestionsDTO> languageOFInstructionQuestions) {
		this.languageOFInstructionQuestions = languageOFInstructionQuestions;
	}

	public ICoursesFacade getCoursesInstFacade() {
		return this.coursesInstFacade;
	}
	public List<InstructorDTO> getInsLst() {
		return insLst;
	}
	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}
	public IStudentAddDropFormFacade getCoursesFacade() {
		return coursesFacade;
	}
	public void setCoursesFacade(IStudentAddDropFormFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}
	public List<Integer> getSelectedInstructorIDs() {
		return selectedInstructorIDs;
	}
	public void setSelectedInstructorIDs(List<Integer> selectedInstructorIDs) {
		this.selectedInstructorIDs = selectedInstructorIDs;
	}
	public List<InstructorDTO> getTaLst() {
		return taLst;
	}
	public void setTaLst(List<InstructorDTO> taLst) {
		this.taLst = taLst;
	}
	public ICourseEvaluationFacade getCourseEvalQuesFacade() {
		return courseEvalQuesFacade;
	}
	public void setCourseEvalQuesFacade(ICourseEvaluationFacade courseEvalQuesFacade) {
		this.courseEvalQuesFacade = courseEvalQuesFacade;
	}
	
	public void setCoursesInstFacade(ICoursesFacade coursesInstFacade) {
		
		this.coursesInstFacade = coursesInstFacade;
	}
	public Integer getSelectedIns() {
		return selectedIns;
	}

	public void setSelectedIns(Integer selectedIns) {
		this.selectedIns = selectedIns;
	}

	public List<InstructorDTO> getInstructorsList() {
		return instructorsList;
	}

	public void setInstructorsList(List<InstructorDTO> instructorsList) {
		this.instructorsList = instructorsList;
	}		

	public List<CourseEvalInsQuestionsDTO> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<CourseEvalInsQuestionsDTO> questionsList) {
		this.questionsList = questionsList;
	}

	public Integer getOldIns() {
		return oldIns;
	}

	public void setOldIns(Integer oldIns) {
		this.oldIns = oldIns;
	}
	public String getQuestType() {
		return questType;
	}
	public void setQuestType(String questType) {
		this.questType = questType;
	}
	public List<CourseEvalAnswersDTO> getInstAnswersList() {
		return instAnswersList;
	}
	public void setInstAnswersList(List<CourseEvalAnswersDTO> instAnswersList) {
		this.instAnswersList = instAnswersList;
	}
	public List<CourseEvalAnswersDTO> getTaAnswersList() {
		return taAnswersList;
	}
	public void setTaAnswersList(List<CourseEvalAnswersDTO> taAnswersList) {
		this.taAnswersList = taAnswersList;
	}
	public List<CourseEvalAnswersDTO> getLabAnswersList() {
		return labAnswersList;
	}
	public void setLabAnswersList(List<CourseEvalAnswersDTO> labAnswersList) {
		this.labAnswersList = labAnswersList;
	}
	public int getActiveIndex() {
		return activeIndex;
	}
	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	public List<CourseEvalQuestionsDTO> getOtherComments() {
		return otherComments;
	}
	public void setOtherComments(List<CourseEvalQuestionsDTO> otherComments) {
		this.otherComments = otherComments;
	}
	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}
	public int getIndexofTheSelectedCourse() {
		return IndexofTheSelectedCourse;
	}
	public void setIndexofTheSelectedCourse(int indexofTheSelectedCourse) {
		IndexofTheSelectedCourse = indexofTheSelectedCourse;
	}
	
}
