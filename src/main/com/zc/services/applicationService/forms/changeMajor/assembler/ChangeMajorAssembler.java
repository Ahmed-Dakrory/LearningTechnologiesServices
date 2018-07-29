/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya.alaa
 * @author Eman
 */
public class ChangeMajorAssembler {

	public ChangeMajorDTO toDTO(ChangeMajorForm form)
	{
		ChangeMajorDTO dto=new ChangeMajorDTO();
		dto.setId(form.getId());
		dto.setStep(form.getStep());
		dto.setReverted(form.getReverted());
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review");
			if(form.getForwardTOIns()!=null)
			{
				dto.setNextStatus("Reviewing By " + form.getForwardTOIns().getName());
			}
			else {
			if(form.getNewMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewing By " + form.getNewMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewing By Program Advisor");
			}
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getForwardTOIns()!=null)
			{
				dto.setCurrentStatus("Reviewed By " +form.getForwardTOIns().getName());
			}
			else {
			if(form.getNewMajor().getHeadOfMajorId() != null)
				dto.setCurrentStatus("Reviewed By " + form.getNewMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewed By Program Advisor");
			}
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
			dto.setNextStatus("Reviewing By Admission Head");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
		{
			dto.setCurrentStatus("Reviewed By Admission Head");
			dto.setNextStatus("Action taken Admission Department");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Reviewed By Admission Department");
		/*	if (form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon ");
			} else if (form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}*/
		}
		
		dto.setStatus(form.getStatus());
		
		/* try{
				MajorDTO major=new MajorDTO();
				major.setId(form.getMajor().getId());
				major.setMajorName(form.getMajor().getMajorName());
				dto.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}*/
		 try{
				MajorDTO curMajor=new MajorDTO();
				curMajor.setId(form.getCurMajor().getId());
				curMajor.setMajorName(form.getCurMajor().getMajorName());
				dto.setCurMajor(curMajor);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		 
		 try{
				MajorDTO newMajor=new MajorDTO();
				newMajor.setId(form.getNewMajor().getId());
				newMajor.setMajorName(form.getNewMajor().getMajorName());
				InstructorDTO ins=new InstructorDTO();
				ins.setId(form.getNewMajor().getHeadOfMajorId().getId());
				ins.setName(form.getNewMajor().getHeadOfMajorId().getName());
				ins.setMail(form.getNewMajor().getHeadOfMajorId().getMail());
				newMajor.setHeadOfMajor(ins);
				dto.setNewMajor(newMajor);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		 
			dto.setSubmissionDate(form.getSubmissionDate());
			dto.setPerformed(form.getPerformed());
			dto.setMobile(form.getMobile());
			
			  try{
					StudentDTO studentDTO=new StudentDTO();
					studentDTO.setId(form.getStudent().getId());
					studentDTO.setMail(form.getStudent().getData().getMail());
					studentDTO.setFacultyId(form.getStudent().getFileNo());
					studentDTO.setName(form.getStudent().getData().getNameInEnglish());
			       
					dto.setStudent(studentDTO);
					dto.setImage(form.getStudent().getData().getStudentImage());
					       }
			        catch(Exception ex)
					{
						System.out.println("Can't add student to petitions");
					}
			  dto.setGpa(form.getGpa());
			  dto.setMoreDetails(form.getMoreDetails());
			  dto.setCurSpecialization(form.getCurSpecialization());
			  dto.setNewSpecialization(form.getNewSpecialization());
			  dto.setDoubleSpecialization(form.getDoubleSpecialization());
			  
			  AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
			  dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
			  
			  dto.setComment(form.getComment());
			  dto.setNotifyAt(form.getInsNotifyDate());
			  try
				{
					InstructorDTO insDto=new InstructorDTO();
					insDto.setId(form.getForwardTOIns().getId());
					insDto.setName(form.getForwardTOIns().getName());
					dto.setForwardIns(insDto);
				}
				catch(Exception ex)
				{
					//ex.printStackTrace();
					System.out.println("Error in assign forwarded ins");
					System.out.println(ex.toString());
				}
		return dto;
	}

	public ChangeMajorForm  toEntity(ChangeMajorDTO dto)
	{
		ChangeMajorForm form=new ChangeMajorForm();
		form.setId(dto.getId());
		form.setStep(dto.getStep());
		form.setReverted(dto.getReverted());
	
		
		form.setStatus(dto.getStatus());
		
		/* try{
				Majors major=new Majors();
				major.setId(dto.getMajor().getId());
				major.setMajorName(dto.getMajor().getMajorName());
				form.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}*/
		 try{
				Majors curMajor=new Majors();
				curMajor.setId(dto.getCurMajor().getId());
				//curMajor.setMajorName(dto.getCurMajor().getMajorName());
				form.setCurMajor(curMajor);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		 
		 try{
				Majors newMajor=new Majors();
				newMajor.setId(dto.getNewMajor().getId());
				//newMajor.setMajorName(dto.getNewMajor().getMajorName());
				form.setNewMajor(newMajor);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		 
		 form.setSubmissionDate(dto.getSubmissionDate());
		 form.setPerformed(dto.getPerformed());
		 form.setMobile(dto.getMobile());
			
			  try{
					Student student=new Student();
					student.setId(dto.getStudent().getId());
					form.setStudent(student);
			        }
			        catch(Exception ex)
					{
						System.out.println("Can't add student to petitions");
					}
			  form.setGpa(dto.getGpa());
			  form.setMoreDetails(dto.getMoreDetails());
			  form.setCurSpecialization(dto.getCurSpecialization());
			  form.setNewSpecialization(dto.getNewSpecialization());
			  form.setDoubleSpecialization(dto.getDoubleSpecialization());
			  
			  AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
			  form.setAttachments(attachmentAssm.toEntity(dto.getAttachments()));
				
			  form.setComment(dto.getComment());
			  form.setInsNotifyDate(dto.getNotifyAt());
			  if(	 dto.getNotifyAt()==null)
				{
					form.setInsSendMail(null);
				}
			  try{
					Employee ins=new Employee();
					ins.setId(dto.getForwardIns().getId());
					form.setForwardTOIns(ins);
				}
				catch(Exception ex)
				{
					System.out.println("Error in assign forwarded ins");
					System.out.println(ex.toString());
				}
			  try{
					Employee ins=new Employee();
					ins.setId(dto.getForwardTOIns().getId());
					form.setForwardTOIns(ins);
				}
				catch(Exception ex)
				{
					System.out.println("Error in assign forwarded ins");
					System.out.println(ex.toString());
				}
			  try{
					Employee ins=new Employee();
					ins.setId(dto.getForwardFromIns().getId());
					form.setForwardFromIns(ins);
				}
				catch(Exception ex)
				{
					System.out.println("Error in assign forwarded ins");
					System.out.println(ex.toString());
				}
				       return form;
	}
}
