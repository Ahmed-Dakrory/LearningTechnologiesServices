/**
 * 
 */
package main.com.zc.services.presentation.forms.emails.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.domain.petition.model.ChangeMajorForm;
import main.com.zc.services.domain.petition.model.CoursePetition;
import main.com.zc.services.domain.petition.model.DropAddForm;
import main.com.zc.services.domain.petition.model.IAddDropFormRepository;
import main.com.zc.services.domain.petition.model.IChangeMajorFormRep;
import main.com.zc.services.domain.petition.model.ICoursePetitionRep;
import main.com.zc.services.domain.petition.model.IOverloadRequestRep;
import main.com.zc.services.domain.petition.model.IRepeatCourseFormRep;
import main.com.zc.services.domain.petition.model.ITAJuniorProgramRep;
import main.com.zc.services.domain.petition.model.OverloadRequest;
import main.com.zc.services.domain.petition.model.RepeatCourseForm;
import main.com.zc.services.domain.petition.model.TAJuniorProgram;
import main.com.zc.services.domain.shared.Constants;
import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;
import main.com.zc.services.presentation.forms.emails.service.ICheckNewMails;
import main.com.zc.shared.appService.IGetInstructorDataAppService;
import main.com.zc.shared.appService.IPersonGetDataAppService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author omnya
 *
 */
@Named
@Scope("singleton")
public class CheckNewEmails extends Thread implements ICheckNewMails {

	@Inject
	IStudentRepository rep;
	@Inject
	ICoursePetitionRep academPetRep;
	@Inject
	IAddDropFormRepository dropAddPetRep;
	@Inject
	IChangeMajorFormRep changeMajorRep;
	@Inject
	IEmployeeRepository insRep;
	@Inject
	IOverloadRequestRep overloadRep;
	@Inject
	IRepeatCourseFormRep repeatCourseFormRep;

	List<Employee> instructors = new ArrayList<Employee>();
	@Inject
	ISharedNotifyService sharedNotifyService;
	
	@Inject
	ITAJuniorProgramRep juniorTARep;

	@PostConstruct
	public void init() {
		startHandler();
		createJobs();
	}

