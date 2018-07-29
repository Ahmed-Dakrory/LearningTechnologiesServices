/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.academicPetition.facade.ISharedAcademicPetFacade;
import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationActionsFacade;
import main.com.zc.services.presentation.forms.changeOfConcentration.facade.IChangeConcentrationAdHFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class ChangeOfConcentrationADHBean {

	@ManagedProperty("#{IChangeConcentrationAdHFacade}")
	private IChangeConcentrationAdHFacade facade;
	@ManagedProperty("#{SharedAcademicPetFacadeImpl}")
	private ISharedAcademicPetFacade sharedAcademicPetFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;


	private List<ChangeConcentrationDTO> pendingForms;
	private List<ChangeConcentrationDTO> archievedForms;
	private ChangeConcentrationDTO selectedPendingForms;
	private List<ChangeConcentrationDTO> filteredPendingForms;
	private ChangeConcentrationDTO selectedArchievedForms;
	private List<ChangeConcentrationDTO> filteredArchievedForms;
	private ChangeConcentrationDTO detailedForm;
	@ManagedProperty("#{IChangeConcentrationActionsFacade}")
	private IChangeConcentrationActionsFacade actionFacade;
	
	private Integer selectedSemester;
	private Integer selectedYear;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;
	
	
	
	@PostConstruct
	public void init()
	{
			fillPendingFormLst();
			archievedForms=new ArrayList<ChangeConcentrationDTO>();
		//fillArchievedPetitionsLst();
		detailedForm=new ChangeConcentrationDTO();
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
	public void fillPendingFormLst()
	{
		pendingForms=new ArrayList<ChangeConcentrationDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_HEAD)){
				pendingForms=facade.getPendingFormsOfAdmissionHead();
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
		
	}

	public void fillArchievedPetitionsLst()
	{
		archievedForms=new ArrayList<ChangeConcentrationDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.equals(Constants.ADMISSION_HEAD)){
				archievedForms=facade.getArchievedFormsOfAdmissionHead();
				
			}
			else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "Not Allowed To see This Petitions");
			}
		}
	}
    public void onRowSelect(SelectEvent event) {  
	  	try {
	  		ChangeConcentrationDTO selectedDTO=(ChangeConcentrationDTO) event.getObject();
			showDetails(selectedDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
    public void showDetails(ChangeConcentrationDTO form) {
    			try {
        		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        		StringBuffer url=origRequest.getRequestURL();
        		if(url.toString().contains("changeOfConcentrationAdmissionHead1"))
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
    		}

	public void ininttialzieYear() {
		selectedYear = null;
		archievedForms = new ArrayList<ChangeConcentrationDTO>();
	}

	public void getPetitionsBySemester() {
		if (getSelectedYear() != null) {
			if (getSelectedSemester() == 0)
				archievedForms = facade.getOldFall(getSelectedYear());
			else if (getSelectedSemester() == 1)
				archievedForms = facade.getOldSpring(getSelectedYear());
			else if (getSelectedSemester() == 2)
				archievedForms = facade.getOldSummer(getSelectedYear());
		}
	}
	public IChangeConcentrationAdHFacade getFacade() {
		return facade;
	}


	public void setFacade(IChangeConcentrationAdHFacade facade) {
		this.facade = facade;
	}


	public ISharedAcademicPetFacade getSharedAcademicPetFacade() {
		return sharedAcademicPetFacade;
	}


	public void setSharedAcademicPetFacade(ISharedAcademicPetFacade sharedAcademicPetFacade) {
		this.sharedAcademicPetFacade = sharedAcademicPetFacade;
	}


	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}


	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}


	public List<ChangeConcentrationDTO> getPendingForms() {
		return pendingForms;
	}


	public void setPendingForms(List<ChangeConcentrationDTO> pendingForms) {
		this.pendingForms = pendingForms;
	}


	public List<ChangeConcentrationDTO> getArchievedForms() {
		return archievedForms;
	}


	public void setArchievedForms(List<ChangeConcentrationDTO> archievedForms) {
		this.archievedForms = archievedForms;
	}


	public ChangeConcentrationDTO getSelectedPendingForms() {
		return selectedPendingForms;
	}


	public void setSelectedPendingForms(ChangeConcentrationDTO selectedPendingForms) {
		this.selectedPendingForms = selectedPendingForms;
	}


	public List<ChangeConcentrationDTO> getFilteredPendingForms() {
		return filteredPendingForms;
	}


	public void setFilteredPendingForms(List<ChangeConcentrationDTO> filteredPendingForms) {
		this.filteredPendingForms = filteredPendingForms;
	}


	public ChangeConcentrationDTO getSelectedArchievedForms() {
		return selectedArchievedForms;
	}


	public void setSelectedArchievedForms(ChangeConcentrationDTO selectedArchievedForms) {
		this.selectedArchievedForms = selectedArchievedForms;
	}


	public List<ChangeConcentrationDTO> getFilteredArchievedForms() {
		return filteredArchievedForms;
	}


	public void setFilteredArchievedForms(List<ChangeConcentrationDTO> filteredArchievedForms) {
		this.filteredArchievedForms = filteredArchievedForms;
	}


	public ChangeConcentrationDTO getDetailedForm() {
		return detailedForm;
	}


	public void setDetailedForm(ChangeConcentrationDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public IChangeConcentrationActionsFacade getActionFacade() {
		return actionFacade;
	}
	public void setActionFacade(IChangeConcentrationActionsFacade actionFacade) {
		this.actionFacade = actionFacade;
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
	
	
	
	
	
	
}
