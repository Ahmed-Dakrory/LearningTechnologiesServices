/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.dto;

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
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public class ChangeConcentrationDTO {

	private Integer id;
	
	private MajorDTO major;
	
	private BaseDTO currentConcen;
		
	private BaseDTO newConcen;
	
	private StudentDTO student;
		
	private PetitionStepsEnum step;
	
	private Boolean performed;
	
	private Date insNotifyDate;
	
	private Boolean insSendMail;
		
	private String moreDetails;
	
	private InstructorDTO forwardTOIns;
	
	private InstructorDTO forwardFromIns;
	
	private AttachmentDTO attachments;
	
	private Calendar submissionDate;

	private String nextStatus;

	private String friendlyDate;
	private String currentStatus;
	private byte[] image;
	private Boolean reverted;
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	private Date notifyAt;
	private InstructorDTO forwardIns;
	public AcceptRefuseFormEnum  getAcceptRefuseStatus()
	{
	
		// initialize the color to blank
		AcceptRefuseFormEnum result = AcceptRefuseFormEnum.Not;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		
		if(getStep().equals(PetitionStepsEnum.PA))
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
						else 
							result = AcceptRefuseFormEnum.Not;
						break;
					}
				}
			}
		}

		
		else if(getStep().equals(PetitionStepsEnum.ADMISSION_PROCESSING))
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

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	public BaseDTO getCurrentConcen() {
		return currentConcen;
	}

	public void setCurrentConcen(BaseDTO currentConcen) {
		this.currentConcen = currentConcen;
	}

	public BaseDTO getNewConcen() {
		return newConcen;
	}

	public void setNewConcen(BaseDTO newConcen) {
		this.newConcen = newConcen;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
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

	public String getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
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


	public AttachmentDTO getAttachments() {
		return attachments;
	}

	public void setAttachments(AttachmentDTO attachments) {
		this.attachments = attachments;
	}

	public Calendar getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
	}

	public List<PetitionsActionsDTO> getActionDTO() {
		return actionDTO;
	}

	public void setActionDTO(List<PetitionsActionsDTO> actionDTO) {
		this.actionDTO = actionDTO;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public Boolean getReverted() {
		return reverted;
	}

	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}
	
	
	
}
