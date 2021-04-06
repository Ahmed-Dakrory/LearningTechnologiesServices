/**
 * 
 */
package main.com.zc.services.presentation.forms.overloadRequest.dto;

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
public class OverloadRequestDTO {


	private Integer id;
	

	private String status;
	
	
	private String mobile;
	

	private StudentDTO student;
	

	private Calendar submissionDate;

	
	private PetitionStepsEnum step;
	
	
	private Boolean performed;
	
	
	private String reason;
	
	
	private String year;
	
	
	private CoursesDTO course;

	private String friendlyDate;
	private String currentStatus;
	private String nextStatus;
	
	
	private Calendar insActionDate;
	

	private Calendar deanActionDate;
	
	
	private Calendar provostActionDate;
	
	
	
	private Calendar admissionHeadActionDate;
	
	
	
	private Calendar admissionDeptActionDate;
	
	private AttachmentDTO Attachments;
	
	private Boolean  provostRequired;
	private String gpa;
	private MajorDTO major;
	private byte[] image;
	
	private String comment;
	
	private Date notifyAt;

	private InstructorDTO forwardTOIns;
    private InstructorDTO forwardFromIns;	
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	
	
	private Boolean reverted;
	public AcceptRefuseFormEnum  getAcceptRefuseStatus()
	{
		// initialize the color to blank
		AcceptRefuseFormEnum result = AcceptRefuseFormEnum.Not;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		
		if(getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(authentication.getName().equals(getMajor().getHeadOfMajor().getMail()))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(getMajor().getHeadOfMajor().getId()))
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
						else 	result = AcceptRefuseFormEnum.Not;
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
					if(getActionDTO().get(i).getActionType()!=null)
					{
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
						result = AcceptRefuseFormEnum.Not;
			        		break;
			
					}
				}
			
		}
		else if(getStep().equals(PetitionStepsEnum.PROVOST))
		{
			if(authentication.getName().equals(Constants.PROVOST))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getActionType()!=null)
					{
					if(getActionDTO().get(i).getInstructorID().equals(Constants.PROVOST_ID)&&getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.PROVOST_APPROVED))
					{
						
							result = AcceptRefuseFormEnum.Accept;
							break;
					}
						else if(getActionDTO().get(i).getInstructorID().equals(Constants.PROVOST_ID)&&getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.PROVOST_REFUSED))							
							{
								result = AcceptRefuseFormEnum.Refuse;
								break;
							}
						
					}
					else 
						result = AcceptRefuseFormEnum.Not;
					    break;
					}
				}
			
		}
		else if(getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
		{
			if(authentication.getName().equals(Constants.REGISTRAR_HEAD_EMAIL))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(Constants.REGISTRAR_HEAD_ID))
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
						else result = AcceptRefuseFormEnum.Not;
						
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
							result = AcceptRefuseFormEnum.Not;
						break;
					}
				}
			}
		}
		
		return result;
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


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public CoursesDTO getCourse() {
		return course;
	}


	public void setCourse(CoursesDTO course) {
		this.course = course;
	}


	public String getFriendlyDate() {
		if(getSubmissionDate()!=null){
			 
		 	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    String strDate = sdf.format(getSubmissionDate().getTime());
		    return strDate;
		}
		
		else return "";
	}


	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
	}


	public String getCurrentStatus() {
		return currentStatus;
	}


	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}


	public String getNextStatus() {
		return nextStatus;
	}


	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}


	public Calendar getInsActionDate() {
		return insActionDate;
	}


	public void setInsActionDate(Calendar insActionDate) {
		this.insActionDate = insActionDate;
	}


	public Calendar getDeanActionDate() {
		return deanActionDate;
	}


	public void setDeanActionDate(Calendar deanActionDate) {
		this.deanActionDate = deanActionDate;
	}


	public Calendar getProvostActionDate() {
		return provostActionDate;
	}


	public void setProvostActionDate(Calendar provostActionDate) {
		this.provostActionDate = provostActionDate;
	}


	public Calendar getAdmissionHeadActionDate() {
		return admissionHeadActionDate;
	}


	public void setAdmissionHeadActionDate(Calendar admissionHeadActionDate) {
		this.admissionHeadActionDate = admissionHeadActionDate;
	}


	public Calendar getAdmissionDeptActionDate() {
		return admissionDeptActionDate;
	}


	public void setAdmissionDeptActionDate(Calendar admissionDeptActionDate) {
		this.admissionDeptActionDate = admissionDeptActionDate;
	}


	public AttachmentDTO getAttachments() {
		return Attachments;
	}


	public void setAttachments(AttachmentDTO attachments) {
		Attachments = attachments;
	}


	public Boolean getProvostRequired() {
		return provostRequired;
	}


	public void setProvostRequired(Boolean provostRequired) {
		this.provostRequired = provostRequired;
	}
		public String getGpa() {
		return gpa;
	}


	public void setGpa(String gpa) {
		this.gpa = gpa;
	}

	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public MajorDTO getMajor() {
		return major;
	}


	public void setMajor(MajorDTO major) {
		this.major = major;
	}
	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getRemindFriendlyDate() {
		if(getNotifyAt()!=null){
			 
			   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			    String strDate = sdf.format(getNotifyAt().getTime());
			    return strDate;
			}
			
			else return "";
	}
	public Date getNotifyAt() {
		return notifyAt;
	}
	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
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
