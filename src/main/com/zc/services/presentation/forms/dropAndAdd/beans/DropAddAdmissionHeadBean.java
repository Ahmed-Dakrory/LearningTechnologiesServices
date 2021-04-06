/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.beans;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import main.com.zc.services.applicationService.forms.addAndDrop.services.PetitionStepsEnum;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.AddDropFormTypesEnum;
import main.com.zc.services.presentation.forms.academicPetition.facade.IAcademicPetitionActionsFacade;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IAddDropActionsFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.impl.AdmissionHAddDropFormFacadeImpl;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Omnya.Alaa
 *
 */
@ManagedBean
@ViewScoped
public class DropAddAdmissionHeadBean {

	@ManagedProperty("#{AdmissionHAddDropFormFacadeImpl}")
	private AdmissionHAddDropFormFacadeImpl facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;

	private List<DropAddFormDTO> pendingForms;
	private List<DropAddFormDTO> archievedForms;
	private DropAddFormDTO selectedPendingForms;
	private List<DropAddFormDTO> filteredPendingForms;
	private DropAddFormDTO selectedArchievedForms;
	private List<DropAddFormDTO> filteredArchievedForms;
	private DropAddFormDTO detailedForm;
	private String newComment;
	private boolean renderAddAction;
	private boolean renderDropAction;

	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadeAddDropImpl}")
	private IReportsFacade reportsFacade;
	
	@ManagedProperty("#{IAddDropActionsFacade}")
	private IAddDropActionsFacade actionFacade;
	
	@PostConstruct
	public void init() {
		detailView = false;

		fillPendingFormLst();
		//fillArchievedPetitionsLst();
		detailedForm = new DropAddFormDTO();
		
		try
		{
			HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
			if(origRequest.getParameterValues("action")[0].equals("approve"))
			{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
			setSelectedPendingForms(actionFacade.getByID(dtoID));
			}
			else if(origRequest.getParameterValues("action")[0].equals("refuse"))
			{
				setSelectedPendingForms(actionFacade.getByID(dtoID));
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
			}
		}
		catch(Exception ex){
			System.out.println("No redirect");
		}
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		yearLst=new ArrayList<Integer>();
		for(int i=0;i<20;i++){
			yearLst.add(2013+i);
		}
	}

	public void fillPendingFormLst() {
		pendingForms = new ArrayList<DropAddFormDTO>();
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if (mail.equals(Constants.REGISTRAR_HEAD_EMAIL)) {
				pendingForms = facade.getPendingFormsOfAdmissionHead();
			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Not Allowed To see This Petitions");
			}
		}

	}

