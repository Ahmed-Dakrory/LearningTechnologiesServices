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
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IInstructorAddDropFormFacade;
import main.com.zc.services.presentation.forms.dropAndAdd.facade.IReportsFacade;
import main.com.zc.services.presentation.forms.formsHistory.dto.FormDTO;
import main.com.zc.services.presentation.forms.formsHistory.facade.IFormsHistoryFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.AttachmentDownloaderHelper;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class DropAddBeanInstructor {

	@ManagedProperty("#{InstructorAddDropFormFacadeImpl}")
	private IInstructorAddDropFormFacade facade;

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;

	private List<DropAddFormDTO> pendingForms;
	private List<DropAddFormDTO> archievedForms;
	private DropAddFormDTO selectedPendingForms;
	private List<DropAddFormDTO> filteredPendingForms;
	private DropAddFormDTO selectedArchievedForms;
	private List<DropAddFormDTO> filteredArchievedForms;
	private List<DropAddFormDTO> pendingFormsDean;
	private List<DropAddFormDTO> archievedFormsDean;
	private DropAddFormDTO selectedPendingFormsDean;
	private List<DropAddFormDTO> filteredPendingFormsDean;
	private DropAddFormDTO selectedArchievedFormsDean;
	private List<DropAddFormDTO> filteredArchievedFormsDean;
	private List<BaseDTO> userTypesLst;
	private DropAddFormDTO detailedForm;
	private DropAddFormDTO detailedFormDean;
	private Integer typeOfUser;
	private Boolean deanMood;
	private Boolean showIns;
	private String newComment;
	private boolean renderAddAction;
	private boolean renderDropAction;
	private List<InstructorDTO> instructors;
	private Integer selectedInstructor;
	@ManagedProperty("#{FormsHistoryFacadeImpl}")
	private IFormsHistoryFacade formsHistoryFacade;
	private boolean detailView;
	private FormDTO formDetail;
    private Integer selectedType;
    private List<BaseDTO> dropTypeList;
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;	
	
	@ManagedProperty("#{ReportsFacadeAddDropImpl}")
	private IReportsFacade reportsFacade;
	
	@PostConstruct
	public void init() {
		detailView = false;

		HttpServletRequest origRequest = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url = origRequest.getRequestURL();
		if (url.toString().contains("Dean")) {
			setShowIns(true);
			typeOfUser = 2;
		}
		pendingForms = new ArrayList<DropAddFormDTO>();
		fillPendingForms();
		archievedForms = new ArrayList<DropAddFormDTO>();
		fillArchievedForms();
		pendingFormsDean = new ArrayList<DropAddFormDTO>();
		fillPendingFormsDean();
		archievedFormsDean = new ArrayList<DropAddFormDTO>();
		//fillArchievedFormsDean();
		fillInstructorsLst();
		userTypesLst = new ArrayList<BaseDTO>();
		userTypesLst.add(new BaseDTO(1, "Instructor Mode"));
		userTypesLst.add(new BaseDTO(2, "Dean Mode"));
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			String mail = authentication.getName();
			if (mail.equals(Constants.DEAN_OF_STRATEGIC)) {
				if (!getInsDataFacade.isCouurseCoordinator(mail)) {

					// return true;
					setShowIns(true);
				}
			} else {
				setShowIns(false);
			}
			//HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			try
			{
				Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
				
				if(origRequest.getParameterValues("action")[0].equals("approveIns"))
				{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
				setSelectedPendingForms(facade.getByID(dtoID));
				}
				else if(origRequest.getParameterValues("action")[0].equals("refuseIns"))
				{
					setSelectedPendingForms(facade.getByID(dtoID));
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
				}
				else if(origRequest.getParameterValues("action")[0].equals("approveDean"))
				{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is approved successfully !" );
				setSelectedPendingFormsDean(facade.getByID(dtoID));
				}
				else if(origRequest.getParameterValues("action")[0].equals("refuseDean"))
				{
					setSelectedPendingFormsDean(facade.getByID(dtoID));
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is Refused successfully !" );
				}
				
				
			}
			catch(Exception ex)
			{
				System.out.println("Not redirect");
				ex.toString();
			}
			
			
			try
			{
				Integer dtoID=Integer.parseInt(origRequest.getParameterValues("id")[0]);
				if(dtoID!=null){
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Done !" );
					setSelectedPendingForms(facade.getByID(dtoID));
				}
			}
			catch(Exception ex)
			{
				ex.toString();
			}
			try
			{
				Integer forwaredInsID=Integer.parseInt(origRequest.getParameterValues("forwaredIns")[0]);
				InstructorDTO forwardedIns=getInsDataFacade.getInsByPersonID(forwaredInsID);
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form is forwarded successfully to : "+forwardedIns.getName() );
				
			}
			catch(Exception ex)
			{
				ex.toString();	
			}
		}
        dropTypeList=new ArrayList<BaseDTO>();
		dropTypeList.add(new BaseDTO(1,"WP"));
		dropTypeList.add(new BaseDTO(2,"WF"));
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		yearLst=new ArrayList<Integer>();
		for(int i=0;i<20;i++){
			yearLst.add(2013+i);
		}
		
		

	}

	public void fillPendingForms() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			String mail = authentication.getName();
			pendingForms = facade.getPendingFormsOfInstructor(getInsDataFacade
					.getInsByPersonMail(mail).getId());
			if (pendingForms == null) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Error In getting Petitions");
			}
		}
	}

	public void fillArchievedForms() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			String mail = authentication.getName();
			archievedForms = facade
					.getArchievedFormsOfInstructor(getInsDataFacade
							.getInsByPersonMail(mail).getId());
			if (archievedForms == null) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Error In getting Petitions");
			}
		}

	}

	
	public void ininttialzieYear(){
		selectedYear=null;
		archievedFormsDean=new ArrayList<DropAddFormDTO>();
	}
  public void getPetitionsBySemester(){
		if(getSelectedYear()!=null){
		if(getSelectedSemester()==0)
			archievedFormsDean=reportsFacade.getOldFall(getSelectedYear());
		else if(getSelectedSemester()==1)
			archievedFormsDean=reportsFacade.getOldSpring(getSelectedYear());
		else if(getSelectedSemester()==2)
			archievedFormsDean=reportsFacade.getOldSummer(getSelectedYear());
		}
	}
  public void exportExcelReport(){
		if(getArchievedForms()!=null)
			if(getArchievedForms().size()>0)
				reportsFacade.generateExcelByList(getArchievedFormsDean());
	}

	

	public void navigate() {
		if (getTypeOfUser() == 2) {
			/*
			 * fillPendingFormsDean(); fillArchievedFormsDean();
			 */
			setShowIns(true);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("addDropDean.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else/* if(getTypeOfUser()==1) */
		{
			setShowIns(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("addDropIns.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void fillPendingFormsDean() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			pendingFormsDean = facade.getPendingFormsOfDean();
			if (pendingFormsDean == null) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Error In getting Petitions");
			}
		}
	}

	/*public void fillArchievedFormsDean() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			archievedFormsDean = facade.getArchievedFormsOfDean();
			if (archievedFormsDean == null) {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"Error In getting Petitions");
			}
		}
	}*/

	public void showDetailsDean(DropAddFormDTO dto) {
		  try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("addDropDean1"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+dto.getId()+"&cases=Dean&oldMood=1");
	    		}
	    		else 
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+dto.getId()+"&cases=Dean");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		/*RequestContext.getCurrentInstance().reset("detFormDean:detGridDean");
		setDetailedFormDean(dto);
		if (dto.getType().equals(AddDropFormTypesEnum.ADD)) {
			setRenderAddAction(true);
			setRenderDropAction(false);

		} else if (dto.getType().equals(AddDropFormTypesEnum.DROP)) {
			setRenderAddAction(false);
			setRenderDropAction(true);

		} else if (dto.getType().equals(AddDropFormTypesEnum.DROPANDADD)) {
			setRenderAddAction(true);
			setRenderDropAction(true);

		}

		RequestContext.getCurrentInstance().execute("detDlgDean.show();");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("detFormDean:detGridDean");
		StringBuffer url = (StringBuffer) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURL();
		if (!url.toString().contains("1")) {
			FacesContext.getCurrentInstance().getPartialViewContext()
					.getRenderIds().add("detFormDean:dialogBtn");
			RequestContext.getCurrentInstance().reset("detFormDean:dialogBtn");
		}*/
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

	public void forwardPetition() {
		if (getSelectedInstructor() == null || getSelectedInstructor() == 0) {
			JavaScriptMessagesHandler.RegisterErrorMessage(null,
					"Please, Select Instructor To forward");
		} else {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged
																		// in
			{

				try {
					DropAddFormDTO dto = getDetailedForm();
					InstructorDTO forwardIns = new InstructorDTO();
					forwardIns.setId(getSelectedInstructor());
					dto.setForwardIns(forwardIns);
					if(newComment!=null && !newComment.equals(""))
				     { dto=constructComment(dto);
				     	getFormDetail().setComment(dto.getComment());
				     }
		    		dto = facade.forwardPetition(dto);
					if (dto != null) {
						for (int i = 0; i < instructors.size(); i++) {
							if (instructors.get(i).getId()
									.equals(getSelectedInstructor())) {
								fillPendingForms();
								 detailView = false;
				    				JavaScriptMessagesHandler
										.RegisterNotificationMessage(null,
												"Form is forwarded successfully to : "
														+ instructors.get(i)
																.getName());
								JavaScriptMessagesHandler
										.RegisterNotificationMessage(null,
												"You can find it in old petitions section");
								break;
							}
						}

					}
				} catch (Exception ex) {

				}
			}
		}
	}

	public void fillInstructorsLst() {
		instructors = new ArrayList<InstructorDTO>();
		instructors = facade.fillInsLst();
		for (int i = 0; i < instructors.size(); i++) {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged
																		// in
			{

				String mail = authentication.getName();
				if (getInsDataFacade.getInsByPersonMail(mail).getId()
						.equals(instructors.get(i).getId())) {
					instructors.remove(i);
				}
			}

		}
	}
	 public void onRowSelect(SelectEvent event) {  
	      	
	          DropAddFormDTO selectedDTO=(DropAddFormDTO) event.getObject();
	          try {
	      		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	      		StringBuffer url=origRequest.getRequestURL();
	      		if(url.toString().contains("addDropIns1"))
	      		{
	      			FacesContext.getCurrentInstance().getExternalContext().redirect
	      			("formDetails.xhtml?id="+selectedDTO.getId()+"&cases=Ins&oldMood=1");
	      		}
	      		else 
	      		FacesContext.getCurrentInstance().getExternalContext().redirect
	      		("formDetails.xhtml?id="+selectedDTO.getId()+"&cases=Ins");
	      	} catch (IOException e) {
	      		// TODO Auto-generated catch block
	      		e.printStackTrace();
	      	}
	      	
	    }  
	 
	 public void onRowSelectDean(SelectEvent event) {  
	      	
         DropAddFormDTO selectedDTO=(DropAddFormDTO) event.getObject();
         try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    		if(url.toString().contains("addDropDean1"))
	    		{
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formDetails.xhtml?id="+selectedDTO.getId()+"&cases=Dean&oldMood=1");
	    		}
	    		else 
				FacesContext.getCurrentInstance().getExternalContext().redirect
				("formDetails.xhtml?id="+selectedDTO.getId()+"&cases=Dean");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     	
   }  
	public IInstructorAddDropFormFacade getFacade() {
		return facade;
	}

	public void setFacade(IInstructorAddDropFormFacade facade) {
		this.facade = facade;
	}

	public List<DropAddFormDTO> getPendingForms() {
		fillPendingForms();
		return pendingForms;
	}

	public void setPendingForms(List<DropAddFormDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}

	public List<DropAddFormDTO> getArchievedForms() {
		fillArchievedForms();
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

	

	public DropAddFormDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}

	public void setSelectedArchievedForms(DropAddFormDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}

	public DropAddFormDTO getSelectedArchievedFormsDean() {
		return selectedArchievedFormsDean;
	}

	public void setSelectedArchievedFormsDean(
			DropAddFormDTO selectedArchievedFormsDean) {
		this.selectedArchievedFormsDean = selectedArchievedFormsDean;
	}

	public List<DropAddFormDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}

	public void setFilteredArchievedForms(
			List<DropAddFormDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public DropAddFormDTO getDetailedForm() {
		return detailedForm;
	}

	public void setDetailedForm(DropAddFormDTO detailedForm) {
		this.detailedForm = detailedForm;
	}

	public Integer getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(Integer typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	public Boolean getDeanMood() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{

			String mail = authentication.getName();
			if (mail.equals(Constants.DEAN_OF_STRATEGIC)) {
				if (getInsDataFacade.isPA(mail)) {

					return true;

				} else {

					return false;
				}
			} else {

				return false;
			}
		} else
			return false;

	}

	public void setDeanMood(Boolean deanMood) {
		this.deanMood = deanMood;
	}

	public List<BaseDTO> getUserTypesLst() {
		return userTypesLst;
	}

	public void setUserTypesLst(List<BaseDTO> userTypesLst) {
		this.userTypesLst = userTypesLst;
	}

	public Boolean getShowIns() {
		return showIns;
	}

	public void setShowIns(Boolean showIns) {
		this.showIns = showIns;
	}

	public List<DropAddFormDTO> getPendingFormsDean() {
		fillPendingFormsDean();
		return pendingFormsDean;
	}

	public void setPendingFormsDean(List<DropAddFormDTO> pendingFormsDean) {
		this.pendingFormsDean = pendingFormsDean;
	}

	public List<DropAddFormDTO> getArchievedFormsDean() {
		//fillArchievedFormsDean();
		return archievedFormsDean;
	}

	public void setArchievedFormsDean(List<DropAddFormDTO> archievedFormsDean) {
		this.archievedFormsDean = archievedFormsDean;
	}

	public DropAddFormDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}

	public void setSelectedPendingForms(DropAddFormDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}

	public DropAddFormDTO getSelectedPendingFormsDean() {
		return selectedPendingFormsDean;
	}

	public void setSelectedPendingFormsDean(
			DropAddFormDTO selectedPendingFormsDean) {
		this.selectedPendingFormsDean = selectedPendingFormsDean;
	}

	public List<DropAddFormDTO> getFilteredPendingFormsDean() {
		return filteredPendingFormsDean;
	}

	public void setFilteredPendingFormsDean(
			List<DropAddFormDTO> filteredPendingFormsDean) {
		this.filteredPendingFormsDean = filteredPendingFormsDean;
	}

	

	public List<DropAddFormDTO> getFilteredArchievedFormsDean() {
		return filteredArchievedFormsDean;
	}

	public void setFilteredArchievedFormsDean(
			List<DropAddFormDTO> filteredArchievedFormsDean) {
		this.filteredArchievedFormsDean = filteredArchievedFormsDean;
	}

	public DropAddFormDTO getDetailedFormDean() {
		return detailedFormDean;
	}

	public void setDetailedFormDean(DropAddFormDTO detailedFormDean) {
		this.detailedFormDean = detailedFormDean;
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

	public List<InstructorDTO> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<InstructorDTO> instructors) {
		this.instructors = instructors;
	}

	public Integer getSelectedInstructor() {
		return selectedInstructor;
	}

	public void setSelectedInstructor(Integer selectedInstructor) {
		this.selectedInstructor = selectedInstructor;
	}

	public void showDetails(DropAddFormDTO form) {
	
	try {
		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		StringBuffer url=origRequest.getRequestURL();
		if(url.toString().contains("addDropIns1"))
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect
			("formDetails.xhtml?id="+form.getId()+"&cases=Ins&oldMood=1");
		}
		else 
		FacesContext.getCurrentInstance().getExternalContext().redirect
		("formDetails.xhtml?id="+form.getId()+"&cases=Ins");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	

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

	public Integer getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(Integer selectedType) {
		this.selectedType = selectedType;
	}

	public List<BaseDTO> getDropTypeList() {
		return dropTypeList;
	}

	public void setDropTypeList(List<BaseDTO> dropTypeList) {
		this.dropTypeList = dropTypeList;
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
