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

import org.primefaces.context.RequestContext;

import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@ViewScoped
@ManagedBean
public class ManageMajorsBean {

	
	private List<MajorDTO> majors;
	private MajorDTO addedMajor;
	private MajorDTO updatedMajor;
	private List<InstructorDTO> instructors;
	private BaseDTO addedConcentration;
	@ManagedProperty("#{IMajorsFacade}")
   	private IMajorsFacade majorFacades; 
	@ManagedProperty("#{IStudentCourseFacade}")
   	private IStudentCourseFacade insfacade; 
	@PostConstruct
	public void init()
	{
		addedMajor=new MajorDTO();
		updatedMajor=new MajorDTO();
		addedConcentration=new BaseDTO();
		
		fillMajorsList();
		instructors=new ArrayList<InstructorDTO>();
		fillInsList();
		
	}
	public void fillInsList()
	{
		instructors=insfacade.getAllInstructors();
	}

	private void fillMajorsList() {
		majors=new ArrayList<MajorDTO>();
		majors=majorFacades.getMajorsWithConcentrations();
		
	}
	public void preAddMajor()
	{
		addedMajor=new MajorDTO();
		RequestContext.getCurrentInstance().execute("addMajorDlg.show();");
		
		
	}
	public void preaddConcentration(MajorDTO major)
	{
		addedConcentration=new BaseDTO();
		addedConcentration.setFileNo(major.getId());
		RequestContext.getCurrentInstance().execute("addConDlg.show();");
		
		
	}
	public void preUpdateMajor(MajorDTO major)
	{
		addedMajor=new MajorDTO();
		setAddedMajor(major);
		RequestContext.getCurrentInstance().execute("addMajorDlg.show();");
		
	}
	public void addMajor(){
		
		if(addedMajor.getId()==null){//Adding case
		addedMajor=majorFacades.addMajor(getAddedMajor());
		if(addedMajor!=null&&addedMajor.getId()!=null)
		{fillMajorsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in adding");
		}
		else //Updating case
		{
			addedMajor=majorFacades.updateMajor(getAddedMajor());
			if(addedMajor!=null&&addedMajor.getId()!=null)
			{fillMajorsList();
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "updated Successfully");
			}
			else 
				JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in updating");
			
		}
	}
public void addConcentration(){
		addedConcentration=majorFacades.addConcentration(getAddedConcentration());
		if(addedConcentration!=null&&addedConcentration.getId()!=0)
		{
			fillMajorsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Added Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in adding");
		
	
	}
	public void deleteMajor(MajorDTO major){
		boolean b=majorFacades.deleteMajor(major);
		if(b)
		{fillMajorsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "deleted Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in deleting, this major has already been used by students , you can hide it only");
	}
	public void deleteConcentration(BaseDTO concentration){
		boolean b=majorFacades.deleteCocnentration(concentration);
		if(b)
		{
			fillMajorsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "deleted Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in deleting, this concentration has already been used by students , you can hide it only");
	}
	public void hideConcentration(BaseDTO concentration){
		boolean b=majorFacades.hideCocnentration(concentration);
		if(b)
		{
			fillMajorsList();
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "hidded Successfully");
		}
		else 
			JavaScriptMessagesHandler.RegisterNotificationMessage(null, "Error in deleting, this concentration has already been used by students , you can hide it only");
	}
	
	public List<MajorDTO> getMajors() {
		return majors;
	}

	public void setMajors(List<MajorDTO> majors) {
		this.majors = majors;
	}

	public MajorDTO getAddedMajor() {
		return addedMajor;
	}

	public void setAddedMajor(MajorDTO addedMajor) {
		this.addedMajor = addedMajor;
	}

	public MajorDTO getUpdatedMajor() {
		return updatedMajor;
	}

	public void setUpdatedMajor(MajorDTO updatedMajor) {
		this.updatedMajor = updatedMajor;
	}

	public IMajorsFacade getMajorFacades() {
		return majorFacades;
	}

	public void setMajorFacades(IMajorsFacade majorFacades) {
		this.majorFacades = majorFacades;
	}
	public List<InstructorDTO> getInstructors() {
		return instructors;
	}
	public void setInstructors(List<InstructorDTO> instructors) {
		this.instructors = instructors;
	}
	public IStudentCourseFacade getInsfacade() {
		return insfacade;
	}
	public void setInsfacade(IStudentCourseFacade insfacade) {
		this.insfacade = insfacade;
	}
	public BaseDTO getAddedConcentration() {
		return addedConcentration;
	}
	public void setAddedConcentration(BaseDTO addedConcentration) {
		this.addedConcentration = addedConcentration;
	}
	
}
