/**
 * 
 */
package main.com.zc.services.presentation.survey.CourseEvalNew.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.IInstructorsEvalAnswersFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.facade.ICourseEvalQuestionsFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;

/**
 * @author Omnya Alaa
 *
 */
@ManagedBean(name = "TaToTaEvalSubmission",eager=false)
@SessionScoped
public class TaToTaEvalSubmission {
	private List<InstructorDTO> taLst = new ArrayList<InstructorDTO>();
	private List<InstructorDTO> selectedTAsLst;
	private List<CourseEvalQuestionsDTO> currentquestions;
	private List<List<CourseEvalQuestionsDTO>> instructorsQuestions;
	private Integer currentTA;
	
	@ManagedProperty("#{ICourseEvalQuestionsFacade}")
	private ICourseEvalQuestionsFacade facade;

	@ManagedProperty("#{ICourseInstructorFacade}")
	private ICourseInstructorFacade insFacde;

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;

	@ManagedProperty("#{IStudentCourseFacade}")
	private IStudentCourseFacade coursesFacade;
	
	@ManagedProperty("#{IInstructorsEvalAnswersFacade}")
	private IInstructorsEvalAnswersFacade ansFacade;
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade formStatus; 
	@PostConstruct
	public void init() {
		
		fillInsAndTAs();
		updateInstructorList();
	}

