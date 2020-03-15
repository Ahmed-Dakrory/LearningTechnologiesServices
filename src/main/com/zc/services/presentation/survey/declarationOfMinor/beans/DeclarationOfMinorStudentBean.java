/**
 * 
 */
package main.com.zc.services.presentation.survey.declarationOfMinor.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import main.com.zc.services.domain.survey.model.OfficialMajor;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.declarationOfMinor.facade.IDeclarationOfMinorFacade;
import main.com.zc.services.presentation.survey.intendedMajor.dto.IntendedMajorSurveyDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author omnya
 *
 */
@ManagedBean(name="DeclarationOfMinorStudentBean")
@ViewScoped
public class DeclarationOfMinorStudentBean {

	 private List<SelectItem> concentrationLst;
	 private Integer selectedMinor;  
	 private String mobile;
     private boolean updateMood;
	 private boolean renderInputs;
	 private String selectedMinorName;
	 private IntendedMajorSurveyDTO studentMinor;
	 private List<BaseDTO> concentrationsDTOLst;
	 
	 @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
		private IGetLoggedInStudentDataFacade studentDataFacade;
	 
	 @ManagedProperty("#{IDeclarationOfMinorFacade}")
		private IDeclarationOfMinorFacade facade;
	 
	 @ManagedProperty("#{IMajorsFacade}")
		private IMajorsFacade majorFacade;
	 
