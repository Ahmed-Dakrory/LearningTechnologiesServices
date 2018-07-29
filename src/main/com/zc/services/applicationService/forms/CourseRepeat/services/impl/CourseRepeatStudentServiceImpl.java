/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.CourseRepeat.assembler.CourseRepeatAssembler;
import main.com.zc.services.applicationService.forms.CourseRepeat.services.ICourseRepeatStudentService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service
public class CourseRepeatStudentServiceImpl implements ICourseRepeatStudentService{

	@Autowired
	IRepeatCourseFormRep rep;
	@Autowired
	ICoursesRepository coursesRep;
	@Autowired
	IMajorRepository majorRep;
	CourseRepeatAssembler assem=new CourseRepeatAssembler();
	@Override
	public CourseRepeatDTO add(CourseRepeatDTO dto) {
		
		try{
			RepeatCourseForm form=assem.toEntity(dto);
			dto=assem.toDTO(rep.add(form));
			return dto;
		}
		catch(Exception  ex){
			System.out.println("----------- Error in submitting form-----------");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CourseRepeatDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		List<CourseRepeatDTO> dtos=new ArrayList<CourseRepeatDTO>();
		try{
			List<RepeatCourseForm> forms=rep.getByStudentID(studentID);
		
		for(int i=0;i<forms.size();i++)
		{
			if(forms.get(i).getPerformed()!=null){
				if(forms.get(i).getPerformed()!=true)
				{
					dtos.add(assem.toDTO(forms.get(i)));
				}
			}
			else {
				dtos.add(assem.toDTO(forms.get(i)));
			}
			
		}}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<CourseRepeatDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		List<CourseRepeatDTO> dtos=new ArrayList<CourseRepeatDTO>();
		try{
			List<RepeatCourseForm> forms=rep.getByStudentID(studentID);
		
		for(int i=0;i<forms.size();i++)
		{
			if(forms.get(i).getPerformed()!=null){
				if(forms.get(i).getPerformed()==true)
				{
					dtos.add(assem.toDTO(forms.get(i)));
				}
			}
		
			
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return dtos;
	}

	
	

	@Override
	public List<CoursesDTO> getAllCourses() {
		List<Courses> courses=new ArrayList<Courses>();
		List<CoursesDTO> coursesDTO=new ArrayList<CoursesDTO>();
		try{
		courses=coursesRep.getAll();
		for(int i=0;i<courses.size();i++)
		{
			CoursesDTO dto=new CoursesDTO();
			dto.setId(courses.get(i).getId());
			dto.setName(courses.get(i).getName());
			try{
			InstructorDTO insDto=new InstructorDTO();
			insDto.setId(courses.get(i).getCourseCoordinator().getId());
			insDto.setName(courses.get(i).getCourseCoordinator().getName());
			dto.setCoordinator(insDto);
			}
			catch(Exception ex)
			{
				
			}
			coursesDTO.add(dto);
		}
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting courses");
			ex.printStackTrace();
		}
		return coursesDTO;
	}

	@Override
	public CourseRepeatDTO getByID(Integer id) {
		CourseRepeatDTO dto=new CourseRepeatDTO();
		try {
		RepeatCourseForm course=rep.getById(id);
		 dto=assem.toDTO(course);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	

}
