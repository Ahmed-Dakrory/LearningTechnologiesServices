/**
 * 
 */
package main.com.zc.services.applicationService.generalFeedback.assempler;

import main.com.zc.services.applicationService.forms.shared.AttachmentsAssembler;
import main.com.zc.services.domain.feedback.model.Feedback;
import main.com.zc.services.domain.feedback.model.FeedbackCategory;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Attachments;
import main.com.zc.services.domain.petition.model.Majors;
import main.com.zc.services.presentation.generalFeedback.dto.FeedbackDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * @author Omnya Alaa
 * 
 */
public class FeedBackAssempler {

	public FeedbackDTO toDTO(Feedback feedback)
	{
		FeedbackDTO dto=new FeedbackDTO();
		dto.setId(feedback.getId());
		dto.setCategoryID(feedback.getCategory().getId());
		dto.setCategoryName(feedback.getCategory().getCategoryName());
		dto.setCategoryHeadID(feedback.getCategory().getCategoryHeadID());
		dto.setFeedbackForm(feedback.getFeedbackForm());
		dto.setSubmittedDate(feedback.getSubmittedDate());
		dto.setTitle(feedback.getTitle());
		dto.setStep(feedback.getStep());
		
		dto.setPerformed(feedback.getPerformed());
		try{
			MajorDTO major=new MajorDTO();
			major.setId(feedback.getMajor().getId());
			major.setMajorName(feedback.getMajor().getMajorName());
			InstructorDTO majorIns=new InstructorDTO();
			majorIns.setId(feedback.getMajor().getHeadOfMajorId().getId());
			majorIns.setName(feedback.getMajor().getHeadOfMajorId().getName());
			majorIns.setMail(feedback.getMajor().getHeadOfMajorId().getMail());
			major.setHeadOfMajor(majorIns);
			dto.setMajor(major);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add major to feedback form");
			System.out.println(ex.toString());
		}
		try{
			StudentDTO student=new StudentDTO();
			student.setFacultyId(feedback.getStudent().getFileNo());
			student.setId(feedback.getStudent().getId());
			student.setMail(feedback.getStudent().getData().getMail());
			student.setPhone(feedback.getStudent().getData().getPhone());
			student.setName(feedback.getStudent().getData().getNameInEnglish());
			dto.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add stduent to feedback form");
			System.out.println(ex.toString());
		}
		try{
			AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
			dto.setAttachments(attachmentAssm.toDTO(feedback.getAttachments()));
		
		}
		catch (Exception e) {
			System.out.println("Can't add attachment to feedback form");
			System.out.println(e.toString());
		}
		try{
		dto.setImage(feedback.getStudent().getData().getStudentImage());
		}
		catch (Exception e) {
			System.out.println("Can't add img to feedback form");
			System.out.println(e.toString());
		}
		return dto;
	}
	
	public Feedback  toEntity(FeedbackDTO dto)
	{
		Feedback feedback=new Feedback();
		feedback.setId(feedback.getId());
		FeedbackCategory category=new FeedbackCategory();
		category.setId(dto.getCategoryID());
		feedback.setCategory(category);
		feedback.setFeedbackForm(dto.getFeedbackForm());
		feedback.setSubmittedDate(dto.getSubmittedDate());
		feedback.setTitle(dto.getTitle());
		feedback.setStep(dto.getStep());
		feedback.setPerformed(dto.getPerformed());
		
		try{
			Majors major=new Majors();
			major.setId(dto.getMajor().getId());
			feedback.setMajor(major);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add major to feedback form");
			System.out.println(ex.toString());
		}
		try{
			Student student=new Student();
			student.setId(dto.getStudent().getId());
			feedback.setStudent(student);
		}
		catch(Exception ex)
		{
			System.out.println("Can't add stduent to feedback form");
			System.out.println(ex.toString());
		}
		try{
			AttachmentsAssembler attachmentAssm = new AttachmentsAssembler();
			feedback.setAttachments(attachmentAssm.toEntity(dto.getAttachments()));
		
		}
		catch (Exception e) {
			System.out.println("Can't add attachment to feedback form");
			System.out.println(e.toString());
		}
		return feedback;
	}
	
	
}