	public void fillInsAndTAs() {
		taLst = new ArrayList<InstructorDTO>();
		// get lst of logged in instructor
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			if (!authentication.getName().startsWith("s-")
					&& !authentication.getName().startsWith("S-"))

			{
				List<CoursesDTO> courses = new ArrayList<CoursesDTO>();
				InstructorDTO loggedInInstructor = getInsDataFacade
						.getInsByPersonMail(authentication.getName());
				courses = coursesFacade
						.getCoursesOfInstructor(loggedInInstructor.getId());
				FormsStatusDTO form=formStatus.getById(13);
				
				if(form.getStatus().equals(FormsStatusEnum.Active))
					courses = coursesFacade
							.getCoursesOfInstructorBySemesterAndYear(loggedInInstructor.getId(),form.getYear(),form.getSemester().getId());

				// then loop on courses and get TAs of each course
				for (int i = 0; i < courses.size(); i++) {
					List<InstructorDTO> courseTas = new ArrayList<InstructorDTO>();
					courseTas = insFacde.getAllTAsByCourseId(courses.get(i)
							.getId());
					if (courseTas != null) {
						for (int j = 0; j < courseTas.size(); j++) {
							if (!checkDuplication(taLst, courseTas.get(j)))
								if(!courseTas.get(j).getId().equals(loggedInInstructor.getId()))
								taLst.add(courseTas.get(j));
						}
					}
				}

			}
		}

	}
	public void updateInstructorList(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			for(int i=0;i<taLst.size();i++)
		{

			
		    InstructorDTO loggedININs=getInsDataFacade.getInsByPersonMail(authentication.getName());
	        
			List<InstructorsEvalAnswersDTO> answers=ansFacade.getByFromINSAndTOINS(loggedININs.getId(), taLst.get(i).getId());
			if(answers.size()!=0)
			{
				taLst.get(i).setSelected(true);
				
			}
			}
		
		
		}
	}
	public boolean checkDuplication(List<InstructorDTO> lst, InstructorDTO ins) {
		boolean existed = false;
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).getId().equals(ins.getId())) {
				existed = true;
				break;
			}

		}
		return existed;
	}
	public void saveSelectionToTA(InstructorDTO ins, boolean selection) {
		ins.setSelected(selection);
	}

	public void startSurvey() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{
			  InstructorDTO loggedININs=getInsDataFacade.getInsByPersonMail(authentication.getName());
		        
			selectedTAsLst = new ArrayList<InstructorDTO>();
			for (int i = 0; i < taLst.size(); i++) {
				if (taLst.get(i).isSelected()) {
					List<InstructorsEvalAnswersDTO> answers=ansFacade.getByFromINSAndTOINS(loggedININs.getId(),taLst.get(i).getId());
					
					if(answers.size()==0)
					selectedTAsLst.add(taLst.get(i));
				}
			}	instructorsQuestions = new ArrayList<List<CourseEvalQuestionsDTO>>();
			for (int i = 0; i < selectedTAsLst.size(); i++) {
			
				List<CourseEvalQuestionsDTO> currentQues = new ArrayList<CourseEvalQuestionsDTO>();
				currentQues = facade
						.getBySectionID(QuestionsCategory.TA_Eval_TA.getID());
				for(int j=0;j<currentQues.size();j++)
				{
					currentQues.get(j).setInstructor(selectedTAsLst.get(i));
				}
				instructorsQuestions.add(currentQues);
				
			}
			currentquestions=instructorsQuestions.get(0);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("taEval.xhtml?-faces-redirect=true");
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}
public void switchToQuestions(Integer insID){
	for(int i=0;i<instructorsQuestions.size();i++)
	{
		if(instructorsQuestions.get(i).get(0).getInstructor().getId()!=null)
		{
			if(instructorsQuestions.get(i).get(0).getInstructor().getId().equals(insID))
			{
				setCurrentquestions(instructorsQuestions.get(i));
			}
		}
		
	}
}
	public void saveAns(int select, CourseEvalQuestionsDTO ques, int pageCase) {
		for (int i = 0; i < currentquestions.size(); i++) {
			if (currentquestions.get(i).getId() == ques.getId()) {
				currentquestions.get(i).setSelection(select);
				break;
			}
		}
	}

	public void endSurvey() {
		// 1- Submit answers
		for(int j=0;j<instructorsQuestions.size();j++){
		for (int i = 0; i < instructorsQuestions.get(j).size(); i++) {
			if (instructorsQuestions.get(j).get(i).getSelection() != 0) {
				InstructorsEvalAnswersDTO ans = new InstructorsEvalAnswersDTO();

				ans.setQuestion(instructorsQuestions.get(j).get(i));
				ans.setSelection(instructorsQuestions.get(j).get(i).getSelection());
				Authentication authentication = SecurityContextHolder
						.getContext().getAuthentication();
				if (!authentication.getPrincipal().equals("anonymousUser"))// logged
																			// in
				{

					InstructorDTO insFrom = getInsDataFacade
							.getInsByPersonMail(authentication.getName());

					ans.setFrom(insFrom);
				}
				InstructorDTO insTO = getInsDataFacade
						.getInsByPersonMail(authentication.getName());

				ans.setTo(instructorsQuestions.get(j).get(i).getInstructor());
                
				 ansFacade.add(ans);
			}

		}}
			try {
		
    		init();
    		FacesContext.getCurrentInstance().getExternalContext().redirect("taEvalSettings.xhtml?-faces-redirect=true");
			
		} catch (IOException e) {
		e.printStackTrace();
		}
	}

	public List<InstructorDTO> getTaLst() {
		return taLst;
	}

	public void setTaLst(List<InstructorDTO> taLst) {
		this.taLst = taLst;
	}

	public List<InstructorDTO> getSelectedTAsLst() {
		return selectedTAsLst;
	}

	public void setSelectedTAsLst(List<InstructorDTO> selectedTAsLst) {
		this.selectedTAsLst = selectedTAsLst;
	}

	public List<CourseEvalQuestionsDTO> getCurrentquestions() {
		return currentquestions;
	}

	public void setCurrentquestions(List<CourseEvalQuestionsDTO> currentquestions) {
		this.currentquestions = currentquestions;
	}

	public List<List<CourseEvalQuestionsDTO>> getInstructorsQuestions() {
		return instructorsQuestions;
	}

	public void setInstructorsQuestions(
			List<List<CourseEvalQuestionsDTO>> instructorsQuestions) {
		this.instructorsQuestions = instructorsQuestions;
	}

	public Integer getCurrentTA() {
		return currentTA;
	}

	public void setCurrentTA(Integer currentTA) {
		this.currentTA = currentTA;
	}

	public ICourseEvalQuestionsFacade getFacade() {
		return facade;
	}

	public void setFacade(ICourseEvalQuestionsFacade facade) {
		this.facade = facade;
	}

	public ICourseInstructorFacade getInsFacde() {
		return insFacde;
	}

	public void setInsFacde(ICourseInstructorFacade insFacde) {
		this.insFacde = insFacde;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public IStudentCourseFacade getCoursesFacade() {
		return coursesFacade;
	}

	public void setCoursesFacade(IStudentCourseFacade coursesFacade) {
		this.coursesFacade = coursesFacade;
	}

	public IInstructorsEvalAnswersFacade getAnsFacade() {
		return ansFacade;
	}

	public void setAnsFacade(IInstructorsEvalAnswersFacade ansFacade) {
		this.ansFacade = ansFacade;
	}

	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}
	
}
