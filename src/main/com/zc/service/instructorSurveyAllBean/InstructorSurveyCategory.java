package main.com.zc.service.instructorSurveyAllBean;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.service.instructor_all_survey_ans.Iinstructor_all_survey_ansAppService;
import main.com.zc.service.instructor_all_survey_ques.instructor_all_survey_ques;
import main.com.zc.service.instructor_all_survey_ques_choose.Iinstructor_all_survey_ques_chooseAppService;

public class InstructorSurveyCategory {

	public List<questionWithChooses> questionWithChooses_Categ;
	
	
	public InstructorSurveyCategory(int studentId,int courseId,int instructorId,Iinstructor_all_survey_ansAppService instructor_all_survey_ansFacade,List<instructor_all_survey_ques> questionsOnly_Categ, Iinstructor_all_survey_ques_chooseAppService instructor_all_survey_ques_chooseFacade) {
		questionWithChooses_Categ = new ArrayList<questionWithChooses>();
		for(int i=0;i<questionsOnly_Categ.size();i++) {
			questionWithChooses Q_W_A = new questionWithChooses(studentId, courseId, instructorId, instructor_all_survey_ansFacade,questionsOnly_Categ.get(i),instructor_all_survey_ques_chooseFacade);
			questionWithChooses_Categ.add(Q_W_A);
			
		}
	}


	public List<questionWithChooses> getQuestionWithChooses_Categ() {
		return questionWithChooses_Categ;
	}


	public void setQuestionWithChooses_Categ(List<questionWithChooses> questionWithChooses_Categ) {
		this.questionWithChooses_Categ = questionWithChooses_Categ;
	}


	
	
	
	
	
}
