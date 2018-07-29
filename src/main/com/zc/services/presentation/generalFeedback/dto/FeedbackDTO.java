/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.enumurations.AcceptRefuseFormEnum;
import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;
import main.com.zc.services.presentation.forms.shared.dto.PetitionsActionsDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * @author Omnya Alaa
 * 
 */
public class FeedbackDTO {
	private Integer id;
	private String feedbackForm;
	private Integer personLoginID;
	private String studentName;
	
	private String mail;
	private Integer studentID;
	private Calendar submittedDate;
	private String categoryName;
	private Integer categoryID;
	private String friendlyDate;
    private String title;
	private PetitionStepsEnum step;
	private Date notifyAt;
	private MajorDTO major=new MajorDTO();
	private Boolean performed;
	private byte[] image;
	private StudentDTO student;
	private AttachmentDTO Attachments;
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	private Integer categoryHeadID;
	private String categoryHeadName;
	public AcceptRefuseFormEnum  getAcceptRefuseStatus()
	{
		// initialize the color to blank
				AcceptRefuseFormEnum result = AcceptRefuseFormEnum.Not;
				
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
				
				if(getStep().equals(PetitionStepsEnum.CLOSED))
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

	public String getFeedbackForm() {
		return feedbackForm;
	}

	public void setFeedbackForm(String feedbackForm) {
		this.feedbackForm = feedbackForm;
	}


	public Calendar getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Calendar submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getFriendlyDate() {
		if (getSubmittedDate() != null) {

			// SimpleDateFormat sdf = new
			// SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = sdf.format(getSubmittedDate().getTime());
			return strDate;
		}

		else
			return "";
	}

	public void setFriendlyDate(String friendlyDate) {
		this.friendlyDate = friendlyDate;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getStudentID() {
		return studentID;
	}

	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}

	public Integer getPersonLoginID() {
		return personLoginID;
	}

	public void setPersonLoginID(Integer personLoginID) {
		this.personLoginID = personLoginID;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public PetitionStepsEnum getStep() {
		return step;
	}

	public void setStep(PetitionStepsEnum step) {
		this.step = step;
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

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public Date getNotifyAt() {
		return notifyAt;
	}

	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
	}

	public List<PetitionsActionsDTO> getActionDTO() {
		return actionDTO;
	}

	public void setActionDTO(List<PetitionsActionsDTO> actionDTO) {
		this.actionDTO = actionDTO;
	}

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
	}

	public AttachmentDTO getAttachments() {
		return Attachments;
	}

	public void setAttachments(AttachmentDTO attachments) {
		Attachments = attachments;
	}
	public Integer getCategoryHeadID() {
		return categoryHeadID;
	}
	public void setCategoryHeadID(Integer categoryHeadID) {
		this.categoryHeadID = categoryHeadID;
	}
	public String getCategoryHeadName() {
		return categoryHeadName;
	}
	public void setCategoryHeadName(String categoryHeadName) {
		this.categoryHeadName = categoryHeadName;
	}

	
}
