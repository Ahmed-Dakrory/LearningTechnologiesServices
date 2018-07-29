/**
 * 
 */
package main.com.zc.services.presentation.forms.incompleteGrade.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.AcceptRefuseFormEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * @author omnya
 *
 */
public class IncompleteGradeDTO {

	
	private Integer id;
	
	private Boolean reverted;
	
	private String status;
	
	
	private String mobile;
	
	
	private PetitionStepsEnum step;
	
	
	private Boolean performed;

	
	private Date insNotifyDate;
	
	
	private Boolean insSendMail;
	
	
	private String comment;	
	
	
	private StudentDTO student;
	
	
	private Calendar submissionDate;
	
	
	private Calendar dateOfExam;
	

	private MajorDTO major;
	

	private CoursesDTO course;
	

	private InstructorDTO instructor;
	
	
	private String reason;
	

	private AttachmentDTO attachments;

	
	private InstructorDTO forwardTOIns;
	

	private InstructorDTO forwardFromIns;
	

	private SemesterEnum semester;

	private byte[] image;
	
	private String friendlyDate;
	private String friendlyDateOfExam;

	private String nextStatus;
	
	private String currentStatus;

	private Date notifyAt;
	
	private String remindFriendlyDate;
	private String forwardHistory;
	
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public PetitionStepsEnum getStep() {
		return step;
	}


	public void setStep(PetitionStepsEnum step) {
		this.step = step;
	}


