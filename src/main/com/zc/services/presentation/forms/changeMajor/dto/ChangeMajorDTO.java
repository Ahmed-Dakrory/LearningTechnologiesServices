/**
 * 
 */
package main.com.zc.services.presentation.forms.changeMajor.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.petition.model.Majors;
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
public class ChangeMajorDTO {

	
	private Integer id;
	
	
	private String status;
	
	
	private String mobile;
	
	
	private Double gpa;
	
	
	private MajorDTO major;
	
	
	private MajorDTO curMajor;
	
	private Boolean reverted;
	
	
	private MajorDTO newMajor;
	
	
	private String curSpecialization;
	
	
	private String newSpecialization;
	
	
	private String question1;
	
	
	private String question2;
	
	
	
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
	
		
		if(getStep().equals(PetitionStepsEnum.INSTRUCTOR))
		{
			if(authentication.getName().equals(getNewMajor().getHeadOfMajor().getMail()))
			{
				for(int i=0;i<getActionDTO().size();i++)
				{
					if(getActionDTO().get(i).getInstructorID().equals(getNewMajor().getHeadOfMajor().getId()))
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
						else 
							result = AcceptRefuseFormEnum.Not;
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


	public Double getGpa() {
		return gpa;
	}


	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}


	

	


	public String getCurSpecialization() {
		return curSpecialization;
	}


	public void setCurSpecialization(String curSpecialization) {
		this.curSpecialization = curSpecialization;
	}


	public String getNewSpecialization() {
		return newSpecialization;
	}


	public void setNewSpecialization(String newSpecialization) {
		this.newSpecialization = newSpecialization;
	}


	


	public MajorDTO getMajor() {
		return major;
	}


	public void setMajor(MajorDTO major) {
		this.major = major;
	}


	public MajorDTO getCurMajor() {
		return curMajor;
	}


	public void setCurMajor(MajorDTO curMajor) {
		this.curMajor = curMajor;
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

    



	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
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
