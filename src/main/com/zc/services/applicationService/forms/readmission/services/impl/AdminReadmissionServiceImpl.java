/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.applicationService.forms.readmission.assembler.ReadmissionAssembler;
import main.com.zc.services.applicationService.forms.readmission.services.IAdminReadmissionService;
import main.com.zc.services.domain.petition.model.IReadmissionFormRep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service
public class AdminReadmissionServiceImpl implements IAdminReadmissionService {

	@Autowired 
	IReadmissionFormRep rep;
	ReadmissionAssembler assem=new ReadmissionAssembler();
	@Override
	public ReadmissionDTO updateRequest(ReadmissionDTO dto) {
		try{
			ReadmissionForm form=assem.toEntity(dto);
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
	public List<ReadmissionDTO> getPendingPetitionsOfstuent() {
		List<ReadmissionForm> forms=rep.getAll();
		List<ReadmissionDTO> dtos=new ArrayList<ReadmissionDTO>();
		for(int i=0;i<forms.size();i++)
		{
			ReadmissionForm form = forms.get(i);
			if(form.getPerformed()== null || form.getPerformed() != true)
					dtos.add(assem.toDTO(form));
		}
		return dtos;
	}

	@Override
	public List<ReadmissionDTO> getArchievedPetitionsOfstuent() {
		List<ReadmissionForm> forms=rep.getAll();
		List<ReadmissionDTO> dtos=new ArrayList<ReadmissionDTO>();
		for(int i=0;i<forms.size();i++)
		{
			ReadmissionForm form = forms.get(i);
			if(form.getPerformed()!=null && form.getPerformed()==true)
					dtos.add(assem.toDTO(form));
			
		}
		return dtos;
	}

}
