/**
 * 
 */
package main.com.zc.services.applicationService.forms.graduationForm.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.com.zc.services.applicationService.forms.graduationForm.assembler.GraduationInformationAssembler;
import main.com.zc.services.applicationService.forms.graduationForm.service.IGraduationInformationAppService;
import main.com.zc.services.domain.petition.model.GraduationInformation;
import main.com.zc.services.domain.petition.model.IGraduationInformationRep;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;

/**
 * @author omnya
 *
 */
@Service
public class GraduationInformationAppServiceImpl implements IGraduationInformationAppService{

	@Autowired
	IGraduationInformationRep rep;
	
	GraduationInformationAssembler assem=new GraduationInformationAssembler();
	
	@Override
	public GraduationInformationDTO add(GraduationInformationDTO form) {
		try{
			GraduationInformation entity=assem.toEntity(form);
			entity=rep.add(entity);
			GraduationInformationDTO dto=new GraduationInformationDTO();
			
			dto=assem.toDTO(rep.getById(entity.getId()));
			return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean remove(GraduationInformationDTO form) {
		try{
			GraduationInformation entity=assem.toEntity(form);
			boolean b=rep.remove(entity);
			
			return b;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public GraduationInformationDTO update(GraduationInformationDTO form) {
		try{
			GraduationInformation entity=assem.toEntity(form);
			entity=rep.update(entity);
			entity=rep.getById(form.getId());
			GraduationInformationDTO dto=new GraduationInformationDTO();
			dto=assem.toDTO(entity);
			return dto;
		}
		catch(Exception ex){   
			ex.printStackTrace();
			return null;
		}	}

	@Override
	public List<GraduationInformationDTO> getAll() {
		List<GraduationInformationDTO> lst=new ArrayList<GraduationInformationDTO>();
		try{
			List<GraduationInformation> forms=new ArrayList<GraduationInformation>();
			forms=rep.getAll();
			for(GraduationInformation form:forms)
			{
				GraduationInformationDTO dto=new GraduationInformationDTO();
				dto=assem.toDTO(form);
				lst.add(dto);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return lst;
		}
	
	

	@Override
	public List<GraduationInformationDTO> getFormByStudentID(Integer studentID) {
		List<GraduationInformationDTO> lst=new ArrayList<GraduationInformationDTO>();
		try{
			List<GraduationInformation> forms=new ArrayList<GraduationInformation>();
			forms=rep.getFormByStudentID(studentID);
			for(GraduationInformation form:forms)
			{
				GraduationInformationDTO dto=new GraduationInformationDTO();
				dto=assem.toDTO(form);
				lst.add(dto);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return lst;
	}

	@Override
	public GraduationInformationDTO getById(Integer id) {
	
		try{
			GraduationInformationDTO dto=new GraduationInformationDTO();
			GraduationInformation form=new GraduationInformation();
			form=rep.getById(id);
			dto=assem.toDTO(form);
			return dto;
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
			
		}
		}

	@Override
	public GraduationInformationDTO getFormByStudentIDAndSemesterAndYear(Integer studentID, Integer year,
			Integer semester) {
		try{
			GraduationInformationDTO dto=new GraduationInformationDTO();
			GraduationInformation form=new GraduationInformation();
			form=rep.getFormByStudentIDAndSemesterAndYear(studentID,year,semester);
			dto=assem.toDTO(form);
			return dto;
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
			
		}
	}

	@Override
	public  List<GraduationInformationDTO> getFormBySemesterAndYear(Integer year, Integer semester) {
		List<GraduationInformationDTO> lst=new ArrayList<GraduationInformationDTO>();
		try{
			List<GraduationInformation> forms=new ArrayList<GraduationInformation>();
			forms=rep.getFormBySemesterAndYear(year,semester);
			for(GraduationInformation form:forms)
			{
				GraduationInformationDTO dto=new GraduationInformationDTO();
				dto=assem.toDTO(form);
				lst.add(dto);
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return lst;
	}

}
