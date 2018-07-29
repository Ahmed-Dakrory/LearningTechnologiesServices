/**
 * 
 */
package main.com.zc.services.presentation.survey.intendedMajor.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.survey.intendedMajor.facade.IStudentIntendedMajorFacade;
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
@ManagedBean(name="StudentIntendedMajorBean")
@ViewScoped
public class StudentIntendedMajorBean {

	@ManagedProperty("#{IStudentIntendedMajorFacade}")
	private IStudentIntendedMajorFacade facade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;

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
							studentMajor=facade.getByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
						
					
					if(studentMajor.getId()!=null)
					updateMood=true;
					else 
						updateMood= false;
						
					 fillMajors();
					
					 majorsLst=new ArrayList<SelectItem>();
					 SelectItemGroup g1 = new SelectItemGroup("Science Majors");
				        g1.setSelectItems(new SelectItem[]
				        		{new SelectItem(majors.get(0).getId(),majors.get(0).getMajorName()), 
				        		new SelectItem(majors.get(1).getId(), majors.get(1).getMajorName()), 
				        		new SelectItem(majors.get(2).getId(), majors.get(2).getMajorName()),
				        		new SelectItem(majors.get(3).getId(), majors.get(3).getMajorName())});
				         
				        SelectItemGroup g2 = new SelectItemGroup("Engineering Majors");
				        g2.setSelectItems(new SelectItem[] 
				        		{new SelectItem(majors.get(4).getId(),majors.get(4).getMajorName()), 
				        		new SelectItem(majors.get(5).getId(), majors.get(5).getMajorName()), 
				        		new SelectItem(majors.get(6).getId(), majors.get(6).getMajorName()),
				        		new SelectItem(majors.get(7).getId(), majors.get(7).getMajorName())});
				         
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
		majors=facade.getMajors();
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

	public IStudentIntendedMajorFacade getFacade() {
		return facade;
	}

	public void setFacade(IStudentIntendedMajorFacade facade) {
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
	public IntendedMajorSurveyDTO getStudentMajor() {
   
		
		return studentMajor;
	}
	public boolean isRenderInputs() {
		return renderInputs;
	}
	public void setRenderInputs(boolean renderInputs) {
		this.renderInputs = renderInputs;
	}

	
	
}
