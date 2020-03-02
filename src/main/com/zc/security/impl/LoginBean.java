/**
 * 
 */
package main.com.zc.security.impl;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.com.zc.security.AuthenticationService;
import main.com.zc.services.domain.model.heads.Heads;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.service.repository.IEmployeeRepositoryImpl;
import main.com.zc.services.domain.service.repository.heads.HeadsAppServiceImpl;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.appService.IConfirmationStepAppService;
import main.com.zc.shared.appService.ILoginAppService;
import main.com.zc.shared.appService.ILoginSecurityAppService;
import main.com.zc.shared.presentation.dto.LoginStaffDTO;
import main.com.zc.shared.presentation.facade.ILoginFacade;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


/**
 * @author Omnya Alaa
 * 
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

	/**
	 * 
	 */

	private String mail;
	private String name;
	private String password;
	private String confirmPassword;
	private String registerMail;
	private String registerPassword;
	private String registerConfirmPass;
	private String registerName;
	private byte[] image;
	private boolean registerMood;
	private boolean uplodeImageMood;
	// private Integer confirmCode;
	private String confirmCode;
	private int generatedCode;
	private String forgetMail;
	private UploadedFile uploadedImage;
	//private String ImageString;
	private Boolean rememberMe;
	@ManagedProperty(value = "#{authenticationService}")
	private AuthenticationService authenticationService;

	/*
	 * @Autowired ILoginSecurityAppService loginSecAppService;
	 */
	@ManagedProperty(value = "#{loginSecurityAppServiceImpl}")
	private ILoginSecurityAppService loginSecAppService;

	/*
	 * @Autowired ILoginFacade loginfacade;
	 */
	@ManagedProperty(value = "#{loginFacadeImpl}")
	private ILoginFacade loginfacade;

	/*
	 * @Autowired ILoginAppService loginAppService;
	 */
	@ManagedProperty(value = "#{loginAppServiceImpl}")
	private ILoginAppService loginAppService;

	/*
	 * @Autowired IConfirmationStepAppService confirmAPPService;
	 */
	@ManagedProperty(value = "#{confirmationStepAppServiceImpl}")
	private IConfirmationStepAppService confirmAPPService;

	@ManagedProperty("#{headsFacadeImpl}")
   	private HeadsAppServiceImpl headFacades; 
	

	
	
	public static Heads head ;//= headRep.getByType(Heads.Regist_Head);
	public static Employee  ADMISSION_DEPTEm =null;//= instructorRepository.getById(head.getHeadPersonId().getId());
	public static Heads head1 ;//= headRep.getByType(Heads.Regist_Head);
	public static Employee  ADMISSION_HEADEm =null;// instructorRepository.getById(head1.getHeadPersonId().getId());
	public static Heads head2 ;//= headRep.getByType(Heads.Regist_Head);
	public static Employee  DEAN_OF_ACADEMICEm =null;// instructorRepository.getById(head2.getHeadPersonId().getId());
	public static Heads head3 ;//= headRep.getByType(Heads.Regist_Head);
	public static Employee  PROVOSTEm =null;// instructorRepository.getById(head3.getHeadPersonId().getId());
	public static Heads head4 ;//= headRep.getByType(Heads.Regist_Head);
	public static Employee DEAN_OF_STRATEGICEm =null;// instructorRepository.getById(head4.getHeadPersonId().getId());
	
	@PostConstruct
	public void init() {
		registerMood = false;
		setName("");
		setPassword("");
		setMail("");
		setPassword("");
		cancelRegisteration();
		setImage(null);
	//	loginfacade.changeAllPasswords();
		
		refresh();
	}

	public void refresh() {

		head = headFacades.getByType(Heads.REGISTRAR_STAFF);
		ADMISSION_DEPTEm = head.getHeadPersonId();
		head1 = headFacades.getByType(Heads.Regist_Head);
		ADMISSION_HEADEm = head1.getHeadPersonId();
		head2 = headFacades.getByType(Heads.DEAN_OF_ACADEMIC);
		DEAN_OF_ACADEMICEm = head2.getHeadPersonId();
		head3 = headFacades.getByType(Heads.PROVOST);
		PROVOSTEm = head3.getHeadPersonId();
		head4 = headFacades.getByType(Heads.DEAN_OF_STRATIGIC_ENROLLEMENT);
		DEAN_OF_STRATEGICEm = head4.getHeadPersonId();
		
		Constants.DEAN_OF_STRATEGIC=DEAN_OF_STRATEGICEm.getMail();//"mabdrabou@zewailcity.edu.eg";
		Constants.DEAN_OF_STRATEGIC_ID=DEAN_OF_STRATEGICEm.getId();//1379;
		Constants.DEAN_OF_STRATEGIC_NAME=DEAN_OF_STRATEGICEm.getName();//"Dr. Mahmoud Abd Rabouh";
		Constants.DEAN_OF_ACADEMIC=DEAN_OF_ACADEMICEm.getMail();//"tibrahim@zewailcity.edu.eg";
		Constants.DEAN_OF_ACADEMIC_ID=DEAN_OF_ACADEMICEm.getId();//20;
		Constants.DEAN_OF_ACADEMIC_NAME=DEAN_OF_ACADEMICEm.getName();//"Dr. Tarek Ibrahim";
		

		System.out.println("Ahmed:"+String.valueOf(Constants.DEAN_OF_STRATEGIC_ID));
		System.out.println("Ahmed:"+String.valueOf(Constants.DEAN_OF_STRATEGIC));
		
		Constants.ADMISSION_HEAD=ADMISSION_HEADEm.getMail();//"ghazem@zewailcity.edu.eg";
		Constants.ADMISSION_HEAD_ID=ADMISSION_HEADEm.getId();//888;
		
		Constants.ADMISSION_HEAD_NAME=ADMISSION_HEADEm.getName();//"Mrs. Ghada";
		Constants.ADMISSION_DEPT=ADMISSION_DEPTEm.getMail();//"registrar@zewailcity.edu.eg";
		Constants.ADMISSION_DEPT_ID=ADMISSION_DEPTEm.getId();//999;
		Constants.PROVOST=PROVOSTEm.getMail();//"sobbaya@zewailcity.edu.eg";
		Constants.PROVOST_ID=PROVOSTEm.getId();//21;
		Constants.PROVOST_NAME=PROVOSTEm.getName();//"Dr. Salah Obayya";
	}
	public String login() {
		try {
			// System.out.println("HI >>>>> ");
			LoginStaffDTO daoUSerLogin =  getLoginfacade().checkRegisteryOFMail(mail);
			if(daoUSerLogin!=null) {
			boolean success = authenticationService.login(mail, password);

			if (success) {

				User user = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				String name = user.getUsername(); // get logged in username

				setName(name);
				if (this.mail.toLowerCase().startsWith("s-")||StringUtils.isNumeric(this.mail.substring(0, 4))) {
					byte[] dbImage = loginAppService.getStudentImage(this.mail);
					//if (dbImage != null) {
						this.uplodeImageMood = false;
						this.image = dbImage;
						FacesContext.getCurrentInstance().getExternalContext()
								.getSessionMap().put("resetMenu", true);
						JavaScriptMessagesHandler.RegisterNotificationMessage(
								null, "Welcome ..");
						return "/pages/secured/dashboard.xhtml?faces-redirect=true";
//					} else {
//						this.uplodeImageMood = true;
//						this.image = null;
//						JavaScriptMessagesHandler
//								.RegisterErrorMessage(
//										null,
//										"Please, Upload your personal image as it will be submitted with your petition.");
//						SecurityContextHolder.getContext().setAuthentication(
//								null);
//						return "";
//					}
				} else {
					this.image = null;
					this.uplodeImageMood = false;
					FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap().put("resetMenu", true);
					JavaScriptMessagesHandler.RegisterNotificationMessage(null,
							"Welcome ..");
					return "/pages/secured/dashboard.xhtml?faces-redirect=true";

				}
			} else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"The email or password you entered is incorrect. ");
				return "";
			}
			
			}else {
				JavaScriptMessagesHandler.RegisterErrorMessage(null,
						"The email is not registered");
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");
			return "";
		}
	}

