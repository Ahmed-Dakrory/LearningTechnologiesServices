/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.assembler.IntendedMajorAssembler;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IAdminIntenedMajorService;
import main.com.zc.services.domain.survey.model.IIntendedMajorSurveyRep;
import main.com.zc.services.domain.survey.model.IntendedMajorSurvey;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
@Service
public class AdminIntenedMajorServiceImpl implements IAdminIntenedMajorService{

	@Autowired
	IIntendedMajorSurveyRep rep;
	IntendedMajorAssembler assem=new IntendedMajorAssembler();
	@Override
	public List<IntendedMajorSurveyDTO> getbyMajorID(Integer id) {
		List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		try{
		List<IntendedMajorSurvey> results=rep.getByMajorID(id);
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
	public List<IntendedMajorSurveyDTO> getAll() {
		 List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		 try
		 {
			 List<IntendedMajorSurvey> results= rep.getAll();
			 for(int i=0;i<results.size();i++)
			 {
				 IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
				 dto.setId(results.get(i).getId());
				 StudentDTO student=new StudentDTO();
				 student.setId(results.get(i).getStudent().getId());
				 student.setName(results.get(i).getStudent().getData().getNameInEnglish());
				 student.setFacultyId(results.get(i).getStudent().getFileNo());
				 dto.setStudent(student);
				 MajorDTO major=new MajorDTO();
				 major.setId(results.get(i).getMajor().getId());
				 major.setMajorName(results.get(i).getMajor().getMajorName());
				 dto.setMajor(major);
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
	public List<IntendedMajorSurveyDTO> getbyMajorIDAndYearAndSemester(
			Integer id, Integer year, Integer semester) {
		List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		try{
		List<IntendedMajorSurvey> results=rep.getByMajorIDAndYearAndSemester(id, year, semester);
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
	public List<IntendedMajorSurveyDTO> getByYearAndSemester(Integer year,
			Integer semester) {
		List<IntendedMajorSurveyDTO> dtos=new ArrayList<IntendedMajorSurveyDTO>();
		try{
		List<IntendedMajorSurvey> results=rep.getByYearAndSemester( year, semester);
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
	public IntendedMajorSurveyDTO update(IntendedMajorSurveyDTO dto) {
		// TODO Auto-generated method stub
		IntendedMajorSurvey survey =  assem.toEntity(dto);
		rep.update(survey);
		return null;
	}
	}


