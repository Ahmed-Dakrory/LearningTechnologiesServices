/**
 * 
 */
package main.com.zc.services.applicationService.forms.incompleteGrade.assembler;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.IncompleteGrade;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class IncompleteGradeAssembler {

	public IncompleteGradeDTO toDTO(IncompleteGrade form)
	{
		IncompleteGradeDTO dto=new  IncompleteGradeDTO();
		
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
			CoursesDTO course=new CoursesDTO();
			course.setId(form.getCourse().getId());
			course.setName(form.getCourse().getName());
			dto.setCourse(course);
			
			}
			catch(Exception ex)
			{
				System.out.println("Can't add  course to petitions");
			}
		
		dto.setDateOfExam(form.getDateOfExam());
		
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
				
			}
		
		 
		 
		 dto.setId(form.getId());
		 dto.setInsNotifyDate(form.getInsNotifyDate());
		 dto.setInsSendMail(form.getInsSendMail());
		 try
			{
				InstructorDTO insDto=new InstructorDTO();
				insDto.setId(form.getInstructor().getId());
				insDto.setName(form.getInstructor().getName());
				insDto.setMail(form.getInstructor().getMail());
				dto.setInstructor(insDto);
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				System.out.println("Error in assign forwarded ins");
				
			}
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
		 dto.setPerformed(form.getPerformed());
		 dto.setReason(form.getReason());
		 dto.setSemester(form.getSemester());
		 dto.setStatus(form.getStatus());
		 dto.setStep(form.getStep());
		 dto.setReverted(form.getReverted());
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
		  if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
			  
				dto.setCurrentStatus("Under Review");
				if(form.getForwardTOIns()!=null)
				{
					dto.setNextStatus("Reviewing By " + form.getForwardTOIns().getName());
				}
				else {
				if(form.getInstructor() != null)
					dto.setNextStatus("Reviewing By " + form.getInstructor().getName()+ " (Course Instructor)");
				else
					dto.setNextStatus("Course Instructor");
			}}
		  
			else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
			{
				if(form.getForwardTOIns()!=null)
				{
					dto.setCurrentStatus("Reviewed By " +form.getForwardTOIns().getName());
				}
				else {
					if(form.getInstructor() != null)
					dto.setCurrentStatus("Reviewed By " + form.getInstructor().getName() + " (Course Instructor)");
				else
					dto.setCurrentStatus("Reviewed By Course Instructor");
				}
				
				dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
			}
			else if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
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
				dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
			}
			else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
			{
				dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
				dto.setNextStatus("Reviewing By Admission Head");
			}
			else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
			{
				dto.setCurrentStatus("Reviewed By Admission Head");
				dto.setNextStatus("Action taken Admission Department");
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
			}
		return dto;
	}
	public IncompleteGrade toEntity(IncompleteGradeDTO dto)
	{
		IncompleteGrade form=new IncompleteGrade();
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
		
		form.setDateOfExam(dto.getDateOfExam());
		
		 try
			{
				Employee ins=new Employee();
				ins.setId(dto.getForwardFromIns().getId());
				form.setForwardFromIns(ins);
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				System.out.println("Error in assign forwarded ins");
				System.out.println(ex.toString());
			}
		 try
			{
				Employee ins=new Employee();
				ins.setId(dto.getForwardTOIns().getId());
				form.setForwardTOIns(ins);
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				System.out.println("Error in assign forwarded ins");
				
			}
		
		 form.setId(dto.getId());
		 form.setInsNotifyDate(dto.getInsNotifyDate());
		 form.setInsSendMail(dto.getInsSendMail());
		 try
			{
				Employee ins=new Employee();
				ins.setId(dto.getInstructor().getId());
				
				form.setInstructor(ins);
			}
			catch(Exception ex)
			{
				//ex.printStackTrace();
				System.out.println("Error in assign forwarded ins");
				
			}
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
		 form.setPerformed(dto.getPerformed());
		 form.setReason(dto.getReason());
		 form.setSemester(dto.getSemester());
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
		return form;
	}
}
