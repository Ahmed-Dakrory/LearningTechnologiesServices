/**
 * 
 */
package main.com.zc.services.presentation.forms.tAJuniorProgram.dto;

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
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
public class TAJuniorProgramDTO {


	private Integer id;

	private CoursesDTO course;
    
	private Double gpa;

	private Double hours;
	
	private StudentDTO student;
	
	private PetitionStepsEnum step;

	private Calendar submissionDate;

	private String comment;

	private MajorDTO major;

	private Boolean performed;
	
	private String curStatus;
	
	private String nextStatus;
	
	private String grade;
	private String friendlyDate;
	private Date notifyAt;
	private byte[] image;
	
	
	private String task;
	
	
	private Double hoursINS;
	
	List<PetitionsActionsDTO> actionDTO =new ArrayList<PetitionsActionsDTO>();
	
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
								else 
									result = AcceptRefuseFormEnum.Not;
								break;
							}
						}
					}
				}

				else if(getStep().equals(PetitionStepsEnum.DEAN))
				{
					if(authentication.getName().equals(Constants.DEAN_OF_ACADEMIC))
					{
						for(int i=0;i<getActionDTO().size();i++)
						{
							if(getActionDTO().get(i).getActionType()!=null)
							{
							if(getActionDTO().get(i).getInstructorID().equals(Constants.DEAN_OF_ACADEMIC_ID)&&getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_APPROVED))
							{
								
									result = AcceptRefuseFormEnum.Accept;
									break;
							}
								else if(getActionDTO().get(i).getInstructorID().equals(Constants.DEAN_OF_ACADEMIC_ID)&&getActionDTO().get(i).getActionType().equals(PetitionActionTypeEnum.DEAN_REFUSED))
									
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
								else 	result = AcceptRefuseFormEnum.Not;
								
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

	public CoursesDTO getCourse() {
		return course;
	}

	public void setCourse(CoursesDTO course) {
		this.course = course;
	}



	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
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

	public Calendar getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Calendar submissionDate) {
		this.submissionDate = submissionDate;
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

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<PetitionsActionsDTO> getActionDTO() {
		return actionDTO;
	}

	public void setActionDTO(List<PetitionsActionsDTO> actionDTO) {
		this.actionDTO = actionDTO;
	}

	public Date getNotifyAt() {
		return notifyAt;
	}

	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
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
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public Double getHoursINS() {
		return hoursINS;
	}
	public void setHoursINS(Double hoursINS) {
		this.hoursINS = hoursINS;
	}
	
	
}
