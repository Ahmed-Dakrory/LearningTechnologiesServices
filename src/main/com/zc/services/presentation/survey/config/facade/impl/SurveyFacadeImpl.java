/**
 * 
 */
package main.com.zc.services.presentation.survey.config.facade.impl;

import java.util.List;

import main.com.zc.services.applicationService.survey.config.ISurveyService;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveyDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveySectionsDTO;
import main.com.zc.services.presentation.survey.config.facade.ISurveyFacade;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("ISurveyFacade")
public class SurveyFacadeImpl implements ISurveyFacade{

	@Autowired
	ISurveyService service;
	@Override
	public List<SurveyDTO> getSurveys() {
	
		return service.getSurveys();
	}

	@Override
	public SurveyDTO addSurvey(SurveyDTO survey) {
		return service.addSurvey(survey);
	}

	@Override
	public List<SectionsDTO> getSections() {
		return service.getSections();
	}

	@Override
	public SectionsDTO addSection(SectionsDTO section) {
		return service.addSection(section);
	}

	@Override
	public List<SurveyDTO> getSurveysBySectionID(Integer id) {
		return service.getSurveysBySectionID(id);
	}

	@Override
	public boolean saveSurveysSections(List<SurveySectionsDTO> lst) {
		return service.saveSurveysSections(lst);
	}

	@Override
	public List<SurveyDTO> getOtherSurveysBySectionID(Integer id) {
		return service.getOtherSurveysBySectionID(id);
	}

	@Override
	public boolean deleteSurvey(Integer survey, Integer section) {
		return service.deleteSurvey(survey,section);
		
	}

	@Override
	public  List<SurveySectionsDTO> getSectionsBySurveyID(Integer id) {
		return service.getSectionsBySurveyID(id);
		
	}

	@Override
	public boolean addSurveySection(Integer survey, Integer section) {
		return service.addSurveySection(survey,section);
	}

	@Override
	public boolean deleteSection(Integer id) {
		return service.deleteSection(id);
	}

	@Override
	public SectionsDTO getByIdAndCourseID(Integer id, Integer cID) {
		return service.getByIdAndCourseID( id,  cID);
	}

	@Override
	public List<ScaleTypeDTO> getScales() {
		return service.getScales();
	}

	@Override
	public ScaleTypeDTO getByName(String name) {
		return service.getByName(name);
	}

	@Override
	public List<SurveySectionsDTO> getGeneralSectionsBySurveyID(Integer id) {
		return service.getGeneralSectionsBySurveyID(id);
	}

	@Override
	public List<SurveySectionsDTO> getCourseSectionsBySurveyID(Integer surveyId,Integer courseID) {
		return service.getCourseSectionsBySurveyID(surveyId,courseID);
	}

}
