/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IStudentcourse_replacement_formService;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;

/**
 * @author omnya.alaa
 *
 */
@Service
public class Studentcourse_replacement_formServiceImpl implements IStudentcourse_replacement_formService{

	@Autowired 
	Icourse_replacement_formFormRep rep;
	course_replacement_formAssembler assem=new course_replacement_formAssembler();
	@Override
	public course_replacement_formDTO add(course_replacement_formDTO dto) {
		try{
			course_replacement_formForm form=assem.toEntity(dto);
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
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent(Integer studentID) {
		
		List<course_replacement_formDTO> dtos=new ArrayList<course_replacement_formDTO>();
		try
		{
			
			List<course_replacement_formForm> forms=rep.getByStudentID(studentID);
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
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent(Integer studentID) {
		
		List<course_replacement_formDTO> dtos=new ArrayList<course_replacement_formDTO>();
		try{
			List<course_replacement_formForm> forms=rep.getByStudentID(studentID);
		
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
	public course_replacement_formDTO getByID(Integer id) {
		course_replacement_formDTO dto=new course_replacement_formDTO();
		try
		{
			course_replacement_formForm form=rep.getById(id);
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}
	}


