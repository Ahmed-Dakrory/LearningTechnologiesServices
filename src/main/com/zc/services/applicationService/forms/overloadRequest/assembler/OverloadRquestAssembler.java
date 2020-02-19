/**
 * 
 */
package main.com.zc.services.applicationService.forms.overloadRequest.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya.alaa
 *
 */
public class OverloadRquestAssembler {

	public OverloadRequestDTO toDTO(OverloadRequest form)
	{
		OverloadRequestDTO dto=new OverloadRequestDTO();
		dto.setId(form.getId());
		dto.setYear(form.getYear());
		dto.setMobile(form.getMobile());
		dto.setPerformed(form.getPerformed());
		dto.setReason(form.getReason());
			dto.setStatus(form.getStatus());
		dto.setReverted(form.getReverted());
		dto.setStep(form.getStep());
		dto.setSubmissionDate(form.getSubmissionDate());
		dto.setInsActionDate(form.getInsActionDate());
		dto.setDeanActionDate(form.getDeanActionDate());
		dto.setProvostActionDate(form.getProvostActionDate());
		dto.setAdmissionHeadActionDate(form.getAdmissionHeadActionDate());
		dto.setAdmissionDeptActionDate(form.getAdmissionDeptActionDate());
		dto.setProvostRequired(form.getProvostRequired());
		
		try{
			StudentDTO student=new StudentDTO();
			student.setId(form.getStudent().getId());
			student.setName(form.getStudent().getData().getNameInEnglish());
			student.setMail(form.getStudent().getData().getMail());
			student.setFacultyId(form.getStudent().getFileNo());
			dto.setStudent(student);
			dto.setImage(form.getStudent().getData().getStudentImage());
				}
		catch(Exception ex)
		{
			System.out.println("Error in getting student of overload request");
			ex.printStackTrace();
		}
		
		try{
			CoursesDTO course=new CoursesDTO();
			course.setId(form.getCourse().getId());
			course.setName(form.getCourse().getName());
			dto.setCourse(course);
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting course of overload request");
			ex.printStackTrace();
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
			System.out.println("Error in getting major of overload request");
			ex.printStackTrace();
		}
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
			else {
			
			if(form.getMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewing By " + form.getMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
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
			if(form.getMajor().getHeadOfMajorId() != null)
				dto.setCurrentStatus("Reviewed By " + form.getMajor().getHeadOfMajorId().getName() + "  (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewed By Program Advisor");
			
			
			}
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
			if(dto.getProvostRequired() !=null && dto.getProvostRequired())
			dto.setNextStatus("Reviewing By Provost");
			else
				dto.setNextStatus("Reviewing By Admission Head");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.PROVOST))
		{
			dto.setCurrentStatus("Reviewed By Provost");
			dto.setNextStatus("Reviewing By Admission Head");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
		{
			dto.setCurrentStatus("Waiting Action From Registrar");
			dto.setNextStatus("Finished");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Action taken by Admission Department");
			/*if (form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon");
			} else if (form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}*/
		}
		else if(form.getStep().equals(PetitionStepsEnum.CLOSED))
		{
			dto.setCurrentStatus("Closed By Admission Department: "+form.getStatus());
		
		}
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
		dto.setGpa(form.getGPA());
		dto.setComment(form.getComment());
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
	
	public OverloadRequest toEntity(OverloadRequestDTO dto)
	{
		OverloadRequest request=new OverloadRequest();
		request.setId(dto.getId());
		request.setMobile(dto.getMobile());
		request.setPerformed(dto.getPerformed());
		request.setReason(dto.getReason());
		request.setStatus(dto.getStatus());
		request.setStep(dto.getStep());
		request.setSubmissionDate(dto.getSubmissionDate());
		request.setInsActionDate(dto.getInsActionDate());
		request.setDeanActionDate(dto.getDeanActionDate());
		request.setProvostActionDate(dto.getProvostActionDate());
		request.setAdmissionHeadActionDate(dto.getAdmissionHeadActionDate());
		request.setAdmissionDeptActionDate(dto.getAdmissionDeptActionDate());
		request.setYear(dto.getYear());
		request.setProvostRequired(dto.getProvostRequired());
		request.setReverted(dto.getReverted());
		try{
			Courses course=new Courses();
			course.setId(dto.getCourse().getId());
			request.setCourse(course);
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting course of overload request");
			ex.printStackTrace();
		}
		try{
			Majors major=new Majors();
			major.setId(dto.getMajor().getId());
			request.setMajor(major);
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting course of overload request");
			ex.printStackTrace();
		}
		try{
			Student student=new Student();
			student.setId(dto.getStudent().getId());
			request.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting student of overload request");
			ex.printStackTrace();
		}
		
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		request.setAttachments(attachmentAssm.toEntity(dto.getAttachments()));
		request.setGPA(dto.getGpa());
		request.setComment(dto.getComment());
		 request.setInsNotifyDate(dto.getNotifyAt());
	if(	 dto.getNotifyAt()==null)
	{
		request.setInsSendMail(null);
	}
	try{
		Employee ins=new Employee();
		ins.setId(dto.getForwardTOIns().getId());
		request.setForwardTOIns(ins);
	}
	catch(Exception ex)
	{
		System.out.println("Error in assign forwarded ins");
		System.out.println(ex.toString());
	}
	try{
		Employee ins=new Employee();
		ins.setId(dto.getForwardFromIns().getId());
		request.setForwardFromIns(ins);
	}
	catch(Exception ex)
	{
		System.out.println("Error in assign forwarded ins");
		System.out.println(ex.toString());
	}
	
		return request;
	}
}
