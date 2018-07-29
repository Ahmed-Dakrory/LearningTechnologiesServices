/**
 * 
 */
package main.com.zc.services.presentation.survey.DeclarationOfMajor.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.DeclarationOfMajor.facade.IStudentDeclarationOfMajorFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="StudentDeclarationOfMajorBean")
@ViewScoped
public class StudentDeclarationOfMajorBean {

	@ManagedProperty("#{IStudentDeclarationOfMajorFacade}")
	private IStudentDeclarationOfMajorFacade facade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;

	@ManagedProperty("#{IMajorsFacade}")
	private IMajorsFacade majorFacade;
	
	
	private String mobile;
	private Integer selectedMajor;
	private List<MajorDTO> majors;
	private IntendedMajorSurveyDTO studentMajor;
	private List<SelectItem> majorsLst;
	private boolean updateMood;
	private boolean renderInputs;
	
	@PostConstruct
	public void init()
	{
		renderInputs=true;
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
				

								//studentMajor=facade.getByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
				
				studentMajor=facade.getByStudentIDAndYearAndSemester(studentDataFacade.getPersonByPersonMail(mail).getId());
					
					if(studentMajor.getId()!=null)
					updateMood=true;
					else 
						updateMood= false;
						
					 fillMajors();
					
					List<MajorDTO> scienceMajor=new ArrayList<MajorDTO>();
					 List<MajorDTO> engineeringMajor=new ArrayList<MajorDTO>();
					 for(MajorDTO major:majors){
						 if(major.getType()!=null)
						 {
							 if(major.getType()==1)//Science
							 {
								 scienceMajor.add(major);
							 }
							 else if(major.getType()==2)//engineering
							 {
								 engineeringMajor.add(major);
							 }
						 }
					 }
					 SelectItem[] scienceMajorArray = new SelectItem[scienceMajor.size()];
					 SelectItem[] engineeringMajorArray = new SelectItem[engineeringMajor.size()];
					for(int i=0;i<scienceMajor.size();i++){
						scienceMajorArray[i]=new SelectItem(scienceMajor.get(i).getId(),scienceMajor.get(i).getMajorName());
					}
					for(int i=0;i<engineeringMajor.size();i++){
						engineeringMajorArray[i]=new SelectItem(engineeringMajor.get(i).getId(),engineeringMajor.get(i).getMajorName());
					}
					 SelectItemGroup g1 = new SelectItemGroup("Science Majors");
				        g1.setSelectItems(scienceMajorArray);
				         
				        SelectItemGroup g2 = new SelectItemGroup("Engineering Majors");
				        g2.setSelectItems(engineeringMajorArray);
				         
				        majorsLst = new ArrayList<SelectItem>();
				        majorsLst.add(g1);
				        majorsLst.add(g2);
				
			}}}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}
	public void fillMajors()
	{
		majors=new ArrayList<MajorDTO>();
		//majors=facade.getMajors();
		majors=majorFacade.getAvailableOnly();
	}
	

	public void submit()
	{
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
			
					IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
					if(getStudentMajor().getId()!=null)
					{
						dto=getStudentMajor();
						dto.setMobile(getMobile());
						MajorDTO major=new MajorDTO();
						major.setId(getSelectedMajor());
						dto.setMajor(major);
						dto=facade.update(dto);
					}
					else {
				
				dto.setMobile(getMobile());
				MajorDTO major=new MajorDTO();
				major.setId(getSelectedMajor());
				dto.setMajor(major);
				StudentDTO student=new StudentDTO();
				student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
				dto.setStudent(student);
				dto=facade.submit(dto);
					}
				if(dto!=null)
				{
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,"Form has been submitted successfully" );
					init();
				}
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can not Be Submitted");
				}
			
			
			}
		}
		
		}
		catch(Exception ex)
		{
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Form Can not Be Submitted");
			ex.printStackTrace();
		}
	}
	
	public void preUpdate()
	{
        renderInputs=true;
        updateMood=false;
		setSelectedMajor(getStudentMajor().getMajor().getId());
		setMobile(getStudentMajor().getMobile());
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("mainForm:form:mainPanel:inputs");
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("mainForm:form:updatePanel:updatePnl");
		
	}
	
	public IStudentDeclarationOfMajorFacade getFacade() {
		return facade;
	}
	public void setFacade(IStudentDeclarationOfMajorFacade facade) {
		this.facade = facade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getSelectedMajor() {
		return selectedMajor;
	}
	public void setSelectedMajor(Integer selectedMajor) {
		this.selectedMajor = selectedMajor;
	}
	public List<MajorDTO> getMajors() {
		return majors;
	}
	public void setMajors(List<MajorDTO> majors) {
		this.majors = majors;
	}
	public IntendedMajorSurveyDTO getStudentMajor() {
		return studentMajor;
	}
	public void setStudentMajor(IntendedMajorSurveyDTO studentMajor) {
		this.studentMajor = studentMajor;
	}
	public List<SelectItem> getMajorsLst() {
		return majorsLst;
	}
	public void setMajorsLst(List<SelectItem> majorsLst) {
		this.majorsLst = majorsLst;
	}
	public boolean isUpdateMood() {
		return updateMood;
	}
	public void setUpdateMood(boolean updateMood) {
		this.updateMood = updateMood;
	}
	public boolean isRenderInputs() {
		return renderInputs;
	}
	public void setRenderInputs(boolean renderInputs) {
		this.renderInputs = renderInputs;
	}
	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}
	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}
	
	
}
