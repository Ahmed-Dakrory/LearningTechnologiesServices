/**
 * 
 */
package main.com.zc.services.applicationService.configuration.assembler;

import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class CourseStudentAssembler {

	public StudentCourseDTO toDTO(CourseStudent form)
	{
		StudentCourseDTO dto=new StudentCourseDTO();
		dto.setId(form.getId());
		try
		{
			CoursesDTO course=new CoursesDTO();
			course.setId(form.getCourse().getId());
			course.setName(form.getCourse().getName());
			course.setSemester(form.getCourse().getSemester());
			course.setYear(form.getCourse().getYear());
			dto.setCourse(course);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add course");
			ex.toString();
			
		}
		try
		{
			StudentDTO student=new StudentDTO();
			student.setId(form.getStudent().getId());
		
			student.setFacultyId(form.getStudent().getFileNo());
			//student.setMail(form.getStudent().getData().getMail());
			dto.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add student");
			ex.toString();
		}
		return dto;
	}
	
	public CourseStudent  toEntity(StudentCourseDTO dto)
	{
		CourseStudent form=new CourseStudent();
		form.setId(dto.getId());
		try
		{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			course.setName(dto.getCourse().getName());
			course.setSemester(dto.getCourse().getSemester());
			course.setYear(dto.getCourse().getYear());
			form.setCourse(course);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add course");
			ex.toString();
			
		}
		try
		{
			Student student=new Student();
			student.setId(dto.getStudent().getId());
			student.setFileNo(dto.getStudent().getFacultyId());
			form.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add student");
			ex.toString();
		}
		return form;
	}
	
	
}
