/**
 * 
 */
package main.com.zc.services.presentation.forms.gap_form;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.service.repository.heads.HeadsAppServiceImpl;
import main.com.zc.services.presentation.accountSetting.facade.impl.StudentProfileFacadeImpl;
import main.com.zc.services.presentation.forms.gap_form.implementation.gap_formAppServiceImpl;
import main.com.zc.services.presentation.shared.facade.impl.CouresFacadeImpl;
import main.com.zc.services.presentation.shared.facade.impl.MajorsFacadeImpl;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.impl.StudentFacadeImpl;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author omnya
 *
 */
@ManagedBean(name="gap_formBean")
@ViewScoped
public class gap_formBean {

	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
   	private IGetLoggedInInstructorData getInsDataFacade;
   	
    @ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
    private IGetLoggedInStudentDataFacade studentDataFacade;
    
    @ManagedProperty("#{IStudentFacade}")
    private StudentFacadeImpl studentFacadeImpl;
    

    @ManagedProperty("#{IStudentProfileFacade}")
	private StudentProfileFacadeImpl facade;
    
    @ManagedProperty("#{IMajorsFacade}")
	private MajorsFacadeImpl majorfacade;
    
    @ManagedProperty("#{ICouresFacade}")
	private CouresFacadeImpl coursesfacade;
    
    @ManagedProperty("#{gap_formFacadeImpl}")
	private gap_formAppServiceImpl cccAppServiceImpl;
    
    

	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades;
    
    private StudentDTO student;
    private MajorDTO major;
    private List<CoursesDTO> coursesDTOsTaken;
    private List<CoursesDTO> coursesDTOsAll;
    private List<gap_form> courseChangeComfirmations;
    private int studentId;
    

    
    private int step;

    private gap_form newCourseComfirmation;
    

    private gap_form selectedgap_form;
    
	@PostConstruct
	public void init()
	{
HttpServletRequest origRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		
		try{

			Integer id=Integer.parseInt(origRequest.getParameter("id"));
			
				if(id!=null){
					studentId=id;
					student=studentFacadeImpl.getById(studentId);
					newCourseComfirmation=new gap_form();
					courseChangeComfirmations=cccAppServiceImpl.getByStudentId(studentId);
					student.setStudentProfileDTO(facade.getCurrentPRofileByStudentID(studentId));
					
					major=majorfacade.getById(student.getStudentProfileDTO().getMajor().getId());
					//coursesDTOsTaken=coursesfacade.getCoursesByStudentID(student.getId());
					coursesDTOsTaken=coursesfacade.getAll();
					coursesDTOsAll=coursesfacade.getAll();
					System.out.println("Ahmed Dakrory: "+String.valueOf(major.getType()));
				}

				
				
			}
		catch(Exception ex){
			 
		}
		
		
		try{

		Integer idgap_form=Integer.parseInt(origRequest.getParameter("c"));
		
		if(idgap_form!=null) {
			selectedgap_form=cccAppServiceImpl.getById(idgap_form);
			
			
			studentId=selectedgap_form.getStudentId().getId();
			student=studentFacadeImpl.getById(studentId);
			student.setStudentProfileDTO(facade.getCurrentPRofileByStudentID(studentId));
			
			major=majorfacade.getById(student.getStudentProfileDTO().getMajor().getId());
		}
		
		}catch(Exception ex){
			 
		}
		
	
	}
	
	
	public void addgap_form(){
		step=0;
		Student studentNew=new Student();
		studentNew.setId(studentId);
		newCourseComfirmation.setStudentId(studentNew);
		newCourseComfirmation.setFormStep(step);
		newCourseComfirmation.setMajorId(major.getId());
		newCourseComfirmation.setAction(0);
		newCourseComfirmation.setSubmissionDate(Calendar.getInstance());
		newCourseComfirmation.setLastUpdateDate(Calendar.getInstance());
		cccAppServiceImpl.addgap_form(newCourseComfirmation);
		
		
		
		//Registerar
		Heads registerar= headFacades.getByType(Heads.REGISTRAR_STAFF);
		
		sendEmailForStudent(student.getName(),student.getMail(),"Please Check your dashboard for a New Gap Form");
		sendEmailForStudent(registerar.getHeadPersonId().getName(),registerar.getHeadPersonId().getMail(),"Please Check your dashboard for a New Gap Form");
		
		
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Gap form requist was added successfully");

		courseChangeComfirmations=cccAppServiceImpl.getByStudentId(studentId);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("addCourseComfirmationDlg.hide();");
		newCourseComfirmation=new gap_form();
	}
	
