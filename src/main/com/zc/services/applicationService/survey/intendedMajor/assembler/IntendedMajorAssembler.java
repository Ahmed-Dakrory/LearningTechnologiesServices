/**
 * 
 */
package main.com.zc.services.applicationService.survey.intendedMajor.assembler;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.survey.model.IntendedMajorSurvey;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class IntendedMajorAssembler {
public IntendedMajorSurveyDTO toDTO(IntendedMajorSurvey survey)
{
	IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
	dto.setId(survey.getId());
	dto.setMobile(survey.getMobile());
	try
	{
	MajorDTO major=new MajorDTO();
	major.setId(survey.getMajor().getId());
	major.setMajorName(survey.getMajor().getMajorName());
	dto.setMajor(major);
	}
	catch(Exception ex)
	{
		ex.toString();
		System.out.println("Error in attach major");
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
		System.out.println("Error in attach major ");
	}
	return dto;

}
public IntendedMajorSurvey toEntity(IntendedMajorSurveyDTO  dto)
{
	IntendedMajorSurvey survey=new IntendedMajorSurvey();
	survey.setId(dto.getId());
	survey.setMobile(dto.getMobile());
	try
	{
	Majors major=new Majors();
	major.setId(dto.getMajor().getId());
	
	survey.setMajor(major);
	}
	catch(Exception ex)
	{
		ex.toString();
		System.out.println("Error in attach major ");
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
		System.out.println("Error in attach major ");
	}
	return survey;

}
}
