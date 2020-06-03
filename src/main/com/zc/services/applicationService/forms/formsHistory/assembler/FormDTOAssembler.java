/**
 * 
 */
package main.com.zc.services.applicationService.forms.formsHistory.assembler;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;



public class FormDTOAssembler  {

	public FormDTO toDTO(DropAddForm form)
	{
		FormDTO dto=new FormDTO();
		dto.setId(form.getId());
		try{
		CoursesDTO addedCourseDto=new CoursesDTO();
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
			if(form.getMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewing By " + form.getMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewing By Program Advisor");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getMajor().getHeadOfMajorId() != null)
				dto.setCurrentStatus("Reviewed By " + form.getMajor().getHeadOfMajorId().getName() + "  (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewed By Program Advisor");
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
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
			if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon ");
			} else if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}
		}
		dto.setStatus(form.getStatus());
        try{
		MajorDTO major=new MajorDTO();
		major.setId(form.getMajor().getId());
		major.setMajorName(form.getMajor().getMajorName());
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
		dto.setFormTypesEnum(FormTypesEnum.DROPADD);
		return dto;
		
	}
	public FormDTO toDTO(CoursePetition form)
	{
		FormDTO dto=new FormDTO();
		dto.setId(form.getId());
		try{
			CoursesDTO courseDto=new CoursesDTO();
			courseDto.setId(form.getCourse().getId());
			courseDto.setName(form.getCourse().getName());
			dto.setCourse(courseDto);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign course to petitions");
		}
		
			try{
				StudentDTO studentDTO=new StudentDTO();
				studentDTO.setId(form.getPerson().getId());
				studentDTO.setMail(form.getPerson().getData().getMail());
				studentDTO.setFacultyId(form.getPerson().getFileNo());
				studentDTO.setName(form.getPerson().getData().getNameInEnglish());
				dto.setStudent(studentDTO);
				dto.setImage(form.getPerson().getData().getStudentImage());
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Can't assign student to petitions");
		}
		dto.setPerformed(form.getPerformed());
		dto.setPhone(form.getMobileNo());
	       
		dto.setRequestText(form.getRequestText());
		dto.setStatus(form.getStatus());
	
		dto.setSubmittedDate(form.getSubmittedDate());
		dto.setStep(form.getStep());
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review");
			if(form.getCourse().getCourseCoordinator() != null)
				dto.setNextStatus("Reviewing By " + form.getCourse().getCourseCoordinator().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewing By Program Advisor");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getCourse().getCourseCoordinator() != null)
				dto.setCurrentStatus("Reviewed By " + form.getCourse().getCourseCoordinator().getName() + "  (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewed By Program Advisor");
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
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
			if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon ");
			} else if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}

		}
		dto.setTitle(form.getTitle());
		
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
		dto.setComment(form.getComment());
		dto.setNotifyAt(form.getInsNotifyDate());
		dto.setFormTypesEnum(FormTypesEnum.ACADEMICPETITION);
		return dto;
	}
	public FormDTO toDTO(ChangeMajorForm form)
	{
		FormDTO dto=new FormDTO();
		dto.setId(form.getId());
		dto.setStep(form.getStep());
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review");
			if(form.getNewMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewing By " + form.getNewMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewing By Program Advisor");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getNewMajor().getHeadOfMajorId() != null)
				dto.setNextStatus("Reviewed By " + form.getNewMajor().getHeadOfMajorId().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewed By Program Advisor");
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
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
			if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon ");
			} else if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}
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
				dto.setNewMajor(newMajor);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		 
			dto.setSubmittedDate(form.getSubmissionDate());
			dto.setPerformed(form.getPerformed());
			dto.setPhone(form.getMobile());
			
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
			  dto.setQuestion1(form.getQuestion1());
			  dto.setCurSpecialization(form.getCurSpecialization());
			  dto.setNewSpecialization(form.getNewSpecialization());
			  dto.setQuestion2(form.getQuestion2());
			  
			  AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
			  dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
			  
			  dto.setComment(form.getComment());
			  dto.setNotifyAt(form.getInsNotifyDate());
			  dto.setFormTypesEnum(FormTypesEnum.CHANGEMAJOR);
				return dto;
	}
	public FormDTO toDTO(RepeatCourseForm form)
	{
		FormDTO dto=new  FormDTO();
		dto.setId(form.getId());
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
		  try{
				MajorDTO major=new MajorDTO();
				major.setId(form.getMajor().getId());
				major.setMajorName(form.getMajor().getMajorName());
				dto.setMajor(major);
		        }
		    	catch(Exception ex)
				{
					System.out.println("Can't add major to petitions");
				}
		  dto.setPhone(form.getMobile());
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
		  dto.setSubmittedDate(form.getSubmissionDate());
		  if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
			{
				dto.setCurrentStatus("Under Review");
				if(form.getCourse().getCourseCoordinator() != null)
					dto.setNextStatus("Reviewing By " + form.getCourse().getCourseCoordinator().getName() + " (Program Advisor)");
				else
					dto.setNextStatus("Reviewing By Program Advisor");
			}
			else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
			{
				if(form.getCourse().getCourseCoordinator() != null)
					dto.setNextStatus("Reviewed By " + form.getCourse().getCourseCoordinator().getName() + " (Program Advisor)");
				else
					dto.setCurrentStatus("Reviewed By Program Advisor");
				
				dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
			}
			else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
			{
				dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
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
				if (form.getStatus()!=null&&form.getStatus().contains(
						Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
					dto.setNextStatus(Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT);
				} else if (form.getStatus()!=null&&form.getStatus().contains(
						Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
					dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
				}
			}
		  dto.setNotifyAt(form.getInsNotifyDate());  
		  dto.setFormTypesEnum(FormTypesEnum.REPEATECOURSE);
			return dto;
	}
	public FormDTO toDTO(OverloadRequest form)
	{
		FormDTO dto=new FormDTO();
		dto.setId(form.getId());
		dto.setYear(form.getYear());
		dto.setPhone(form.getMobile());
		dto.setPerformed(form.getPerformed());
		dto.setReason(form.getReason());
			dto.setStatus(form.getStatus());
		
		dto.setStep(form.getStep());
		dto.setSubmittedDate(form.getSubmissionDate());
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
			dto.setMajor(major);
		}
		catch(Exception ex)
		{
			System.out.println("Error in getting major of overload request");
			ex.printStackTrace();
		}
		if(form.getStep().equals(PetitionStepsEnum.UNDER_REVIEW))
		{
			dto.setCurrentStatus("Under Review");
			if(form.getCourse().getCourseCoordinator() != null)
				dto.setNextStatus("Reviewing By " + form.getCourse().getCourseCoordinator().getName() + " (Program Advisor)");
			else
				dto.setNextStatus("Reviewing By Program Advisor");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(form.getCourse().getCourseCoordinator() != null)
				dto.setCurrentStatus("Reviewed By " + form.getCourse().getCourseCoordinator().getName() + "  (Program Advisor)");
			else
				dto.setCurrentStatus("Reviewed By Program Advisor");
			dto.setNextStatus("Reviewing By Dean of Strategic Enrollment Management");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.DEAN))
		{
			dto.setCurrentStatus("Reviewed By Dean of Strategic Enrollment Management");
			if(dto.getProvostRequired() != null && dto.getProvostRequired())
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
			dto.setCurrentStatus("Waiting For Action From Registrar");
			dto.setNextStatus("Finished");
		}
		else 	if(form.getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			dto.setCurrentStatus("Action taken by Admission Department");
			if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_APPROVED_BY_DEPARTMENT)) {
				dto.setNextStatus("Approved By Admission Department </br>and will be performed soon");
			} else if (form.getStatus()!=null&&form.getStatus().contains(
					Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT)) {
				dto.setNextStatus(Constants.PETITION_STATUS_REFUSED_BY_DEPARTMENT);
			}
		}
		
		AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
		dto.setAttachments(attachmentAssm.toDTO(form.getAttachments()));
		if(form.getGPA()!=null && !form.getGPA().equals(""))
		{try{dto.setGpa(Double.parseDouble(form.getGPA()));}catch(Exception e){e.printStackTrace();}
		}dto.setComment(form.getComment());
		 dto.setNotifyAt(form.getInsNotifyDate()); 
		 dto.setFormTypesEnum(FormTypesEnum.OVERLOADREQUEST);
				return dto; 
	}
	

}