	@PostConstruct
	public void init()
	{
		renderInputs=true;
		concentrationLst = new ArrayList<SelectItem>();
		   
		fillConcentrartionLst();
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				
					studentMinor=facade.getByStudentID(studentDataFacade.getPersonByPersonMail(mail).getId());
						
					if(studentMinor!=null) {
					if(studentMinor.getId()!=null)
					updateMood=true;
					else 
						updateMood= false;
					}else {
						updateMood= false;
					}
	
			}}}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void fillConcentrartionLst()
	{
		List<BaseDTO> concentrations=new ArrayList<BaseDTO>();
		//concentrations=facade.getAllMinors();
		   List<MajorDTO> majors=new ArrayList<MajorDTO>();
		   majors=majorFacade.getAvailableOnly();
		   
		   for(int i=0;i<majors.size();i++)
		   {
			   concentrations=facade.getMinorsByMajor(majors.get(i).getId());
			   if(concentrations.size()>0){
				SelectItemGroup g1 = new SelectItemGroup(majors.get(i).getMajorName());
				List<SelectItem> items=new ArrayList<SelectItem>();
				for(int j=0;j<concentrations.size();j++)
				{
					/*if(concentrations.get(j).getFileNo()!=null)
					{
					if(concentrations.get(j).getFileNo()==concentrations.get(i).getId())
					{*/
						items.add(new SelectItem((Integer)concentrations.get(j).getId(), concentrations.get(j).getName()));
					/*}
					}*/
				}
				SelectItem[] array =new SelectItem[items.size()];
				for(int j=0;j<items.size();j++){
					array[j]=items.get(j);
				}
				g1.setSelectItems(array);
				concentrationLst.add(g1);
			   }
				
			  
		   }
/*		for(int i=0;i<concentrations.size();i++)
		{
			if(concentrations.get(i).getFileNo()==null)
			{
				SelectItemGroup g1 = new SelectItemGroup(concentrations.get(i).getName());
				List<SelectItem> items=new ArrayList<SelectItem>();
				for(int j=0;j<concentrations.size();j++)
				{
					if(concentrations.get(j).getFileNo()!=null)
					{
					if(concentrations.get(j).getFileNo()==concentrations.get(i).getId())
					{
						items.add(new SelectItem((Integer)concentrations.get(j).getId(), concentrations.get(j).getName()));
					}
					}
				}
				SelectItem[] array =new SelectItem[items.size()];
				for(int j=0;j<items.size();j++){
					array[j]=items.get(j);
				}
				g1.setSelectItems(array);
				concentrationLst.add(g1);
			}
			
		        
		    
		}*/
		/*SelectItemGroup g1 = new SelectItemGroup("Biomedical Sciences");
        g1.setSelectItems(new SelectItem[]
        	   {new SelectItem((Integer)1, "Computational Biology and Genomics Minor"),
        		new SelectItem((Integer)2, "Molecular Cell Biology Minor"), 
        		new SelectItem((Integer)3, "Drug Design and Development Minor")});
                new SelectItem((Integer)4,"Medical Science Minor");
        SelectItemGroup g2 = new SelectItemGroup("PYSICS OF Earth and Universe");
        g2.setSelectItems(new SelectItem[] 
        		{new SelectItem((Integer)5, "Astrophysics Minor"),
        		new SelectItem((Integer)6, "High Energy Physics Minor"), 
        		new SelectItem((Integer)7, "Earth and Atmospheric Sciences Minor")});
         
        concentrationLst = new ArrayList<SelectItem>();
        concentrationLst.add(g1);
        concentrationLst.add(g2);
	*/
       /* concentrationsDTOLst=new ArrayList<BaseDTO>();
        concentrationsDTOLst.add(new BaseDTO(1,"Computational Biology and Genomics Minor"));
        concentrationsDTOLst.add(new BaseDTO(2,"Molecular Cell Biology Minor"));
        concentrationsDTOLst.add(new BaseDTO(3,"Drug Design and Development Minor"));
        concentrationsDTOLst.add(new BaseDTO(4,"Medical Science Minor"));
        concentrationsDTOLst.add(new BaseDTO(5,"Astrophysics Minor"));
        concentrationsDTOLst.add(new BaseDTO(6,"High Energy Physics Minor"));
        concentrationsDTOLst.add(new BaseDTO(7,"Earth and Atmospheric Sciences Minor"));*/
	}


	public void submit()
	{
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")||StringUtils.isNumeric(mail.substring(0, 4))){
				/*if(Integer.toString(studentDataFacade.getPersonByPersonMail(mail).getFileNo()).startsWith("2014")||Integer.toString(studentDataFacade.getPersonByPersonMail(mail).getFileNo()).startsWith("2013"))
				{*/
					IntendedMajorSurveyDTO dto=new IntendedMajorSurveyDTO();
					if(studentMinor!=null) {
					if(getStudentMinor().getId()!=null)
					{
						dto=getStudentMinor();
						dto.setMobile(getMobile());
						BaseDTO con=new BaseDTO(getSelectedMinor());
						dto.setConcentration(con);
						dto.setState(OfficialMajor.STATE_Waiting);
						dto=facade.update(dto);
					}
					else {
				
				dto.setMobile(getMobile());
				BaseDTO con=new BaseDTO(getSelectedMinor());
				dto.setConcentration(con);
			
			    
				StudentDTO student=new StudentDTO();
				student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
				dto.setStudent(student);
				dto.setState(OfficialMajor.STATE_Waiting);
				dto=facade.submit(dto);
					}
					}else {
						
						
						dto.setMobile(getMobile());
						BaseDTO con=new BaseDTO(getSelectedMinor());
						dto.setConcentration(con);
					
					    
						StudentDTO student=new StudentDTO();
						student.setId(studentDataFacade.getPersonByPersonMail(mail).getId());
						dto.setStudent(student);
						dto.setState(OfficialMajor.STATE_Waiting);
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
				/*}
				else 
				{
					JavaScriptMessagesHandler.RegisterErrorMessage(null,"Allowed only for first year students" );
				}*/
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
		setSelectedMinor(getStudentMinor().getConcentration().getId());
		setMobile(getStudentMinor().getMobile());
		getMinorByID(getSelectedMinor());
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("mainForm:form:mainPanel:inputs");
		FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("mainForm:form:updatePanel:updatePnl");
		
	}

	public void getMinorByID(Integer concentrationID)
	{
		try{
		BaseDTO concen=new BaseDTO();
		concen=facade.getMinorById(concentrationID);
		setSelectedMinorName(concen.getName());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		/*for(int i=0;i<concentrationsDTOLst.size();i++)
		{
			if(((Integer)concentrationsDTOLst.get(i).getId()).equals(concentrationID))
			{
				setSelectedMinorName(concentrationsDTOLst.get(i).getName());
				break;
			}
		}*/
	}

	public List<SelectItem> getMinorLst() {
		return concentrationLst;
	}

	public void setMinorLst(List<SelectItem> concentrationLst) {
		this.concentrationLst = concentrationLst;
	}

	public Integer getSelectedMinor() {
		return selectedMinor;
	}


	public void setSelectedMinor(Integer selectedMinor) {
		this.selectedMinor = selectedMinor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public IntendedMajorSurveyDTO getStudentMinor() {
		return studentMinor;
	}

	public void setStudentMinor(IntendedMajorSurveyDTO studentMinor) {
		this.studentMinor = studentMinor;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public IDeclarationOfMinorFacade getFacade() {
		return facade;
	}

	public void setFacade(IDeclarationOfMinorFacade facade) {
		this.facade = facade;
	}

	public String getSelectedMinorName() {
		if(getStudentMinor()!=null)
		getMinorByID(getStudentMinor().getConcentration().getId());
		
		return selectedMinorName;
	}

	public void setSelectedMinorName(String selectedMinorName) {
		this.selectedMinorName = selectedMinorName;
	}

	public List<BaseDTO> getMinorsDTOLst() {
		return concentrationsDTOLst;
	}

	public void setMinorsDTOLst(List<BaseDTO> concentrationsDTOLst) {
		this.concentrationsDTOLst = concentrationsDTOLst;
	}

	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}

	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}

	
}
