/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.assembler;

import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.courseEval.model.InstructorsEvalAnswers;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.InstructorsEvalAnswersDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
public class InstructorEvalAnswersAssembler {
	public InstructorsEvalAnswersDTO toDTO(InstructorsEvalAnswers ans)
	{
		InstructorsEvalAnswersDTO dto=new InstructorsEvalAnswersDTO();
		dto.setId(ans.getId());
		dto.setSelection(ans.getSelection());
		dto.setComment(ans.getComment());
	    
		CourseEvalQuestionsAssembler assem=new CourseEvalQuestionsAssembler();
		
		try{
		
			dto.setQuestion(assem.toDTO(ans.getQuestion()));
		}
		catch(Exception ex)
		{
			System.out.println("Can't add ques to ANSWER");
			ex.toString();
		}
		
		
		try{
			InstructorDTO fromIns=new InstructorDTO();
			fromIns.setId(ans.getFrom().getId());
			fromIns.setName(ans.getFrom().getName());
			
			dto.setFrom(fromIns);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add Instructor from to ANSWER");
			ex.toString();
		}
		try{
			InstructorDTO toIns=new InstructorDTO();
			toIns.setId(ans.getTo().getId());
			toIns.setName(ans.getTo().getName());
			
			dto.setTo(toIns);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add Instructor to to ANSWER");
			ex.toString();
		}
		
		
		return dto;
	} 
	
	public InstructorsEvalAnswers toEntity(InstructorsEvalAnswersDTO dto)
	{
		InstructorsEvalAnswers ans=new InstructorsEvalAnswers();
		ans.setId(dto.getId());
		ans.setSelection(dto.getSelection());
		ans.setComment(dto.getComment());
	
		
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
	
		try{
			Employee from=new Employee();
			from.setId(dto.getFrom().getId());
			
			
			ans.setFrom(from);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add Instructor-from to ANSWER");
			ex.toString();
		}
		try{
			Employee to=new Employee();
			to.setId(dto.getTo().getId());
			
			
			ans.setTo(to);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add Instructor-to to ANSWER");
			ex.toString();
		}
		
		return ans;
	} 
}
