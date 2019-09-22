/**
 * 
 */
package main.com.zc.services.presentation.accountSetting.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import main.com.zc.services.presentation.accountSetting.facade.IStudentProfileFacade;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentProfileDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

import org.apache.commons.lang.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */

@ManagedBean(name = "StudentProfileBean")
@ViewScoped
public class StudentProfileBean {

	@ManagedProperty("#{IStudentProfileFacade}")
	private IStudentProfileFacade facade;

	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;

	@ManagedProperty("#{IFormsStatusFacade}")
	private IFormsStatusFacade formSettingFacade;

	private StudentProfileDTO studentProfile;

	private UploadedFile uploadedImage;

	private byte[] image;

	private List<MajorDTO> majorsLst;
	private Integer selectedCurMajorId;

	@PostConstruct
	public void init() {
		try {
			studentProfile = new StudentProfileDTO();
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (authentication.getName().toLowerCase().startsWith("s-")||StringUtils.isNumeric(authentication.getName().substring(0, 4))) {
				PersonDataDTO studentDto = studentDataFacade
						.getPersonByPersonMail(authentication.getName());
				FormsStatusDTO formSetting = formSettingFacade.getById(11);
				studentProfile = facade.getBySemesterAndYearAndStudentId(
						formSetting.getSemester().getId(),
						formSetting.getYear(), studentDto.getId());
				if(studentProfile.getId()==null){

					RequestContext.getCurrentInstance().execute("alertDlg.show();");
					FacesContext.getCurrentInstance().getPartialViewContext()
					.getRenderIds().add("alertForm:alertPanel");
				}
				fillMajorsLst();
			if(null!=	studentProfile.getMajor())
				{
				selectedCurMajorId = studentProfile.getMajor().getId();
				}
			}
			else{
				JavaScriptMessagesHandler
				.RegisterErrorMessage(null, "Not Allowed ");
				FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath()+"/pages/secured/dashboard.xhtml?faces-redirect=true");
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void fillMajorsLst() {
		majorsLst = facade.getAllMajors();
	}

	public StreamedContent getImagePreview() {
		InputStream dbStream = null;
		if (this.image != null) {
			dbStream = new ByteArrayInputStream(this.image);
			return new DefaultStreamedContent(dbStream);
		}
		return null;
	}

	public void previewImage(FileUploadEvent event) {
		this.image = event.getFile().getContents();
		studentProfile = facade.updatePhoto(image, getStudentProfile());
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("studentProfile.xhtml");
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,
					"Photo is updated successfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void save() {
		MajorDTO major = new MajorDTO();
		major.setId(selectedCurMajorId);
		studentProfile.setMajor(major);
		StudentProfileDTO result = facade.saveStudentProfile(studentProfile);
		
		if (result != null) {
			setStudentProfile(result);
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,
					"Profile is saved successfully!");

		} else {
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "System Error");
		}
	}

	public IStudentProfileFacade getFacade() {
		return facade;
	}

	public void setFacade(IStudentProfileFacade facade) {
		this.facade = facade;
	}

	public StudentProfileDTO getStudentProfile() {
		return studentProfile;
	}

	public void setStudentProfile(StudentProfileDTO studentProfile) {
		this.studentProfile = studentProfile;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(
			IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public IFormsStatusFacade getFormSettingFacade() {
		return formSettingFacade;
	}

	public void setFormSettingFacade(IFormsStatusFacade formSettingFacade) {
		this.formSettingFacade = formSettingFacade;
	}

	public UploadedFile getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(UploadedFile uploadedImage) {
		this.uploadedImage = uploadedImage;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<MajorDTO> getMajorsLst() {
		return majorsLst;
	}

	public void setMajorsLst(List<MajorDTO> majorsLst) {
		this.majorsLst = majorsLst;
	}

	public Integer getSelectedCurMajorId() {
		return selectedCurMajorId;
	}

	public void setSelectedCurMajorId(Integer selectedCurMajorId) {
		this.selectedCurMajorId = selectedCurMajorId;
	}

}
