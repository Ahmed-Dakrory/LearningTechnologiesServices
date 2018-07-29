/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfConcentrationResultsService;
import main.com.zc.services.domain.survey.model.DeclarationOfConcentration;
import main.com.zc.services.domain.survey.model.IDeclarationOfConcentrationRep;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service
public class DeclarationOfConcentrationResultsServiceImpl implements IDeclarationOfConcentrationResultsService{

	@Autowired
	IDeclarationOfConcentrationRep rep;
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

}
