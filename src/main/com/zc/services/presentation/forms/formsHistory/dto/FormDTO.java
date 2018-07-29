/**
 * 
 */
package main.com.zc.services.presentation.forms.formsHistory.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.SemesterEnum;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.presentation.dto.AttachmentDTO;

/**
 * description
 * 
 * @author heba
 * @since Mar 3, 2015
 * @lastUpdated Mar 3, 2015
 */
public class FormDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private StudentDTO student;

	private Boolean performed;

	private String status;

	private String currentStatus;

	private String nextStatus;

	private Calendar submittedDate;

	private AttachmentDTO Attachments;

	private String comment;

	private byte[] image;

	private Date notifyAt;

	private PetitionStepsEnum step;

	private String title;

	private CoursesDTO course;

	private Double gpa;

	private MajorDTO major;

	private MajorDTO curMajor;

	private MajorDTO newMajor;

	private String curSpecialization;

	private String newSpecialization;

	private String doubleSpecialization;

	private String moreDetails;

	private String reason;

	private SemesterEnum oldSem;

	private SemesterEnum newSem;

	private String requestText;

	private CoursesDTO addedCourse;

	private CoursesDTO dropCourse;

	private String addedSection;

	private String droppedSection;

	private AddDropFormTypesEnum type;

	private String phone;

	private String year;

	private Calendar insActionDate;

	private Calendar deanActionDate;

	private Calendar provostActionDate;

	private Calendar admissionHeadActionDate;

	private Calendar admissionDeptActionDate;

	private Boolean provostRequired;
	
	private FormTypesEnum formTypesEnum ;

	private String grade;
	
	private Boolean insSendMail;
	private boolean showMarkAsDone;
	private boolean showRemind;
	private StringBuffer url;
	
	public FormDTO() {
		super();
		 url = (StringBuffer) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURL();
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

	public Calendar getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Calendar submittedDate) {
		this.submittedDate = submittedDate;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PetitionStepsEnum getStep() {
		return step;
	}

	public void setStep(PetitionStepsEnum step) {
		this.step = step;
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

	public Double getGpa() {
		return gpa;
	}

	public void setGpa(Double gpa) {
		this.gpa = gpa;
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

	public String getDoubleSpecialization() {
		return doubleSpecialization;
	}

	public void setDoubleSpecialization(String doubleSpecialization) {
		this.doubleSpecialization = doubleSpecialization;
	}

	public String getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
	}

	public StudentDTO getStudent() {
		return student;
	}

	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public Boolean getPerformed() {
		return performed;
	}

	public void setPerformed(Boolean performed) {
		this.performed = performed;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CoursesDTO getCourse() {
		return course;
	}

	public void setCourse(CoursesDTO course) {
		this.course = course;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public SemesterEnum getOldSem() {
		return oldSem;
	}

	public void setOldSem(SemesterEnum oldSem) {
		this.oldSem = oldSem;
	}

	public SemesterEnum getNewSem() {
		return newSem;
	}

	public void setNewSem(SemesterEnum newSem) {
		this.newSem = newSem;
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

	public AddDropFormTypesEnum getType() {
		return type;
	}

	public void setType(AddDropFormTypesEnum type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public Boolean getProvostRequired() {
		return provostRequired;
	}

	public void setProvostRequired(Boolean provostRequired) {
		this.provostRequired = provostRequired;
	}

	public FormTypesEnum getFormTypesEnum() {
		return formTypesEnum;
	}

	public void setFormTypesEnum(FormTypesEnum formTypesEnum) {
		this.formTypesEnum = formTypesEnum;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Boolean getInsSendMail() {
		return insSendMail;
	}

	public void setInsSendMail(Boolean insSendMail) {
		this.insSendMail = insSendMail;
	}
	
	public String getRemindFriendlyDate() {
		if(getNotifyAt()!=null){
			   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			    String strDate = sdf.format(getNotifyAt().getTime());
			    return strDate;
			}
			
			else return "";
	}

	public String getFriendlyDate() {
		if(getSubmittedDate()!=null){
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(getSubmittedDate().getTime());
			    return strDate;
			}
			else return "";
	}

	public boolean getShowForward() {
		if(url.toString().toLowerCase().contains("ins"))
		return	 true;
		else
		return false;
	}

	public boolean getShowOther() {
		if (url.toString().contains("1")) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean getShowMarkAsDone() {
		return showMarkAsDone;
	}

	public void setShowMarkAsDone(boolean showMarkAsDone) {
		this.showMarkAsDone = showMarkAsDone;
	}

	public boolean getShowRemind() {
		return showRemind;
	}

	public void setShowRemind(boolean showRemind) {
		this.showRemind = showRemind;
	}
	public boolean getShowProvostRequired(){
		if(getFormTypesEnum().equals(FormTypesEnum.OVERLOADREQUEST)&&url.toString().contains("Dean.xhtml")) 
			return true;
		else return false;
	}
	
}
