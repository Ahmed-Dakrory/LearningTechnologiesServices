/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.overloadRequest.assembler.OverloadRquestAssembler;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IStudentOverloadRequestService;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya.alaa
 *
 */
@Service
public class StudentOverloadRequestServiceImpl implements IStudentOverloadRequestService{

	@Autowired
	IOverloadRequestRep rep;
	@Autowired
	ICoursesRepository coursesRep;
	@Autowired
	IMajorRepository majorRep;
	OverloadRquestAssembler assem=new OverloadRquestAssembler();
	@Override
	public List<OverloadRequestDTO> getPendingRequestsOfStudent(
			Integer studentID) {
		List<OverloadRequestDTO> dtos=new ArrayList<OverloadRequestDTO>();
		try{
		List<OverloadRequest> requests=rep.getByStudentID(studentID);
		for(int i=0;i<requests.size();i++){
			if(requests.get(i).getPerformed()!=null)
				 {
				if(requests.get(i).getPerformed()!=true)
				{
					dtos.add(assem.toDTO(requests.get(i)));
				}
				 }
			else if(requests.get(i).getPerformed()==null)
			{
				dtos.add(assem.toDTO(requests.get(i)));
			}
		}
		return dtos;
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting pending");
			return dtos;
		}
	}

	@Override
	public List<OverloadRequestDTO> getArchievedRequestsOfStudent(
			Integer studentID) {
		List<OverloadRequestDTO> dtos=new ArrayList<OverloadRequestDTO>();
		try{
		List<OverloadRequest> requests=rep.getByStudentID(studentID);
		for(int i=0;i<requests.size();i++){
			if(requests.get(i).getPerformed()!=null)
				 {
				if(requests.get(i).getPerformed()==true)
				{
					dtos.add(assem.toDTO(requests.get(i)));
				}
				 }
			
		}
		return dtos;
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting pending");
			return dtos;
		}
	}

	@Override
	public OverloadRequestDTO submitRequest(OverloadRequestDTO dto) {
		
		try
		{
			OverloadRequest request=assem.toEntity(dto);
		
		return assem.toDTO(rep.add(request));
		}
		catch(Exception ex)
		{
			System.out.println("-------- Error in submitting form");
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
		return coursesDTO;	}


	@Override
	public OverloadRequestDTO getByID(Integer id) {
		OverloadRequestDTO dto=new OverloadRequestDTO();
		try
		{
			OverloadRequest form=rep.getById(id);
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}	}


