/**
 * 
 */
package main.com.zc.shared.presentation.beans;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import main.com.zc.services.presentation.forms.CourseRepeat.dto.CourseRepeatDTO;
import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.incompleteGrade.dto.IncompleteGradeDTO;
import main.com.zc.services.presentation.forms.overloadRequest.dto.OverloadRequestDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * description
 * 
 * @author heba
 * @since Feb 26, 2015
 * @lastUpdated Feb 26, 2015
 */
@ManagedBean(name = "notificationBean")
@ViewScoped
public class NotificationBean {

	private Date notifyAt;
	private CoursePetitionDTO coursePetitionDTO;
	private DropAddFormDTO addFormDTO;
	private ChangeMajorDTO changeMajorDTO;
	private OverloadRequestDTO overloadRequestDTO;
	private CourseRepeatDTO courseRepeatDTO;
	private IncompleteGradeDTO incompleteGradeDTO;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;

	@PostConstruct
	public void init() {
		reset();
	}

	public void preRemindMe(CoursePetitionDTO coursePetitionDTO) {
		reset();
		RequestContext.getCurrentInstance().reset("remindForm:remindGrid");
		this.coursePetitionDTO = coursePetitionDTO;
		RequestContext.getCurrentInstance().execute("remindDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("remindForm:remindGrid");
	}

	public void preRemindMe(ChangeMajorDTO changeMajorDTO) {
		reset();
		RequestContext.getCurrentInstance().reset("remindForm:remindGrid");
		this.changeMajorDTO = changeMajorDTO;
		RequestContext.getCurrentInstance().execute("remindDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("remindForm:remindGrid");
	}

	public void preRemindMe(DropAddFormDTO addFormDTO) {
		reset();
		RequestContext.getCurrentInstance().reset("remindForm:remindGrid");
		this.addFormDTO = addFormDTO;
		RequestContext.getCurrentInstance().execute("remindDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("remindForm:remindGrid");
	}

	public void preRemindMe(OverloadRequestDTO overloadRequestDTO) {
		reset();
		RequestContext.getCurrentInstance().reset("remindForm:remindGrid");
		this.overloadRequestDTO = overloadRequestDTO;
		RequestContext.getCurrentInstance().execute("remindDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("remindForm:remindGrid");
	}

	public void preRemindMe(CourseRepeatDTO courseRepeatDTO) {
		reset();
		RequestContext.getCurrentInstance().reset("remindForm:remindGrid");
		this.courseRepeatDTO = courseRepeatDTO;
		RequestContext.getCurrentInstance().execute("remindDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("remindForm:remindGrid");
	}
	public void preRemindMe(IncompleteGradeDTO incomGradeDTO)
	{
		reset();
		RequestContext.getCurrentInstance().reset("remindForm:remindGrid");
		incompleteGradeDTO=incomGradeDTO;
		RequestContext.getCurrentInstance().execute("remindDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
		.getRenderIds().add("remindForm:remindGrid");
	}
	private void reset() {
		this.addFormDTO = null;
		this.changeMajorDTO = null;
		this.coursePetitionDTO = null;
		this.courseRepeatDTO = null;
		incompleteGradeDTO=null;
		this.notifyAt = new Date();
	}

	public void saveReminder() {
		try {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (this.addFormDTO != null) {
				this.addFormDTO.setNotifyAt(this.notifyAt);
				sharedAcademicPetFacade.notifyAt(this.addFormDTO,
						authentication.getName());
			} else if (this.changeMajorDTO != null) {
				this.changeMajorDTO.setNotifyAt(this.notifyAt);
				sharedAcademicPetFacade.notifyAt(this.changeMajorDTO,
						authentication.getName());
			} else if (this.coursePetitionDTO != null) {
				this.coursePetitionDTO.setNotifyAt(this.notifyAt);
				sharedAcademicPetFacade.notifyAt(this.coursePetitionDTO,
						authentication.getName());
			} else if (this.courseRepeatDTO != null) {
				this.courseRepeatDTO.setNotifyAt(this.notifyAt);
				sharedAcademicPetFacade.notifyAt(this.courseRepeatDTO,
						authentication.getName());
			}
			else if (this.overloadRequestDTO != null) {
				this.overloadRequestDTO.setNotifyAt(this.notifyAt);
				sharedAcademicPetFacade.notifyAt(this.overloadRequestDTO,
						authentication.getName());
			}
			else if (getIncompleteGradeDTO()!= null) {
				incompleteGradeDTO.setNotifyAt(this.notifyAt);
				sharedAcademicPetFacade.notifyAt(getIncompleteGradeDTO(),
						authentication.getName());
			}
			JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"Notification will Be send on time");
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"System Error");
		}
	}

	public Date getNotifyAt() {
		return notifyAt;
	}

	public void setNotifyAt(Date notifyAt) {
		this.notifyAt = notifyAt;
	}

	public Date getCurrentDate() {
		return new Date();
	}

	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}

	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}

	public CoursePetitionDTO getCoursePetitionDTO() {
		return coursePetitionDTO;
	}

	public void setCoursePetitionDTO(CoursePetitionDTO coursePetitionDTO) {
		this.coursePetitionDTO = coursePetitionDTO;
	}

	public DropAddFormDTO getAddFormDTO() {
		return addFormDTO;
	}

	public void setAddFormDTO(DropAddFormDTO addFormDTO) {
		this.addFormDTO = addFormDTO;
	}

	public ChangeMajorDTO getChangeMajorDTO() {
		return changeMajorDTO;
	}

	public void setChangeMajorDTO(ChangeMajorDTO changeMajorDTO) {
		this.changeMajorDTO = changeMajorDTO;
	}

	public OverloadRequestDTO getOverloadRequestDTO() {
		return overloadRequestDTO;
	}

	public void setOverloadRequestDTO(OverloadRequestDTO overloadRequestDTO) {
		this.overloadRequestDTO = overloadRequestDTO;
	}

	public CourseRepeatDTO getCourseRepeatDTO() {
		return courseRepeatDTO;
	}

	public void setCourseRepeatDTO(CourseRepeatDTO courseRepeatDTO) {
		this.courseRepeatDTO = courseRepeatDTO;
	}

	public IncompleteGradeDTO getIncompleteGradeDTO() {
		return incompleteGradeDTO;
	}

	public void setIncompleteGradeDTO(IncompleteGradeDTO incompleteGradeDTO) {
		this.incompleteGradeDTO = incompleteGradeDTO;
	}

}