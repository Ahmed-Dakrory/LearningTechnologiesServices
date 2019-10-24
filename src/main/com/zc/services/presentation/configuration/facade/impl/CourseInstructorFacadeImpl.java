/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.configuration.facade.ICourseInstructorFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya
 *
 */
@Service("ICourseInstructorFacade")
public class CourseInstructorFacadeImpl implements ICourseInstructorFacade {

	@Autowired
	IEmployeeRepository instructorRep;
	@Autowired
	ICoursesRepository courseRep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Override
	public List<InstructorDTO> getAllInstructors() {
		List<Employee> instructors=new ArrayList<Employee>();
		instructors=instructorRep.getAll();
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(instructors.get(i).getId());
			dto.setName(instructors.get(i).getName());
			dto.setMail(instructors.get(i).getMail());
			dtos.add(dto);
		}
		return dtos;
	}
	@Override
	public boolean deleteCourse(CoursesDTO course) {
		try{
			Courses entity=courseRep.getById(course.getId());
			return courseRep.remove(entity);
			
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	@Override
	public List<InstructorDTO> getAllInstructorsByCourseId(Integer courseID) {
		try
		{
			List<Courses_Instructors> instructors=courseInsRep.getByCourseId(courseID);
			List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
			for(int i=0;i<instructors.size();i++)
			{
				InstructorDTO dto=new InstructorDTO();
				dto.setId(instructors.get(i).getInstructor().getId());
				dto.setName(instructors.get(i).getInstructor().getName());
				dto.setMail(instructors.get(i).getInstructor().getMail());
				dtos.add(dto);
			}
			return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	
	}
	@Override
	public boolean deleteInstructorFromCourse(InstructorDTO dto, CoursesDTO course) {
	    try{ 
		Courses_Instructors relation=courseInsRep.getByCourseIdAndInsId(course.getId(), dto.getId());
	     
		return courseInsRep.remove(relation);
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return false;
	    }
	}
	@Override
	public CoursesDTO getCourseById(Integer courseID) {
		Courses course=new Courses();
		course=courseRep.getById(courseID);
		CoursesDTO dto=new CoursesDTO();
		dto.setId(course.getId());
		dto.setName(course.getName());
		dto.setClo(course.getClo());
		if(course.getCourseCoordinator()!=null)
		{	InstructorDTO ins=new InstructorDTO();
			ins.setId(course.getCourseCoordinator().getId());
			ins.setName(course.getCourseCoordinator().getName());
			ins.setMail(course.getCourseCoordinator().getMail());
			dto.setCoordinator(ins);
		}
		dto.setSemester(course.getSemester());
		dto.setYear(course.getYear());
		
		return dto;
	}
	@Override
	public boolean addInstructorToCourse(Integer insID, Integer courseID) {
		try{
		Courses_Instructors relation=new Courses_Instructors();
		Employee ins=instructorRep.getById(insID);
		Courses course=courseRep.getById(courseID);
		relation.setCourse(course);
		relation.setInstructor(ins);
		int i=courseInsRep.add(relation);
		if(i!=0)
			return true;
		else return false;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}
	@Override
	public CoursesDTO editCourseCoordinator(CoursesDTO dto) {
		try
		{
			Courses course=courseRep.getById(dto.getId());
			Employee newCoord=instructorRep.getById(dto.getCoordinator().getId());
			course.setCourseCoordinator(newCoord);
			course=courseRep.update(course);
			CoursesDTO courseDTO=new CoursesDTO();
			courseDTO.setId(course.getId());
			courseDTO.setName(course.getName());
			courseDTO.setClo(course.getClo());
			return courseDTO;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	@Override
	public CoursesDTO addNewCourse(CoursesDTO dto) {
		try
		{
			Courses course=new Courses();
			Employee newCoord=instructorRep.getById(dto.getCoordinator().getId());
			course.setName(dto.getName());
			course.setYear(dto.getYear());
			course.setSemester(dto.getSemester());
			course.setClo(dto.getClo());
			
			course.setCourseCoordinator(newCoord);
			int i=courseRep.add(course);
			course=courseRep.getById(i);
			CoursesDTO courseDTO=new CoursesDTO();
			courseDTO.setId(course.getId());
			courseDTO.setName(course.getName());
			courseDTO.setClo(course.getClo());
			return courseDTO;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<InstructorDTO> getAllTAsByCourseId(Integer courseID) {
		try
		{
			List<Courses_Instructors> instructors=courseInsRep.getTAsByCourseID(courseID);
			List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
			for(int i=0;i<instructors.size();i++)
			{
				InstructorDTO dto=new InstructorDTO();
				dto.setId(instructors.get(i).getInstructor().getId());
				dto.setName(instructors.get(i).getInstructor().getName());
				dto.setMail(instructors.get(i).getInstructor().getMail());
				dto.setPhoto(instructors.get(i).getInstructor().getImage());
				dtos.add(dto);
			}
			return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<InstructorDTO> getAllTas() {
		List<Employee> instructors=new ArrayList<Employee>();
		instructors=instructorRep.getAllTas();
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(instructors.get(i).getId());
			dto.setName(instructors.get(i).getName());
			dto.setMail(instructors.get(i).getMail());
			dtos.add(dto);
		}
		return dtos;
	}

}
