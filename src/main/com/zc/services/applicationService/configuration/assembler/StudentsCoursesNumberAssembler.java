/**
 * 
 */
package main.com.zc.services.applicationService.configuration.assembler;

import main.com.zc.services.domain.configurations.model.StudentsCoursesNumber;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.presentation.configuration.dto.StudentsCoursesNumberDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class StudentsCoursesNumberAssembler {
	public StudentsCoursesNumberDTO toDTO(StudentsCoursesNumber form)
	{
		StudentsCoursesNumberDTO dto=new StudentsCoursesNumberDTO();
		dto.setId(form.getId());
		dto.setNum(form.getNum());
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
	
		return dto;
	}
	
	public StudentsCoursesNumber  toEntity(StudentsCoursesNumberDTO dto)
	{
		StudentsCoursesNumber form=new StudentsCoursesNumber();
		form.setId(dto.getId());
		form.setNum(dto.getNum());
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
		return form;
	}
	
}
