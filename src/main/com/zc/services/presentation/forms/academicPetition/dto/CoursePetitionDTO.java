/**
 * 
 */
package main.com.zc.services.presentation.forms.academicPetition.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.AcceptRefuseFormEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Omnya Alaa
 *
 */
public class CoursePetitionDTO {

	private Integer id;
	private String requestText;
	private int personId;
	private Calendar submittedDate;

	private String status;
	private String mobileNo;
	private int courseID;
	private String courseName;
	private String friendlyDate;

	private String studentName;
	private int studentFileNo;
	private String studentMail;

	private Boolean done;
    private String title;
	private String curStatus;
	private String nextStatus;
	private PetitionStepsEnum step;
	private AttachmentDTO Attachments;
    private InstructorDTO forwardTOIns;
	private InstructorDTO forwardFromIns;
	private InstructorDTO instructor;
	private String comment;
	
	private byte[] image;
	
	private Date notifyAt;
	
	private InstructorDTO forwardIns;
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	private StudentProfileDTO studentProfileDTO;
	private Boolean reverted;
	public CoursePetitionDTO() {
		super();
	}
	
	public AcceptRefuseFormEnum  getAcceptRefuseStatus()
	{
		// initialize the color to blank
		AcceptRefuseFormEnum result = AcceptRefuseFormEnum.Not;
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		
		 if(getStep().equals(PetitionStepsEnum.DEAN))
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
						
					{
						result = AcceptRefuseFormEnum.Not;
						break;
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
						if(getActionDTO().get(i).getActionType()!=null){
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
							result = AcceptRefuseFormEnum.Not;
						break;
					}
				}
			}
		}else 
			if(getStep().equals(PetitionStepsEnum.INSTRUCTOR))
			{
				if(authentication.getName().equals(getInstructor().getMail()))
				{
					for(int i=0;i<getActionDTO().size();i++)
					{
						if(getActionDTO().get(i).getInstructorID().equals(getInstructor().getId()))
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
	public String getRequestText() {
		return requestText;
	}
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public Calendar getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Calendar submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}


	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public String getFriendlyDate() {
		if(getSubmittedDate()!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getSubmittedDate().getTime());
			    return strDate;
			}
			
			else return "";
	}


	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
	}
	
	public String getRemindFriendlyDate() {
		if(getNotifyAt()!=null){
			 
			   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			    String strDate = sdf.format(getNotifyAt().getTime());
			    return strDate;
			}
			
			else return "";
	}


	public String getStudentName() {
		return studentName;
	}


	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}


	public int getStudentFileNo() {
		return studentFileNo;
	}


	public void setStudentFileNo(int studentFileNo) {
		this.studentFileNo = studentFileNo;
	}


	public String getStudentMail() {
		return studentMail;
	}


	public void setStudentMail(String studentMail) {
		this.studentMail = studentMail;
	}


	




	public Boolean getDone() {
		return done;
	}


	public void setDone(Boolean done) {
		this.done = done;
	}


	public String getCurStatus() {
		return curStatus;
	}


	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}


	public String getNextStatus() {
		return nextStatus;
	}


	public void setNextStatus(String nextStatus) {
		this.nextStatus = nextStatus;
	}


	public PetitionStepsEnum getStep() {
		return step;
	}


	public void setStep(PetitionStepsEnum step) {
		this.step = step;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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

	public InstructorDTO getInstructor() {
		return instructor;
	}

	public void setInstructor(InstructorDTO instructor) {
		this.instructor = instructor;
	}

	public StudentProfileDTO getStudentProfileDTO() {
		return studentProfileDTO;
	}

	public void setStudentProfileDTO(StudentProfileDTO studentProfileDTO) {
		this.studentProfileDTO = studentProfileDTO;
	}

	public Boolean getReverted() {
		return reverted;
	}

	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}
	
	
}
