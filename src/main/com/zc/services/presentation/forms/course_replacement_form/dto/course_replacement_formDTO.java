/**
 * 
 */
package main.com.zc.services.presentation.forms.course_replacement_form.dto;

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
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * @author omnya.alaa
 * @author Eman
 */
public class course_replacement_formDTO {

	
	private Integer id;
	
	
	private String status;
	
	
	
	private Boolean reverted;
	
	
	private MajorDTO newMajor;
	
	
	

	private String courseFinished;
	private String toReplaceCourse;
	
	
	
	private StudentDTO student;
	
	
	private Calendar submissionDate;

	
	private PetitionStepsEnum step;
	
	
	private Boolean performed;


	private String nextStatus;
	
	private String friendlyDate;
	private String currentStatus;
	   private InstructorDTO forwardTOIns;
		private InstructorDTO forwardFromIns;
	private AttachmentDTO Attachments;
	
	private String comment;
	
	private byte[] image;
	
	private Date notifyAt;
	
	private InstructorDTO forwardIns;
	
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	
	
	public AcceptRefuseFormEnum  getAcceptRefuseStatus()
	{
	
		// initialize the color to blank
		AcceptRefuseFormEnum result = AcceptRefuseFormEnum.Not;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		
		if(getStep().equals(PetitionStepsEnum.DEAN_OF_ACADIMICS))
		{
			if(authentication.getName().equals(Constants.DEAN_OF_ACADEMIC))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(Constants.DEAN_OF_ACADEMIC_ID))
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
						{result = AcceptRefuseFormEnum.Refuse;
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


	

	public MajorDTO getNewMajor() {
		return newMajor;
	}


	public void setNewMajor(MajorDTO newMajor) {
		this.newMajor = newMajor;
	}


	public void setStudent(StudentDTO student) {
		this.student = student;
	}

   



	public String getCourseFinished() {
		return courseFinished;
	}

	public void setCourseFinished(String courseFinished) {
		this.courseFinished = courseFinished;
	}

	public String getToReplaceCourse() {
		return toReplaceCourse;
	}

	public void setToReplaceCourse(String toReplaceCourse) {
		this.toReplaceCourse = toReplaceCourse;
	}

	public StudentDTO getStudent() {
		return student;
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


	public String getNextStatus() {
		return nextStatus;
	}


	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
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


	public AttachmentDTO getAttachments() {
		return Attachments;
	}


	public void setAttachments(AttachmentDTO attachments) {
		Attachments = attachments;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
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


	public InstructorDTO getForwardIns() {
		return forwardIns;
	}


	public void setForwardIns(InstructorDTO forwardIns) {
		this.forwardIns = forwardIns;
	}

	public List<PetitionsActionsDTO> getActionDTO() {
		return actionDTO;
	}

	public void setActionDTO(List<PetitionsActionsDTO> actionDTO) {
		this.actionDTO = actionDTO;
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

	public Boolean getReverted() {
		return reverted;
	}

	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}
	
}
