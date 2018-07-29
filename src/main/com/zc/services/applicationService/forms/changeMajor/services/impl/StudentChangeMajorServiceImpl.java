/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.changeMajor.assembler.ChangeMajorAssembler;
import main.com.zc.services.applicationService.forms.changeMajor.services.IStudentChangeMajorService;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author omnya.alaa
 *
 */
@Service
public class StudentChangeMajorServiceImpl implements IStudentChangeMajorService{

	@Autowired 
	IChangeMajorFormRep rep;
	ChangeMajorAssembler assem=new ChangeMajorAssembler();
	@Override
	public ChangeMajorDTO add(ChangeMajorDTO dto) {
		try{
			ChangeMajorForm form=assem.toEntity(dto);
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
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		List<ChangeMajorDTO> dtos=new ArrayList<ChangeMajorDTO>();
		try
		{
			
			List<ChangeMajorForm> forms=rep.getByStudentID(studentID);
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
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		List<ChangeMajorDTO> dtos=new ArrayList<ChangeMajorDTO>();
		try{
			List<ChangeMajorForm> forms=rep.getByStudentID(studentID);
		
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
	public ChangeMajorDTO getByID(Integer id) {
		ChangeMajorDTO dto=new ChangeMajorDTO();
		try
		{
			ChangeMajorForm form=rep.getById(id);
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}
	}


