/**
 * 
 */
package main.com.zc.services.applicationService.forms.CourseRepeat.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class CourseRepeatAssembler {

	public CourseRepeatDTO toDTO(RepeatCourseForm form)
	{
		CourseRepeatDTO dto=new  CourseRepeatDTO();
		dto.setId(form.getId());
		dto.setReverted(form.getReverted());

		try 
		{
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
		}
		catch(Exception ex)
		{
			System.out.println("can't add attachment to repeat fprm");
		}
		dto.setComment(form.getComment());
		try{
			CoursesDTO addedCourseDto=new CoursesDTO();
			addedCourseDto.setId(form.getCourse().getId());
			addedCourseDto.setName(form.getCourse().getName());
			dto.setCourse(addedCourseDto);
			
			
			}
			catch(Exception ex)
			{
				System.out.println("Can't add  course to petitions");
			}
		dto.setGrade(form.getGrade());
		dto.setNotifyAt(form.getInsNotifyDate());
		dto.setInsSendMail(form.getInsSendMail());
		  try{
				MajorDTO major=new MajorDTO();
				major.setId(form.getMajor().getId());
				major.setMajorName(form.getMajor().getMajorName());
				InstructorDTO majorIns=new InstructorDTO();
			    majorIns.setId(form.getMajor().getHeadOfMajorId().getId());
			    majorIns.setName(form.getMajor().getHeadOfMajorId().getName());
			    majorIns.setMail(form.getMajor().getHeadOfMajorId().getMail());
			    major.setHeadOfMajor(majorIns);
				dto.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		  dto.setMobile(form.getMobile());
		  dto.setNewSem(form.getNewSem());
		  dto.setOldSem(form.getOldSem());
		  dto.setPerformed(form.getPerformed());
		  dto.setReason(form.getReason());
		  dto.setStatus(form.getStatus());
		  dto.setStep(form.getStep());
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
		  
		  
		  
		  dto.setSubmissionDate(form.getSubmissionDate());
		  if(form.getStep().equals(PetitionStepsEnum.AUDITING))
			{
				dto.setCurrentStatus("Auditing By Admission Department");
				
				dto.setNextStatus("Under Review" );
			}else
				if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
			  
				dto.setCurrentStatus("Under Review");
				if(form.getForwardTOIns()!=null)
				{
					dto.setNextStatus("Reviewing By " + form.getForwardTOIns().getName());
					
				}
				//TODO 
				//TODO the program advisor step will be skipped refference to Salam Mohsen request ++
			
				else 
					dto.setNextStatus("Reviewing By Dean of Academic Affairs");
				//TODO the program advisor step will be skipped refference to Salam Mohsen request --
				/*else {
				if(form.getMajor().getHeadOfMajorId() != null)
					dto.setNextStatus("Reviewing By " + form.getMajor().getHeadOfMajorId().getName()+ " (Program Advisor)");
				else
					dto.setNextStatus("Reviewing By Program Advisor");
			}
			*/}
		//TODO the program advisor step will be skipped refference to Salam Mohsen request --
			/*else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
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
				
				dto.setNextStatus("Reviewing By Dean Of students");
			}*/
			else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
			{
				dto.setCurrentStatus("Reviewed By Dean of Academic Affairs");
				dto.setNextStatus("Reviewing By Admission Head");
			}
			else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{
				dto.setCurrentStatus("Waiting For Action From Registrar");
				dto.setNextStatus("Finished");

			}
			else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
			{
				dto.setCurrentStatus("Reviewed By Admission Department");
				/*if (form.getStatus().contains(
						Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					dto.setNextStatus(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT);
				} else if (form.getStatus().contains(
						Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
				}*/
			}else if(form.getStep().equals(PetitionStepsEnum.CLOSED))
			{
				dto.setCurrentStatus("Closed");
			
			}
		  dto.setNotifyAt(form.getInsNotifyDate());  
		  try
			{
				InstructorDTO insDto=new InstructorDTO();
				insDto.setId(form.getForwardTOIns().getId());
				insDto.setName(form.getForwardTOIns().getName());
				dto.setForwardTOIns(insDto);
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				System.out.println("Error in assign forwarded ins");
				System.out.println(ex.toString());
			}
		  try
			{
				InstructorDTO insDto=new InstructorDTO();
				insDto.setId(form.getForwardFromIns().getId());
				insDto.setName(form.getForwardFromIns().getName());
				dto.setForwardFromIns(insDto);
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				System.out.println("Error in assign forwarded ins");
				System.out.println(ex.toString());
			}
		return dto;
	}
	public RepeatCourseForm toEntity(CourseRepeatDTO dto)
	{
		RepeatCourseForm form=new RepeatCourseForm();
		form.setId(dto.getId());
		try 
		{
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		form.setAttachments(attachmentAssm.toEntity(dto.getAttachments()));
		}
		catch(Exception ex)
		{
			System.out.println("can't add attachment to repeat fprm");
		}
		form.setComment(dto.getComment());
		try{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			
			form.setCourse(course);
			
			
			}
			catch(Exception ex)
			{
				System.out.println("Can't add  course to petitions");
			}
		form.setGrade(dto.getGrade());
		form.setInsNotifyDate(dto.getNotifyAt());
		form.setInsSendMail(dto.getInsSendMail());
		  try{
				Majors major=new Majors();
				major.setId(dto.getMajor().getId());
			    
				form.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		  form.setMobile(dto.getMobile());
		  form.setNewSem(dto.getNewSem());
		  form.setOldSem(dto.getOldSem());
		  form.setPerformed(dto.getPerformed());
		  form.setReason(dto.getReason());
		  form.setStatus(dto.getStatus());
		  form.setStep(dto.getStep());
		  form.setReverted(dto.getReverted());

		  try{
				Student student=new Student();
				student.setId(dto.getStudent().getId());
				
		       
				form.setStudent(student);
		        }
		        catch(Exception ex)
				{
					System.out.println("Can't add student to petitions");
				}
		  form.setSubmissionDate(dto.getSubmissionDate());
		 form.setInsNotifyDate(dto.getNotifyAt());
		 if(	 dto.getNotifyAt()==null)
			{
				form.setInsSendMail(null);
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
