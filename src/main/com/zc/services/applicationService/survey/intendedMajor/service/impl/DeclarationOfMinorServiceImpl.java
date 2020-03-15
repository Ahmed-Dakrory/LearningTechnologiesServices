/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.survey.intendedMajor.assembler.DeclarationOfMinorAssembler;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfMinorService;
import main.com.zc.services.domain.petition.model.FormsStatus;
import main.com.zc.services.domain.petition.model.IFormsStatusRepository;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.DeclarationOfMinor;
import main.com.zc.services.domain.survey.model.IConcentrationRep;
import main.com.zc.services.domain.survey.model.IDeclarationOfMinorRep;
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
public class DeclarationOfMinorServiceImpl implements IDeclarationOfMinorService{

	@Autowired
	IDeclarationOfMinorRep rep;
	@Autowired
	IConcentrationRep concentratioRep;
	@Autowired
	IFormsStatusRepository formStatusRep;
	DeclarationOfMinorAssembler assem=new DeclarationOfMinorAssembler();
	@Override
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto) {
		try{
			IntendedMajorSurveyDTO addedDTO=new IntendedMajorSurveyDTO();
			DeclarationOfMinor vote=assem.toEntity(dto);
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
					
			DeclarationOfMinor form=assem.toEntity(dto);
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
			DeclarationOfMinor form=rep.getByStudentIDAndYearAndSemester(studentID,declarationForm.getYear(),declarationForm.getSemester().getID());
			
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<BaseDTO> getAllMinors() {
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
	public List<IntendedMajorSurveyDTO> getResultsByMinorID(
			Integer minorD) {
		try {
			List<DeclarationOfMinor> results = new ArrayList<DeclarationOfMinor>();
			results = rep.getByMinorID(minorD);
			List<IntendedMajorSurveyDTO> resultsDTO = new ArrayList<IntendedMajorSurveyDTO>();
			for (int i = 0; i < results.size(); i++) {
				IntendedMajorSurveyDTO dto = new IntendedMajorSurveyDTO();
				dto.setId(results.get(i).getId());
				BaseDTO con=new BaseDTO(results.get(i).getMinor().getId(),results.get(i).getMinor().getName());
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
			List<DeclarationOfMinor> results = new ArrayList<DeclarationOfMinor>();
			results = rep.getAll();
			List<IntendedMajorSurveyDTO> resultsDTO = new ArrayList<IntendedMajorSurveyDTO>();
			for (int i = 0; i < results.size(); i++) {
				IntendedMajorSurveyDTO dto = new IntendedMajorSurveyDTO();
				dto.setId(results.get(i).getId());
				BaseDTO con=new BaseDTO(results.get(i).getMinor().getId(),results.get(i).getMinor().getName());
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
	public List<IntendedMajorSurveyDTO> getAllByMinorIDAndYearAndSemester(
			Integer minorID, Integer year, Integer semester) {
		List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		try{
		List<DeclarationOfMinor> results=rep.getAllByMinorIDAndYearAndSemester(minorID, year, semester);
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
		List<DeclarationOfMinor> results=rep.getAllByYearAndSemester( year, semester);
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
	public List<BaseDTO> getMinorsByMajorID(Integer majorID) {
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
	public BaseDTO getMinorById(Integer id) {
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
