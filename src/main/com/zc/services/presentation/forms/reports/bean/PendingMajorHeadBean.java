/**
 * 
 */
package main.com.zc.services.presentation.forms.reports.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import main.com.zc.services.applicationService.dashboard.IDashboardAppService;
import main.com.zc.services.presentation.survey.CourseEvalNew.facade.ICoursesFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.facade.IInstructorFacade;

/**
 * @author omnya
 *
 */
@ManagedBean(name="PendingMajorHeadBean")
@ViewScoped
public class PendingMajorHeadBean {

	@ManagedProperty("#{ICoursesEvalFacade}")
	private ICoursesFacade courseFacade;
	
	
	@ManagedProperty("#{IInstructorFacade}")
	private IInstructorFacade insFacade;
	
	@ManagedProperty("#{IDashboardAppService}")
	IDashboardAppService dashboardService;
	
	
	@PostConstruct
	public void init(){
		exportAcademicPetition();	
	}
	public void exportAcademicPetition(){
		List<InstructorDTO> instructors=insFacade.getAllIns();
		
		for(int i=0;i<instructors.size();i++)
		{
			Integer total=0;
			if(instructors.get(i).getMail()!=null)
		{
			if(instructors.get(i).getId()==6){
			try{
				total+=dashboardService.getDeanJuniorTAPending();
				}
				catch(Exception ex){
					
				}
				try{
					total+=dashboardService.getDeanChangeMajorPending().size();
					}
					catch(Exception ex){
						
					}
				try{
					total+=dashboardService.getDeanIncompleteGradePending();
					}
					catch(Exception ex){
						
					}
				try{
					total+=dashboardService.getDeanOverloadRequestPending().size();
					}
					catch(Exception ex){
						
					}
				try{
					total+=dashboardService.getDeanAddDropPending().size();
					}
					catch(Exception ex){
						
					}
				try{
					total+=dashboardService.getDeanAcademicPetitionsPending().size();
					}
					catch(Exception ex){
						
					}
				try{
					total+=dashboardService.getDeanCourseRepeatPending();
					}
					catch(Exception ex){
						
					}
		}
			try{
			total+=dashboardService.getInstructorJuniorTAPending(instructors.get(i).getMail());
			}
			catch(Exception ex){
				
			}
			try{
				total+=dashboardService.getInstructorChangeMajorPending(instructors.get(i).getMail());
				}
				catch(Exception ex){
					
				}
			try{
				total+=dashboardService.getInstructorIncompleteGradePending(instructors.get(i).getMail());
				}
				catch(Exception ex){
					
				}
			try{
				total+=dashboardService.getInstructorOverloadRequestPending(instructors.get(i).getMail());
				}
				catch(Exception ex){
					
				}
			try{
				total+=dashboardService.getInstructorAddDropPending(instructors.get(i).getMail());
				}
				catch(Exception ex){
					
				}
			try{
				total+=dashboardService.getInstructorAcademicPetitionsPending(instructors.get(i).getMail());
				}
				catch(Exception ex){
					
				}
			try{
				total+=dashboardService.getInstructorCourseRepeatPending(instructors.get(i).getMail());
				}
				catch(Exception ex){
					
				}
			
			System.out.println(instructors.get(i).getName()+" : "+total);
		}}
		//print total and name
	
	}
	public ICoursesFacade getCourseFacade() {
		return courseFacade;
	}
	public void setCourseFacade(ICoursesFacade courseFacade) {
		this.courseFacade = courseFacade;
	}
	public IInstructorFacade getInsFacade() {
		return insFacade;
	}
	public void setInsFacade(IInstructorFacade insFacade) {
		this.insFacade = insFacade;
	}
	public IDashboardAppService getDashboardService() {
		return dashboardService;
	}
	public void setDashboardService(IDashboardAppService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
}
