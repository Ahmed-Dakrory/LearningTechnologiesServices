/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.dto;

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
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.DropTypesEnum;
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
public class DropAddFormDTO {

	private Integer id;
	private CoursesDTO addedCourse;
	private CoursesDTO dropCourse;
	private AddDropFormTypesEnum type;
	private Boolean performed;
	private StudentDTO student;
	private Calendar submittedDate;
	private String friendlyDate;
	private String currentStatus;
	private String status;
	private String nextStatus;
	private String phone;
    private MajorDTO major;
    private PetitionStepsEnum step;
    private AttachmentDTO Attachments;
    private String comment;
    private String addedSection;
	private String droppedSection;
	private byte[] image;
	private InstructorDTO droppedCourseIns;
	private DropTypesEnum dropType;
	private Date notifyAt;
	private InstructorDTO forwardTOIns;
    private InstructorDTO forwardFromIns;
	private InstructorDTO forwardIns;
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	private Integer phase;
	private Boolean repeatedCourse;
	private String courseLab;
	private Boolean courseLabBool;
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
					if(getDroppedCourseIns()!=null)
					{
						if(getActionDTO().get(i).getInstructorID().equals(getDroppedCourseIns().getId()))
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
					else if(getActionDTO().get(i).getInstructorID().equals(getMajor().getHeadOfMajor().getId()))
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
					{
						result = AcceptRefuseFormEnum.Refuse;
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
	
	public AddDropFormTypesEnum getType() {
		return type;
	}
	public void setType(AddDropFormTypesEnum type) {
		this.type = type;
	}
	public Boolean getPerformed() {
		return performed;
	}
	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}
	public Calendar getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Calendar submittedDate) {
		this.submittedDate = submittedDate;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public MajorDTO getMajor() {
		return major;
	}
	public void setMajor(MajorDTO major) {
		this.major = major;
	}
	public PetitionStepsEnum getStep() {
		return step;
	}
	public void setStep(PetitionStepsEnum step) {
		this.step = step;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFriendlyDate() {
		if(getSubmittedDate()!=null){
			 
			 	 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getSubmittedDate().getTime());
			    return strDate;
			}
			
			else return "";
	}
	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
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
	public CoursesDTO getAddedCourse() {
		return addedCourse;
	}
	public void setAddedCourse(CoursesDTO addedCourse) {
		this.addedCourse = addedCourse;
	}
	public CoursesDTO getDropCourse() {
		return dropCourse;
	}
	public void setDropCourse(CoursesDTO dropCourse) {
		this.dropCourse = dropCourse;
	}
	public String getAddedSection() {
		return addedSection;
	}
	public void setAddedSection(String addedSection) {
		this.addedSection = addedSection;
	}
	public String getDroppedSection() {
		return droppedSection;
	}
	public void setDroppedSection(String droppedSection) {
		this.droppedSection = droppedSection;
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

	public InstructorDTO getDroppedCourseIns() {
		return droppedCourseIns;
	}

	public void setDroppedCourseIns(InstructorDTO droppedCourseIns) {
		this.droppedCourseIns = droppedCourseIns;
	}

	public DropTypesEnum getDropType() {
		return dropType;
	}

	public void setDropType(DropTypesEnum dropType) {
		this.dropType = dropType;
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

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	public Boolean getRepeatedCourse() {
		return repeatedCourse;
	}

	public void setRepeatedCourse(Boolean repeatedCourse) {
		this.repeatedCourse = repeatedCourse;
	}

	public String getCourseLab() {
		return courseLab;
	}

	public void setCourseLab(String courseLab) {
		this.courseLab = courseLab;
	}

	public Boolean getReverted() {
		return reverted;
	}

	public void setReverted(Boolean reverted) {
		this.reverted = reverted;
	}

	public Boolean getCourseLabBool() {
		if(getCourseLab()!=null)
			return true;
		else
		return courseLabBool;
	}

	public void setCourseLabBool(Boolean courseLabBool) {
		if(courseLabBool==false){setCourseLab(null); this.courseLabBool = courseLabBool;}
		else
		this.courseLabBool = courseLabBool;
	}

}
