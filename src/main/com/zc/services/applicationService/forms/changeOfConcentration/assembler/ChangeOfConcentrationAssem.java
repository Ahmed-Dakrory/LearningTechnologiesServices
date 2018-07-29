/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.ChangeConcentration;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class ChangeOfConcentrationAssem {
	public ChangeConcentrationDTO toDTO(ChangeConcentration form)
	{
		ChangeConcentrationDTO dto=new ChangeConcentrationDTO();
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
			if(form.getMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewing By " + form.getMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewing By Program Advisor");
			}
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.PA))
		{
			if(form.getForwardTOIns()!=null)
			{
				dto.setCurrentStatus("Reviewed By " +form.getForwardTOIns().getName());
			}
			else {
			if(form.getMajor().getHeadOfMajorId() != null)
				dto.setCurrentStatus("Reviewed By " + form.getMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewed By Program Advisor");
			}
			dto.setNextStatus("Reviewing By Admission Head");
		}
	
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
		{
			dto.setCurrentStatus("Reviewed By Admission Head");
			dto.setNextStatus("Finalizing By Registrar");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Finalized By Registrar");
		
		}
		
		 try{
				BaseDTO curConcen=new BaseDTO();
				curConcen.setId(form.getCurrentConcen().getId());
				curConcen.setName(form.getCurrentConcen().getName());
				dto.setCurrentConcen(curConcen);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add concentrtaion");
				}
		 try{
				BaseDTO newConcen=new BaseDTO();
				newConcen.setId(form.getNewConcen().getId());
				newConcen.setName(form.getNewConcen().getName());
				dto.setNewConcen(newConcen);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add concentrtaion");
				}
		 try{
				MajorDTO major=new MajorDTO();
				major.setId(form.getMajor().getId());
				major.setMajorName(form.getMajor().getMajorName());
				InstructorDTO ins=new InstructorDTO();
				ins.setId(form.getMajor().getHeadOfMajorId().getId());
				ins.setName(form.getMajor().getHeadOfMajorId().getName());
				ins.setMail(form.getMajor().getHeadOfMajorId().getMail());
				major.setHeadOfMajor(ins);
				dto.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add concentrtaion");
				}
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
			  
			  dto.setMoreDetails(form.getMoreDetails());
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

	public ChangeConcentration  toEntity(ChangeConcentrationDTO dto)
	{
		ChangeConcentration form=new ChangeConcentration();
		form.setId(dto.getId());
		form.setStep(dto.getStep());
		form.setReverted(dto.getReverted());
	
		
	 try{
				Majors major=new Majors();
				major.setId(dto.getMajor().getId());
				form.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		 
		 try{
				Concentration curConcen=new Concentration();
				curConcen.setId(dto.getCurrentConcen().getId());
				form.setCurrentConcen(curConcen);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add current concen to petitions");
				}
		 try{
				Concentration newConcen=new Concentration();
				newConcen.setId(dto.getNewConcen().getId());
				form.setNewConcen(newConcen);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add new concen to petitions");
				}
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
				
			  form.setMoreDetails(dto.getMoreDetails());
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
