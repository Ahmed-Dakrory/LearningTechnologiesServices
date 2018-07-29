/**
 * 
 */
package main.com.zc.services.presentation.survey.lectureObjectiveFeedback.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.facade.ISemesterWeeksFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean
public class ConfigureSemesterBean {

	@ManagedProperty("#{ISemesterWeeksFacade}")
   	private ISemesterWeeksFacade facade; 
	
	private List<SemesterWeeksDTO> weeks;
	private List<BaseDTO> semesterLst;
	private List<Integer> yearLst;
	private SemesterWeeksDTO addedWeek;
	private boolean addWeekMode;
	SemesterWeeksDTO selectedWeek;
	private List<SemesterWeeksDTO> filteredWeeks;
	private SemesterWeeksDTO deletedWeek;
	@PostConstruct
	public void init()
	{
		weeks=new ArrayList<SemesterWeeksDTO>();
		fillSemesterLst();
		addedWeek=new SemesterWeeksDTO();
	/*	selectedWeek=new SemesterWeeksDTO();
		filteredWeeks=new ArrayList<SemesterWeeksDTO>();*/
	}

	public void fillSemesterLst()
	{
		semesterLst=new ArrayList<BaseDTO>();
		semesterLst.add(new BaseDTO(0,"Fall"));
		semesterLst.add(new BaseDTO(1,"Spring"));
		semesterLst.add(new BaseDTO(2,"Summer"));
		//semesterLst.add(new BaseDTO(3,"Winter"));
	}
	public void fillYearLst(AjaxBehaviorEvent event)
	{
		 yearLst=new ArrayList<Integer>();
		 getAddedWeek().setYear(null);
		 for(int i=2013;i<2031;i++)
		{
			yearLst.add(i);
		}
	}
	public void fillweeks(AjaxBehaviorEvent event)
	{
		//TODO 
		//get weeks given semester and year
		weeks=facade.getBySemesterAndyear(getAddedWeek().getSemester(), getAddedWeek().getYear());
	}
	public void showAddWeek()
	{
		if(addWeekMode==false)
			addWeekMode=true;
		else if(addWeekMode==true)
			addWeekMode=false;
	/*	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form1:mainpnlsettings");
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form1:mainpnlsettings");
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":form1:advSettingGrid");*/
	}
	public void addWeek(){
		System.out.println("Name: "+getAddedWeek().getName());
		System.out.println("Start date: "+getAddedWeek().getStartDateFriendly());
		System.out.println("End date: "+getAddedWeek().getEndDateFriendly());
		System.out.println("semester : "+getAddedWeek().getSemester());
		System.out.println("year: "+getAddedWeek().getYear());
		SemesterWeeksDTO newAdded=facade.add(getAddedWeek());
		
		if(newAdded!=null)
		{
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Week is added Successfully");
			weeks=facade.getBySemesterAndyear(getAddedWeek().getSemester(), getAddedWeek().getYear());
			setSelectedWeek(newAdded);
			getAddedWeek().setName(null);
			getAddedWeek().setStartDate(null);
			getAddedWeek().setEndDate(null);
		}
		
	}
	public void preDeleteWeek(SemesterWeeksDTO week){ 
		setDeletedWeek(week);
	}
	public void delete(){
		@SuppressWarnings("unused")
		boolean deleted=facade.remove(getDeletedWeek().getId());
		weeks=facade.getBySemesterAndyear(getAddedWeek().getSemester(), getAddedWeek().getYear());
	}
	public void activate(SemesterWeeksDTO week){
	
		week.setActive(true);
		SemesterWeeksDTO updatedWeek=facade.update(week);
		if(updatedWeek!=null)
			if(updatedWeek.getId()!=null)
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Week is opened Successfully");
		weeks=facade.getBySemesterAndyear(getAddedWeek().getSemester(), getAddedWeek().getYear());
	}
	public void deActivate(SemesterWeeksDTO week){
		
		week.setActive(false);
		SemesterWeeksDTO updatedWeek=facade.update(week);
		if(updatedWeek!=null)
			if(updatedWeek.getId()!=null)
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Week is closed Successfully");
		weeks=facade.getBySemesterAndyear(getAddedWeek().getSemester(), getAddedWeek().getYear());
	}
	public ISemesterWeeksFacade getFacade() {
		return facade;
	}

	public void setFacade(ISemesterWeeksFacade facade) {
		this.facade = facade;
	}

	public List<SemesterWeeksDTO> getWeeks() {
		return weeks;
	}

	public void setWeeks(List<SemesterWeeksDTO> weeks) {
		this.weeks = weeks;
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

	public SemesterWeeksDTO getAddedWeek() {
		return addedWeek;
	}

	public void setAddedWeek(SemesterWeeksDTO addedWeek) {
		this.addedWeek = addedWeek;
	}

	public boolean isAddWeekMode() {
		return addWeekMode;
	}

	public void setAddWeekMode(boolean addWeekMode) {
		this.addWeekMode = addWeekMode;
	}

	public SemesterWeeksDTO getSelectedWeek() {
		return selectedWeek;
	}

	public void setSelectedWeek(SemesterWeeksDTO selectedWeek) {
		this.selectedWeek = selectedWeek;
	}

	public List<SemesterWeeksDTO> getFilteredWeeks() {
		return filteredWeeks;
	}

	public void setFilteredWeeks(List<SemesterWeeksDTO> filteredWeeks) {
		this.filteredWeeks = filteredWeeks;
	}

	public SemesterWeeksDTO getDeletedWeek() {
		return deletedWeek;
	}

	public void setDeletedWeek(SemesterWeeksDTO deletedWeek) {
		this.deletedWeek = deletedWeek;
	}
	
	
}