//	public String saveImage() {
//		this.image = loginAppService.saveImage(this.mail, this.getImage());
//		return login();
//	}

	public String register() {
		// navigateionBean.setShowDialog(false);
		registerMood = true;
		setRegisterMail("");
		setRegisterName("");
		setRegisterPassword("");
		setRegisterConfirmPass("");
		setImage(null);
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("registerName");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("registerMail");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("registerPass");
		FacesContext.getCurrentInstance().getPartialViewContext()
				.getRenderIds().add("registerConfirmPass");
		return "/";
	}

	public void cancelRegisteration() {
		// System.out.println(">>>>>> ");
		setRegisterConfirmPass("");
		setRegisterMail("");
		setRegisterName("");
		setRegisterPassword("");
		setImage(null);
		registerMood = false;
	}

	public String addLogin() {

		try {
			// System.out.println("Name : " + getRegisterName());
			// System.out.println("Mail : " + getRegisterMail());
			// System.out.println("Pass : " + getRegisterPassword());
			if (getRegisterMail().startsWith("s-")||StringUtils.isNumeric(getRegisterMail().substring(0, 4))
					|| getRegisterMail().startsWith("S-"))// Student add login
			{
				System.out.println("student");
				if (loginfacade.checkStudents(getRegisterMail())) {
					LoginStaffDTO dao = loginAppService.addInsLogin(
							getRegisterName(), getRegisterMail(),
							getRegisterPassword(), getImage());
					if (dao != null) {
						// Login using spring seurity
						setMail(getRegisterMail());
						setPassword(getRegisterPassword());
						//boolean success = authenticationService.login(mail, password);

						boolean success = authenticationService.autoLogin(mail, password);
/*//						boolean success = authenticationService.login(
//								dao.getMail(), dao.getPassword());
*/
						if (success) {

							User user = (User) SecurityContextHolder
									.getContext().getAuthentication()
									.getPrincipal();
							String name = user.getUsername(); // get logged in
																// username

							setName(name);
							cancelRegisteration();
							FacesContext.getCurrentInstance()
									.getExternalContext().getSessionMap()
									.put("resetMenu", true);
							JavaScriptMessagesHandler
									.RegisterNotificationMessage("",
											"Congratulations you have successfully created your own account!");
							return "/pages/secured/dashboard.xhtml?faces-redirect=true";
						} else {

							return "";
						}

					} else {
						JavaScriptMessagesHandler
								.RegisterErrorMessage("",
										"An account already exists for this email address");
						return "";
					}
				} else {
					JavaScriptMessagesHandler
							.RegisterErrorMessage(
									"",
									"TThe entered email is not a valid zewail account, please enter your zewail email address");
					return "";

				}
			}

			else// Instructor case
			{
				// System.out.println("Instructor");
				if (loginfacade.checkInstuctors(getRegisterMail())) {
					LoginStaffDTO dao = loginAppService.addInsLogin(
							getRegisterName(), getRegisterMail(),
							getRegisterPassword(), null);
					if (dao != null) {
						/*boolean success = authenticationService.login(
								dao.getMail(), dao.getPassword());*/
						setMail(getRegisterMail());
						setPassword(getRegisterPassword());
						//boolean success = authenticationService.login(mail, password);
						boolean success = authenticationService.autoLogin(mail, password);
						if (success) {

							User user = (User) SecurityContextHolder
									.getContext().getAuthentication()
									.getPrincipal();
							String name = user.getUsername(); // get logged in
																// username

							setName(name);
							cancelRegisteration();
							FacesContext.getCurrentInstance()
									.getExternalContext().getSessionMap()
									.put("resetMenu", true);
							JavaScriptMessagesHandler
									.RegisterNotificationMessage("",
											"Congratulations you have successfully created your own account!");
							return "/pages/secured/dashboard.xhtml?faces-redirect=true";
						} else {

							return "";
						}
					} else {
						JavaScriptMessagesHandler
								.RegisterErrorMessage("",
										"An account already exists for this email address");
						return "";
					}

				} else {
					JavaScriptMessagesHandler.RegisterErrorMessage("",
							"The email you entered is incorrect. ");
					return "";

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");
			return "";
		}

	}

	public String logout() {
		try {
			SecurityContextHolder.getContext().setAuthentication(null);
			// System.out.println(">>>>>> ");
			setName("");
			return "/pages/public/login.xhtml?faces-redirect=true";
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");
			return "";
		}

	}

	
	private static boolean sendFromGMail( final  InternetAddress[] toAddress, final String subject, final String body,final String problemMessage,final String successMessage) {
		 
				
				String from = "LearningTechnologies@zewailcity.edu.eg";
		        String pass = "DELF-651984@dr";
				
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
						message.setRecipients(Message.RecipientType.TO, toAddress);
			            

			            message.setSubject(subject);
			            message.setText(body);

			    		message.setContent(body, "text/html; charset=ISO-8859-1");
			            Transport transport = session.getTransport("smtp");
			            transport.connect(host, from, pass);
			            transport.sendMessage(message, message.getAllRecipients());
			            transport.close();
			            System.out.println("Done Email Send");JavaScriptMessagesHandler.RegisterErrorMessage("",
								successMessage);
			            return true;
			        }
			        catch (AddressException ae) {
			            ae.printStackTrace();
			            JavaScriptMessagesHandler.RegisterErrorMessage("",
								problemMessage);
			            return false;
			        }
			        catch (MessagingException me) {
			            me.printStackTrace();
			            JavaScriptMessagesHandler.RegisterErrorMessage("",
								problemMessage);
			            return false;
			        }
			        
		
	       
	    }
	public void forgetPassword() {
		try {
			LoginStaffDTO dao = loginfacade.checkRegisteryOFMail(forgetMail);
			if (dao != null) { // Implementation of sending mails
				String generatedPassword= UUID.randomUUID().toString().replace("-", "").substring(0,6).toUpperCase();
				dao.setPassword(generatedPassword);
				loginAppService.changePassword(forgetMail, generatedPassword);
						
				javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
				
     				addressTo[0] = new javax.mail.internet.InternetAddress(
						forgetMail);
	



				String htmlText = "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
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
						+ "<span style=color:#676767>Dear "
						+ dao.getName()
						+ ",</span><br/><br/><br/>"
						+ "<span style=color:#676767>You're receiving this email because you requested a new password for your account</span><br/><br/><br/>"
						+ "<span style=color:#676767><b>Your new password:"
						+ generatedPassword
						+ "</span><br/><br/>"
						+ "<span style=color:#676767>We kindly ask you to change your password after login.</span><br/><br/>"
						+ "<span style=color:#676767>Thank you, </span><br/><br/>"
						+ "<span style=color:#676767>Center for Learning Technologies</span>"
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
						+ " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
						+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
						+ "</div>" + "</li>" + "</ul>" + "</div>";
				
			sendFromGMail( addressTo, "Forgetting Password",htmlText,"This email address is not registered in the system!","Please, Check Your Inbox");
			
			}
			}catch (Exception e) {
				// TODO: handle exception
				
				JavaScriptMessagesHandler.RegisterErrorMessage("",
						"Problem!!");
			}
			
			
			
		}

	public String confirmCode() {

		try {
			Integer no = Integer.parseInt(getConfirmCode());
			if (getGeneratedCode() == no) {
				// System.out.println("Confirm code is identical to the generated");
				return addLogin();
			} else {
				// System.out.println("Confirm code is not identical to the generated");
				JavaScriptMessagesHandler.RegisterErrorMessage("",
						"The code you entered is incorrect. ");
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("",
					"Please enter valid confirmation code");
			return "";
		}
	}

	public void resendCode() {

		try {
			Random rand = new Random();

			int randomNum = rand.nextInt((1000000000 - 10000000) + 1) + 10000000;
			setGeneratedCode(randomNum);
			boolean b = confirmAPPService.sendConfirmMail(
					getRegisterMail(), randomNum);
			setConfirmCode(null);
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");

		}

	}
	public String confirmRegisteration() {
		if(isZewailAccount(getRegisterMail().toLowerCase())) {
		try {

			if (registerPassword.equals(registerConfirmPass) == false) {
				JavaScriptMessagesHandler.RegisterErrorMessage("",
						"Confirm Password doesn't match Password !");
				return "";
			} else {
				boolean b;
				LoginStaffDTO dao = loginfacade
						.checkRegisteryOFMail(getRegisterMail());
				if (dao == null) {
					if (getRegisterMail().toLowerCase().startsWith("s-")||StringUtils.isNumeric(getRegisterMail().substring(0, 4)))// Student add
																	// login
					{
						// System.out.println("student");
						if (loginfacade.checkStudents(getRegisterMail())) {
							Random rand = new Random();

							// nextInt is normally exclusive of the top value,
							// so add 1 to make it inclusive
							int randomNum = rand
									.nextInt((1000000000 - 10000000) + 1) + 10000000;
							setGeneratedCode(randomNum);
							b = confirmAPPService.sendConfirmMail(
									getRegisterMail(), randomNum);
							setConfirmCode(null);
							return "/pages/public/confirmPage.xhtml";
						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage("",
									"The email you entered is incorrect. ");
							return "";

						}
					} else {
						if (loginfacade
								.checkInstuctors(getRegisterMail())) {
							Random rand = new Random();

							// nextInt is normally exclusive of the top value,
							// so add 1 to make it inclusive
							int randomNum = rand
									.nextInt((1000000000 - 10000000) + 1) + 10000000;
							setGeneratedCode(randomNum);
							b = confirmAPPService.sendConfirmMail(
									getRegisterMail(), randomNum);
							setConfirmCode(null);
							return "/pages/public/confirmPage.xhtml";
						} else {
							JavaScriptMessagesHandler.RegisterErrorMessage("",
									"The email you entered is incorrect. ");
							return "";

						}
					}
				} else {
					JavaScriptMessagesHandler.RegisterErrorMessage("",
							"An account already exists for this email address");
					return "";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");
			return "";
		}
		}
		else {
			JavaScriptMessagesHandler.RegisterErrorMessage("", "This is not Zewail Email");
		
			return "";
			}

	}

	private boolean isZewailAccount(String lowerCase) {
		// TODO Auto-generated method stub
		if(lowerCase.toLowerCase().contains("@zewailcity.edu.eg"))
			return true;
		return false;
	}

	public String getMail() {

		try {
			Authentication authentication = SecurityContextHolder.getContext()

			.getAuthentication();
			if (authentication.getPrincipal().equals("anonymousUser"))// not
																		// logged
																		// in
			{

				return "";
			} else
				return mail;
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");
			return "";
		}
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		try {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			if (!authentication.getPrincipal().equals("anonymousUser"))// logged
																		// in
			{
				String mail = authentication.getName();
				LoginStaffDTO dao = loginSecAppService.getUserByMail(mail);
				return dao.getName();
			} else {
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage("", "Error");
			return "";
		}

	}

	public void previewImage(FileUploadEvent fileUploadEvent) {
		this.image = fileUploadEvent.getFile().getContents();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getRegisterMail() {
		return registerMail;
	}

	public void setRegisterMail(String registerMail) {
		this.registerMail = registerMail;
	}

	public String getRegisterPassword() {
		return registerPassword;
	}

	public void setRegisterPassword(String registerPassword) {
		this.registerPassword = registerPassword;
	}

	public String getRegisterConfirmPass() {
		return registerConfirmPass;
	}

	public void setRegisterConfirmPass(String registerConfirmPass) {
		this.registerConfirmPass = registerConfirmPass;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public boolean isRegisterMood() {
		return registerMood;
	}

	public void setRegisterMood(boolean registerMood) {
		this.registerMood = registerMood;
	}

	/*
	 * public Integer getConfirmCode() { return confirmCode; }
	 * 
	 * public void setConfirmCode(Integer confirmCode) { this.confirmCode =
	 * confirmCode; }
	 */

	public int getGeneratedCode() {
		return generatedCode;
	}

	public void setGeneratedCode(int generatedCode) {
		this.generatedCode = generatedCode;
	}

	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public ILoginSecurityAppService getLoginSecAppService() {
		return loginSecAppService;
	}

	public void setLoginSecAppService(
			ILoginSecurityAppService loginSecAppService) {
		this.loginSecAppService = loginSecAppService;
	}

	public ILoginFacade getLoginfacade() {
		return loginfacade;
	}

	public void setLoginfacade(ILoginFacade loginfacade) {
		this.loginfacade = loginfacade;
	}

	public ILoginAppService getLoginAppService() {
		return loginAppService;
	}

	public void setLoginAppService(ILoginAppService loginAppService) {
		this.loginAppService = loginAppService;
	}

	public IConfirmationStepAppService getConfirmAPPService() {
		return confirmAPPService;
	}

	public void setConfirmAPPService(
			IConfirmationStepAppService confirmAPPService) {
		this.confirmAPPService = confirmAPPService;
	}

	public String getConfirmCode() {
		return confirmCode;
	}

	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	public String getForgetMail() {
		return forgetMail;
	}

	public void setForgetMail(String forgetMail) {
		this.forgetMail = forgetMail;
	}

	public UploadedFile getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(UploadedFile uploadedImage) {
		this.uploadedImage = uploadedImage;
	}

	public StreamedContent getImagePreview() {
		InputStream dbStream = null;
		if (this.image != null) {
			dbStream = new ByteArrayInputStream(this.image);
			return new DefaultStreamedContent(dbStream);
		}
		return null;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public boolean isUplodeImageMood() {
		return uplodeImageMood;
	}

	public void setUplodeImageMood(boolean uplodeImageMood) {
		this.uplodeImageMood = uplodeImageMood;
	}

/*	public String getImageString() {
		if (this.image != null) {
			return "ddddd";
		} else {
			return null;
		}
	}

	public void setImageString(String imageString) {
		ImageString = imageString;
	}*/

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public HeadsAppServiceImpl getHeadFacades() {
		return headFacades;
	}

	public void setHeadFacades(HeadsAppServiceImpl headFacades) {
		this.headFacades = headFacades;
	}

	

	
}
