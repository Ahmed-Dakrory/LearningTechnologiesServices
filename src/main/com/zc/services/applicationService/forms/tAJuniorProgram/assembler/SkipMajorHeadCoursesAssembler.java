/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.assembler;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.petition.model.SkipMajorHeadCourses;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.SkipMajorHeadCoursesDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
public class SkipMajorHeadCoursesAssembler {

	public SkipMajorHeadCoursesDTO toDTO(SkipMajorHeadCourses form)
	{
		SkipMajorHeadCoursesDTO dto=new  SkipMajorHeadCoursesDTO();
		
		dto.setId(form.getId());
	    try{
	    	CoursesDTO courseEnt=new CoursesDTO();
			courseEnt.setId(form.getCourse().getId());
			courseEnt.setName(form.getCourse().getName());
			dto.setCourse(courseEnt);
		
	    }
	    catch(Exception  ex){
	    	
	    }
		
			
		return dto;
	}
	public SkipMajorHeadCourses toEntity(SkipMajorHeadCoursesDTO dto)
	{
        SkipMajorHeadCourses form=new  SkipMajorHeadCourses();
		
        form.setId(dto.getId());
	    try{
	    	Courses courseEnt=new Courses();
			courseEnt.setId(form.getCourse().getId());
			courseEnt.setName(form.getCourse().getName());
			form.setCourse(courseEnt);
		
	    }
	    catch(Exception  ex){
	    	
	    }
		
		return form;
	}
	
}
