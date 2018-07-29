/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.academicPetition.assembler.AcademicPetitionAssembler;
import main.com.zc.services.applicationService.forms.academicPetition.services.IStudentAcademicPetitionService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service
public class StudentAcademicPetitionServiceImpl implements IStudentAcademicPetitionService {

	@Autowired
	ICoursePetitionRep rep;
	@Autowired
	ICoursesRepository coursesRep;
	AcademicPetitionAssembler assem=new AcademicPetitionAssembler();
	@Override
	public List<CoursePetitionDTO> getPendingPetOfStudent(Integer studentId) {
		List<CoursePetitionDTO> dtos=new ArrayList<CoursePetitionDTO>();
		try
		{
			List<CoursePetition> all=rep.getByPersonID(studentId);
			for(int i=0;i<all.size();i++)
			{
				if(all.get(i).getPerformed()==null)
				{
					dtos.add(assem.toDTO(all.get(i)));
				}
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in get petitions");
		}
		return dtos;
	}

	@Override
	public List<CoursePetitionDTO> getOldPetOfStudent(Integer studentId) {
		List<CoursePetitionDTO> dtos=new ArrayList<CoursePetitionDTO>();
		try
		{
			List<CoursePetition> all=rep.getByPersonID(studentId);
			for(int i=0;i<all.size();i++)
			{
				if(all.get(i).getPerformed()!=null)
				{
					if(all.get(i).getPerformed()==true)
					dtos.add(assem.toDTO(all.get(i)));
				}
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in get petitions");
		}
		return dtos;
	}

	@Override
	public CoursePetitionDTO submit(CoursePetitionDTO dto) {
		try{
			CoursePetitionDTO addedDTO=new CoursePetitionDTO();
		
			addedDTO=assem.toDTO(rep.add(assem.toEntity(dto)));
			return addedDTO;
		}
		catch(Exception ex)
		{
			System.out.println("Can't submit new form");
			ex.printStackTrace();
			return null;
		}
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
	public CoursePetitionDTO getById(Integer id) {
		CoursePetitionDTO dto=new CoursePetitionDTO();
		try
		{
			CoursePetition coursePet=rep.getById(id);
			dto=assem.toDTO(coursePet);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<CoursesDTO> getAllCoursesBySemesterAndYear(Integer semester,
			Integer year) {
		List<Courses> courses=new ArrayList<Courses>();
		List<CoursesDTO> coursesDTO=new ArrayList<CoursesDTO>();
		try{
		courses=coursesRep.getByYearAndSemester(semester,year);
		for(int i=0;i<courses.size();i++)
		{
			CoursesDTO dto=new CoursesDTO();
			dto.setId(courses.get(i).getId());
			dto.setName(courses.get(i).getName());
			dto.setSemester(courses.get(i).getSemester());
			dto.setYear(courses.get(i).getYear());
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
	}


