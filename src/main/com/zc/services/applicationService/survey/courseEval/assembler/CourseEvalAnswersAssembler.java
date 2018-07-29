/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.assembler;

import main.com.zc.services.domain.courseEval.model.CourseEvalAnswers;
import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class CourseEvalAnswersAssembler {

	public CourseEvalAnswersDTO toDTO(CourseEvalAnswers ans)
	{
		CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
		dto.setId(ans.getId());
		dto.setSelections(ans.getSelections());
		dto.setComment(ans.getComment());
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
		
		try{
			CoursesDTO course=new CoursesDTO();
			course.setId(ans.getCourse().getId());
			course.setName(ans.getCourse().getName());
			
			dto.setCourse(course);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add course to ANSWER");
			ex.toString();
		}
		try{
			InstructorDTO instructor=new InstructorDTO();
			instructor.setId(ans.getInstructor().getId());
			instructor.setName(ans.getInstructor().getName());
			
			dto.setInstructor(instructor);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add Instructor to ANSWER");
			ex.toString();
		}
		
		return dto;
	} 
	
	public CourseEvalAnswers toEntity(CourseEvalAnswersDTO dto)
	{
		CourseEvalAnswers ans=new CourseEvalAnswers();
		ans.setId(dto.getId());
		ans.setSelections(dto.getSelections());
		ans.setComment(dto.getComment());
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
		try{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			
			
			ans.setCourse(course);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add course to ANSWER");
			ex.toString();
		}
		try{
			Employee instructor=new Employee();
			instructor.setId(dto.getInstructor().getId());
			
			
			ans.setInstructor(instructor);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add Instructor to ANSWER");
			ex.toString();
		}
		return ans;
	} 
}
