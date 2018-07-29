/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.IMajorAppService;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfConcentrationService;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.IConcentrationRep;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author Omnya Alaa
 *
 */
@Service
public class MajorAppServiceImpl implements IMajorAppService{

	@Autowired
	IMajorRepository majorRep;
	@Autowired
	IDeclarationOfConcentrationService concentrationsService;
	@Autowired
	IConcentrationRep conRep;
	@Override
	public boolean isMajorHead(Integer empID) {
		
		List<Majors> majors= majorRep.getByInsID(empID);
		if(majors!=null)
		{
			if(majors.size()>0)
			{
				return true;
			}
			else return false;
		}
		else return false;
		
	}

	@Override
	public List<MajorDTO> getAll() {
		List<MajorDTO> dtos=new ArrayList<MajorDTO>();
		try{
			List<Majors> majors=majorRep.getAllOLdNew();
			
			for(int i=0;i<majors.size();i++)
			{
				MajorDTO dto=new MajorDTO();
				dto.setId(majors.get(i).getId());
				dto.setMajorName(majors.get(i).getMajorName());
				dto.setType(majors.get(i).getType());
				dto.setVisabiltiy(majors.get(i).getHidden());
				dtos.add(dto);
				InstructorDTO ins=new InstructorDTO();
				ins.setId(majors.get(i).getHeadOfMajorId().getId());
				ins.setName(majors.get(i).getHeadOfMajorId().getName());
				dto.setHeadOfMajor(ins);
			}
		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		
		}
		return dtos;
	}

	@Override
	public List<MajorDTO> getAvailableOnly() {
		List<MajorDTO> dtos=new ArrayList<MajorDTO>();
		try{
			List<Majors> majors=majorRep.getAll();
			
			for(int i=0;i<majors.size();i++)
			{
				MajorDTO dto=new MajorDTO();
				dto.setId(majors.get(i).getId());
				dto.setMajorName(majors.get(i).getMajorName());
				dto.setType(majors.get(i).getType());
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
	public MajorDTO addMajor(MajorDTO major) {
		MajorDTO dto=new MajorDTO();
		try{
			Majors addedmajor=new Majors();
			Employee emp=new Employee();
			emp.setId(major.getHeadOfMajor().getId());
			addedmajor.setHeadOfMajorId(emp);
			addedmajor.setMajorName(major.getMajorName());
			addedmajor.setType(major.getType());
			addedmajor.setHidden(major.isVisabiltiy());
			addedmajor=majorRep.add(addedmajor);
			if(addedmajor!=null&&addedmajor.getId()!=null)
			{
				dto.setId(addedmajor.getId());
				dto.setMajorName(addedmajor.getMajorName());
				InstructorDTO ins=new InstructorDTO();
				ins.setName(addedmajor.getHeadOfMajorId().getName());
				ins.setMail(addedmajor.getHeadOfMajorId().getMail());
				dto.setType(major.getType());
				dto.setHeadOfMajor(ins);
				/*List<BaseDTO> concentrations=concentrationsService.getConcentrationsByMajorID(addedmajor.getId());
				dto.setConcentrations(concentrations);
				*/
				
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		
		}
		return dto;
	}

	@Override
	public boolean deleteMajor(MajorDTO major) {
	
		try{
			return majorRep.remove(major.getId());
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public MajorDTO updateMajor(MajorDTO major) {
		MajorDTO dto=new MajorDTO();
		try{
			Majors addedmajor=new Majors();
			Employee emp=new Employee();
			emp.setId(major.getHeadOfMajor().getId());
			addedmajor.setHeadOfMajorId(emp);
			addedmajor.setMajorName(major.getMajorName());
			addedmajor.setType(major.getType());
			addedmajor.setHidden(major.isVisabiltiy());
			addedmajor.setId(major.getId());
			addedmajor=majorRep.update(addedmajor);
			if(addedmajor!=null&&addedmajor.getId()!=null)
			{
				dto.setId(addedmajor.getId());
				dto.setMajorName(addedmajor.getMajorName());
				InstructorDTO ins=new InstructorDTO();
				ins.setName(addedmajor.getHeadOfMajorId().getName());
				ins.setMail(addedmajor.getHeadOfMajorId().getMail());
				dto.setType(major.getType());
				dto.setHeadOfMajor(ins);
			
				
			}
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		
		}
		return dto;
	}

	@Override
	public boolean deleteCocnentration(BaseDTO con) {
		try{
			return conRep.remove(con.getId());
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hideConcentration(Integer id) {
		try{
			return conRep.hide(id);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public BaseDTO addConcentration(BaseDTO con) {
		try{
			Concentration concentration=new Concentration();
			concentration.setName(con.getName());
			Majors major=new Majors();
			major.setId(con.getFileNo());
			concentration.setParent(major);
			Concentration addedconcentration=new Concentration();
			addedconcentration=conRep.add(concentration);
			BaseDTO adddedBaseDto=new BaseDTO();
			adddedBaseDto.setId(addedconcentration.getId());
			adddedBaseDto.setName(addedconcentration.getName());
			return adddedBaseDto;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