	public Boolean getPerformed() {
		return performed;
	}


	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}


	public Date getInsNotifyDate() {
		return insNotifyDate;
	}


	public void setInsNotifyDate(Date insNotifyDate) {
		this.insNotifyDate = insNotifyDate;
	}


	public Boolean getInsSendMail() {
		return insSendMail;
	}


	public void setInsSendMail(Boolean insSendMail) {
		this.insSendMail = insSendMail;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public StudentDTO getStudent() {
		return student;
	}


	public void setStudent(StudentDTO student) {
		this.student = student;
	}


	public Calendar getSubmissionDate() {
		return submissionDate;
	}


	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}


	public Calendar getDateOfExam() {
		return dateOfExam;
	}


	public void setDateOfExam(Calendar dateOfExam) {
		this.dateOfExam = dateOfExam;
	}


	public MajorDTO getMajor() {
		return major;
	}


	public void setMajor(MajorDTO major) {
		this.major = major;
	}


	public CoursesDTO getCourse() {
		return course;
	}


	public void setCourse(CoursesDTO course) {
		this.course = course;
	}


	public InstructorDTO getInstructor() {
		return instructor;
	}


	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public AttachmentDTO getAttachments() {
		return attachments;
	}


	public void setAttachments(AttachmentDTO attachments) {
		this.attachments = attachments;
	}


	public InstructorDTO getForwardTOIns() {
		return forwardTOIns;
	}


	public void setForwardTOIns(InstructorDTO forwardTOIns) {
		this.forwardTOIns = forwardTOIns;
	}


	public InstructorDTO getForwardFromIns() {
		return forwardFromIns;
	}


	public void setForwardFromIns(InstructorDTO forwardFromIns) {
		this.forwardFromIns = forwardFromIns;
	}


	public SemesterEnum getSemester() {
		return semester;
	}


	public void setSemester(SemesterEnum semester) {
		this.semester = semester;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}


	public String getFriendlyDate() {
		if(getSubmissionDate()!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getSubmissionDate().getTime());
			    return strDate;
			}
			
			else return "";
		}


	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
	}


	public String getNextStatus() {
		return nextStatus;
	}


	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}


	public String getCurrentStatus() {
		return currentStatus;
	}


	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}


	


	public String getFriendlyDateOfExam() {
		if(getDateOfExam()!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getDateOfExam().getTime());
			    return strDate;
			}
			
			else return "";
	}


	public void setFriendlyDateOfExam(String friendlyDateOfExam) {
		this.friendlyDateOfExam = friendlyDateOfExam;
	}


	public Date getNotifyAt() {
		return notifyAt;
	}


	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
	}


	public String getRemindFriendlyDate() {
		if(getNotifyAt()!=null){
			 
			   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			    String strDate = sdf.format(getNotifyAt().getTime());
			    return strDate;
			}
			
			else return "";
	}


	public void setRemindFriendlyDate(String remindFriendlyDate) {
		this.remindFriendlyDate = remindFriendlyDate;
	}
	public String getAcceptRefuseCssName()
	{
		String result = "";
		
		switch (getAcceptRefuseStatus()) {
		case Refuse:
				result = "RefusedFormCss";
			break;
		case Accept:
				result = "AcceptFormCss";
			break;
		default:
				result = "NotFormCss";
			break;
		}
		
		return result;
	}
	
	public AcceptRefuseFormEnum  getAcceptRefuseStatus()
	{
		// initialize the color to blank
		AcceptRefuseFormEnum result = AcceptRefuseFormEnum.Not;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		
		if(getStep().equals(PetitionStepsEnum.INSTRUCTOR_OF_COURSE))
		{
			if(authentication.getName().equals(getInstructor().getMail()))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					
					if(getActionDTO().get(i).getInstructorID().equals(getInstructor().getId()))
					{
						if(getActionDTO().get(i).getActionType()!=null){
						if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Approved))
						
						{
							result = AcceptRefuseFormEnum.Accept;
						}
						else if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Refused))
							
							{
								result = AcceptRefuseFormEnum.Refuse;
							}
						else 
							
							result = AcceptRefuseFormEnum.Not;
						}
						else 
						{
							result = AcceptRefuseFormEnum.Not;
						}
						break;
					}
				}
			}
		}
		else if(getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(authentication.getName().equals(getMajor().getHeadOfMajor().getMail()))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(getMajor().getHeadOfMajor().getId()))
					{
						if(getActionDTO().get(i).getActionType()!=null){
						if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Approved))
						
						{
							result = AcceptRefuseFormEnum.Accept;
						}
						else if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Refused))
							
							{
								result = AcceptRefuseFormEnum.Refuse;
							}
						else 
							
							result = AcceptRefuseFormEnum.Not;
						}
						else
						{
							result = AcceptRefuseFormEnum.Not;
						}
						break;
					}
				}
			}
		}

		else if(getStep().equals(PetitionStepsEnum.DEAN))
		{
			if(authentication.getName().equals(Constants.DEAN_OF_STRATEGIC))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getActionType()!=null){
					if(getActionDTO().get(i).getInstructorID().equals(Constants.DEAN_OF_STRATEGIC_ID)&&getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED))
					{
						
							result = AcceptRefuseFormEnum.Accept;
							break;
					}
						else if(getActionDTO().get(i).getInstructorID().equals(Constants.DEAN_OF_STRATEGIC_ID)&&getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
							
							{
								result = AcceptRefuseFormEnum.Refuse;
								break;
							}
						
					
					}
					else 
					{
						result = AcceptRefuseFormEnum.Not;
					}
					}
				
					
				}
			
		}
		
		else if(getStep().equals(PetitionStepsEnum.ADMISSION_HEAD))
		{
			if(authentication.getName().equals(Constants.ADMISSION_HEAD))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(Constants.ADMISSION_HEAD_ID))
					{
						if(getActionDTO().get(i).getActionType()!=null)
						{
						if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Approved))
						
						{
							result = AcceptRefuseFormEnum.Accept;
						}
						else if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Admission_Refused))
							
							{
								result = AcceptRefuseFormEnum.Refuse;
							}
						else 
							
							result = AcceptRefuseFormEnum.Not;
						}
						else 
						{
							result = AcceptRefuseFormEnum.Not;
						}
						break;
					}
				}
			}
		}
		else if(getStep().equals(PetitionStepsEnum.ADMISSION_DEPT))
		{
			if(authentication.getName().equals(Constants.ADMISSION_DEPT))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(Constants.ADMISSION_DEPT_ID))
					{
						if(getActionDTO().get(i).getActionType()!=null)
						{
						if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Approved))
						
						{
							result = AcceptRefuseFormEnum.Accept;
						}
						else if(getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.Refused))
							
							{
								result = AcceptRefuseFormEnum.Refuse;
							}
						else 
							
							result = AcceptRefuseFormEnum.Not;
						}
						else 
						{
							result = AcceptRefuseFormEnum.Not;
						}
						break;
					}
				}
			}
		}
		
		return result;
	}


	public String getForwardHistory() {
		return forwardHistory;
	}


	public void setForwardHistory(String forwardHistory) {
		this.forwardHistory = forwardHistory;
	}


	public List<PetitionsActionsDTO> getActionDTO() {
		return actionDTO;
	}


	public void setActionDTO(List<PetitionsActionsDTO> actionDTO) {
		this.actionDTO = actionDTO;
	}


	public Boolean getReverted() {
		return reverted;
	}


	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}


	
}