	public void sendEmailForStudent(String name,String mail, String string) {
		 String from = "learningtechnologies@zewailcity.edu.eg";
	        String pass = "DELF-651984@dr";
	        String[] to = {mail }; // list of recipient email addresses 
	        String subject = "Gap Form New Message";
	        String htmlText = "<div style=\"width:700px;margin:0\" auto;font:normal=\"\" 13px=\"\" 30px=\"\" segoe,=\"\" segoe=\"\" ui,=\"\" dejavu=\"\" sans,=\"\" trebuchet=\"\" ms,=\"\" verdana,=\"\" sans-serif=\"\" !important;=\"\">\n" + 
	        		"					<ul style=\"margin:0;padding:0;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin:0;\">\n" + 
	        		"						<ul style=\"margin:0;padding:0;width:700px;margin-top:18px;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:260px;padding:0;\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/zewailLogo.png.xhtml?ln=Copy%20of%20images\" alt=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\" style=\"\n" + 
	        		"    width: 77px;\n" + 
	        		"\"></li>\n" + 
	        		"					<li style=\"list-style:none;float:right;width:121px;margin-right: 55px;padding:0;\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/welocome_logo.png.xhtml?ln=Copy%20of%20images\" alt=\"Center\" for=\"\" learning=\"\" technologies=\"\" style=\"margin-top:4px;width: 170px;\"></li>\n" + 
	        		"					</ul>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom: 25px;background:#f1f2f2;\" 0=\"\" 24px=\"\" 0;padding:1px=\"\" 0;=\"\">&nbsp;</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;\">\n" + 
	        		"					<h2 style=\"margin:0;padding:0;color:#404040\" !important;=\"\">Learning Technologies Services</h2>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;\">\n" + 
	        		"					<div style=\"padding:24px\" 36px;color:#676767=\"\" !important;=\"\">\n" + 
	        		"					<span style=\"color:#676767\">Dear \n" + 
	        		"					"+name+"\n" + 
	        		"					,</span><br><br><br>\n" + 
	        		"					<span style=\"color:#676767\">"+string+"</span><br><br><br>\n" + 
	        		"					<a href='https://lts.zewailcity.edu.eg/LearningTechnologiesServices/pages/public/login.xhtml' style=\"color:#237cd8\">Login Now</a><br><br><br>\n" + 
	        		"					<br><br>\n" + 
	        		"					<span style=\"color:#676767\">Thank you, </span><br><br>\n" + 
	        		"					<span style=\"color:#676767\">Center for Learning Technologies</span>\n" + 
	        		"					</div>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;\">\n" + 
	        		"					<ul style=\"margin:0;padding:0;\">\n" + 
	        		"					<li style=\"list-style:none;float:left;width:134px;margin:0;padding:18px\" 36px=\"\" !important;color:#717070;=\"\">\n" + 
	        		"					<a href=\"http://www.zclt.info/\" title=\"Center\" for=\"\" learning=\"\" technologies=\"\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/welocome_logo.png.xhtml?ln=Copy%20of%20images\" alt=\"Center\" for=\"\" learning=\"\" technologies=\"\" style=\"\n" + 
	        		"    width: 139px;\n" + 
	        		"\"></a><br>\n" + 
	        		"					<span style=\"color:#404040;font-size:11px;\">Giving Fuel to Innovation</span>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:right;padding: 24px;\" 36px=\"\" !important;color:#717070;=\"\">\n" + 
	        		"					<a href=\"http://www.zewailcity.edu.eg/\" title=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\"><img src=\"https://lts.zewailcity.edu.eg/LearningTechnologiesServices/javax.faces.resource/zewailLogo.png.xhtml?ln=Copy%20of%20images\" alt=\"Zewail\" city=\"\" of=\"\" science=\"\" and=\"\" technology=\"\" style=\"\n" + 
	        		"    width: 66px;\n" + 
	        		"\"></a>\n" + 
	        		"					</li>\n" + 
	        		"					</ul>\n" + 
	        		"					</li>\n" + 
	        		"					<li style=\"list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;\">\n" + 
	        		"					<div style=\"padding:8px\" 16px;color:#a1a0a0;font-size:11px;line-height:20px;=\"\">\n" + 
	        		"					 <br><b><span style=\"color:#a1a0a0;font-size:11px;\">Follow us:</span></b><a href=\"https://www.facebook.com/learning.technologies.zewailcity\" title=\"ZC\" lt=\"\" facebook=\"\" style=\"\n" + 
	        		"\"><img src=\"https://static.xx.fbcdn.net/rsrc.php/yo/r/iRmz9lCMBD2.ico\" alt=\"ZC\" lt=\"\" facebook=\"\" style=\"vertical-align:middle;width: 21px;margin-left: 13px;\"></a>\n" + 
	        		"					  <a href=\"https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A\" title=\"ZC\" lt=\"\" youtube=\"\"><img src=\"https://s.ytimg.com/yts/img/favicon_144-vfliLAfaB.png\" alt=\"ZC\" lt=\"\" youtube=\"\" style=\"vertical-align:middle;width: 23px;margin-left: 14px;\"></a>\n" + 
	        		"					</div> </li> </ul> </div>";

	        System.out.println("Email Sent To: "+name+" With Mail: "+mail);
	        sendFromGMail(from, pass, to, subject, htmlText);
	        
	
	}

