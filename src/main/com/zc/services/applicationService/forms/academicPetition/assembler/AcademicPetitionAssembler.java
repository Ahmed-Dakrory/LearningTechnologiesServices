/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

/**
 * @author omnya.alaa
 *
 */
public class AcademicPetitionAssembler {

	public CoursePetitionDTO toDTO(CoursePetition form)
	{
		CoursePetitionDTO dto=new CoursePetitionDTO();
		dto.setId(form.getId());
		try{
		dto.setCourseID(form.getCourse().getId());
		dto.setCourseName(form.getCourse().getName());
		dto.setReverted(form.getReverted());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign course to petitions");
		}
		try
		{
			dto.setPersonId(form.getPerson().getId());
			dto.setStudentFileNo(form.getPerson().getFileNo());
			dto.setStudentMail(form.getPerson().getData().getMail());
			dto.setStudentName(form.getPerson().getData().getNameInEnglish());
			dto.setImage(form.getPerson().getData().getStudentImage());
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign student to petitions");
		}
		dto.setDone(form.getPerformed());
		dto.setMobileNo(form.getMobileNo());
		
		dto.setRequestText(form.getRequestText());
		dto.setStatus(form.getStatus());
	
		dto.setSubmittedDate(form.getSubmittedDate());
		dto.setStep(form.getStep());
		if(form.getStep().equals(PetitionStepsEnum.AUDITING))
		{
			dto.setCurStatus("Auditing By Admission Department");
			
			dto.setNextStatus("Under Review" );
		}else
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurStatus("Under Review");
			if(form.getForwardTOIns()!=null)
			{
				dto.setNextStatus("Reviewing By " + form.getForwardTOIns().getName());
			}
			else 
			{
			if(form.getCourse().getCourseCoordinator() != null)
				dto.setNextStatus("Reviewing By " + form.getCourse().getCourseCoordinator().getName() + " (Course Coordinator)");
			else
				dto.setNextStatus("Reviewing By Course Coordinator");
			}
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getCourse().getCourseCoordinator() != null)
				dto.setCurStatus("Reviewed By " + form.getCourse().getCourseCoordinator().getName() + "  (Course Coordinator)");
			else
				dto.setCurStatus("Reviewed By Course Coordinator");
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurStatus("Reviewed By Dean of Strategic Enrollment Management");
			dto.setNextStatus("Reviewing By Admission Head");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
		{
			dto.setCurStatus("Reviewed By Admission Head");
			dto.setNextStatus("Action taken Admission Department");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurStatus("Reviewed By Admission Department");
		/*	if (form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon ");
			} else if (form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}*/

		}
		else if(form.getStep().equals(PetitionStepsEnum.CLOSED))
		{
			dto.setCurStatus("Closed By Admission Department: "+form.getStatus());
		
		}
		dto.setTitle(form.getTitle());
		
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
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
			insDto.setId(form.getCourse().getCourseCoordinator().getId());
			insDto.setName(form.getCourse().getCourseCoordinator().getName());
			insDto.setMail(form.getCourse().getCourseCoordinator().getMail());
			dto.setInstructor(insDto);
		}
		catch(Exception ex)
		{
			//ex.printStackTrace();
			System.out.println("Error in assign forwarded ins");
			System.out.println(ex.toString());
		}
		return dto;
	}
	public CoursePetition toEntity(CoursePetitionDTO dto)
	{
		CoursePetition form=new CoursePetition();
		form.setId(dto.getId());
		try{
			Courses course=new Courses();
			course.setId(dto.getCourseID());
			form.setCourse(course);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign course to petitions");
		}
		try
		{
			Student student=new Student();
			student.setId(dto.getPersonId());
			form.setPerson(student);
			
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign student to petitions");
		}
		try
		{
			Employee ins=new Employee();
			ins.setId(dto.getForwardTOIns().getId());
			form.setForwardTOIns(ins);
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning  forward to instructor");
		}
		try
		{
			Employee ins=new Employee();
			ins.setId(dto.getForwardFromIns().getId());
			form.setForwardFromIns(ins);
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning  forward to instructor");
		}
		form.setPerformed(dto.getDone());
		form.setMobileNo(dto.getMobileNo());
		
		form.setRequestText(dto.getRequestText());
		form.setStatus(dto.getStatus());
		form.setReverted(dto.getReverted());
		form.setSubmittedDate(dto.getSubmittedDate());
		form.setStep(dto.getStep());
		form.setTitle(dto.getTitle());
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
		return form;
	}
	
}
