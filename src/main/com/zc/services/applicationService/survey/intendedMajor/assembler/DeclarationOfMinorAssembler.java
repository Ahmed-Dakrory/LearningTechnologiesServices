/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.assembler;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.DeclarationOfMinor;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/*
 * @author omnya
 *
 */
public class DeclarationOfMinorAssembler {

	public IntendedMajorSurveyDTO toDTO(DeclarationOfMinor survey)
	{
		
		IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
		
		if(survey!=null) {
			dto.setId(survey.getId());
		
		dto.setMobile(survey.getMobile());
		dto.setState(survey.getState());
		try{
		BaseDTO concentration=new BaseDTO();
		concentration.setId(survey.getMinor().getId());
		concentration.setName(survey.getMinor().getName());
		dto.setConcentration(concentration);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign concentration to student");
		}
		try
		{
		StudentDTO student=new StudentDTO();
		student.setId(survey.getStudent().getId());
		student.setFacultyId(survey.getStudent().getFileNo());
		student.setName(survey.getStudent().getData().getNameInEnglish());
		student.setMail(survey.getStudent().getData().getMail());
		dto.setStudent(student);
		}
		catch(Exception ex)
		{
			ex.toString();
			System.out.println("Error in attach student ");
		}
		return dto;
		}else {
			return null;
		}
		

	}
	public DeclarationOfMinor toEntity(IntendedMajorSurveyDTO  dto)
	{
		DeclarationOfMinor survey=new DeclarationOfMinor();
		survey.setId(dto.getId());
		survey.setMobile(dto.getMobile());
		survey.setState(dto.getState());
		try
		{
			Concentration concentration=new Concentration();
			concentration.setId(dto.getConcentration().getId());
			survey.setMinor(concentration);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign concentration to student");
		}
		
	    
	     
		try
		{
		Student student=new Student();
		student.setId(dto.getStudent().getId());
		survey.setStudent(student);
		}
		catch(Exception ex)
		{
			ex.toString();
			System.out.println("Error in attach student ");
		}
		return survey;

	}
	
}