	 private static void sendFromGMail(final String from, final String pass, final String[] to, final String subject, final String body) {
		 new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Properties props = System.getProperties();
			        String host = "smtp.gmail.com";
			        props.put("mail.smtp.starttls.enable", "true");
			        props.put("mail.smtp.host", host);
			        props.put("mail.smtp.user", from);
			        props.put("mail.smtp.password", pass);
			        props.put("mail.smtp.port", "587");
			        props.put("mail.smtp.auth", "true");

			        Session session = Session.getDefaultInstance(props);
			        MimeMessage message = new MimeMessage(session);

			        try {
			            message.setFrom(new InternetAddress(from));
			            InternetAddress[] toAddress = new InternetAddress[to.length];

			            // To get the array of addresses
			            for( int i = 0; i < to.length; i++ ) {
			                toAddress[i] = new InternetAddress(to[i]);
			            }

			            for( int i = 0; i < toAddress.length; i++) {
			                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			            }

			            message.setSubject(subject);
			            message.setText(body);

			    		message.setContent(body, "text/html; charset=ISO-8859-1");
			            Transport transport = session.getTransport("smtp");
			            transport.connect(host, from, pass);
			            transport.sendMessage(message, message.getAllRecipients());
			            transport.close();
			        }
			        catch (AddressException ae) {
			            ae.printStackTrace();
			        }
			        catch (MessagingException me) {
			            me.printStackTrace();
			        }
			}
		}).start();
	       
	    }
	 
	 
	
	public void deletegap_form(int Id){
		/*preRequisitFacade.delete(preRequisitFacade.getById(Id));
		courseSyllabusCollection.setPre_Requisites(preRequisitFacade.getByCourseId(courseId));
		*/
		cccAppServiceImpl.delete(cccAppServiceImpl.getById(Id));
		courseChangeComfirmations=cccAppServiceImpl.getByStudentId(studentId);
		JavaScriptMessagesHandler.RegisterErrorMessage(null, "Gap form requist was Deleted successfully");

		System.out.println("Deleted Item: "+String.valueOf(Id));
	}
	
	public String getCourseCountAsString(int countAs) {
		switch(countAs){
		case 0:
			return "Major Req";
		case 1:
			return "Humanities";
		case 2:
			return "Major Elective";
		case 3:
			return "Collateral Req";
			
		}
		return "";
	}
	
	public String getMajorType(int type) {
		switch(type){
		case 1:
			return "Engineering";
		case 2:
			return "Science";
			
		}
		return "";
	}

	public void onRowSelect(SelectEvent event) {  
	  	try {
	  		selectedgap_form = (gap_form) event.getObject();
	  		

			System.out.println("Ahmed Dakrory new: "+String.valueOf(selectedgap_form.getStudentId().getId()));
	  		try {
	    			FacesContext.getCurrentInstance().getExternalContext().redirect
					("studentDetailsgap_formForStudent.xhtml?stepNow=-1&emailForState=-1&majorId="+String.valueOf(selectedgap_form.getMajorId())+"&c="+selectedgap_form.getId());
	    			
	    			
	    		
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public String getFriendlySubmissionDate(Calendar calender) {
		if(calender!=null){
			 
			  // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			    String strDate = sdf.format(calender.getTime());
			    return strDate;
			}
			
			else return "";
	}
	
	
	
	
	
	
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public StudentFacadeImpl getStudentFacadeImpl() {
		return studentFacadeImpl;
	}
	public void setStudentFacadeImpl(StudentFacadeImpl studentFacadeImpl) {
		this.studentFacadeImpl = studentFacadeImpl;
	}
	public StudentDTO getStudent() {
		return student;
	}
	public void setStudent(StudentDTO student) {
		this.student = student;
	}

	public StudentProfileFacadeImpl getFacade() {
		return facade;
	}
	
	public void setFacade(StudentProfileFacadeImpl facade) {
		this.facade = facade;
	}

	public MajorsFacadeImpl getMajorfacade() {
		return majorfacade;
	}

	public void setMajorfacade(MajorsFacadeImpl majorfacade) {
		this.majorfacade = majorfacade;
	}

	public CouresFacadeImpl getCoursesfacade() {
		return coursesfacade;
	}

	public void setCoursesfacade(CouresFacadeImpl coursesfacade) {
		this.coursesfacade = coursesfacade;
	}

	public MajorDTO getMajor() {
		return major;
	}

	public void setMajor(MajorDTO major) {
		this.major = major;
	}

	public List<CoursesDTO> getCoursesDTOsTaken() {
		return coursesDTOsTaken;
	}

	public void setCoursesDTOsTaken(List<CoursesDTO> coursesDTOsTaken) {
		this.coursesDTOsTaken = coursesDTOsTaken;
	}

	public List<CoursesDTO> getCoursesDTOsAll() {
		return coursesDTOsAll;
	}

	public void setCoursesDTOsAll(List<CoursesDTO> coursesDTOsAll) {
		this.coursesDTOsAll = coursesDTOsAll;
	}

	public gap_formAppServiceImpl getCccAppServiceImpl() {
		return cccAppServiceImpl;
	}
	
	public void setCccAppServiceImpl(gap_formAppServiceImpl cccAppServiceImpl) {
		this.cccAppServiceImpl = cccAppServiceImpl;
	}

	public List<gap_form> getCourseChangeComfirmations() {
		return courseChangeComfirmations;
	}

	public void setCourseChangeComfirmations(List<gap_form> courseChangeComfirmations) {
		this.courseChangeComfirmations = courseChangeComfirmations;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}



	public gap_form getNewCourseComfirmation() {
		return newCourseComfirmation;
	}


	public void setNewCourseComfirmation(gap_form newCourseComfirmation) {
		this.newCourseComfirmation = newCourseComfirmation;
	}




	public int getStep() {
		return step;
	}


	public void setStep(int step) {
		this.step = step;
	}


	public gap_form getSelectedgap_form() {
		return selectedgap_form;
	}


	public void setSelectedgap_form(gap_form selectedgap_form) {
		this.selectedgap_form = selectedgap_form;
	}


	public HeadsAppServiceImpl getHeadFacades() {
		return headFacades;
	}


	public void setHeadFacades(HeadsAppServiceImpl headFacades) {
		this.headFacades = headFacades;
	}




	

}
