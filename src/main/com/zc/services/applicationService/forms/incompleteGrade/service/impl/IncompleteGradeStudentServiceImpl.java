/**
 * 
 */
package main.com.zc.services.applicationService.forms.incompleteGrade.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.incompleteGrade.assembler.IncompleteGradeAssembler;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeStudentService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.petition.model.IIncompleteGradeRep;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class IncompleteGradeStudentServiceImpl implements IIncompleteGradeStudentService{

	@Autowired
	ICoursesRepository coursesRep;
	@Autowired
	IMajorRepository majorRep;
	@Autowired
	IIncompleteGradeRep rep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	IEmployeeRepository insRep;
	
	IncompleteGradeAssembler assem=new IncompleteGradeAssembler();
	
	@Override
	public IncompleteGradeDTO add(IncompleteGradeDTO dto) {
		try{
			IncompleteGrade form=assem.toEntity(dto);
			form=rep.add(form);
			dto=assem.toDTO(form);
			return dto;
		}
		catch(Exception  ex){
			System.out.println("----------- Error in submitting form-----------");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<IncompleteGradeDTO> getPendingPetitionsOfstuent(
			Integer studentID) {
		List<IncompleteGradeDTO> dtos=new ArrayList<IncompleteGradeDTO>();
		try{
			List<IncompleteGrade> forms=rep.getByStudentID(studentID);
		
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
	public List<IncompleteGradeDTO> getArchievedPetitionsOfstuent(
			Integer studentID) {
		List<IncompleteGradeDTO> dtos=new ArrayList<IncompleteGradeDTO>();
		try{
			List<IncompleteGrade> forms=rep.getByStudentID(studentID);
		
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
	public List<InstructorDTO> getAllInstructorsOfCourse(Integer id) {
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		try{
			
		
		List<Courses_Instructors> instructors=courseInsRep.getByCourseId(id);
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(insRep.getById(instructors.get(i).getInstructor().getId()).getId());
			dto.setName(insRep.getById(instructors.get(i).getInstructor().getId()).getName());
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public IncompleteGradeDTO getByID(Integer id) {
		IncompleteGradeDTO dto=new IncompleteGradeDTO();
		try {
			IncompleteGrade course=rep.getById(id);
		 dto=assem.toDTO(course);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

}
