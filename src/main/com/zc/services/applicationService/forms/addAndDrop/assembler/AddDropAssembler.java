/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public class AddDropAssembler  {

	public DropAddFormDTO toDTO(DropAddForm form)
	{
		DropAddFormDTO dto=new DropAddFormDTO();
		dto.setId(form.getId());
		try{
		CoursesDTO addedCourseDto=new CoursesDTO();
		dto.setCourseLab(form.getCourseLab());
		addedCourseDto.setId(form.getAddedCourse().getId());
		addedCourseDto.setName(form.getAddedCourse().getName());
		dto.setAddedCourse(addedCourseDto);
		
		
		}
		catch(Exception ex)
		{
			System.out.println("Can't add added course to petitions");
		}
		try{
			CoursesDTO droppedCourseDto=new CoursesDTO();
			droppedCourseDto.setId(form.getDroppedCourse().getId());
			droppedCourseDto.setName(form.getDroppedCourse().getName());
			dto.setDropCourse(droppedCourseDto);
			
			
			}
			catch(Exception ex)
			{
				System.out.println("Can't add droppped course to petitions");
			}
		
		dto.setStep(form.getStep());
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review");
			if(form.getForwardTOIns()!=null)
			{
				dto.setNextStatus("Reviewing By " + form.getForwardTOIns().getName());
			}
			else {
				//TODO Uncomment it in phase one and two
			
			 if(form.getDroppedCourseIns() != null)
			{	
				dto.setNextStatus("Reviewing By " + form.getDroppedCourseIns().getName() + " (Course Instructor)");
			}
			else if(form.getMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewing By " + form.getMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
			{
				//dto.setNextStatus("Reviewing By (Program Advisor)");
			    //Comment in phase 1 and 2
				//dto.setNextStatus("Reviewing By (Course Instructor)");
			}
			}
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getForwardTOIns()!=null)
			{
				dto.setCurrentStatus("Reviewed By " +form.getForwardTOIns().getName());
			}
			else {
				//TODO Uncomment it in phase one and two
			//ifform.getMajor().getHeadOfMajorId() != null)
				if(form.getDroppedCourseIns()!= null)
				{
				//TODO Uncomment it in phase one and two
				
				dto.setCurrentStatus("Reviewed By " + form.getDroppedCourseIns().getName() + "  (Course Instructor)");
				}
				else if(form.getMajor().getHeadOfMajorId()!=null)
				{
					dto.setCurrentStatus("Reviewed By " + form.getMajor().getHeadOfMajorId().getName() + "  (Program Advisor)");
				}
			///else
				//TODO Uncomment it in phase one and two
//				//dto.setCurrentStatus("Reviewed By Program Advisor");
				//dto.setCurrentStatus("Reviewed By Course Instructor");
			
			}
			dto.setCurrentStatus("Waiting Action From Registrar");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
			dto.setNextStatus("Reviewing By Admission Head");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.CLOSED))
		{
			dto.setCurrentStatus("Closed");
			dto.setNextStatus("");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
		{
			dto.setCurrentStatus("Waiting Action From Registrar");
			dto.setNextStatus("Finished");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Action taken By Admission Department");
			/*if (form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon ");
			} else if (form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}*/
		}else if(form.getStep().equals(PetitionStepsEnum.UNDER_PROCESSING)){
			dto.setCurrentStatus(PetitionStepsEnum.UNDER_PROCESSING.getName()+" By Admission Department");
			dto.setNextStatus("Action taken By Admission Department");
	
		}
		dto.setStatus(form.getStatus());
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
		dto.setSubmittedDate(form.getSubmissionDate());
		dto.setPerformed(form.getPerformed());
		dto.setPhone(form.getPhone());
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
		dto.setType(form.getType());
	
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
		
		dto.setComment(form.getComment());
		dto.setAddedSection(form.getAddedSection());
		dto.setDroppedSection(form.getDroppedSection());
		 dto.setNotifyAt(form.getInsNotifyDate()); 
		 dto.setReverted(form.getReverted());
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
		 try
			{
				InstructorDTO insDTO =new InstructorDTO();
				insDTO.setId(form.getDroppedCourseIns().getId());
				insDTO.setName(form.getDroppedCourseIns().getName());
				insDTO.setMail(form.getDroppedCourseIns().getMail());
				dto.setDroppedCourseIns(insDTO);
				
			}
			
			catch(Exception ex)
			{
				System.out.println("Can't add droppped course instructor to add drop form");
			}
			try
			{
				dto.setDropType(form.getDropType());
			}
			catch(Exception ex)
			{
				System.out.println("Can't add WF / WP to dto");
			}
			dto.setRepeatedCourse(form.getRepeatedCourse());
		return dto;
		
	}
	
	public DropAddForm toEntity(DropAddFormDTO dto)
	{
		DropAddForm form=new DropAddForm();
		
		try{
		Courses addedCourse=new Courses();
		
		 form.setCourseLab(dto.getCourseLab());
		if(dto.getAddedCourse()!=null) {
		 addedCourse.setId(dto.getAddedCourse().getId());
		form.setAddedCourse(addedCourse);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		try{
		Courses dropedCourse=new Courses();
		dropedCourse.setId(dto.getDropCourse().getId());
		form.setDroppedCourse(dropedCourse);
		}
		catch(Exception ex){}
		
		form.setId(dto.getId());
		Majors major=new Majors();
		major.setId(dto.getMajor().getId());
		form.setMajor(major);
		form.setPerformed(dto.getPerformed());
		form.setPhone(dto.getPhone());
		form.setStatus(dto.getStatus());
		form.setStep(dto.getStep());
		form.setReverted(dto.getReverted());
		Student student=new Student();
		student.setId(dto.getStudent().getId());
		form.setStudent(student);
		form.setSubmissionDate(dto.getSubmittedDate());
		form.setType(dto.getType());
		
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		form.setAttachments(attachmentAssm.toEntity(dto.getAttachments()));
		
		form.setComment(dto.getComment());
		form.setAddedSection(dto.getAddedSection());
		form.setDroppedSection(dto.getDroppedSection());
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
		 
		 try
			{
				Employee ins =new Employee();
				ins.setId(dto.getDroppedCourseIns().getId());
				
				form.setDroppedCourseIns(ins);
				
			}
			
			catch(Exception ex)
			{
				System.out.println("Can't add droppped course instructor to add drop form");
			}
			try
			{
				form.setDropType(dto.getDropType());
			}
			catch(Exception ex)
			{
				System.out.println("Can't add WF / WP to dto");
			}
			form.setRepeatedCourse(dto.getRepeatedCourse());
			return form;
	}
}
