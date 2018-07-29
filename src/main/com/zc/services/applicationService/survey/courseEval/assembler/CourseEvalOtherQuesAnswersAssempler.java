/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.assembler;

import main.com.zc.services.domain.courseEval.model.CourseEvalOtherQuesAnswers;
import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class CourseEvalOtherQuesAnswersAssempler {

	public CourseEvalAnswersDTO toDTO(CourseEvalOtherQuesAnswers ans)
	{
		CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
		dto.setId(ans.getId());
		//dto.setSelections(ans.getSelections());
		dto.setText(ans.getText());
		StudentDTO student=new StudentDTO();
		try{
			student.setId(ans.getStudent().getId());
			student.setMail(ans.getStudent().getData().getMail());
			student.setFacultyId(ans.getStudent().getFileNo());
			dto.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add student to ANSWER");
			ex.toString();
		}
		CourseEvalQuestionsAssembler assem=new CourseEvalQuestionsAssembler();
		
		try{
		
			dto.setQuestion(assem.toDTO(ans.getQuestion()));
		}
		catch(Exception ex)
		{
			System.out.println("Can't add ques to ANSWER");
			ex.toString();
		}
		return dto;
	} 
	
	public CourseEvalOtherQuesAnswers toEntity(CourseEvalAnswersDTO dto)
	{
		CourseEvalOtherQuesAnswers ans=new CourseEvalOtherQuesAnswers();
		ans.setId(dto.getId());
		ans.setText(dto.getText());
		Student student=new Student();
		try{
			student.setId(dto.getStudent().getId());
		    ans.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add student to ANSWER");
			ex.toString();
		}
		
		try{
		
		CourseEvalQuestions ques=new CourseEvalQuestions();
		ques.setId(dto.getQuestion().getId());
		ans.setQuestion(ques);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add ques to ANSWER");
			ex.toString();
		}
		return ans;
	} 
}
