/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.assembler;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.survey.config.assembler.SectionAssembler;
import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.courseEval.model.ScaleSelections;
import main.com.zc.services.domain.courseEval.model.ScaleType;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;

/**
 * @author omnya
 *
 */
public class CourseEvalQuestionsAssembler {

	
	public CourseEvalQuestionsDTO toDTO(CourseEvalQuestions ques)
	{
		CourseEvalQuestionsDTO dto=new CourseEvalQuestionsDTO();
		dto.setId(ques.getId());
		//dto.setCategory(ques.getCategory());
		SectionAssembler assem=new SectionAssembler();
		dto.setSectionDTO(assem.toDTO(ques.getCategory()));
		dto.setText(ques.getText());
		try{
		ScaleTypeDTO scaleTypedto=new ScaleTypeDTO();
		scaleTypedto.setId(ques.getScaleType().getId());
		scaleTypedto.setName(ques.getScaleType().getName());
		List<ScaleSelectionsDTO> selectionslst=new ArrayList<ScaleSelectionsDTO>();
		try{
		for(ScaleSelections selection: ques.getScaleType().getSelections())
		{
			ScaleSelectionsDTO selectionDTO=new ScaleSelectionsDTO();
			selectionDTO.setId(selection.getId());
			selectionDTO.setName(selection.getName());
			selectionDTO.setType(selection.getType());
			selectionDTO.setScaleType(scaleTypedto);
			selectionslst.add(selectionDTO);
		}
		}
		catch(Exception ex){
			System.out.println("Can not add selection");
			System.out.println(ex.toString());
		}
		scaleTypedto.setSelections(selectionslst);
		dto.setScaleType(scaleTypedto);
		}
		catch(Exception ex){
			System.out.println("Can not add scale type");
			System.out.println(ex.toString());
		}
		return dto;
	}
	
	public CourseEvalQuestions toEntity(CourseEvalQuestionsDTO dto)
	{
		CourseEvalQuestions ques=new CourseEvalQuestions();
		ques.setId(dto.getId());
		//ques.setCategory(dto.getCategory());
		SectionAssembler assem=new SectionAssembler();
		ques.setCategory(assem.toEntity(dto.getSectionDTO()));
		
		ques.setText(dto.getText());
		
		try{
			ScaleType scaleType=new ScaleType();
			scaleType.setId(dto.getScaleType().getId());
			scaleType.setName(dto.getScaleType().getName());
			List<ScaleSelections> selectionslst=new ArrayList<ScaleSelections>();
			try{
			for(ScaleSelectionsDTO selectionDTO: dto.getScaleType().getSelections())
			{
				ScaleSelections selection=new ScaleSelections();
				selection.setId(selectionDTO.getId());
				selection.setName(selectionDTO.getName());
				selection.setType(selectionDTO.getType());
				selection.setScaleType(scaleType);
				selectionslst.add(selection);
			}
			}
			catch(Exception ex){
				System.out.println("Can not add selection");
				System.out.println(ex.toString());
			}
			scaleType.setSelections(selectionslst);
			ques.setScaleType(scaleType);
			}
			catch(Exception ex){
				System.out.println("Can not add scale type");
				System.out.println(ex.toString());
			}
		
		
		
		return ques;
	}
	
}
