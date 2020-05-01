/**
 * 
 */
package main.com.zc.services.presentation.dashboard.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.dashboard.dto.DashboardElement;
import main.com.zc.services.presentation.dashboard.facade.IDashboardFacade;
import main.com.zc.shared.presentation.beans.LeftNavigationMenuBean;

/**
 * @author momen
 *
 */
@ManagedBean
@ViewScoped
public class DashboardBean 
{	
	@ManagedProperty("#{dashboardFacadeImpl}")
   	private IDashboardFacade dashboardFacade; 
	
	@ManagedProperty("#{leftNavigationMenuBean}")
	private LeftNavigationMenuBean leftNavigationMenuBean;
	
	private List<DashboardElement> dashboradElements;
	private List<DashboardElement> adminDashboradElements;
	
	private boolean adminMode;
	@PostConstruct
	public void init()
	{
		fillElements();
	}
	
	
	private void fillElements() 
	{
	try{
		dashboradElements = dashboardFacade.fillDashboardElements();
		adminDashboradElements = dashboardFacade.fillAdminDashboardElements();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	}
	
	public IDashboardFacade getDashboardFacade() {
		return dashboardFacade;
	}
	public void setDashboardFacade(IDashboardFacade dashboardFacade) {
		this.dashboardFacade = dashboardFacade;
	}
	public List<DashboardElement> getDashboradElements() {
		return dashboradElements;
	}
	public void setDashboradElements(List<DashboardElement> dashboradElements) {
		this.dashboradElements = dashboradElements;
	}
	public String navigateToDashBoardElement(String url)
	{
		if(url.equals("newPetition"))
			return leftNavigationMenuBean.renderAcademicPet();
		else if(url.equals("newAddDrop"))
			return leftNavigationMenuBean.renderAddDropForm();
		else if(url.equals("newChangeMajor"))
			return leftNavigationMenuBean.renderChangeMajor();
		else if(url.equals("newReadmission"))
			return leftNavigationMenuBean.renderReadmission();
		else if(url.equals("newcourse_replacement_form"))
			return leftNavigationMenuBean.renderCourseReplacement();
		else if(url.equals("newOverload"))
			return leftNavigationMenuBean.renderOverLoadReq();
		else if(url.equals("newCourseRepeat"))
			return leftNavigationMenuBean.renderCourseRepeat();
		else if(url.equals("newIncompleteGrade"))
			return leftNavigationMenuBean.renderIncompleteGrade();
		else if(url.equals("newjuniorTA"))
			return leftNavigationMenuBean.renderJuniorTAProgram();
		else
			return "";
	}
	
	public String navigateToAdminDashBoardElement(String url)
	{
		if(url.equals("newPetition"))
			return "/pages/secured/forms/academicPetition/admissionAcademicPetAdmin.xhtml?faces-redirect=true";
		else if(url.equals("newAddDrop"))
			return "/pages/secured/forms/dropAndAdd/addDropAdmin.xhtml?faces-redirect=true";
		else if(url.equals("newChangeMajor"))
			return "/pages/secured/forms/changeMajor/changeMajorAdmin.xhtml?faces-redirect=true";
		else if(url.equals("newOverload"))
			return "/pages/secured/forms/overloadRequest/overloadRequestAdmin.xhtml?faces-redirect=true";
		else if(url.equals("newCourseRepeat"))
			return "/pages/secured/forms/CourseRepeatForm/courseRepeatFormAdmin.xhtml?faces-redirect=true";
		else if(url.equals("newIncompleteGrade"))
			return "/pages/secured/forms/incompleteGrade/incompleteGradeAdmin.xhtml?faces-redirect=true";
		
		else
			return "";
	}
	public LeftNavigationMenuBean getLeftNavigationMenuBean() {
		return leftNavigationMenuBean;
	}
	public void setLeftNavigationMenuBean(
			LeftNavigationMenuBean leftNavigationMenuBean) {
		this.leftNavigationMenuBean = leftNavigationMenuBean;
	}
	public List<DashboardElement> getAdminDashboradElements() {
		return adminDashboradElements;
	}
	public void setAdminDashboradElements(
			List<DashboardElement> adminDashboradElements) {
		this.adminDashboradElements = adminDashboradElements;
	}


	public boolean isAdminMode() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			if(!authentication.getName().equals(Constants.LTS_SYSTEM_ADMIN)&&
					!authentication.getName().equals(Constants.DEAN_OF_STRATEGIC)&&!authentication.getName().equals(Constants.DEAN_OF_ACADEMIC))
			{
				return true;
			}
			else 
				return false;
			
		}
		else return false;
		
	}


	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}
	
	
}
