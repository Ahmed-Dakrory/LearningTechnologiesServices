/**
 * 
 */
package main.com.zc.services.applicationService.survey.config.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.com.zc.services.applicationService.survey.config.ISurveyService;
import main.com.zc.services.applicationService.survey.config.assembler.SectionAssembler;
import main.com.zc.services.applicationService.survey.config.assembler.SurveyAssembler;
import main.com.zc.services.domain.courseEval.model.IScaleTypeRep;
import main.com.zc.services.domain.courseEval.model.ScaleType;
import main.com.zc.services.domain.survey.model.ISectionRepository;
import main.com.zc.services.domain.survey.model.ISurveyRepository;
import main.com.zc.services.domain.survey.model.ISurveySectionRepository;
import main.com.zc.services.domain.survey.model.Section;
import main.com.zc.services.domain.survey.model.Survey;
import main.com.zc.services.domain.survey.model.SurveySections;
import main.com.zc.services.presentation.survey.config.dto.SectionsDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveyDTO;
import main.com.zc.services.presentation.survey.config.dto.SurveySectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class SurveyServiceImpl implements ISurveyService{

	@Autowired
	ISurveyRepository surveyRepository;
	@Autowired
	ISectionRepository sectionRepository;
	
	@Autowired
	ISurveySectionRepository surveySectionsRepository;
	
	@Autowired
	IScaleTypeRep scaleRep;
	
	SurveyAssembler assem=new SurveyAssembler();
	SectionAssembler sectionAssem=new SectionAssembler();
	@Override
	public List<SurveyDTO> getSurveys() {
		List<SurveyDTO> dtos=new ArrayList<SurveyDTO>();
		List<Survey> surveys=surveyRepository.getAll();
		for(Survey survey:surveys){
			SurveyDTO dto=new SurveyDTO();
			dto=assem.toDTO(survey);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public SurveyDTO addSurvey(SurveyDTO dto) {
		try{
		Survey survey=assem.toEntity(dto);
		survey=surveyRepository.add(survey);
		dto=assem.toDTO(survey);
		return dto;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("----------------------Can not add survey---------------------");
			return null;
		}
		
	}

	@Override
	public List<SectionsDTO> getSections() {
		List<SectionsDTO> dtos=new ArrayList<SectionsDTO>();
		List<Section> sections=sectionRepository.getAll();
		for(Section section:sections){
			SectionsDTO dto=new SectionsDTO();
			dto=sectionAssem.toDTO(section);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public SectionsDTO addSection(SectionsDTO dto) {
		try{
			Section section=sectionAssem.toEntity(dto);
			section=sectionRepository.add(section);
			dto=sectionAssem.toDTO(section);
			return dto;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println("----------------------Can not add section---------------------");
				return null;
			}
	}

	@Override
	public List<SurveyDTO> getSurveysBySectionID(Integer id) {
		List<SurveySections> lst=new ArrayList<SurveySections>();
		lst=surveySectionsRepository.getBySectionId(id);
		List<SurveyDTO> surveys=new ArrayList<SurveyDTO>();
		for(SurveySections relation:lst)
		{
			SurveyDTO dto=assem.toDTO(relation.getSurvey());
			if(dto!=null)
				surveys.add(dto);
		}
		return surveys;
	}

	@Override
	public boolean saveSurveysSections(List<SurveySectionsDTO> lst) {
		SurveySections relation=new SurveySections();
		boolean b=true;
		for(SurveySectionsDTO dto:lst)
			{
			relation.setSection(sectionAssem.toEntity(dto.getSection()));
			relation.setSurvey(assem.toEntity(dto.getSurvey()));
			relation.setDate(new Date());
			SurveySections added=surveySectionsRepository.add(relation);
			if(added!=null)
				b &=true;
			else 
				b &=false;
			}
		return b;
	}

	@Override
	public List<SurveyDTO> getOtherSurveysBySectionID(Integer id) {
		List<Survey> lst=new ArrayList<Survey>();
		lst=surveyRepository.getAll();
		List<SurveyDTO> others=new ArrayList<SurveyDTO>();
		for(Survey survey:lst){
			SurveySections relation=new SurveySections();
			relation=surveySectionsRepository.getBySectionIdAndSurveyID(survey.getId(),id);
			if(relation==null)
			{
					others.add(assem.toDTO(survey));
				
			}
			else if(relation.getId()==null)
			{
				others.add(assem.toDTO(survey));
			
			}
				
		}
		
		return others;
	}

	@Override
	public boolean deleteSurvey(Integer survey, Integer section) {
		SurveySections relation=new SurveySections();
		relation=surveySectionsRepository.getBySectionIdAndSurveyID(survey,section);
		if(relation!=null)
				{
					return surveySectionsRepository.delete(relation);
				}
		else return false;
	}

	@Override
	public List<SurveySectionsDTO> getSectionsBySurveyID(Integer id) {
		
		List<SurveySections> lst=surveySectionsRepository.getBySurveyId(id);
		List<SurveySectionsDTO> dtos=new ArrayList<SurveySectionsDTO>();
		for(SurveySections  entity:lst)
		{
			SurveySectionsDTO dto=new SurveySectionsDTO();
			dto.setSurvey(assem.toDTO(entity.getSurvey()));
			dto.setSection(sectionAssem.toDTO(entity.getSection()));
			dtos.add(dto);
		}
		return dtos;
		
	}


	@Override
	public boolean addSurveySection(Integer survey, Integer section) {
		SurveySections relation=new SurveySections();
		Survey surveyEnt=surveyRepository.getById(survey);
		relation.setSurvey(surveyEnt);
		Section secEnt=sectionRepository.getById(section);
		relation.setSection(secEnt);
		relation.setDate(new Date());
		relation=surveySectionsRepository.add(relation);
		if(relation!=null)
				{
					return true;
				}
		else return false;
	}

	@Override
	public boolean deleteSection(Integer id) {
		Section section=sectionRepository.getById(id);
		return sectionRepository.delete(section);
		
	}

	@Override
	public SectionsDTO getByIdAndCourseID(Integer id, Integer cID) {
		Section section=sectionRepository.getByIdAndCourseID( id,  cID);
		return sectionAssem.toDTO(section);
	}

	@Override
	public List<ScaleTypeDTO> getScales() {
		
		List<ScaleType> scales=scaleRep.getAll();
		List<ScaleTypeDTO> dtos=new ArrayList<ScaleTypeDTO>();
		for(ScaleType scale:scales)
		{
			ScaleTypeDTO dto=new ScaleTypeDTO();
			dto.setId(scale.getId());
			dto.setName(scale.getName());
			List<ScaleSelectionsDTO> selections=new ArrayList<ScaleSelectionsDTO>();
			for(int j=0;j<scale.getSelections().size();j++)
			{
				ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
				selection.setId(scale.getSelections().get(j).getId());
				selection.setName(scale.getSelections().get(j).getName());
				selection.setType(scale.getSelections().get(j).getType());
				selections.add(selection);
			}
			dto.setSelections(selections);
			dtos.add(dto);
		}
		return  dtos;
	}

	@Override
	public ScaleTypeDTO getByName(String name) {
		ScaleType scale=scaleRep.getByName(name);
		ScaleTypeDTO dto=new ScaleTypeDTO();
		dto.setId(scale.getId());
		dto.setName(scale.getName());
		List<ScaleSelectionsDTO> selections=new ArrayList<ScaleSelectionsDTO>();
		for(int j=0;j<scale.getSelections().size();j++)
		{
			ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
			selection.setId(scale.getSelections().get(j).getId());
			selection.setName(scale.getSelections().get(j).getName());
			selection.setType(scale.getSelections().get(j).getType());
			selections.add(selection);
		}
		dto.setSelections(selections);
		return dto;
	}

	@Override
	public List<SurveySectionsDTO> getGeneralSectionsBySurveyID(Integer id) {
		List<SurveySections> lst=surveySectionsRepository.getGeneralBySurveyId(id);
		List<SurveySectionsDTO> dtos=new ArrayList<SurveySectionsDTO>();
		for(SurveySections  entity:lst)
		{
			SurveySectionsDTO dto=new SurveySectionsDTO();
			dto.setSurvey(assem.toDTO(entity.getSurvey()));
			dto.setSection(sectionAssem.toDTO(entity.getSection()));
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<SurveySectionsDTO> getCourseSectionsBySurveyID(Integer surveyID,Integer courseID) {
		List<SurveySections> lst=surveySectionsRepository.getCourseSectionsBySurveyID(surveyID,courseID);
		List<SurveySectionsDTO> dtos=new ArrayList<SurveySectionsDTO>();
		for(SurveySections  entity:lst)
		{
			SurveySectionsDTO dto=new SurveySectionsDTO();
			dto.setSurvey(assem.toDTO(entity.getSurvey()));
			dto.setSection(sectionAssem.toDTO(entity.getSection()));
			dtos.add(dto);
		}
		return dtos;
	}

}
