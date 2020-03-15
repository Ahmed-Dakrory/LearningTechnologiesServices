/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.survey.intendedMajor.service.IDeclarationOfMinorResultsService;
import main.com.zc.services.domain.survey.model.DeclarationOfMinor;
import main.com.zc.services.domain.survey.model.IDeclarationOfMinorRep;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service
public class DeclarationOfMinorResultsServiceImpl implements IDeclarationOfMinorResultsService{

	@Autowired
	IDeclarationOfMinorRep rep;
	@Override
	public List<IntendedMajorSurveyDTO> getResultsByMinorID(
			Integer concentrationID) {
		try {
			List<DeclarationOfMinor> results = new ArrayList<DeclarationOfMinor>();
			results = rep.getByMinorID(concentrationID);
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

}
