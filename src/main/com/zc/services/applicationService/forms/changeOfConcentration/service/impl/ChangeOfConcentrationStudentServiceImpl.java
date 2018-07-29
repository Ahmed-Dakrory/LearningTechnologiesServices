/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.forms.changeOfConcentration.assembler.ChangeOfConcentrationAssem;
import main.com.zc.services.applicationService.forms.changeOfConcentration.service.IChangeOfConcentrationStudentService;
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.IChangeConcentrationRep;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.IConcentrationRep;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service
public class ChangeOfConcentrationStudentServiceImpl implements IChangeOfConcentrationStudentService{
	@Autowired 
	IMajorRepository majorRep;
	@Autowired
	IConcentrationRep concentrationRep;
	@Autowired
	IChangeConcentrationRep rep;
	ChangeOfConcentrationAssem assem=new ChangeOfConcentrationAssem();
	@Override
	public List<MajorDTO> getAllMajors() {
		List<MajorDTO> dtos=new ArrayList<MajorDTO>();
		try{
			List<Majors> majors=majorRep.getAll();
			
			for(int i=0;i<majors.size();i++)
			{
				MajorDTO dto=new MajorDTO();
				dto.setId(majors.get(i).getId());
				dto.setMajorName(majors.get(i).getMajorName());
				dtos.add(dto);
				
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
		return dtos;
	}
	@Override
	public List<BaseDTO> getConcentrationsByMajor(Integer majorID) {
		List<BaseDTO> lst=new ArrayList<BaseDTO>() ;
		
		try{
			List<Concentration> concentrtaions=concentrationRep.getByParentID(majorID);
			for(int i=0;i<concentrtaions.size();i++)
			{
				BaseDTO dto=new BaseDTO();
				dto.setId(concentrtaions.get(i).getId());
				dto.setName(concentrtaions.get(i).getName());
				lst.add(dto);
			}
		}
		catch(Exception ex)
		{
			ex.toString();
		}
		return lst;
	}
	@Override
	public ChangeConcentrationDTO add(ChangeConcentrationDTO request) {
		try{
			ChangeConcentration form=assem.toEntity(request);
			request=assem.toDTO(rep.add(form));
			return request;
		}
		catch(Exception  ex){
			System.out.println("----------- Error in submitting form-----------");
			ex.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<ChangeConcentrationDTO> getPendingPetitionsByStudentID(Integer studentID) {
		List<ChangeConcentrationDTO> dtos=new ArrayList<ChangeConcentrationDTO>();
		try
		{
			
			List<ChangeConcentration> forms=rep.getByStudentID(studentID);
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
	public List<ChangeConcentrationDTO> getArchievedPetitionsByStudentID(Integer studentID) {
		List<ChangeConcentrationDTO> dtos=new ArrayList<ChangeConcentrationDTO>();
		try
		{
			
			List<ChangeConcentration> forms=rep.getByStudentID(studentID);
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

}
