/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.assembler;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.domain.survey.model.DeclarationOfConcentration;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/*
 * @author omnya
 *
 */
public class DeclarationOfConcentrationAssembler {

	public IntendedMajorSurveyDTO toDTO(DeclarationOfConcentration survey)
	{
		IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
		dto.setId(survey.getId());
		dto.setMobile(survey.getMobile());
		try{
		BaseDTO concentration=new BaseDTO();
		concentration.setId(survey.getConcentartion().getId());
		concentration.setName(survey.getConcentartion().getName());
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

	}
	public DeclarationOfConcentration toEntity(IntendedMajorSurveyDTO  dto)
	{
		DeclarationOfConcentration survey=new DeclarationOfConcentration();
		survey.setId(dto.getId());
		survey.setMobile(dto.getMobile());
		try
		{
			Concentration concentration=new Concentration();
			concentration.setId(dto.getConcentration().getId());
			survey.setConcentartion(concentration);
			
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
