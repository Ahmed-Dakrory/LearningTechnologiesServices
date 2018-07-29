/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.configuration.services.ICoursesService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service
public class CoursesServiceImpl implements ICoursesService{

	@Autowired
	ICoursesRepository courseRep;
	@Override
	public List<CoursesDTO> getCoursesByYearAndSemester(Integer year,
			Integer semester) {
		 List<CoursesDTO> dtos=new ArrayList<CoursesDTO>();
		try{
		List<Courses> courses=new ArrayList<Courses>();
		courses=courseRep.getByYearAndSemester( semester,year);
		for(int i=0;i<courses.size();i++)
		{
			CoursesDTO dto=new CoursesDTO();
			dto.setId(courses.get(i).getId());
			dto.setName(courses.get(i).getName());
			dto.setSemester(courses.get(i).getSemester());
			dto.setYear(courses.get(i).getYear());
			dtos.add(dto);
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return dtos;
	}
	
	@Override
	public CoursesDTO getCourseById(Integer id) {
		
		Courses course = courseRep.getById(id);
		CoursesDTO dto = new CoursesDTO();
		dto.setId(course.getId());
		dto.setName(course.getName());	
				
		return dto;
	}

	@Override
	public List<CoursesDTO> getAllCourses() {
		 List<CoursesDTO> dtos=new ArrayList<CoursesDTO>();
			try{
			List<Courses> courses=new ArrayList<Courses>();
			courses=courseRep.getAll();
			for(int i=0;i<courses.size();i++)
			{
				CoursesDTO dto=new CoursesDTO();
				dto.setId(courses.get(i).getId());
				dto.setName(courses.get(i).getName());
				dto.setSemester(courses.get(i).getSemester());
				dto.setYear(courses.get(i).getYear());
				try{
				InstructorDTO ins=new InstructorDTO();
				ins.setId(courses.get(i).getCourseCoordinator().getId());
				ins.setName(courses.get(i).getCourseCoordinator().getName());
				ins.setMail(courses.get(i).getCourseCoordinator().getMail());
				dto.setCoordinator(ins);
				}
				catch(Exception ex){
					
				}
				dtos.add(dto);
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
				
			}
			return dtos;
	}

}
