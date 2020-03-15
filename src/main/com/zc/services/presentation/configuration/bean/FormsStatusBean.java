/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="FormsStatusBean")
public class FormsStatusBean {

	private List<FormsStatusDTO> forms=new ArrayList<FormsStatusDTO>();
	private List<BaseDTO> status; 
	private Integer selectedStatus;
	private FormsStatusDTO detailedForm;
	private boolean academicPetition;
	private boolean dropAdd;
	private boolean dropAddPhase2;
	private boolean dropAddPhase3;
	private boolean changeMajor;
	private boolean readmission;
	private boolean overloadRequest;
	private boolean courseRepeat;
	private boolean incompleteGrade;
	private boolean declarationOfConcentration;
	private boolean declarationOfMinor;
	private boolean declarationOfMajor;
	private boolean intendedMajor;
	private boolean taJuniorProg;
	private boolean changeOfConcentration;
	private List<String> levels=new ArrayList<String>();
	private List<String> selectedLevels=new ArrayList<String>();
	private List<Integer> yearsLst;
	private List<BaseDTO> semestersLst;
	
	@ManagedProperty("#{IFormsStatusFacade}")
   	private IFormsStatusFacade facade; 
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	
	@PostConstruct
	public void init()
	{
		fillFormsList();
		fillSemesterLst();
		fillYearLst();
	
		status=new ArrayList<BaseDTO>();
		status.add(new BaseDTO(0,"Active"));
		status.add(new BaseDTO(1,"Inactive"));
		status.add(new BaseDTO(2,"Phase 2"));
		status.add(new BaseDTO(3,"Phase 3"));
		levels.add("1");
		levels.add("2");
		levels.add("3");
		levels.add("4");
		levels.add("5");
	
	}
	public void fillSemesterLst()
	{
		semestersLst=new ArrayList<BaseDTO>();
		semestersLst.add(new BaseDTO(0,"Fall"));
		semestersLst.add(new BaseDTO(1,"Spring"));
		semestersLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst()
	{
		yearsLst=new ArrayList<Integer>();
		for(int i=2013;i<2031;i++)
		{
			yearsLst.add(i);
		}
	}
	public void fillFormsList()
	{
		forms=facade.getAll();
	}
	public void changeStatus()
	{
		try{
		if(getSelectedStatus()==0)
		{
			getDetailedForm().setStatus(FormsStatusEnum.Active);
		}
		else if(getSelectedStatus()==1)
		{
			getDetailedForm().setStatus(FormsStatusEnum.Inactive);
		}
		else if(getSelectedStatus()==2)
		{
			getDetailedForm().setStatus(FormsStatusEnum.Second_Phase);
		}
		else if(getSelectedStatus()==3)
		{
			getDetailedForm().setStatus(FormsStatusEnum.Third_Phase);
		}
		FormsStatusDTO form=facade.update(getDetailedForm());
		if(form!=null)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Updated successfully");
			fillFormsList();
			setDetailedForm(form);
			try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formStatusDetails.xhtml");
	    			
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public String backToForms()
	{
		return "formStatus.xhtml?faces-redirect=true";
	}
	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		FormsStatusDTO selectedDTO=(FormsStatusDTO) event.getObject();
	  		setDetailedForm(selectedDTO);
	  		 selectedLevels=new ArrayList<String>();
			for(int i=0;i<getDetailedForm().getLevels().size();i++)
			{
				selectedLevels.add(Integer.toString(getDetailedForm().getLevels().get(i)));
			}
	  		try {
	    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	    		StringBuffer url=origRequest.getRequestURL();
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("formStatusDetails.xhtml");
	    			//instructors=courseInsFacade.getAllInstructorsByCourseId(selectedDTO.getId());
	    			
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveLevels()
	{
		try{
			//getDetailedForm().setLevels(getLevels());
			
			List<Integer> levelss=new ArrayList<Integer>();
			for(int i=0;i<selectedLevels.size();i++)
			{
				levelss.add(Integer.parseInt(selectedLevels.get(i)));
			}
			getDetailedForm().setLevels(levelss);
			FormsStatusDTO forms=facade.addLevelsToForm(getDetailedForm());
			if(forms.getLevels()!=null)
			{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Updated successfully");
				fillFormsList();
				
				setDetailedForm(forms);
				try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("formStatusDetails.xhtml");
		    			
		    		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	}
	public void saveYearAndSem(){
		try{
			
			FormsStatusDTO form=facade.updateYearAndSemesters(getDetailedForm());
			if(form!=null)
			{
				JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Updated successfully");
				fillFormsList();
				setDetailedForm(form);
				try {
		    		HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		    		StringBuffer url=origRequest.getRequestURL();
		    			FacesContext.getCurrentInstance().getExternalContext().redirect
						("formStatusDetails.xhtml");
		    			
		    		
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
	}
	public List<FormsStatusDTO> getForms() {
		fillFormsList();
		return forms;
	}
	public void setForms(List<FormsStatusDTO> forms) {
		this.forms = forms;
	}

	public List<BaseDTO> getStatus() {
		return status;
	}
	public void setStatus(List<BaseDTO> status) {
		this.status = status;
	}
	public Integer getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(Integer selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	public IFormsStatusFacade getFacade() {
		return facade;
	}
	public void setFacade(IFormsStatusFacade facade) {
		this.facade = facade;
	}
	public FormsStatusDTO getDetailedForm() {
		return detailedForm;
	}
	public void setDetailedForm(FormsStatusDTO detailedForm) {
		this.detailedForm = detailedForm;
	}
	public boolean isAcademicPetition() {
		FormsStatusDTO form=facade.getById(1);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setAcademicPetition(boolean academicPetition) {
		this.academicPetition = academicPetition;
	}
	public boolean isDropAdd() {
		FormsStatusDTO form=facade.getById(2);
		if(form.getStatus().equals(FormsStatusEnum.Active)||form.getStatus().equals(FormsStatusEnum.Second_Phase)||form.getStatus().equals(FormsStatusEnum.Third_Phase))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setDropAdd(boolean dropAdd) {
		this.dropAdd = dropAdd;
	}
	public boolean isDropAddPhase2() {
		FormsStatusDTO form=facade.getById(2);
		if(form.getStatus().equals(FormsStatusEnum.Second_Phase))
		{
			return true;
		}
		
		else return false;
	}
	public void setDropAddPhase2(boolean dropAddPhase2) {
		this.dropAddPhase2 = dropAddPhase2;
	}
	public boolean isDropAddPhase3() {
		FormsStatusDTO form=facade.getById(2);
		if(form.getStatus().equals(FormsStatusEnum.Third_Phase))
		{
			return true;
		}
		
		else return false;
	}
	public void setDropAddPhase3(boolean dropAddPhase3) {
		this.dropAddPhase3 = dropAddPhase3;
	}
	public boolean isChangeMajor() {
		FormsStatusDTO form=facade.getById(3);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setChangeMajor(boolean changeMajor) {
		this.changeMajor = changeMajor;
	}
	
	
	public boolean isReadmission() {
		FormsStatusDTO form=facade.getById(19);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setReadmission(boolean readmission) {
		this.readmission = readmission;
	}
	public boolean isOverloadRequest() {
		FormsStatusDTO form=facade.getById(4);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setOverloadRequest(boolean overloadRequest) {
		this.overloadRequest = overloadRequest;
	}
	public boolean isCourseRepeat() {
		FormsStatusDTO form=facade.getById(5);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setCourseRepeat(boolean courseRepeat) {
		this.courseRepeat = courseRepeat;
	}
	public boolean isIncompleteGrade() {
		FormsStatusDTO form=facade.getById(6);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setIncompleteGrade(boolean incompleteGrade) {
		this.incompleteGrade = incompleteGrade;
	}
	public boolean isDeclarationOfConcentration() {
		FormsStatusDTO form=facade.getById(7);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			//TODO check on levels too
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{   
				String mail = authentication.getName();
				if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4)))
				{
					PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
					//if(Integer.toString(student.getFileNo()).startsWith("")
					 List<Integer> settings=form.getLevels();
					 boolean exist=false;
					 for(int i=0;i<settings.size();i++)
					 {
						if(settings.get(i).equals(student.getLevelID()))
						{
							exist= true;
							break;
							
						}
						
					 }
					 if(exist)
					 {
						 return true;
					 }
					 else return false;
					
				}
					
				else return false;
			}
			else return false;
		
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setDeclarationOfConcentration(boolean declarationOfConcentration) {
		this.declarationOfConcentration = declarationOfConcentration;
	}
	
	
	
	public boolean isDeclarationOfMinor() {
		FormsStatusDTO form=facade.getById(20);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			//TODO check on levels too
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{   
				String mail = authentication.getName();
				if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4)))
				{
					PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
					//if(Integer.toString(student.getFileNo()).startsWith("")
					 List<Integer> settings=form.getLevels();
					 boolean exist=false;
					 for(int i=0;i<settings.size();i++)
					 {
						if(settings.get(i).equals(student.getLevelID()))
						{
							exist= true;
							break;
							
						}
						
					 }
					 if(exist)
					 {
						 return true;
					 }
					 else return false;
					
				}
					
				else return false;
			}
			else return false;
		
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setDeclarationOfMinor(boolean declarationOfMinor) {
		this.declarationOfMinor = declarationOfMinor;
	}
	public List<String> getLevels() {
		return levels;
	}
	public void setLevels(List<String> levels) {
		this.levels = levels;
	}
	public List<String> getSelectedLevels() {
		if(getDetailedForm().getId()==7)
		{
			List<String> definedLevels=new ArrayList<String>();
			for(int i=0;i<getDetailedForm().getLevels().size();i++)
			{
				definedLevels.add(Integer.toString(getDetailedForm().getLevels().get(i)));
			}
			setSelectedLevels(definedLevels);
		}
		return selectedLevels;
	}
	public void setSelectedLevels(List<String> selectedLevels) {
		this.selectedLevels = selectedLevels;
	}
	public List<Integer> getYearsLst() {
		return yearsLst;
	}
	public void setYearsLst(List<Integer> yearsLst) {
		this.yearsLst = yearsLst;
	}
	public List<BaseDTO> getSemestersLst() {
		return semestersLst;
	}
	public void setSemestersLst(List<BaseDTO> semestersLst) {
		this.semestersLst = semestersLst;
	}
	public boolean isDeclarationOfMajor() {
		FormsStatusDTO form=facade.getById(9);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			//TODO check on levels too
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
			{   
				String mail = authentication.getName();
				if(mail.startsWith("s-")||mail.startsWith("S-")||StringUtils.isNumeric(mail.substring(0, 4)))
				{
					PersonDataDTO student= studentDataFacade.getPersonByPersonMail(mail);
					//if(Integer.toString(student.getFileNo()).startsWith("")
					 List<Integer> settings=form.getLevels();
					 boolean exist=false;
					 for(int i=0;i<settings.size();i++)
					 {
						if(settings.get(i).equals(student.getLevelID()))
						{
							exist= true;
							break;
							
						}
						
					 }
					 if(exist)
					 {
						 return true;
					 }
					 else return false;
					
				}
					
				else return false;
			}
			else return false;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setDeclarationOfMajor(boolean declarationOfMajor) {
		this.declarationOfMajor = declarationOfMajor;
	}
	public boolean isIntendedMajor() {
		FormsStatusDTO form=facade.getById(8);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setIntendedMajor(boolean intendedMajor) {
		this.intendedMajor = intendedMajor;
	}
	public boolean isTaJuniorProg() {
		FormsStatusDTO form=facade.getById(10);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setTaJuniorProg(boolean taJuniorProg) {
		this.taJuniorProg = taJuniorProg;
	}
	public boolean isChangeOfConcentration() {
		FormsStatusDTO form=facade.getById(14);
		if(form.getStatus().equals(FormsStatusEnum.Active))
		{
			return true;
		}
		else if(form.getStatus().equals(FormsStatusEnum.Inactive))
		{
			return false;
		}
		else return false;
	}
	public void setChangeOfConcentration(boolean changeOfConcentration) {
		this.changeOfConcentration = changeOfConcentration;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

}
