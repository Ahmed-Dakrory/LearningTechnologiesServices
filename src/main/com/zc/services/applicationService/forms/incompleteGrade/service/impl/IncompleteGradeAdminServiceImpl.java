/**
 * 
 */
package main.com.zc.services.applicationService.forms.incompleteGrade.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.forms.incompleteGrade.assembler.IncompleteGradeAssembler;
import main.com.zc.services.applicationService.forms.incompleteGrade.service.IIncompleteGradeAdminService;
import main.com.zc.services.domain.petition.model.IIncompleteGradeRep;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class IncompleteGradeAdminServiceImpl implements IIncompleteGradeAdminService{

	@Autowired
	IIncompleteGradeRep rep;


	IncompleteGradeAssembler assem=new IncompleteGradeAssembler();
	
	@Override
	public List<IncompleteGradeDTO> getPendingForms() {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try{
		List<IncompleteGrade> allForms=rep.getAll();
		
		for(int i=0;i<allForms.size();i++)
		{
			IncompleteGrade form = allForms.get(i);
			if(form.getPerformed() == null || form.getPerformed().equals(false))
				filterdDTO.add(assem.toDTO(allForms.get(i)));
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
	public List<IncompleteGradeDTO> getArchievedForms() {
		List<IncompleteGradeDTO> filterdDTO=new ArrayList<IncompleteGradeDTO>();
		try{
			
			List<IncompleteGrade> allForms=rep.getAll();
			
			for(int i=0;i<allForms.size();i++)
			{
				IncompleteGrade form = allForms.get(i);
				if(form.getPerformed() != null && form.getPerformed().equals(true))
					filterdDTO.add(assem.toDTO(allForms.get(i)));
			}
		
			}
			catch(Exception ex)
			{
				System.out.println("-------Error in getting old petition");
				ex.printStackTrace();
				
			}
		return filterdDTO;
	}

	@Override
	public IncompleteGradeDTO getById(Integer id) {
		IncompleteGradeDTO dto=new IncompleteGradeDTO();
		try
		{
			IncompleteGrade form=rep.getById(id);
			dto= assem.toDTO(form);
		}
		catch(Exception ex)
		{
			
		}
		return dto;
	}

	@Override
	public IncompleteGradeDTO updateStatusOfForm(IncompleteGradeDTO dto) {
		try{
			IncompleteGrade form=assem.toEntity(dto);
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
