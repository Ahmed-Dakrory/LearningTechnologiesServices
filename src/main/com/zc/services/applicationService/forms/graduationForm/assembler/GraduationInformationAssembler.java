/**
 * 
 */
package main.com.zc.services.applicationService.forms.graduationForm.assembler;

import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.GraduationInformation;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class GraduationInformationAssembler {
	public GraduationInformationDTO toDTO(GraduationInformation form)
	{
		GraduationInformationDTO dto=new GraduationInformationDTO();
		dto.setId(form.getId());
		dto.setAttend(form.getAttend());
		dto.setBirthPlace(form.getBirthPlace());
		dto.setEmegencyConatactName(form.getEmegencyConatactName());
		dto.setEmegencyMobile(form.getEmegencyMobile());
		dto.setEmegencyRelationship(form.getEmegencyRelationship());
		dto.setHight(form.getHight());
		dto.setMobile(form.getMobile());
		dto.setNationality(form.getNationality());
		dto.setnID(form.getNID());
		dto.setPhone(form.getPhone());
		dto.setSize(form.getSize());
		dto.setYear(form.getYear());
		dto.setSemester(form.getSemester());
		dto.setDate(form.getDate());
		dto.setFirstName(form.getFirstName());
		dto.setMiddleName(form.getMiddleName());
		dto.setLastName(form.getLastName());
		dto.setArabicName(form.getArabicName());
		dto.setAddress(form.getAddress());
		dto.setUpdateDate(form.getUpdateDate());
		try{
			BaseDTO concentration =new BaseDTO();
			concentration.setId(form.getConcentration().getId());
			concentration.setName(form.getConcentration().getName());
			concentration.setFileNo(form.getConcentration().getParent().getId());
			dto.setConcentration(concentration);
		}
		catch(Exception ex){
			System.out.println("************************Can't add concentartion to the graduation form**********************");
		}
		try{
			MajorDTO major=new MajorDTO();
			major.setId(form.getMajor().getId());
			major.setMajorName(form.getMajor().getMajorName());
			dto.setMajor(major);
		}
		catch(Exception ex){
			System.out.println("************************Can't add major to the graduation form**********************");
		}
		try{
			MajorDTO minor=new MajorDTO();
			minor.setId(form.getMinor().getId());
			minor.setMajorName(form.getMinor().getMajorName());
			dto.setMinor(minor);
		}
		catch(Exception ex){
			System.out.println("************************Can't add Minor to the graduation form**********************");
		}
		 try{
				StudentDTO studentDTO=new StudentDTO();
				studentDTO.setId(form.getStudent().getId());
				studentDTO.setMail(form.getStudent().getData().getMail());
				studentDTO.setFacultyId(form.getStudent().getFileNo());
				studentDTO.setName(form.getStudent().getData().getNameInEnglish());
		    	dto.setStudent(studentDTO);
				
				  }
		        catch(Exception ex)
				{
					System.out.println("Can't add student to graduation form");
				}

		return dto;
		
	}
	
	public GraduationInformation toEntity(GraduationInformationDTO dto)
	{
		GraduationInformation form=new GraduationInformation();
		form.setId(dto.getId());
		form.setAttend(dto.getAttend());
		form.setBirthPlace(dto.getBirthPlace());
		form.setEmegencyConatactName(dto.getEmegencyConatactName());
		form.setEmegencyMobile(dto.getEmegencyMobile());
		form.setEmegencyRelationship(dto.getEmegencyRelationship());
		form.setHight(dto.getHight());
		form.setMobile(dto.getMobile());
		form.setNationality(dto.getNationality());
		form.setNID(dto.getnID());
		form.setPhone(dto.getPhone());
		form.setSize(dto.getSize());
		form.setYear(dto.getYear());
		form.setSemester(dto.getSemester());
		form.setDate(dto.getDate());
		form.setFirstName(dto.getFirstName());
		form.setMiddleName(dto.getMiddleName());
		form.setLastName(dto.getLastName());
		form.setArabicName(dto.getArabicName());
		form.setAddress(dto.getAddress());
		form.setUpdateDate(dto.getUpdateDate());
		try{
			Concentration concentration =new Concentration();
			concentration.setId(dto.getConcentration().getId());
			form.setConcentration(concentration);
		}
		catch(Exception ex){
			System.out.println("************************Can't add concentartion to the graduation form**********************");
		}
		try{
			Majors major=new Majors();
			
			major.setId(dto.getMajor().getId());
			form.setMajor(major);
			
		}
		catch(Exception ex){
			System.out.println("************************Can't add major to the graduation form**********************");
		}
		try{
			Majors minor=new Majors();
			if(dto.getMinor().getId()==0)
			{
				form.setMinor(null);
			}
			else {
			minor.setId(dto.getMinor().getId());
			form.setMinor(minor);
			}
		}
		catch(Exception ex){
			System.out.println("************************Can't add Minor to the graduation form**********************");
		}
		 try{
				Student student=new Student();
				student.setId(dto.getStudent().getId());
				form.setStudent(student);
				
				  }
		        catch(Exception ex)
				{
					System.out.println("Can't add student to graduation form");
				}

		return form;
	}
}
