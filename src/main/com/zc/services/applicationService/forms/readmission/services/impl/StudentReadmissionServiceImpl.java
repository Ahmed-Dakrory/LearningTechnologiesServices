/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.applicationService.forms.readmission.assembler.ReadmissionAssembler;
import main.com.zc.services.applicationService.forms.readmission.services.IStudentReadmissionService;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;

/**
 * @author omnya.alaa
 *
 */
@Service
public class StudentReadmissionServiceImpl implements IStudentReadmissionService{

	@Autowired 
	IReadmissionFormRep rep;
	ReadmissionAssembler assem=new ReadmissionAssembler();
	@Override
	public ReadmissionDTO add(ReadmissionDTO dto) {
		try{
			ReadmissionForm form=assem.toEntity(dto);
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
	public List<ReadmissionDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		List<ReadmissionDTO> dtos=new ArrayList<ReadmissionDTO>();
		try
		{
			
			List<ReadmissionForm> forms=rep.getByStudentID(studentID);
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
			
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		List<ReadmissionDTO> dtos=new ArrayList<ReadmissionDTO>();
		try{
			List<ReadmissionForm> forms=rep.getByStudentID(studentID);
		
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
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}



	@Override
	public ReadmissionDTO getByID(Integer id) {
		ReadmissionDTO dto=new ReadmissionDTO();
		try
		{
			ReadmissionForm form=rep.getById(id);
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}
	}