/*	public void fillArchievedPetitionsLst() {
		archievedForms = new ArrayList<DropAddFormDTO>();
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if (mail.equals(Constants.ADMISSION_HEAD)) {
				archievedForms = facade.getArchievedFormsOfAdmissionHead();

			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Not Allowed To see This Petitions");
			}
		}
	}*/

	public void ininttialzieYear(){
		selectedYear=null;
		archievedForms=new ArrayList<DropAddFormDTO>();
	}
  public void getPetitionsBySemester(){
		if(getSelectedYear()!=null){
		if(getSelectedSemester()==0)
		archievedForms=reportsFacade.getOldFall(getSelectedYear());
		else if(getSelectedSemester()==1)
			archievedForms=reportsFacade.getOldSpring(getSelectedYear());
		else if(getSelectedSemester()==2)
			archievedForms=reportsFacade.getOldSummer(getSelectedYear());
		}
	}
  public void exportExcelReport(){
		if(getArchievedForms()!=null)
			if(getArchievedForms().size()>0)
				reportsFacade.generateExcelByList(getArchievedForms());
	}
	public void onRowSelect(SelectEvent event) {  
      	try {
          DropAddFormDTO selectedDTO=(DropAddFormDTO) event.getObject();
		showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
	
	// public void showDetails(DropAddFormDTO form)
	// {
	// RequestContext.getCurrentInstance().reset("detForm:detGrid");
	// setDetailedForm(form);
	// if(form.getType().equals(AddDropFormTypesEnum.ADD))
	// {
	// setRenderAddAction(true);
	// setRenderDropAction(false);
	//
	//
	// }
	// else if(form.getType().equals(AddDropFormTypesEnum.DROP))
	// {
	// setRenderAddAction(false);
	// setRenderDropAction(true);
	//
	// }
	// else if(form.getType().equals(AddDropFormTypesEnum.DROPANDADD))
	// {
	// setRenderAddAction(true);
	// setRenderDropAction(true);
	//
	// }
	//
	// RequestContext.getCurrentInstance().execute("detDlg.show();");
	// FacesContext.getCurrentInstance().getPartialViewContext()
	// .getRenderIds().add("detForm:detGrid");
	// StringBuffer url = (StringBuffer) ((HttpServletRequest) FacesContext
	// .getCurrentInstance().getExternalContext().getRequest())
	// .getRequestURL();
	// if (!url.toString().contains("1")) {
	// FacesContext.getCurrentInstance().getPartialViewContext()
	// .getRenderIds().add("detForm:dialogBtn");
	// RequestContext.getCurrentInstance().reset("detForm:dialogBtn");
	// } }
	/*public void approve() {
		try {
			DropAddFormDTO form = getDetailedForm();
			if (!form.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD)) {
				form.setNotifyAt(null);
			}
			form.setStep(PetitionStepsEnum.ADMISSION_HEAD);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(Calendar.getInstance().getTime());

			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged
																		// in
			{
				if (authentication.getName().equals(Constants.ADMISSION_HEAD)) {
					if (form.getStatus()
							.contains(
									Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
						JavaScriptMessagesHandler.RegisterWarningMessage(null,
								"Already Approved Before !");
					} else {
						if (form.getStatus()
								.contains(
										Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
							// String
							// newStatus=form.getStatus()+"\n"+Constants.PETITION_STATUS_APPROVED_BY_DEAN
							// +" ( Date : "+date+" )";
							form.setStatus(form
									.getStatus()
									.replaceAll(
											Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD,
											Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD));
						}

						else {
							String newStatus = form.getStatus()
									+ "\n"
									+ Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD
									+ " ( Date : " + date + " )";
							form.setStatus(newStatus);
						}
						if (newComment != null && !newComment.equals("")) {
							form = constructComment(form);
							getFormDetail().setComment(form.getComment());
						}
						form = facade.updateStatusOfForm(form);
						if (form != null) {
							JavaScriptMessagesHandler
									.RegisterNotificationMessage(null,
											"Form has been Approved successfully");
							RequestContext.getCurrentInstance().execute(
									"detDlgDean.show();");
							init();
							sharedAcademicPetFacade.notifayNextStepOwner(form);
						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage(
									null, "Form Can't Be Approved ");
						}
					}
				}
			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Allowed Only For Admission Head");
			}
		} catch (Exception ex) {
			JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"Form Can Be Approved ");
		}

	}*/

/*	public void refuse() {
		try {
			DropAddFormDTO form = getDetailedForm();
			if (!form.getStep().equals(PetitionStepsEnum.ADMISSION_HEAD)) {
				form.setNotifyAt(null);
			}
			form.setStep(PetitionStepsEnum.ADMISSION_HEAD);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date = sdf.format(Calendar.getInstance().getTime());

			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged
																		// in
			{
				if (authentication.getName().equals(Constants.ADMISSION_HEAD)) {
					if (form.getStatus()
							.contains(
									Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD)) {
						JavaScriptMessagesHandler.RegisterWarningMessage(null,
								"Already Refused Before !");
					} else {
						if (form.getStatus()
								.contains(
										Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD)) {
							// String
							// newStatus=form.getStatus()+"\n"+Constants.PETITION_STATUS_APPROVED_BY_DEAN
							// +" ( Date : "+date+" )";
							form.setStatus(form
									.getStatus()
									.replaceAll(
											Constants.PETITION_STATUS_APPROVED_BY_ADMISSION_HEAD,
											Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD));
						}

						else {
							String newStatus = form.getStatus()
									+ "\n"
									+ Constants.PETITION_STATUS_REFUSED_BY_ADMISSION_HEAD
									+ " ( Date : " + date + " )";
							form.setStatus(newStatus);
						}
						if (newComment != null && !newComment.equals("")) {
							form = constructComment(form);
							getFormDetail().setComment(form.getComment());
						}
						form = facade.updateStatusOfForm(form);
						if (form != null) {
							JavaScriptMessagesHandler
									.RegisterNotificationMessage(null,
											"Form has been Refused successfully");
							RequestContext.getCurrentInstance().execute(
									"detDlgDean.show();");
							init();
							sharedAcademicPetFacade.notifayNextStepOwner(form);
						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage(
									null, "Form Can't Be Approved ");
						}
					}
				}
			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Allowed Only For Admission Head");
			}
		} catch (Exception ex) {
			JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"Form Can Be Approved ");
		}

	}

	public void addComment(DropAddFormDTO dto) {
		try {

			if (newComment == null || newComment.equals("")) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Please, Write Your Comment");
			} else {
				dto = constructComment(dto);
				facade.addComment(dto.getId(), dto.getComment());
				getFormDetail().setComment(dto.getComment());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DropAddFormDTO constructComment(DropAddFormDTO dto) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(Calendar.getInstance().getTime());

		String addedComment = "";
		if (dto.getComment() != null)
			addedComment = dto.getComment() + "\n";

		addedComment += newComment
				+ " by :"
				+ getInsDataFacade.getInsByPersonMail(authentication.getName())
						.getName() + " (Date: " + date + " )";
		dto.setComment(addedComment);
		newComment = "";
		return dto;
	}

	public void downloadAttachments(DropAddFormDTO form) {
		AttachmentDownloaderHelper.createHTTPDownlodFileResponse(form
				.getAttachments());
	}
*/
	public AdmissionHAddDropFormFacadeImpl getFacade() {
		return facade;
	}

	public void setFacade(AdmissionHAddDropFormFacadeImpl facade) {
		this.facade = facade;
	}

	public List<DropAddFormDTO> getPendingForms() {
		return pendingForms;
	}

	public void setPendingForms(List<DropAddFormDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public List<DropAddFormDTO> getArchievedForms() {
		return archievedForms;
	}

	public void setArchievedForms(List<DropAddFormDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}

	

	public List<DropAddFormDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}

	public void setFilteredPendingForms(
			List<DropAddFormDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}

	

	public DropAddFormDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(DropAddFormDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public DropAddFormDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}

	public void setSelectedArchievedForms(DropAddFormDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}

	public IAddDropActionsFacade getActionFacade() {
		return actionFacade;
	}

	public void setActionFacade(IAddDropActionsFacade actionFacade) {
		this.actionFacade = actionFacade;
	}

	public List<DropAddFormDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<DropAddFormDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	public DropAddFormDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(DropAddFormDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
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

	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}

	public void setSharedAcademicPetFacade(
			ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public void showDetails(DropAddFormDTO form) {
		try {
    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    		StringBuffer url=origRequest.getRequestURL();
    		if(url.toString().contains("addDropAdmissionHead1"))
    		{
    			FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionH&oldMood=1");
    		}
    		else 
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml?id="+form.getId()+"&cases=AdmissionH");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	/*	setDetailedForm(form);
		formDetail = formsHistoryFacade.getFormDetail(form);
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
		detailView = true;
		formDetail.setShowMarkAsDone(false);
		formDetail.setShowRemind(formDetail.getStep().equals(
				PetitionStepsEnum.DEAN));*/
	}

	public IFormsHistoryFacade getFormsHistoryFacade() {
		return formsHistoryFacade;
	}

	public void setFormsHistoryFacade(IFormsHistoryFacade formsHistoryFacade) {
		this.formsHistoryFacade = formsHistoryFacade;
	}

	public boolean isDetailView() {
		return detailView;
	}

	public void setDetailView(boolean detailView) {
		this.detailView = detailView;
	}

	public FormDTO getFormDetail() {
		return formDetail;
	}

	public void setFormDetail(FormDTO formDetail) {
		this.formDetail = formDetail;
	}

	public Integer getSelectedSemester() {
		return selectedSemester;
	}

	public void setSelectedSemester(Integer selectedSemester) {
		this.selectedSemester = selectedSemester;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public List<BaseDTO> getSemesterLst() {
		return semesterLst;
	}

	public void setSemesterLst(List<BaseDTO> semesterLst) {
		this.semesterLst = semesterLst;
	}

	public List<Integer> getYearLst() {
		return yearLst;
	}

	public void setYearLst(List<Integer> yearLst) {
		this.yearLst = yearLst;
	}

	public IReportsFacade getReportsFacade() {
		return reportsFacade;
	}

	public void setReportsFacade(IReportsFacade reportsFacade) {
		this.reportsFacade = reportsFacade;
	}
	
	
}