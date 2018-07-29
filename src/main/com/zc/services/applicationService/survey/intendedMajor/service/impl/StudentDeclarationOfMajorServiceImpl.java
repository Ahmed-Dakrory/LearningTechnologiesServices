/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.assembler.OfficialMajorAssembler;
import main.com.zc.services.applicationService.survey.intendedMajor.service.IStudentDeclarationOfMajorService;
import main.com.zc.services.domain.petition.model.FormsStatus;
import main.com.zc.services.domain.petition.model.IFormsStatusRepository;
import main.com.zc.services.domain.petition.model.IMajorRepository;
import main.com.zc.services.domain.survey.model.IOfficialMajorRep;
import main.com.zc.services.domain.survey.model.OfficialMajor;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;

/**
 * @author omnya
 *
 */
@Service
public class StudentDeclarationOfMajorServiceImpl implements IStudentDeclarationOfMajorService{

	@Autowired
	IOfficialMajorRep rep;
	@Autowired
	IFormsStatusRepository formsSettingRep;
	OfficialMajorAssembler assem=new OfficialMajorAssembler();
	@Override
	public IntendedMajorSurveyDTO submit(IntendedMajorSurveyDTO dto) {
		try{
			IntendedMajorSurveyDTO addedDTO=new IntendedMajorSurveyDTO();
		    OfficialMajor entity=assem.toEntity(dto);
		    FormsStatus decOfMajorSetting=formsSettingRep.getById(9);
		    entity.setYear(decOfMajorSetting.getYear());
		    entity.setSemester(decOfMajorSetting.getSemester());
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
		
			
			OfficialMajor form=assem.toEntity(dto);
			//OfficialMajor entity=assem.toEntity(dto);
			    FormsStatus decOfMajorSetting=formsSettingRep.getById(9);
			    form.setYear(decOfMajorSetting.getYear());
			    form.setSemester(decOfMajorSetting.getSemester());
			
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

			OfficialMajor form=rep.getByStudentID(studentID);
			
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}



	@Override
	public IntendedMajorSurveyDTO getByStudentIDAndYearAndSemester(
			Integer studentID) {
		IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
		try
		{

			 FormsStatus decOfMajorSetting=formsSettingRep.getById(9);
			 /* decOfMajorSetting.getYear();
			  decOfMajorSetting.getSemester();
			*/OfficialMajor form=rep.getByStudentIDAndYearAndSemester(studentID,decOfMajorSetting.getYear(), decOfMajorSetting.getSemester().getID());
			
			dto=assem.toDTO(form);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dto;
	}

}
