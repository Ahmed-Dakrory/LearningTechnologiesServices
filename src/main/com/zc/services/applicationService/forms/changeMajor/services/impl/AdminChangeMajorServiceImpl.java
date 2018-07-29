/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.changeMajor.assembler.ChangeMajorAssembler;
import main.com.zc.services.applicationService.forms.changeMajor.services.IAdminChangeMajorService;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service
public class AdminChangeMajorServiceImpl implements IAdminChangeMajorService {

	@Autowired 
	IChangeMajorFormRep rep;
	ChangeMajorAssembler assem=new ChangeMajorAssembler();
	@Override
	public ChangeMajorDTO updateRequest(ChangeMajorDTO dto) {
		try{
			ChangeMajorForm form=assem.toEntity(dto);
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
	public List<ChangeMajorDTO> getPendingPetitionsOfstuent() {
		List<ChangeMajorForm> forms=rep.getAll();
		List<ChangeMajorDTO> dtos=new ArrayList<ChangeMajorDTO>();
		for(int i=0;i<forms.size();i++)
		{
			ChangeMajorForm form = forms.get(i);
			if(form.getPerformed()== null || form.getPerformed() != true)
					dtos.add(assem.toDTO(form));
		}
		return dtos;
	}

	@Override
	public List<ChangeMajorDTO> getArchievedPetitionsOfstuent() {
		List<ChangeMajorForm> forms=rep.getAll();
		List<ChangeMajorDTO> dtos=new ArrayList<ChangeMajorDTO>();
		for(int i=0;i<forms.size();i++)
		{
			ChangeMajorForm form = forms.get(i);
			if(form.getPerformed()!=null && form.getPerformed()==true)
					dtos.add(assem.toDTO(form));
			
		}
		return dtos;
	}

}
