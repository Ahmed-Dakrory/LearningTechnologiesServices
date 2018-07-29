/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import java.util.ArrayList;
import java.util.List;
import main.com.zc.services.applicationService.survey.intendedMajor.assembler.IntendedMajorAssembler;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IStudentIntendedMajorService;
import main.com.zc.services.domain.petition.model.FormsStatus;
import main.com.zc.services.domain.petition.model.IFormsStatusRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.survey.model.IIntendedMajorSurveyRep;
import main.com.zc.services.domain.survey.model.IntendedMajorSurvey;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class StudentIntendedMajorServiceImpl implements IStudentIntendedMajorService{


	@Autowired
	IIntendedMajorSurveyRep rep;
	@Autowired
	IMajorRepository majorRep;
	@Autowired
	IFormsStatusRepository formSettingRep;
	IntendedMajorAssembler assem=new IntendedMajorAssembler();
	
	@Override
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto) {
		try{
			IntendedMajorSurveyDTO addedDTO=new IntendedMajorSurveyDTO();
			IntendedMajorSurvey entity=assem.toEntity(dto);
			FormsStatus intendedMajorSetting=formSettingRep.getById(8);
			entity.setYear(intendedMajorSetting.getYear());
			entity.setSemester(intendedMajorSetting.getSemester());
			addedDTO=assem.toDTO(rep.add(entity));
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
			
			IntendedMajorSurveyDTO updatedDTO=new IntendedMajorSurveyDTO();
			IntendedMajorSurvey entity=assem.toEntity(dto);
			FormsStatus intendedMajorSetting=formSettingRep.getById(8);
			entity.setYear(intendedMajorSetting.getYear());
			entity.setSemester(intendedMajorSetting.getSemester());
			updatedDTO=assem.toDTO(rep.update(entity));
			
				
			
			return updatedDTO;
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
			
			IntendedMajorSurvey form=rep.getByStudentID(studentID);
				
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<MajorDTO> getMajors() {
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

}
