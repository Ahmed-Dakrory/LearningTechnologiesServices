/**
 * 
 */
package main.com.zc.services.applicationService.forms.course_replacement_form.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.course_replacement_formForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.course_replacement_form.dto.course_replacement_formDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya.alaa
 * @author Eman
 */
public class course_replacement_formAssembler {

	public course_replacement_formDTO toDTO(course_replacement_formForm form)
	{
		course_replacement_formDTO dto=new course_replacement_formDTO();
		dto.setId(form.getId());
		dto.setStep(form.getStep());
		dto.setReverted(form.getReverted());
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review By Registrar");
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
		else if(form.getStep().equals(PetitionStepsEnum.ACCREDITION_ENG))
		{
			String ENG_SCI = "";
			
				ENG_SCI = Constants.ACCREDITION_ENG_NAME;
			
			dto.setCurrentStatus("Reviewing By Head Of Accredition: " +ENG_SCI);
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else if(form.getStep().equals(PetitionStepsEnum.ACCREDITION_Science))
		{
			String ENG_SCI = "";
			
				ENG_SCI = Constants.ACCREDITION_SCI_NAME;
			
			dto.setCurrentStatus("Reviewing By Head Of Accredition: " +ENG_SCI);
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewing By Dean of Strategic Enrollment Management");

			dto.setNextStatus("Reviewing By Registrar");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getForwardTOIns()!=null)
			{
				dto.setCurrentStatus("Reviewing By " + form.getForwardTOIns().getName());
			}
			else {
			if(form.getNewMajor().getHeadOfMajorId() != null)
				dto.setCurrentStatus("Reviewing By " + form.getNewMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewing By Program Advisor");
			}
			String ENG_SCI = "";
			if(dto.getScience_or_engineering()==course_replacement_formForm.SCIENCE_UNIT_ACCEDITION) {
				ENG_SCI = Constants.ACCREDITION_SCI_NAME;
			}else {
				ENG_SCI = Constants.ACCREDITION_ENG_NAME;
			}
			dto.setNextStatus("Reviewing By Head Of Accredition: " +ENG_SCI);
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Reviewing By Registrar");
			dto.setNextStatus("Finished");
		}else 	if(form.getStep().equals(PetitionStepsEnum.CLOSED))
		{
			dto.setCurrentStatus("Closed");
			dto.setNextStatus("");
		}
		
		
		dto.setStatus(form.getStatus());
		
		
		
		 
				
				
		 
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
			  dto.setCourseFinished(form.getCourseFinished());
			  dto.setToReplaceCourse(form.getToReplaceCourse());
			  
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

	public course_replacement_formForm  toEntity(course_replacement_formDTO dto)
	{
		course_replacement_formForm form=new course_replacement_formForm();
		form.setId(dto.getId());
		form.setStep(dto.getStep());
		form.setReverted(dto.getReverted());
	
		
		form.setStatus(dto.getStatus());
		
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
		
		form.setScience_or_engineering(dto.getScience_or_engineering());
		
		 
		 
		 
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
			  form.setCourseFinished(dto.getCourseFinished());
			  form.setToReplaceCourse(dto.getToReplaceCourse());
			 
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
