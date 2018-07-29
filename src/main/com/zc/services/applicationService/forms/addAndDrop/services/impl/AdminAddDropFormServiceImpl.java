/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.assembler.AddDropAssembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdminAddDropFormService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.IAdmissionHAddDropFormService;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author momen
 *
 */
@Service("AdminAddDropFormServiceImpl")
public class AdminAddDropFormServiceImpl implements IAdminAddDropFormService {

	@Autowired
	IAddDropFormRepository addDropRepository;
	AddDropAssembler assem=new AddDropAssembler();
	@Override
	public List<DropAddFormDTO> getPendingFormsOfAdmissionHead() {
	
		List<DropAddFormDTO> filterdDTO=new ArrayList<DropAddFormDTO>();
		try{
		List<DropAddForm> allForms=addDropRepository.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{	
			if(allForms.get(i).getPerformed()==null || allForms.get(i).getPerformed() == false)
			{
				filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
		}
		
		return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting pending petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	
	}
	@Override
	public List<DropAddFormDTO> getArchievedFormsOfAdmissionHead() {
		List<DropAddFormDTO> filterdDTO=new ArrayList<DropAddFormDTO>();
		try
		{
			
			List<DropAddForm> allForms=addDropRepository.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				if(allForms.get(i).getPerformed() != null && allForms.get(i).getPerformed() == true)
				{
					filterdDTO.add(assem.toDTO(allForms.get(i)));
				}
			}
			return filterdDTO;
		}
		catch(Exception ex)
		{
			System.out.println("-------Error in getting old petition");
			ex.printStackTrace();
			return filterdDTO;
		}
	}
	@Override
	public DropAddFormDTO updateStatusOfForm(DropAddFormDTO dto) {
		try{
			DropAddForm form=assem.toEntity(dto);
			form=addDropRepository.update(form);
			
			return assem.toDTO(form);
			}
			catch(Exception ex)
			{
				System.out.println("<<<<<<<<<< Form can't be updated >>>>>>>>>>");
				ex.printStackTrace();
				return null;
			}
	}


}
