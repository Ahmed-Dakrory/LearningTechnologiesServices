/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.overloadRequest.assembler.OverloadRquestAssembler;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdminOverloadRequestService;
import main.com.zc.services.applicationService.forms.overloadRequest.services.IAdmissionHOverloadRequestService;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;

/**
 * @author momen
 *
 */
@Service
public class AdminOverloadRequestServiceImpl implements IAdminOverloadRequestService{

	@Autowired
	IOverloadRequestRep rep;
	OverloadRquestAssembler assem=new OverloadRquestAssembler();
	@Override
	public List<OverloadRequestDTO> getPendingForms() {
		List<OverloadRequestDTO> dtos=new ArrayList<OverloadRequestDTO>();
		try
		{
			List<OverloadRequest> request=rep.getAll();
			for(int i=0;i<request.size();i++)
			{
				if(request.get(i).getPerformed() == null || request.get(i).getPerformed() == false)
					dtos.add(assem.toDTO(request.get(i)));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in getting petitions");
		}
		return dtos;
	}

	@Override
	public List<OverloadRequestDTO> getArchievedForms() {
		List<OverloadRequestDTO> dtos=new ArrayList<OverloadRequestDTO>();
		try
		{
			List<OverloadRequest> request=rep.getAll();
			for(int i=0;i<request.size();i++)
			{
				if(request.get(i).getPerformed() != null && request.get(i).getPerformed() == true)
					dtos.add(assem.toDTO(request.get(i)));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Error in getting petitions");
		}
		return dtos;
	}

	@Override
	public OverloadRequestDTO updateStatus(OverloadRequestDTO dto) {
		
		try{
			OverloadRequest form=assem.toEntity(dto);
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

}