	private void createJobs() {
		List<CoursePetition> coursePetitionDTOs = academPetRep
				.getCoursePetitionJob();
		for (CoursePetition coursePetition : coursePetitionDTOs) {
			if(coursePetition.getInsNotifyDate().before(new Date()))
			{
				coursePetition.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(coursePetition);
		}
		List<DropAddForm> dropAddForms = dropAddPetRep
				.getDropAddFormJob();
		for (DropAddForm addForm : dropAddForms) {
			if(addForm.getInsNotifyDate().before(new Date()))
			{
				addForm.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(addForm);
		}
		List<ChangeMajorForm> changeMajorForms = changeMajorRep
				.getChangeMajorFormJob();
		for (ChangeMajorForm changeMajorForm : changeMajorForms) {
			if(changeMajorForm.getInsNotifyDate().before(new Date()))
			{
				changeMajorForm.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(changeMajorForm);
		}
		List<OverloadRequest> overloadRequests = overloadRep
				.getOverloadRequestJob();
		for (OverloadRequest overloadRequest : overloadRequests) {
			if(overloadRequest.getInsNotifyDate().before(new Date()))
			{
				overloadRequest.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(overloadRequest);
		}
		List<RepeatCourseForm> repeatCourseForms = repeatCourseFormRep
				.getRepeatCourseFormJob();
		for (RepeatCourseForm repeatCourseForm : repeatCourseForms) {
			if(repeatCourseForm.getInsNotifyDate().before(new Date()))
			{
				repeatCourseForm.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(repeatCourseForm);
		}
		//Junior TA
		List<TAJuniorProgram> juniorTAForms = juniorTARep
				.getPendingJob();
		for (TAJuniorProgram juniorTAForm : juniorTAForms) {
			if(juniorTAForm.getInsNotifyDate().before(new Date()))
			{
				juniorTAForm.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(juniorTAForm);
		}
		
		
		
		/*// Incomplete grade
		List<IncompleteF> repeatCourseForms = repeatCourseFormRep
				.getRepeatCourseFormJob();
		for (RepeatCourseForm repeatCourseForm : repeatCourseForms) {
			if(repeatCourseForm.getInsNotifyDate().before(new Date()))
			{
				repeatCourseForm.setInsNotifyDate(new Date(new Date().getTime()+600000));
			}
			sharedNotifyService.notifayAtDate(repeatCourseForm);
		}*/

	}

	@Override
	public void run() {
		while (true) {

			try {

				try {
					sendDialyPendingPetition();
				}

				catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("Can't Daily");
				}
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void startHandler() {
		start();
	}

	public void scanAcademicPetitionTable() {

		List<CoursePetition> academicPetLst = academPetRep.getAll();
		for (int i = 0; i < academicPetLst.size(); i++) {
			try 
			{
				if (academicPetLst.get(i).getInsNotifyDate() != null) {
					if (academicPetLst.get(i).getInsSendMail() != null) {
						if (academicPetLst.get(i).getInsSendMail() != true) {
							// send mails
							System.out.println("Send email to "
									+ academicPetLst.get(i).getCourse()
											.getCourseCoordinator().getName());
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
									+ academicPetLst.get(i).getCourse()
											.getCourseCoordinator().getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>We would like to inform you that you have new academic petition needs an action</span><br/><br/>"
									+ "<span style=color:#676767><b>For Student : "
									+ academicPetLst.get(i).getPerson()
											.getData().getNameInEnglish()
									+ " </span><br/><br/>"
									+ "<span style=color:#676767><b>For Course : "
									+ academicPetLst.get(i).getCourse()
											.getName()
									+ " </span><br/><br/>"
									+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/academicPetition/InstructorCoursePetitionPage.xhtml </span><br/><br/>"
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
							boolean b = sendEmail(new ArrayList<String>(),
									htmlText, "Academic Petition Alarm");

							// update the object after sending
							if (b) {
								CoursePetition updated = academicPetLst.get(i);
								updated.setInsNotifyDate(null);
								updated.setInsSendMail(null);
								academPetRep.update(updated);
							}
						}
					} else {
						// send mails
						System.out.println("Send email to "
								+ academicPetLst.get(i).getCourse()
										.getCourseCoordinator().getName());
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
								+ academicPetLst.get(i).getCourse()
										.getCourseCoordinator().getName()
								+ ",</span><br/><br/><br/>"
								+ "<span style=color:#676767>We would like to inform you that you have new academic petition needs an action</span><br/><br/>"
								+ "<span style=color:#676767><b>For Student : "
								+ academicPetLst.get(i).getPerson().getData()
										.getNameInEnglish()
								+ " </span><br/><br/>"
								+ "<span style=color:#676767><b>For Course : "
								+ academicPetLst.get(i).getCourse().getName()
								+ " </span><br/><br/>"
								+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/academicPetition/InstructorCoursePetitionPage.xhtml </span><br/><br/>"
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
						boolean b = sendEmail(new ArrayList<String>(),
								htmlText, "Academic Petition Alarm");

						// update the object after sending
						if (b) {
							CoursePetition updated = academicPetLst.get(i);
							updated.setInsNotifyDate(null);
							updated.setInsSendMail(null);
							academPetRep.update(updated);
						}
					}
				}
			} catch (Exception ex) {

			}
		}

	}

	public void scandDropDownPetitionTable() {
		// List<DropAddForm> dropDownLst = dropAddPetRep.getAll();
		// for (int i = 0; i < dropDownLst.size(); i++) {
		// try {
		// if (dropDownLst.get(i).getInsNotifyDate() != null) {
		// if (dropDownLst.get(i).getInsSendMail() != null) {
		// if (dropDownLst.get(i).getInsSendMail() != true) {
		// // send mails
		// System.out.println("Send email to "
		// + dropDownLst.get(i).getCourse()
		// .getCourseCoordinator().getName());
		// String htmlText =
		// "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
		// + "<ul style=margin:0;padding:0;>"
		// + "<li style=list-style:none;float:left;width:700px;margin:0;>"
		// + "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
		// +
		// "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
		// +
		// "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
		// +
		// "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
		// + "<div style=padding:24px 36px;color:#676767 !important;>"
		// + "<span style=color:#676767>Dear "
		// + dropDownLst.get(i).getCourse()
		// .getCourseCoordinator().getName()
		// + ",</span><br/><br/><br/>"
		// +
		// "<span style=color:#676767>We would like to inform you that you have new drop/add petition needs an action</span><br/><br/>"
		// + "<span style=color:#676767><b>For Student : "
		// + dropDownLst.get(i).getStudent().getData()
		// .getNameInEnglish()
		// + " </span><br/><br/>"
		// + "<span style=color:#676767><b>For Course : "
		// + dropDownLst.get(i).getCourse().getName()
		// + " </span><br/><br/>"
		// +
		// "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/dropAndAdd/addDropIns.xhtml </span><br/><br/>"
		// + "<span style=color:#676767>Thank you, </span><br/><br/>"
		// + "<span style=color:#676767>Center for Learning Technologies</span>"
		// + "</div>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
		// + "<ul style=margin:0;padding:0;>"
		// +
		// "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
		// +
		// "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
		// + "</li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
		// +
		// "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
		// +
		// "<span style=color:#a1a0a0;font-size:11px;>Please do not reply directly to this email. If you have any questions or feedback, please send to </span><a href=mailto:contacts@zclt.info style=color:#00A7A6;fntsize:11px;>contacts@zclt.info</a>"
		// +
		// " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
		// +
		// "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
		// + "</div>" + "</li>" + "</ul>" + "</div>";
		// boolean b = sendEmail("", htmlText,
		// "Drop/Add Petition Alarm");
		//
		// // update the object after sending
		// if (b) {
		//
		// DropAddForm updated = dropDownLst.get(i);
		// updated.setInsNotifyDate(null);
		// updated.setInsSendMail(null);
		// dropAddPetRep.update(updated);
		// }
		// }
		// } else {
		//
		// System.out.println("Send email to "
		// + dropDownLst.get(i).getCourse()
		// .getCourseCoordinator().getName());
		// // construct mails
		// System.out.println("Send email to "
		// + dropDownLst.get(i).getCourse()
		// .getCourseCoordinator().getName());
		// String htmlText =
		// "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
		// + "<ul style=margin:0;padding:0;>"
		// + "<li style=list-style:none;float:left;width:700px;margin:0;>"
		// + "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
		// +
		// "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
		// +
		// "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
		// +
		// "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
		// + "<div style=padding:24px 36px;color:#676767 !important;>"
		// + "<span style=color:#676767>Dear "
		// + dropDownLst.get(i).getCourse()
		// .getCourseCoordinator().getName()
		// + ",</span><br/><br/><br/>"
		// +
		// "<span style=color:#676767>We would like to inform you that you have new drop/add petition needs an action</span><br/><br/>"
		// + "<span style=color:#676767><b>For Student : "
		// + dropDownLst.get(i).getStudent().getData()
		// .getNameInEnglish()
		// + " </span><br/><br/>"
		// + "<span style=color:#676767><b>For Course : "
		// + dropDownLst.get(i).getCourse().getName()
		// + " </span><br/><br/>"
		// +
		// "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/dropAndAdd/addDropIns.xhtml </span><br/><br/>"
		// + "<span style=color:#676767>Thank you, </span><br/><br/>"
		// + "<span style=color:#676767>Center for Learning Technologies</span>"
		// + "</div>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
		// + "<ul style=margin:0;padding:0;>"
		// +
		// "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
		// +
		// "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
		// + "</li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
		// +
		// "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
		// +
		// "<span style=color:#a1a0a0;font-size:11px;>Please do not reply directly to this email. If you have any questions or feedback, please send to </span><a href=mailto:contacts@zclt.info style=color:#00A7A6;fntsize:11px;>contacts@zclt.info</a>"
		// +
		// " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
		// +
		// "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
		// + "</div>" + "</li>" + "</ul>" + "</div>";
		// boolean b = sendEmail("", htmlText,
		// "Drop/Add Petition Alarm");
		//
		// // update the object after sending
		// if (b) {
		// DropAddForm updated = dropDownLst.get(i);
		// updated.setInsNotifyDate(null);
		// updated.setInsSendMail(null);
		// dropAddPetRep.update(updated);
		// }
		// }
		// }
		// } catch (Exception ex) {
		//
		// }
		// }

	}

	public void scanChangeMajorPetitionTable() {
		// List<ChangeMajorForm> changeMajor = changeMajorRep.getAll();
		// for (int i = 0; i < changeMajor.size(); i++) {
		// try {
		// if (changeMajor.get(i).getInsNotifyDate() != null) {
		// if (changeMajor.get(i).getInsSendMail() != null) {
		// if (changeMajor.get(i).getInsSendMail() != true) {
		// // send mails
		// System.out.println("Send email to "
		// + insRep.getById(changeMajor.get(i)
		// .getMajor().getHeadOfMajorId()
		// .getId()));
		// String htmlText =
		// "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
		// + "<ul style=margin:0;padding:0;>"
		// + "<li style=list-style:none;float:left;width:700px;margin:0;>"
		// + "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
		// +
		// "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
		// +
		// "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
		// +
		// "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
		// + "<div style=padding:24px 36px;color:#676767 !important;>"
		// + "<span style=color:#676767>Dear "
		// + insRep.getById(
		// changeMajor.get(i).getMajor()
		// .getHeadOfMajorId().getId())
		// .getName()
		// + ",</span><br/><br/><br/>"
		// +
		// "<span style=color:#676767>We would like to inform you that you have new Change Major petition needs an action</span><br/><br/>"
		// + "<span style=color:#676767><b>For Student : "
		// + changeMajor.get(i).getStudent().getData()
		// .getNameInEnglish()
		// + " </span><br/><br/>"
		// +
		// "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/academicPetition/InstructorCoursePetitionPage.xhtml </span><br/><br/>"
		// + "<span style=color:#676767>Thank you, </span><br/><br/>"
		// + "<span style=color:#676767>Center for Learning Technologies</span>"
		// + "</div>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
		// + "<ul style=margin:0;padding:0;>"
		// +
		// "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
		// +
		// "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
		// + "</li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
		// +
		// "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
		// +
		// "<span style=color:#a1a0a0;font-size:11px;>Please do not reply directly to this email. If you have any questions or feedback, please send to </span><a href=mailto:contacts@zclt.info style=color:#00A7A6;fntsize:11px;>contacts@zclt.info</a>"
		// +
		// " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
		// +
		// "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
		// + "</div>" + "</li>" + "</ul>" + "</div>";
		// boolean b = sendEmail("", htmlText,
		// "Change Major Petition Alarm");
		//
		// // update the object after sending
		// if (b) {
		//
		// ChangeMajorForm updated = changeMajor.get(i);
		// updated.setInsNotifyDate(null);
		// updated.setInsSendMail(null);
		// changeMajorRep.update(updated);
		// }
		// }
		// } else {
		//
		// System.out.println("Send email to "
		// + insRep.getById(changeMajor.get(i).getMajor()
		// .getHeadOfMajorId().getId()));
		// String htmlText =
		// "<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>"
		// + "<ul style=margin:0;padding:0;>"
		// + "<li style=list-style:none;float:left;width:700px;margin:0;>"
		// + "	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
		// +
		// "<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
		// +
		// "<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
		// +
		// "<h2 style=margin:0;padding:0;color:#404040 !important;>Learning Technologies Services</h2>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
		// + "<div style=padding:24px 36px;color:#676767 !important;>"
		// + "<span style=color:#676767>Dear "
		// + insRep.getById(
		// changeMajor.get(i).getMajor()
		// .getHeadOfMajorId().getId())
		// .getName()
		// + ",</span><br/><br/><br/>"
		// +
		// "<span style=color:#676767>We would like to inform you that you have new Change Major petition needs an action</span><br/><br/>"
		// + "<span style=color:#676767><b>For Student : "
		// + changeMajor.get(i).getStudent().getData()
		// .getNameInEnglish()
		// + " </span><br/><br/>"
		// +
		// "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/academicPetition/InstructorCoursePetitionPage.xhtml </span><br/><br/>"
		// + "<span style=color:#676767>Thank you, </span><br/><br/>"
		// + "<span style=color:#676767>Center for Learning Technologies</span>"
		// + "</div>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>"
		// + "<ul style=margin:0;padding:0;>"
		// +
		// "<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
		// +
		// "<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
		// +
		// "<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
		// + "</li>"
		// + "</ul>"
		// + "</li>"
		// +
		// "<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
		// +
		// "<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
		// +
		// "<span style=color:#a1a0a0;font-size:11px;>Please do not reply directly to this email. If you have any questions or feedback, please send to </span><a href=mailto:contacts@zclt.info style=color:#00A7A6;fntsize:11px;>contacts@zclt.info</a>"
		// +
		// " <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
		// +
		// "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
		// + "</div>" + "</li>" + "</ul>" + "</div>";
		// boolean b = sendEmail("", htmlText,
		// "Change Major Petition Alarm");
		//
		// // update the object after sending
		// if (b) {
		//
		// ChangeMajorForm updated = changeMajor.get(i);
		// updated.setInsNotifyDate(null);
		// updated.setInsSendMail(null);
		// changeMajorRep.update(updated);
		// }
		// }
		// }
		// } catch (Exception ex) {
		//
		// }
		// }
	}

	public void scanOverloadRequestsTable() {
		List<OverloadRequest> overload = overloadRep.getAll();
		for (int i = 0; i < overload.size(); i++) {
			try {
				if (overload.get(i).getInsNotifyDate() != null) {
					if (overload.get(i).getInsSendMail() != null) {
						if (overload.get(i).getInsSendMail() != true) {
							// send mails
							System.out.println("Send email to "
									+ overload.get(i).getCourse()
											.getCourseCoordinator().getName());
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
									+ overload.get(i).getCourse()
											.getCourseCoordinator().getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>We would like to inform you that you have new Overload Request needs an action</span><br/><br/>"
									+ "<span style=color:#676767><b>For Student : "
									+ overload.get(i).getStudent().getData()
											.getNameInEnglish()
									+ " </span><br/><br/>"
									+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/overloadRequest/overloadRequestInstructor.xhtml </span><br/><br/>"
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
							boolean b = sendEmail(new ArrayList<String>(),
									htmlText, "Overload Request Alarm");

							// update the object after sending
							if (b) {

								OverloadRequest updated = overload.get(i);
								updated.setInsNotifyDate(null);
								updated.setInsSendMail(null);
								overloadRep.update(updated);
							}
						}
					} else {
						System.out.println("Send email to "
								+ overload.get(i).getCourse()
										.getCourseCoordinator().getName());

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
								+ overload.get(i).getCourse()
										.getCourseCoordinator().getName()
								+ ",</span><br/><br/><br/>"
								+ "<span style=color:#676767>We would like to inform you that you have new Overload Request needs an action</span><br/><br/>"
								+ "<span style=color:#676767><b>For Student : "
								+ overload.get(i).getStudent().getData()
										.getNameInEnglish()
								+ " </span><br/><br/>"
								+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/overloadRequest/overloadRequestInstructor.xhtml </span><br/><br/>"
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
						boolean b = sendEmail(new ArrayList<String>(),
								htmlText, "Overload Request Alarm");

						// update the object after sending
						if (b) {

							OverloadRequest updated = overload.get(i);
							updated.setInsNotifyDate(null);
							updated.setInsSendMail(null);
							overloadRep.update(updated);
						}
					}
				}
			} catch (Exception ex) {

			}
		}
	}

	public void sendDialyPendingPetition() {
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
		if (hourFormat.format(new Date()).equals("07")) {
			sendDailyForDean();
			sendDailyForAdmissionHead();
			sendDailyForAdmissionDept();
			sendDailyForProvost();
			sendDailyForInstructor();
			for (Employee instructor : instructors) {
				insRep.updateLastNotificationDate(new Date(), instructor
						.getMailSetting().getId());
			}
		}
	}

	private void sendDailyForProvost() {
		try {
			Employee instructor = insRep.getByMail(Constants.PROVOST);
			if (instructor.getMailSetting().getNotifyMe()) {
				Integer every = instructor.getMailSetting().getEveryDays();
				SimpleDateFormat dayFormat = new SimpleDateFormat("ddMMyyyy");
				if (instructor.getMailSetting().getLastNotify() == null
						|| dayFormat.format(
								new Date(instructor.getMailSetting()
										.getLastNotify().getTime()
										+ (every * 86400000))).equals(
								dayFormat.format((new Date())))) {

					List<OverloadRequest> overLoad = overloadRep
							.getProvostPendingOverloadRequest(false);
					if (overLoad != null && overLoad.size() != 0) {
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
								+ instructor.getName()
								+ ",</span><br/><br/><br/>"
								+ "<span style=color:#676767>We would like to inform you that you have "
								+ overLoad.size()
								+ " pending Overload Request(s)  needs an action</span><br/><br/>"
								+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/secured/forms/overloadRequest/overloadRequestInstructor.xhtml </span><br/><br/>"
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
						List<String> recipent = new ArrayList<String>();
						recipent.add(instructor.getMail());
						boolean status = sendEmail(recipent, htmlText,
								"Reminder for Pending Petition(s)");
						if (status) {
							instructors.add(instructor);
						}
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendDailyForInstructor() {
		sendDailyForAcadimecPetionInstructor();
		sendDailyForacAddDropFormInstructor();
		sendDailyForacChangeMajorInstructor();
		sendDailyForacOverLoadInstructor();

	}

	private void sendDailyForacOverLoadInstructor() {
		try {
			List<PendingPetitionCountObject> overLoadList = overloadRep
					.getInstructorPendingOverLoadPetition(false);
			for (PendingPetitionCountObject intsPet : overLoadList) {
				Employee instructor = intsPet.getInstructor();
				if (instructor.getMailSetting().getNotifyMe()) {
					Integer every = instructor.getMailSetting().getEveryDays();
					SimpleDateFormat dayFormat = new SimpleDateFormat(
							"ddMMyyyy");
					if (instructor.getMailSetting().getLastNotify() == null
							|| dayFormat.format(
									new Date(instructor.getMailSetting()
											.getLastNotify().getTime()
											+ (every * 86400000))).equals(
									dayFormat.format((new Date())))) {

						Long overLoad = intsPet.getPetionCount();
						if (overLoad != null && overLoad != 0l) {
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
									+ instructor.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>We would like to inform you that you have "
									+ overLoad
									+ " pending Overload Request(s)  needs an action</span><br/><br/>"
									+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
							List<String> recipent = new ArrayList<String>();
							recipent.add(instructor.getMail());
							boolean status = sendEmail(recipent, htmlText,
									"Reminder for Pending Petition(s)");
							if (status) {
								instructors.add(instructor);
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendDailyForacChangeMajorInstructor() {
		try {
			List<PendingPetitionCountObject> changeMajorList = changeMajorRep
					.getInstructorPendingChangMajorPetition(false);
			for (PendingPetitionCountObject intsPet : changeMajorList) {
				Employee instructor = intsPet.getInstructor();
				if (instructor.getMailSetting().getNotifyMe()) {
					Integer every = instructor.getMailSetting().getEveryDays();
					SimpleDateFormat dayFormat = new SimpleDateFormat(
							"ddMMyyyy");
					if (instructor.getMailSetting().getLastNotify() == null
							|| dayFormat.format(
									new Date(instructor.getMailSetting()
											.getLastNotify().getTime()
											+ (every * 86400000))).equals(
									dayFormat.format((new Date())))) {

						Long changeMajor = intsPet.getPetionCount();
						if (changeMajor != null && changeMajor != 0l) {
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
									+ instructor.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>We would like to inform you that you have "
									+ changeMajor
									+ " pending Change Major petition(s)  needs an action</span><br/><br/>"
									+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
							List<String> recipent = new ArrayList<String>();
							recipent.add(instructor.getMail());
							boolean status = sendEmail(recipent, htmlText,
									"Reminder for Pending Petition(s)");
							if (status) {
								instructors.add(instructor);
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendDailyForacAddDropFormInstructor() {
		try {
			List<PendingPetitionCountObject> dropAddList = dropAddPetRep
					.getInstructorPendingDropAddPetition(false);
			for (PendingPetitionCountObject intsPet : dropAddList) {
				Employee instructor = intsPet.getInstructor();
				if (instructor.getMailSetting().getNotifyMe()) {
					Integer every = instructor.getMailSetting().getEveryDays();
					SimpleDateFormat dayFormat = new SimpleDateFormat(
							"ddMMyyyy");
					if (instructor.getMailSetting().getLastNotify() == null
							|| dayFormat.format(
									new Date(instructor.getMailSetting()
											.getLastNotify().getTime()
											+ (every * 86400000))).equals(
									dayFormat.format((new Date())))) {

						Long dropAdd = intsPet.getPetionCount();
						if (dropAdd != null && dropAdd != 0l) {
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
									+ instructor.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>We would like to inform you that you have "
									+ dropAdd
									+ " pending drop/add petition(s)  needs an action</span><br/><br/>"
									+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
							List<String> recipent = new ArrayList<String>();
							recipent.add(instructor.getMail());
							boolean status = sendEmail(recipent, htmlText,
									"Reminder for Pending Petition(s)");
							if (status) {
								instructors.add(instructor);
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendDailyForAcadimecPetionInstructor() {
		try {
			List<PendingPetitionCountObject> acdimecList = academPetRep
					.getInstructorPendingCoursePetition(false);
			for (PendingPetitionCountObject intsPet : acdimecList) {
				Employee instructor = intsPet.getInstructor();
				if (instructor.getMailSetting().getNotifyMe()) {
					Integer every = instructor.getMailSetting().getEveryDays();
					SimpleDateFormat dayFormat = new SimpleDateFormat(
							"ddMMyyyy");
					if (instructor.getMailSetting().getLastNotify() == null
							|| dayFormat.format(
									new Date(instructor.getMailSetting()
											.getLastNotify().getTime()
											+ (every * 86400000))).equals(
									dayFormat.format((new Date())))) {

						Long acdimec = intsPet.getPetionCount();
						if (acdimec != null && acdimec != 0l) {
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
									+ instructor.getName()
									+ ",</span><br/><br/><br/>"
									+ "<span style=color:#676767>We would like to inform you that you have "
									+ acdimec
									+ " pending academic petition(s)  needs an action</span><br/><br/>"
									+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
							List<String> recipent = new ArrayList<String>();
							recipent.add(instructor.getMail());
							boolean status = sendEmail(recipent, htmlText,
									"Reminder for Pending Petition(s)");
							if (status) {
								instructors.add(instructor);
							}
						}
					}
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendDailyForAdmissionDept() {
		try {
			Employee instructor = insRep.getByMail(Constants.ADMISSION_DEPT);
			if (instructor.getMailSetting().getNotifyMe()) {
				Integer every = instructor.getMailSetting().getEveryDays();
				SimpleDateFormat dayFormat = new SimpleDateFormat("ddMMyyyy");
				if (instructor.getMailSetting().getLastNotify() == null
						|| dayFormat.format(
								new Date(instructor.getMailSetting()
										.getLastNotify().getTime()
										+ (every * 86400000))).equals(
								dayFormat.format((new Date())))) {
					List<CoursePetition> acdimec = academPetRep
							.getAdmissionDeptPendingCoursePetition(false);
					List<DropAddForm> addDrop = dropAddPetRep
							.getAdmissionDeptPendingDropAddForm(false);
					List<ChangeMajorForm> changeMajor = changeMajorRep
							.getAdmissionDeptPendingChangeMajorForm(false);
					List<OverloadRequest> overLoad = overloadRep
							.getAdmissionDeptPendingOverloadRequest(false);
					if ((overLoad != null && overLoad.size() != 0)
							|| (acdimec != null && acdimec.size() != 0)
							|| (addDrop != null && addDrop.size() != 0)
							|| (changeMajor != null && changeMajor.size() != 0)) {
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
								+ instructor.getName()
								+ ",</span><br/><br/><br/>";
						htmlText += "<span style=color:#676767>We would like to inform you that you have "
								+ " pending  petition(s)  needs an action:</span><br/><br/>";
				
						if (acdimec != null && acdimec.size() != 0) {
							htmlText += "<span style=color:#676767>Academic Petition(s):"
									+ acdimec.size()
									+ "petition(s)</span><br/><br/>";
						}
						if (addDrop != null && addDrop.size() != 0) {
							htmlText += "<span style=color:#676767>Drop/Add petition(s):"
									+ addDrop.size()
									+ " petition(s)</span><br/><br/>";
						}
						if (changeMajor != null && changeMajor.size() != 0) {
							htmlText += "<span style=color:#676767>Change Major petition(s):"
									+ changeMajor.size()
									+ " petition(s)</span><br/><br/>";
						}
						// TODO check if next step dean or provost
							if (overLoad != null && overLoad.size() != 0) {
							htmlText += "<span style=color:#676767>Overload Request petition(s):"
									+ overLoad.size()
									+ " petition(s)</span><br/><br/>";
						}
						
						htmlText += "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
						List<String> recipent = new ArrayList<String>();
						recipent.add(instructor.getMail());
						boolean status = sendEmail(recipent, htmlText,
								"Reminder for Pending Petition(s)");
						if (status) {
							instructors.add(instructor);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendDailyForAdmissionHead() {
		try {
			Employee instructor = insRep.getByMail(Constants.ADMISSION_HEAD);
			if (instructor.getMailSetting().getNotifyMe()) {
				Integer every = instructor.getMailSetting().getEveryDays();
				SimpleDateFormat dayFormat = new SimpleDateFormat("ddMMyyyy");
				if (instructor.getMailSetting().getLastNotify() == null
						|| dayFormat.format(
								new Date(instructor.getMailSetting()
										.getLastNotify().getTime()
										+ (every * 86400000))).equals(
								dayFormat.format((new Date())))) {
					List<CoursePetition> acdimec = academPetRep
							.getAdmissionHeadPendingCoursePetition(false);
					List<DropAddForm> addDrop = dropAddPetRep
							.getAdmissionHeadPendingDropAddForm(false);
					List<ChangeMajorForm> changeMajor = changeMajorRep
							.getAdmissionHeadPendingChangeMajorForm(false);
					List<OverloadRequest> overLoad = overloadRep
							.getAdmissionHeadPendingOverloadRequest(false);
					if ((overLoad != null && overLoad.size() != 0)
							|| (acdimec != null && acdimec.size() != 0)
							|| (addDrop != null && addDrop.size() != 0)
							|| (changeMajor != null && changeMajor.size() != 0)) {
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
								+ instructor.getName()
								+ ",</span><br/><br/><br/>";
						
						htmlText += "<span style=color:#676767>We would like to inform you that you have "
								+ " pending  petition(s)  needs an action:</span><br/><br/>";
				
						if (acdimec != null && acdimec.size() != 0) {
							htmlText += "<span style=color:#676767>Academic Petition(s):"
									+ acdimec.size()
									+ "petition(s)</span><br/><br/>";
						}
						if (addDrop != null && addDrop.size() != 0) {
							htmlText += "<span style=color:#676767>Drop/Add petition(s):"
									+ addDrop.size()
									+ " petition(s)</span><br/><br/>";
						}
						if (changeMajor != null && changeMajor.size() != 0) {
							htmlText += "<span style=color:#676767>Change Major petition(s):"
									+ changeMajor.size()
									+ " petition(s)</span><br/><br/>";
						}
						// TODO check if next step dean or provost
							if (overLoad != null && overLoad.size() != 0) {
							htmlText += "<span style=color:#676767>Overload Request petition(s):"
									+ overLoad.size()
									+ " petition(s)</span><br/><br/>";
						}
						
						htmlText += "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
						List<String> recipent = new ArrayList<String>();
						recipent.add(instructor.getMail());
						boolean status = sendEmail(recipent, htmlText,
								"Reminder for Pending Petition(s)");
						if (status) {
							instructors.add(instructor);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendDailyForDean() {
		try {
			Employee instructor = insRep
					.getByMail(Constants.DEAN_OF_STRATEGIC);
			if (instructor.getMailSetting().getNotifyMe()) {
				Integer every = instructor.getMailSetting().getEveryDays();
				SimpleDateFormat dayFormat = new SimpleDateFormat("ddMMyyyy");
				if (instructor.getMailSetting().getLastNotify() == null
						|| dayFormat.format(
								new Date(instructor.getMailSetting()
										.getLastNotify().getTime()
										+ (every * 86400000))).equals(
								dayFormat.format((new Date())))) {
					List<CoursePetition> acdimec = academPetRep
							.getDeanPendingCoursePetition(false);
					List<DropAddForm> addDrop = dropAddPetRep
							.getDeanPendingDropAddForm(false);
					List<ChangeMajorForm> changeMajor = changeMajorRep
							.getDeanPendingChangeMajorForm(false);
					List<OverloadRequest> overLoad = overloadRep
							.getDeanPendingOverloadRequest(false);
					if ((overLoad != null && overLoad.size() != 0)
							|| (acdimec != null && acdimec.size() != 0)
							|| (addDrop != null && addDrop.size() != 0)
							|| (changeMajor != null && changeMajor.size() != 0)) {
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
								+ instructor.getName()
								+ ",</span><br/><br/><br/>";
						htmlText += "<span style=color:#676767>We would like to inform you that you have "
								+ " pending  petition(s)  needs an action:</span><br/><br/>";
				
						if (acdimec != null && acdimec.size() != 0) {
							htmlText += "<span style=color:#676767>Academic Petition(s):"
									+ acdimec.size()
									+ "petition(s)</span><br/><br/>";
						}
						if (addDrop != null && addDrop.size() != 0) {
							htmlText += "<span style=color:#676767>Drop/Add petition(s):"
									+ addDrop.size()
									+ " petition(s)</span><br/><br/>";
						}
						if (changeMajor != null && changeMajor.size() != 0) {
							htmlText += "<span style=color:#676767>Change Major petition(s):"
									+ changeMajor.size()
									+ " petition(s)</span><br/><br/>";
						}
						if (overLoad != null && overLoad.size() != 0) {
							htmlText += "<span style=color:#676767>Overload Request petition(s):"
									+ overLoad.size()
									+ " petition(s)</span><br/><br/>";
						}
						htmlText += "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
						List<String> recipent = new ArrayList<String>();
						recipent.add(instructor.getMail());
						boolean status = sendEmail(recipent, htmlText,
								"Reminder for Pending Petition(s)");
						if (status) {
							instructors.add(instructor);
						}
					}
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean sendEmail(List<String> recipentList, String htmlText,
			String title) {
		if (recipentList != null)
		{
			for(int i=0;i<recipentList.size();i++){
		try {
			ApplicationContext springContext = WebApplicationContextUtils
					.getWebApplicationContext(ContextLoaderListener
							.getCurrentWebApplicationContext()
							.getServletContext());
			
			IGetInstructorDataAppService insDataService = (IGetInstructorDataAppService) springContext.getBean("IGetInstructorDataAppService");
			IPersonGetDataAppService studentDataService = (IPersonGetDataAppService) springContext.getBean("IPersonGetDataAppService");
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				Session session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(
										"learningtechnologies@zewailcity.edu.eg",
										"zcltinfo");
							}
						});

				javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[recipentList.size()];

				//addressTo[0] = new javax.mail.internet.InternetAddress("oalaaeddin@zewailcity.edu.eg");
				//addressTo[0] = new javax.mail.internet.InternetAddress("mshoieb@zewailcity.edu.eg");
				String newhtmlText="";
			    if(insDataService.getInsByMailNew(recipentList.get(i)).getId()!=null){
				 newhtmlText=htmlText.replace("$name$", insDataService.getInsByMailNew(recipentList.get(i)).getName());
			    }
			    else if(studentDataService.getPersonByPersonMail(recipentList.get(i))!=null)
			    {
			    	newhtmlText=htmlText.replace("$name$", studentDataService.getPersonByPersonMail(recipentList.get(i)).getNameInEng());
			    }
				
				// addressTo[0] = new javax.mail.internet.InternetAddress(
				// "helbadry@zewailcity.edu.eg");
				// addressTo[1] = new javax.mail.internet.InternetAddress(
				// "oalaaeddin@zewailcity.edu.eg");
				// addressTo[2] = new javax.mail.internet.InternetAddress(
				// "mshoieb@zewailcity.edu.eg");
				for (int x = 0; x < recipentList.size(); x++) {
					addressTo[x] = new javax.mail.internet.InternetAddress(
							recipentList.get(x));
				}

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(
						"learningtechnologies@zewailcity.edu.eg"));

				message.setRecipients(Message.RecipientType.TO, addressTo);

				message.setSubject(title);

				message.setContent(newhtmlText, "text/html; charset=ISO-8859-1");

				 Transport.send(message);
//				System.out.println("To:");
//				for (InternetAddress c : addressTo) {
//					System.out.println(c.getAddress());
//				}
//				System.out.println("Title:" + title);
//				System.out.println(htmlText);

				return true;

			
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}

		catch (Exception ex) {
			ex.printStackTrace();
			return false;

		}
			}
		return false;
		}
		return false;
	}

	@Override
	public void notifyNextStepOwner(List<String> recipMAil, String name,
			String content, String title) {
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
				+ "$name$"+" , "
				+ "</span><br/><br/>"
				+ "<span style=color:#676767>"
				+ content
				+ "</span><br/><br/>"
				+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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
		sendEmail(recipMAil, htmlText, title);

	}

	public String formateMail(String content, String name) {
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
				+ "$name$"+" , "
				+ "</span><br/><br/><br/>"
				+ "<span style=color:#676767>"
				+ content
				+ "</span><br/><br/>"
				+ "<span style=color:#676767><b>To access the System : http://lts.zclt.info/LearningTechnologiesServices/pages/public/login.xhtml </span><br/><br/>"
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

		return htmlText;
	}

}
