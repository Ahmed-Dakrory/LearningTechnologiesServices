/**
 * 
 */
package main.com.zc.services.presentation.forms.formsHistory.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.domain.shared.enumurations.FormTypesEnum;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author heba
 * @since Mar 3, 2015
 * @lastUpdated Mar 3, 2015
 */
@ManagedBean(name = "formsHistoryBean")
@ViewScoped
public class FormsHistoryBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;

	private List<FormDTO> formDTOs;
	private List<FormDTO> filterFormDTOs;
	private FormDTO detailForm;
	private boolean renderAddAction;
	private boolean renderDropAction;

	private String studentInfo;

	@PostConstruct
	public void init() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if (mail.toLowerCase().equals(
					Constants.DEAN_OF_STRATEGIC.toLowerCase())
					|| mail.toLowerCase().equals(
							Constants.REGISTRAR_HEAD_EMAIL.toLowerCase())|| mail.toLowerCase().equals(
									Constants.LTS_SYSTEM_ADMIN.toLowerCase())) {
				clear();
			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Allowed Only for Admission Head and Dean of Strategic Enrollment Management");

			}
		}

	}

	public void search() {
		setFormDTOs(formsHistoryFacade.getAllFormsHistory(studentInfo));
	}

	public void clear() {
		setStudentInfo(null);
		setFormDTOs(formsHistoryFacade.getAllFormsHistory(null));

	}

	public void showDetails(FormDTO form) {
		RequestContext.getCurrentInstance().reset("detForm:detGrid");
		setDetailForm(form);
		if (form.getFormTypesEnum().equals(FormTypesEnum.DROPADD)) {
			if (form.getType().equals(AddDropFormTypesEnum.ADD)) {
				setRenderAddAction(true);
				setRenderDropAction(false);

			} else if (form.getType().equals(AddDropFormTypesEnum.DROP)) {
				setRenderAddAction(false);
				setRenderDropAction(true);

			} else if (form.getType().equals(AddDropFormTypesEnum.DROPANDADD)) {
				setRenderAddAction(true);
				setRenderDropAction(true);

			}
		}
		RequestContext.getCurrentInstance().execute("detDlg.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("detForm:detGrid");
	}

	public void downloadAttachments(FormDTO form) {
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form
				.getAttachments());
	}

	public IFormsHistoryFacade getFormsHistoryFacade() {
		return formsHistoryFacade;
	}

	public void setFormsHistoryFacade(IFormsHistoryFacade formsHistoryFacade) {
		this.formsHistoryFacade = formsHistoryFacade;
	}

	public String getStudentInfo() {
		return studentInfo;
	}

	public void setStudentInfo(String studentInfo) {
		this.studentInfo = studentInfo;
	}

	public List<FormDTO> getFormDTOs() {
		return formDTOs;
	}

	public void setFormDTOs(List<FormDTO> formDTOs) {
		this.formDTOs = formDTOs;
	}

	public List<FormDTO> getFilterFormDTOs() {
		return filterFormDTOs;
	}

	public void setFilterFormDTOs(List<FormDTO> filterFormDTOs) {
		this.filterFormDTOs = filterFormDTOs;
	}

	public FormDTO getDetailForm() {
		return detailForm;
	}

	public void setDetailForm(FormDTO detailForm) {
		this.detailForm = detailForm;
	}

	public boolean isRenderAddAction() {
		return renderAddAction;
	}

	public void setRenderAddAction(boolean renderAddAction) {
		this.renderAddAction = renderAddAction;
	}

	public boolean isRenderDropAction() {
		return renderDropAction;
	}

	public void setRenderDropAction(boolean renderDropAction) {
		this.renderDropAction = renderDropAction;
	}
}
