/**
 * 
 */
package main.com.zc.services.presentation.forms.graduationForm.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.domain.shared.enumurations.FormsStatusEnum;
import main.com.zc.services.domain.survey.model.Concentration;
import main.com.zc.services.presentation.configuration.dto.FormsStatusDTO;
import main.com.zc.services.presentation.configuration.facade.IFormsStatusFacade;
import main.com.zc.services.presentation.forms.graduationForm.dto.GraduationInformationDTO;
import main.com.zc.services.presentation.forms.graduationForm.facade.IGraduationInformationFacade;
import main.com.zc.services.presentation.forms.shared.facade.ISendEmailFacade;
import main.com.zc.services.presentation.shared.IMajorsFacade;
import main.com.zc.services.presentation.survey.DeclarationOfMajor.facade.IStudentDeclarationOfMajorFacade;
import main.com.zc.services.presentation.survey.declarationOfConcentration.facade.IDeclarationOfConcentrationFacade;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.BaseDTO;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@ManagedBean
@ViewScoped
public class GraduationInformationBean {

	private GraduationInformationDTO addedForm;
	private List<MajorDTO> majors;
	private List<BaseDTO> concentration;
	private List<String> city;
	private FormsStatusDTO form;
	
	
	@ManagedProperty("#{IGraduationInformationFacade}")
	private IGraduationInformationFacade faacde;
    
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	 @ManagedProperty("#{IMajorsFacade}")
	 private IMajorsFacade majorFacade;
	 
	 @ManagedProperty("#{IDeclarationOfConcentrationFacade}")
	 private IDeclarationOfConcentrationFacade concentrationFacade;
	
	 @ManagedProperty("#{IFormsStatusFacade}")
	 private IFormsStatusFacade formStatus;
	 
	 @ManagedProperty("#{ISendEmailFacade}") 
	 private ISendEmailFacade emailFacade;
	 
