/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.survey.intendedMajor.assembler.DeclarationOfConcentrationAssembler;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfConcentrationService;
import main.com.zc.services.domain.petition.model.FormsStatus;
import main.com.zc.services.domain.petition.model.IFormsStatusRepository;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.DeclarationOfConcentration;
import main.com.zc.services.domain.survey.model.IConcentrationRep;
import main.com.zc.services.domain.survey.model.IDeclarationOfConcentrationRep;
import main.com.zc.services.domain.survey.model.IFormsSettingsRep;
import main.com.zc.services.domain.survey.model.OfficialMajor;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class DeclarationOfConcentrationServiceImpl implements IDeclarationOfConcentrationService{

	@Autowired
	IDeclarationOfConcentrationRep rep;
	@Autowired
	IConcentrationRep concentratioRep;
	@Autowired
	IFormsStatusRepository formStatusRep;
	DeclarationOfConcentrationAssembler assem=new DeclarationOfConcentrationAssembler();
	@Override
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto) {
		try{
			IntendedMajorSurveyDTO addedDTO=new IntendedMajorSurveyDTO();
			DeclarationOfConcentration vote=assem.toEntity(dto);
			FormsStatus declarationForm=formStatusRep.getById(7);
			vote.setYear(declarationForm.getYear());
			vote.setSemester(declarationForm.getSemester());
			
			addedDTO=assem.toDTO(rep.add(vote));
			return addedDTO;
		}
		catch(Exception ex)
		{
			System.out.println("Can't submit new form");
			ex.printStackTrace();
			return null;
		}
	
	}

	@Override
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto) {
try{
					
			DeclarationOfConcentration form=assem.toEntity(dto);
			FormsStatus declarationForm=formStatusRep.getById(7);
			form.setYear(declarationForm.getYear());
			form.setSemester(declarationForm.getSemester());
		
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
	public IntendedMajorSurveyDTO getByStudentID(Integer studentID) {
		IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
		try
		{
			FormsStatus declarationForm=formStatusRep.getById(7);
			DeclarationOfConcentration form=rep.getByStudentIDAndYearAndSemester(studentID,declarationForm.getYear(),declarationForm.getSemester().getID());
			
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<BaseDTO> getAllConcentrations() {
		List<BaseDTO> dtos=new ArrayList<BaseDTO>();
		try{
		List<Concentration> all=new ArrayList<Concentration>();
		all=concentratioRep.getAll();
		for(int i=0;i<all.size();i++)
		{
			BaseDTO dto=new BaseDTO(all.get(i).getId(), all.get(i).getName());
			//dto.setFileNo(all.get(i).getmaj);
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public List<IntendedMajorSurveyDTO> getResultsByConcentrationID(
			Integer concentrationID) {
		try {
			List<DeclarationOfConcentration> results = new ArrayList<DeclarationOfConcentration>();
			results = rep.getByConcentrationID(concentrationID);
			List<IntendedMajorSurveyDTO> resultsDTO = new ArrayList<IntendedMajorSurveyDTO>();
			for (int i = 0; i < results.size(); i++) {
				IntendedMajorSurveyDTO dto = new IntendedMajorSurveyDTO();
				dto.setId(results.get(i).getId());
				BaseDTO con=new BaseDTO(results.get(i).getConcentartion().getId(),results.get(i).getConcentartion().getName());
				dto.setConcentration(con);
			    dto.setMobile(results.get(i).getMobile());
				StudentDTO student = new StudentDTO();
				student.setFacultyId(results.get(i).getStudent().getFileNo());
				student.setId(results.get(i).getStudent().getId());
				student.setMail(results.get(i).getStudent().getData().getMail());
				student.setName(results.get(i).getStudent().getData()
						.getNameInEnglish());
				dto.setStudent(student);
				resultsDTO.add(dto);
			}

			return resultsDTO;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<IntendedMajorSurveyDTO> getAllReults() {
		try {
			List<DeclarationOfConcentration> results = new ArrayList<DeclarationOfConcentration>();
			results = rep.getAll();
			List<IntendedMajorSurveyDTO> resultsDTO = new ArrayList<IntendedMajorSurveyDTO>();
			for (int i = 0; i < results.size(); i++) {
				IntendedMajorSurveyDTO dto = new IntendedMajorSurveyDTO();
				dto.setId(results.get(i).getId());
				BaseDTO con=new BaseDTO(results.get(i).getConcentartion().getId(),results.get(i).getConcentartion().getName());
				dto.setConcentration(con);
				dto.setMobile(results.get(i).getMobile());
				StudentDTO student = new StudentDTO();
				student.setFacultyId(results.get(i).getStudent().getFileNo());
				student.setId(results.get(i).getStudent().getId());
				student.setMail(results.get(i).getStudent().getData().getMail());
				student.setName(results.get(i).getStudent().getData()
						.getNameInEnglish());
				dto.setStudent(student);
				resultsDTO.add(dto);
			}

			return resultsDTO;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<IntendedMajorSurveyDTO> getAllByConcentrationIDAndYearAndSemester(
			Integer concentrationID, Integer year, Integer semester) {
		List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		try{
		List<DeclarationOfConcentration> results=rep.getAllByConcentrationIDAndYearAndSemester(concentrationID, year, semester);
		for(int i=0;i<results.size();i++)
		{
			IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
			dto=assem.toDTO(results.get(i));
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
	public List<IntendedMajorSurveyDTO> getAllByYearAndSemester(Integer year,
			Integer semester) {
		List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		try{
		List<DeclarationOfConcentration> results=rep.getAllByYearAndSemester( year, semester);
		for(int i=0;i<results.size();i++)
		{
			IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
			dto=assem.toDTO(results.get(i));
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
	public List<BaseDTO> getConcentrationsByMajorID(Integer majorID) {
		List<BaseDTO> dtos=new ArrayList<BaseDTO>();
		try{
		List<Concentration> all=new ArrayList<Concentration>();
		all=concentratioRep.getByParentID(majorID);
		for(int i=0;i<all.size();i++)
		{
			BaseDTO dto=new BaseDTO(all.get(i).getId(), all.get(i).getName());
			//dto.setFileNo(all.get(i).getmaj);
			dtos.add(dto);
			
		}
		return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public BaseDTO getConcentrationById(Integer id) {
		BaseDTO dto=new BaseDTO();
		try{
		Concentration all=new Concentration();
		all=concentratioRep.getById(id);
		 dto=new BaseDTO(all.getId(), all.getName());
			//dto.setFileNo(all.get(i).getmaj);
			
			
		
		return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}


}
