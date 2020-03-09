/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.ReadmissionForm;
import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya.alaa
 * @author Eman
 */
public class ReadmissionAssembler {

	public ReadmissionDTO toDTO(ReadmissionForm form)
	{
		ReadmissionDTO dto=new ReadmissionDTO();
		dto.setId(form.getId());
		dto.setStep(form.getStep());
		dto.setReverted(form.getReverted());
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review");
			dto.setNextStatus("Reviewing By Registrar");
			if(form.getForwardTOIns()!=null)
			{
				dto.setCurrentStatus("Reviewing By " + form.getForwardTOIns().getName());
			}
			
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
		{
				dto.setCurrentStatus("Reviewed By Registrar");

				dto.setNextStatus("Reviewing By Dean of Academics");
			
			
		}
		
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Academics");
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
			dto.setNextStatus("");
		}else 	if(form.getStep().equals(PetitionStepsEnum.CLOSED))
		{
			dto.setCurrentStatus("Closed");
			dto.setNextStatus("");
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
		
		 
				
				
		 
			dto.setSubmissionDate(form.getSubmissionDate());
			dto.setPerformed(form.getPerformed());
			
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
			  dto.setMoreDetails(form.getMoreDetails());
			 
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

	public ReadmissionForm  toEntity(ReadmissionDTO dto)
	{
		ReadmissionForm form=new ReadmissionForm();
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
		
		 
		 
		 
		 form.setSubmissionDate(dto.getSubmissionDate());
		 form.setPerformed(dto.getPerformed());
			
			  try{
					Student student=new Student();
					student.setId(dto.getStudent().getId());
					form.setStudent(student);
			        }
			        catch(Exception ex)
					{
						System.out.println("Can't add student to petitions");
					}
			  form.setMoreDetails(dto.getMoreDetails());
			 
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
