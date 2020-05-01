/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.applicationService.forms.course_replacement_form.assembler.course_replacement_formAssembler;
import main.com.zc.services.applicationService.forms.course_replacement_form.services.IAdmincourse_replacement_formService;
import main.com.zc.services.domain.petition.model.Icourse_replacement_formFormRep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service
public class Admincourse_replacement_formServiceImpl implements IAdmincourse_replacement_formService {

	@Autowired 
	Icourse_replacement_formFormRep rep;
	course_replacement_formAssembler assem=new course_replacement_formAssembler();
	@Override
	public course_replacement_formDTO updateRequest(course_replacement_formDTO dto) {
		try{
			course_replacement_formForm form=assem.toEntity(dto);
			form=rep.update(form);
			
			return assem.toDTO(form);
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public List<course_replacement_formDTO> getPendingPetitionsOfstuent() {
		List<course_replacement_formForm> forms=rep.getAll();
		List<course_replacement_formDTO> dtos=new ArrayList<course_replacement_formDTO>();
		for(int i=0;i<forms.size();i++)
		{
			course_replacement_formForm form = forms.get(i);
			if(form.getPerformed()== null || form.getPerformed() != true)
					dtos.add(assem.toDTO(form));
		}
		return dtos;
	}

	@Override
	public List<course_replacement_formDTO> getArchievedPetitionsOfstuent() {
		List<course_replacement_formForm> forms=rep.getAll();
		List<course_replacement_formDTO> dtos=new ArrayList<course_replacement_formDTO>();
		for(int i=0;i<forms.size();i++)
		{
			course_replacement_formForm form = forms.get(i);
			if(form.getPerformed()!=null && form.getPerformed()==true)
					dtos.add(assem.toDTO(form));
			
		}
		return dtos;
	}

}
