/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.service.repository.heads.HeadsAppServiceImpl;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean
public class ManageHeadsBean extends SpringBeanAutowiringSupport {

	
	private List<Heads> heads;
	private Heads addedHead;
	private Heads updatedHead;
	private List<Employee> instructors;
	private BaseDTO addedConcentration;
	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	
	private int newEmployeeId;
	
	@Autowired
	IEmployeeRepository insfacade;
	
	@PostConstruct
	public void init()
	{
		addedHead=new Heads();
		updatedHead=new Heads();
		addedConcentration=new BaseDTO();
		
		fillHeadsList();
		instructors=new ArrayList<Employee>();
		fillInsList();
		
	}
	public void fillInsList()
	{
		instructors=insfacade.getAll();
	}

	private void fillHeadsList() {
		heads=new ArrayList<Heads>();
		heads=headFacades.getAll();
		
	}
	public void preAddHead()
	{
		addedHead=new Heads();
		RequestContext.getCurrentInstance().execute("addHeadDlg.show();");
		
		
	}
	
	public void preUpdateHead(Heads head)
	{
		addedHead=new Heads();
		newEmployeeId=head.getHeadPersonId().getId();
		setAddedHead(head);
		RequestContext.getCurrentInstance().execute("addHeadDlg.show();");
		
	}
	public void addHead(){
		
		if(addedHead.getId()==null){//Adding case
			Employee newEmployee=insfacade.getById(newEmployeeId);
			addedHead.setHeadPersonId(newEmployee);
		addedHead=headFacades.addHead(getAddedHead());
		if(addedHead!=null&&addedHead.getId()!=null)
		{fillHeadsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in adding");
		}
		else //Updating case
		{
			addedHead=headFacades.addHead(getAddedHead());
			if(addedHead!=null&&addedHead.getId()!=null)
			{fillHeadsList();
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "updated Successfully");
			}
			else 
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in updating");
			
		}
	}

	public void deleteHead(Heads head){
		boolean b=headFacades.delete(head);
		if(b)
		{fillHeadsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "deleted Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in deleting, this head has already been used by students , you can hide it only");
	}
	
	
	public List<Heads> getHeads() {
		return heads;
	}
	public void setHeads(List<Heads> heads) {
		this.heads = heads;
	}
	public Heads getAddedHead() {
		return addedHead;
	}
	public void setAddedHead(Heads addedHead) {
		this.addedHead = addedHead;
	}
	public Heads getUpdatedHead() {
		return updatedHead;
	}
	public void setUpdatedHead(Heads updatedHead) {
		this.updatedHead = updatedHead;
	}
	
	public BaseDTO getAddedConcentration() {
		return addedConcentration;
	}
	public void setAddedConcentration(BaseDTO addedConcentration) {
		this.addedConcentration = addedConcentration;
	}
	
	

	public List<Employee> getInstructors() {
		return instructors;
	}
	public void setInstructors(List<Employee> instructors) {
		this.instructors = instructors;
	}
	
	public HeadsAppServiceImpl getHeadFacades() {
		return headFacades;
	}
	public void setHeadFacades(HeadsAppServiceImpl headFacades) {
		this.headFacades = headFacades;
	}
	public IEmployeeRepository getInsfacade() {
		return insfacade;
	}
	public void setInsfacade(IEmployeeRepository insfacade) {
		this.insfacade = insfacade;
	}
	public int getNewEmployeeId() {
		return newEmployeeId;
	}
	public void setNewEmployeeId(int newEmployeeId) {
		this.newEmployeeId = newEmployeeId;
	}
	
	
	
	

	
	
}
