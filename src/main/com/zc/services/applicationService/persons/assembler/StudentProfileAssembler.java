/**
 * 
 */
package main.com.zc.services.applicationService.persons.assembler;

import main.com.zc.services.domain.data.model.StudentProfile;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;

/**
 * @author omnya
 *
 */
public class StudentProfileAssembler {

	public StudentProfileDTO toDTO(StudentProfile profile)
	{
		
		StudentProfileDTO dto=new StudentProfileDTO();
		dto.setId(profile.getId());
		dto.setCompletedCreditHrs(profile.getCompletedCreditHrs());
		dto.setRegisteredCreditHrs(profile.getCurrentCreditHrs());
		dto.setRepeatedCourses(profile.getRepeatedCourses());
		dto.setSemester(profile.getSemester());
		dto.setYear(profile.getYear());
		dto.setGpa(profile.getGpa());
		dto.setConcentration(profile.getConcentration());
		dto.setMinor(profile.getMinor());
		dto.setTranscript(profile.getTranscript());
		dto.setAttempt_credit_hours(profile.getAttempt_credit_hours());
	try
		{
			StudentDTO student=new StudentDTO();
			student.setFacultyId(profile.getStudent().getFileNo());
			student.setId(profile.getStudent().getId());
			student.setMail(profile.getStudent().getData().getMail());
			student.setName(profile.getStudent().getData().getNameInEnglish());
			student.setPhone(profile.getStudent().getData().getPhone());
			dto.setStudent(student);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		try
		{
			MajorDTO major=new MajorDTO();
			major.setId(profile.getMajor().getId());
			major.setMajorName(profile.getMajor().getMajorName());
			dto.setMajor(major);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		dto.setStudentImage(profile.getStudent().getData().getStudentImage());
		dto.setMobile(profile.getStudent().getData().getPhone());
		dto.setGender(profile.getStudent().getData().getGender());
		return dto;
	}
	
	public StudentProfile  toEntity(StudentProfileDTO dto)
	{
		StudentProfile profile=new StudentProfile();
		profile.setId(dto.getId());
		profile.setCompletedCreditHrs(dto.getCompletedCreditHrs());
		profile.setCurrentCreditHrs(dto.getRegisteredCreditHrs());
		profile.setRepeatedCourses(dto.getRepeatedCourses());
		profile.setSemester(dto.getSemester());
		profile.setYear(dto.getYear());
		profile.setGpa(dto.getGpa());
		profile.setConcentration(dto.getConcentration());
		profile.setMinor(dto.getMinor());
		profile.setTranscript(dto.getTranscript());
		profile.setAttempt_credit_hours(dto.getAttempt_credit_hours());
		try
		{
			Student student=new Student();
			student.setId(dto.getStudent().getId());
			/*Data data=new Data();
			data.setGender(dto.getGender());
			data.setPhone(dto.getMobile());
			student.setData(data);*/
			profile.setStudent(student);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		try
		{
			Majors major=new Majors();
			major.setId(dto.getMajor().getId());
			profile.setMajor(major);
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		
		return profile;
	}
	
	}
	
	

