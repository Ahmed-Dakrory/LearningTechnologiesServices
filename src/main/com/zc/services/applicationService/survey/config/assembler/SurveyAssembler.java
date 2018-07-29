/**
 * 
 */
package main.com.zc.services.applicationService.survey.config.assembler;

import main.com.zc.services.domain.survey.model.Survey;
import main.com.zc.services.presentation.survey.config.dto.SurveyDTO;

/**
 * @author omnya
 *
 */
public class SurveyAssembler {
	public SurveyDTO toDTO(Survey survey)
	{
		SurveyDTO dto=new SurveyDTO();
		dto.setId(survey.getId());
		dto.setName(survey.getName());
		dto.setActive(survey.getActive());
		dto.setDate(survey.getDate());
		return dto;
	} 
	
	public Survey toEntity(SurveyDTO dto)
	{
		Survey survey=new Survey();
		survey.setId(dto.getId());
		survey.setName(dto.getName());
		survey.setActive(dto.getActive());
		survey.setDate(dto.getDate());
		return survey;
	} 
}
