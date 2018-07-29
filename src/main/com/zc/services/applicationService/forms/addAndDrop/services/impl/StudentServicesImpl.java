/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.addAndDrop.assembler.AddDropAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IStudentAddDropFormServices;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

/**
 * @author omnya
 *
 */
@Service

public class StudentServicesImpl implements IStudentAddDropFormServices{

	@Autowired
	IAddDropFormRepository addDropRepository;
	@Autowired
	IMajorRepository majorRep;
	@Autowired
	ICoursesRepository coursesRep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	IEmployeeRepository insRep;
	AddDropAssembler assem=new AddDropAssembler();
	@Override
	public DropAddFormDTO addForm(DropAddFormDTO dto) {
		try{
			DropAddFormDTO addedDTO=new DropAddFormDTO();
		
			DropAddForm newForm=assem.toEntity(dto); 
			
			
			//TODO phase three 
			if(dto.getDroppedCourseIns()!=null)
			{
				if(dto.getDroppedCourseIns().getId()!=null)
				{
					Employee ins=new Employee();
					ins.setId(dto.getDroppedCourseIns().getId());
					newForm.setDroppedCourseIns(ins);
				}
			}
			
		
			addedDTO=assem.toDTO(addDropRepository.add(newForm));
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
	public List<DropAddFormDTO> getPendingFormsOfStudent(Integer studentId) {
		List<DropAddForm> allForms=addDropRepository.getByStudentID(studentId);
		List<DropAddFormDTO> filterdDTO=new ArrayList<DropAddFormDTO>();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()==null)
			{
				filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
			else if(allForms.get(i).getPerformed()!=true)
			{
				filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
		}
		return filterdDTO;
	}
	@Override
	public List<DropAddFormDTO> getArchievedFormsOfStudent(Integer studentId) {
		List<DropAddForm> allForms=addDropRepository.getByStudentID(studentId);
		List<DropAddFormDTO> filterdDTO=new ArrayList<DropAddFormDTO>();
		for(int i=0;i<allForms.size();i++)
		{
			if(allForms.get(i).getPerformed()!=null)
			{
			if(allForms.get(i).getPerformed()==true)
			{
				filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
			}
		}
		return filterdDTO;
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
	public DropAddFormDTO getByID(Integer id) {
		DropAddFormDTO dto=new DropAddFormDTO();
		try
		{
			DropAddForm form=addDropRepository.getById(id);
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
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
}
