package main.com.zc.service.instructorSurveyAllBean;

import java.util.Calendar;
import java.util.List;

import main.com.zc.service.instructor_all_survey_ans.Iinstructor_all_survey_ansAppService;
import main.com.zc.service.instructor_all_survey_ans.instructor_all_survey_ans;
import main.com.zc.service.instructor_all_survey_ques.instructor_all_survey_ques;
import main.com.zc.service.instructor_all_survey_ques_choose.Iinstructor_all_survey_ques_chooseAppService;
import main.com.zc.service.instructor_all_survey_ques_choose.instructor_all_survey_ques_choose;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;

public class questionWithChooses {
	public instructor_all_survey_ques question;
	public List<instructor_all_survey_ques_choose> chooses;
	public instructor_all_survey_ans answer;
	public String[] manyAns;
	
	public questionWithChooses(int studentId,int courseId,int instructorId,Iinstructor_all_survey_ansAppService instructor_all_survey_ansFacade,instructor_all_survey_ques question,Iinstructor_all_survey_ques_chooseAppService instructor_all_survey_ques_chooseFacade) {
		this.question = question;
		this.chooses = instructor_all_survey_ques_chooseFacade.getAllByQuesId(this.question.getId());
		
		answer = instructor_all_survey_ansFacade.getAllByCourseAndInstructorAndStudentAndQuestion(courseId, instructorId, studentId, this.question.getId());
		if(answer == null) {
			answer = new instructor_all_survey_ans();
			Courses c=new Courses();
			c.setId(courseId);
			Employee inst = new Employee();
			inst.setId(instructorId);
			Student student = new Student();
			student.setId(studentId);
			
			answer.setCourseId(c);
			answer.setInstructorId(inst);
			answer.setStudentId(student);
			answer.setQuesId(this.question);
			answer.setDate(Calendar.getInstance());
		}
		
		if(question.getType().intValue()==instructor_all_survey_ques.TYPE_SELECT.intValue()){
			if(answer!=null) {
				manyAns = answer.getData().split(",");
			}else {
				manyAns = new String[chooses.size()];
			}
		}
	}
	
	public instructor_all_survey_ques getQuestion() {
		return question;
	}
	public void setQuestion(instructor_all_survey_ques question) {
		this.question = question;
	}

	public List<instructor_all_survey_ques_choose> getChooses() {
		return chooses;
	}

	public void setChooses(List<instructor_all_survey_ques_choose> chooses) {
		this.chooses = chooses;
	}

	public instructor_all_survey_ans getAnswer() {
		return answer;
	}

	public void setAnswer(instructor_all_survey_ans answer) {
		this.answer = answer;
	}

	public String[] getManyAns() {
		return manyAns;
	}

	public void setManyAns(String[] manyAns) {
		this.manyAns = manyAns;
	}


	
	
	
}
