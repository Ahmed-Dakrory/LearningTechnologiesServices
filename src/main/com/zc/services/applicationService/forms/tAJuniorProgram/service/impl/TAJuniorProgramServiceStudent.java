/**
 * 
 */
package main.com.zc.services.applicationService.forms.tAJuniorProgram.service.impl;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.assembler.TAJuniorProgramAssembler;
import main.com.zc.services.applicationService.forms.tAJuniorProgram.service.ITAJuniorProgramServiceStudent;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.ITAJuniorProgramRep;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;
import main.com.zc.services.presentation.forms.tAJuniorProgram.dto.TAJuniorProgramDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service 
public class TAJuniorProgramServiceStudent implements ITAJuniorProgramServiceStudent {

	@Autowired
	ITAJuniorProgramRep rep;
	@Autowired
	ICoursesRepository coursesRep;
	TAJuniorProgramAssembler assem=new TAJuniorProgramAssembler();
	@Override
	public List<TAJuniorProgramDTO> getPendingRequestsOfStudent(
			Integer studentID) {
		List<TAJuniorProgramDTO> dtos=new ArrayList<TAJuniorProgramDTO>();
		try{
		List<TAJuniorProgram> requests=rep.getByStudentID(studentID);
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
	public List<TAJuniorProgramDTO> getArchievedRequestsOfStudent(
			Integer studentID) {
		List<TAJuniorProgramDTO> dtos=new ArrayList<TAJuniorProgramDTO>();
		try{
		List<TAJuniorProgram> requests=rep.getByStudentID(studentID);
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
	public TAJuniorProgramDTO submitRequest(TAJuniorProgramDTO dto) {
		try
		{
			TAJuniorProgram request=assem.toEntity(dto);
		
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
	public TAJuniorProgramDTO getByID(Integer id) {
		TAJuniorProgramDTO dto=new TAJuniorProgramDTO();
		try
		{
			TAJuniorProgram form=rep.getById(id);
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}
	}
	