	 private BaseDTO concentrationSelected;
	@PostConstruct
	public void init(){
		addedForm=new GraduationInformationDTO();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!authentication.getPrincipal().equals("anonymousUser"))// logged in
		{
			 form=formStatus.getById(16);
			if(form.getStatus().equals(FormsStatusEnum.Active))
			{
			String mail = authentication.getName();
			if(mail.startsWith("S-")||mail.startsWith("s-")){
			PersonDataDTO student=(studentDataFacade.getPersonByPersonMail(mail));
			 fillMajors();
			 fillCities();
			GraduationInformationDTO old=new GraduationInformationDTO();
			old=faacde.getFormByStudentIDAndSemesterAndYear(student.getId(), form.getYear(), form.getSemester().getId());
			if(old!=null)
			{
				if(old.getId()!=null)
			{
				addedForm=old;
				concentrationSelected=concentrationFacade.getConcentrationById(getAddedForm().getConcentration().getId());
				
				concentration=concentrationFacade.getConcentrationsByMajor(getAddedForm().getMajor().getId());
			}
				else {
					StudentDTO studentDTO=new StudentDTO();
					studentDTO.setFacultyId(student.getFileNo());
					studentDTO.setId(student.getId());
					studentDTO.setName(student.getNameInEng());
					studentDTO.setMail(mail);
					addedForm.setStudent(studentDTO);
				
				}
			}
			else 
			{
				StudentDTO studentDTO=new StudentDTO();
				studentDTO.setFacultyId(student.getFileNo());
				studentDTO.setId(student.getId());
				studentDTO.setName(student.getNameInEng());
				studentDTO.setMail(mail);
				addedForm.setStudent(studentDTO);
				
			}
		fillStringsById();
			}
			}

			
		}
	}
	public void fillMajors(){
		majors=new ArrayList<MajorDTO>();
		majors=majorFacade.getAll();
		for(int i=0;i<majors.size();i++)
		{
			if(majors.get(i).getId()==9)
			{
				majors.remove(i);
			}
		}
	}
	public void fillConcentrartionLst()
	{
		concentration=concentrationFacade.getConcentrationsByMajor(getAddedForm().getMajor().getId());
			 
	}
	public void fillCities(){
	
			city = new ArrayList<String>();
	        city.add("Alexandria");
	        city.add("Assiut");
	        city.add("Aswan");
	        city.add("Al Beheira");
	        city.add("Beni Suef");
	        city.add("Cairo");
	        city.add("Al Daqahliyya");
	        city.add("Damietta");
	        city.add("Fayoum");
	        city.add("Al Gharbiyah");
	        city.add("Al Giza");
	        city.add("Ismailiya");
	        city.add("Kafr El Sheikh");
	        city.add("Luxor");
	        city.add("Al Minya");
	        city.add("Marsa Matrouh");
	        city.add("Al Monufiyya");
	        city.add("North Sinai");
	        city.add("New Valley");
	       city.add("Port Said");
	        city.add("Qena");
	        city.add("Qalubiya");
	        city.add("Red Sea");
	        city.add("Sohag");
	        city.add("Al Sharqiyah");
	        city.add("South Sinai");
	        city.add("Suez");
	        city.add("Other Country");
	        
	}
	public void submit(){
		
		getAddedForm().setSemester(form.getSemester().getId());
		getAddedForm().setYear(form.getYear());
		GraduationInformationDTO dto;
		concentrationSelected=concentrationFacade.getConcentrationById(getAddedForm().getConcentration().getId());
		
		getAddedForm().setConcentration((getAddedForm().getConcentration().getId()==0)?null:getAddedForm().getConcentration());
		if(getAddedForm().getId()!=null)
		{
			if(getAddedForm().getId()!=0)
				{
				getAddedForm().setUpdateDate(Calendar.getInstance());
				 dto=faacde.update(getAddedForm());
				 addedForm=dto;
				}
			else 
				{
				getAddedForm().setDate(Calendar.getInstance());
				dto=faacde.add(getAddedForm());
				addedForm=dto;
				}
		}
		else 
			{
			getAddedForm().setDate(Calendar.getInstance());
			dto=faacde.add(getAddedForm());
			addedForm=dto;
			}
		
		
		
		
		
		if(getAddedForm()!=null)
		{
			 FacesContext.getCurrentInstance().getPartialViewContext()
	 			.getRenderIds().add("mainForm:printForm:feedbackformPanel");
			if(getAddedForm().getId()!=null)
			{
				JavaScriptMessagesHandler.RegisterErrorMessage(null, "The form is submitted successfully");
				fillStringsById();
				//TODO Send emails and display confirm message 
				ArrayList<String> ccLst=new ArrayList<String>();
				ccLst.add(getAddedForm().getStudent().getMail());
				//ccLst.add("omnya3ala2@gmail.com");
				ArrayList<String> toLst=new ArrayList<String>();
				//toLst.add(Constants.ADMISSION_DEPT);
				toLst.add(Constants.GRADUATION_FORM_EMAIL);
				//toLst.add("oalaaeddin@zewailcity.edu.eg");
				String htmlText =
						  "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
						+ "<ul style=margin:0;padding:0;>"
						+ "<li style=list-style:none;float:left;width:700px;margin:0;>"
						+ "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
						+ "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
						+ "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
						+ "</ul>"
						+ "</li>"
						+ "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
						+ "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
						+ "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
						+ "</li>"
						+ "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
						+ "<div style=padding:24px 36px;color:#676767 !important;>"
						+ "<span>Dear "
						+ "Admission and Registeration Department"
						+ ",</span><br/><br/>"
						+"We would like to inform you that student <b>"+getAddedForm().getStudent().getName()+"</b> has submitted his/her graduation form."
						+"<br/>Stundet ID : "+getAddedForm().getStudent().getFacultyId()
						+"<br/>Stundet Email : "+getAddedForm().getStudent().getMail()
						+"<br/>Major : "+getAddedForm().getMajor().getMajorName()
						+"<br/>Concentration : "+concentrationSelected.getName()
						+"<br/><br/>"
						+ "</div>"
						+ "</li>"
						+ "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
						+ "<ul style=margin:0;padding:0;>"
						+ "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
						+ "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
						+ "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
						+ "</li>"
						+ "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
						+ "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
						+ "</li>"
						+ "</ul>"
						+ "</li>"
						+ "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
						+ "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
						+ "<span style=color:#a1a0a0;font-size:11px;>Please do not reply directly to this email. If you have any questions or feedback, please send to </span><a href=mailto:contacts@zclt.info style=color:#00A7A6;fntsize:11px;>contacts@zclt.info</a>"
						+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
						+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
						+ "</div>" + "</li>" + "</ul>" + "</div>";
			
				String subject="Graduation Form - "+getAddedForm().getStudent().getName()+"-"+getAddedForm().getYear();
				emailFacade.sendMail(toLst, ccLst, null, htmlText, subject);
			}
			RequestContext.getCurrentInstance().execute("confirmDlg.show()");
			
			 
		}
	}
	public void fillStringsById(){
		try{
			for(int i=0;i<majors.size();i++)
		
		{
			if(majors.get(i).getId().equals(getAddedForm().getMajor().getId()))
					{
						getAddedForm().getMajor().setMajorName(majors.get(i).getMajorName());
					}
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			for(int i=0;i<majors.size();i++)
		
		{
			if(majors.get(i).getId().equals(getAddedForm().getMinor().getId()))
			{
				getAddedForm().getMinor().setMajorName(majors.get(i).getMajorName());
			}
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	try{
		for(int i=0;i<concentration.size();i++)
	
		{
			if(concentration.get(i).getId()==getAddedForm().getMinor().getId())
			{
				getAddedForm().getConcentration().setName(concentration.get(i).getName());
			}
		}
		if(!city.contains(getAddedForm().getBirthPlace())){
			city.add(getAddedForm().getBirthPlace());
		}
	}
	catch(Exception ex){
		ex.printStackTrace();
	}
	}
	
	public GraduationInformationDTO getAddedForm() {
		return addedForm;
	}

	public void setAddedForm(GraduationInformationDTO addedForm) {
		this.addedForm = addedForm;
	}

	public IGraduationInformationFacade getFaacde() {
		return faacde;
	}

	public void setFaacde(IGraduationInformationFacade faacde) {
		this.faacde = faacde;
	}

	public List<MajorDTO> getMajors() {
		return majors;
	}

	public void setMajors(List<MajorDTO> majors) {
		this.majors = majors;
	}


	public List<BaseDTO> getConcentration() {
		return concentration;
	}

	public void setConcentration(List<BaseDTO> concentration) {
		this.concentration = concentration;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	/*public IStudentDeclarationOfMajorFacade getMajorFacade() {
		return majorFacade;
	}
	public void setMajorFacade(IStudentDeclarationOfMajorFacade majorFacade) {
		this.majorFacade = majorFacade;
	}*/
	public IDeclarationOfConcentrationFacade getConcentrationFacade() {
		return concentrationFacade;
	}
	public void setConcentrationFacade(IDeclarationOfConcentrationFacade concentrationFacade) {
		this.concentrationFacade = concentrationFacade;
	}
	public List<String> getCity() {
		return city;
	}
	public void setCity(List<String> city) {
		this.city = city;
	}
	public IFormsStatusFacade getFormStatus() {
		return formStatus;
	}
	public void setFormStatus(IFormsStatusFacade formStatus) {
		this.formStatus = formStatus;
	}
	public FormsStatusDTO getForm() {
		return form;
	}
	public void setForm(FormsStatusDTO form) {
		this.form = form;
	}
	public ISendEmailFacade getEmailFacade() {
		return emailFacade;
	}
	public void setEmailFacade(ISendEmailFacade emailFacade) {
		this.emailFacade = emailFacade;
	}
	public IMajorsFacade getMajorFacade() {
		return majorFacade;
	}
	public void setMajorFacade(IMajorsFacade majorFacade) {
		this.majorFacade = majorFacade;
	}
	public BaseDTO getConcentrationSelected() {
		return concentrationSelected;
	}
	public void setConcentrationSelected(BaseDTO concentrationSelected) {
		this.concentrationSelected = concentrationSelected;
	}

	
}
