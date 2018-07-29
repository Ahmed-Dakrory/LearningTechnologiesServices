/**
 * 
 */
package main.com.zc.services.applicationService.elections.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.elections.services.ISendEmailByVotingAppService;
import main.com.zc.services.domain.elections.model.ElectionCodes;
import main.com.zc.services.domain.elections.model.IElectionCandidateRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;

/**
 * @author omnya
 *
 */
@Service
public class SendEmailByVotingAppServiceImpl implements ISendEmailByVotingAppService {
 
	@Autowired
	IStudentRepository personRep;
    @Autowired
    IElectionCandidateRepository candidateRep;
    
	@Override
	public boolean sendEmailByVoting(ElectionResultDTO vote) {
		
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
								"oalaaeddin@zewailcity.edu.eg",
								"alaaeddin123456789nahed");
					}
				});
	
	
		try {
			javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[1];
			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			
			
			addressTo[0] = new javax.mail.internet.InternetAddress(
					"oalaaeddin@zewailcity.edu.eg");
			
			
				addressBCC[0] = new javax.mail.internet.InternetAddress(personRep.getPersonByFileNo(vote.getStudentID()).getData().getMail());
				//addressBCC[1] = new javax.mail.internet.InternetAddress("attendance@zewailcity.edu.eg");
				
					
				System.out.println("send to "+ personRep.getPersonByFileNo(vote.getStudentID()).getData().getMail());

			

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
			//TODO 
			//Comment it
			message.setRecipients(Message.RecipientType.TO, addressTo);

			message.setSubject("Students' Union Elections");
			//TODO Uncomment
			message.setRecipients(javax.mail.Message.RecipientType.BCC,
					addressBCC);
			  String actList="";
			 for(int j=0;j<vote.getActivities().size();j++)
	            {
	            	actList+=", "+candidateRep.getByID(vote.getActivities().get(j).getId()).getName();
	            }

	            if(actList.startsWith(","))
	            	actList=actList.replaceFirst(",","");
	            if(actList.equals(""))
	            {
	            	actList="No Vote";
	            }
	            System.out.println("Activties : "+actList);
            String academicList="";
            for(int j=0;j<vote.getAcademic().size();j++)
            {
            	academicList+=", "+candidateRep.getByID(vote.getAcademic().get(j).getId()).getName();
            }
          
            if(academicList.startsWith(","))
            	academicList=academicList.replaceFirst(",","");
            
            if(academicList.equals(""))
            {
            	academicList="No Vote";
            }
            System.out.println("Academic : "+academicList);
           
            String servList="";
            for(int j=0;j<vote.getServices().size();j++)
            {
            	servList+=", "+candidateRep.getByID(vote.getServices().get(j).getId()).getName();
            }
            if(servList.startsWith(","))
            	servList=servList.replaceFirst(",","");
            if(servList.equals(""))
            {
            	servList="No Vote";
            }
            System.out.println("Services : "+servList);


            //TODO UnComment it For general case
            //{
          /*  String president="";
            String vice="";
            if(candidateRep.getByID(vote.getVice().getId())==null){
            	vice="No Vote";
            }
            else 
            {
            	vice=candidateRep.getByID(vote.getVice().getId()).getName();
            }
            String activtiesPresident="";
            String servicesPresident="";
            
            String acadPresident="";
            if(candidateRep.getByID(vote.getAcademicPresident().getId())==null)
            {
            	acadPresident="No Vote";
            }
            else 
            {
            	acadPresident=candidateRep.getByID(vote.getAcademicPresident().getId()).getName();
            }
            
            String activties="";
            String services="";
            String academic="";
            String htmlText="<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>" 
			    	+"<ul style=margin:0;padding:0;>" 
			        	+"<li style=list-style:none;float:left;width:700px;margin:0;>"
			            +"	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
			                	+"<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
			                    +"<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
			                +"</ul>"
			            +"</li>"
			            +"<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
			            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Students' Union Elections Code</h2>"
			            +"</li>"
			            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
			            	+"<div style=padding:24px 36px;color:#676767 !important;>"
			                	+"<span style=color:#676767>Dear " +personRep.getPersonByFileNo(vote.getStudentID()).getData().getNameInEnglish() +",</span><br/><br/><br/>"
			                    +"<span style=color:#676767>You have voted successfully for:</span><br/><br/>"
			                    +"<span style=color:#676767><b>President:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+candidateRep.getByID(vote.getPresident().getId()).getName()+" </span><br/><br/>"
			                    +"<span style=color:#676767><b>Vice:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+candidateRep.getByID(vote.getVice().getId()).getName()+" </span><br/><br/>"
			                    +"<span style=color:#676767><b>Activties President:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+candidateRep.getByID(vote.getActivitiesPresident().getId()).getName() +"</span><br/><br/>"
			                    +"<span style=color:#676767><b>Service President:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+candidateRep.getByID(vote.getServicesPresident().getId()).getName() +"</span><br/><br/>"
			                    +"<span style=color:#676767><b>Academic President:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+candidateRep.getByID(vote.getAcademicPresident().getId()).getName() +" </span><br/><br/>"
			                    
			                    +"<span style=color:#676767><b>Academic section:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+academicList +" </span><br/><br/>"
			                    +"<span style=color:#676767><b>Activties section:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+actList +" </span><br/><br/>"
			                    +"<span style=color:#676767><b>Services section:</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+servList +" </span><br/><br/>"
			                    +"<br/>"
			                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
			                    +"<span style=color:#676767>Center for Learning Technologies</span>"
			                +"</div>"
						+"</li>"
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>" 
			            	+"<ul style=margin:0;padding:0;>" 
			                	+"<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
			            			+"<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
			                		+"<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
			                    +"</li>"
			                    +"<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
			                    	+"<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
			                    +"</li>"
			                +"</ul>" 
			            +"</li>" 
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
			            	+"<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
			                	+"<span style=color:#a1a0a0;font-size:11px;>Please do not reply directly to this email. If you have any questions or feedback, please send to </span><a href=mailto:contacts@zclt.info style=color:#00A7A6;fntsize:11px;>contacts@zclt.info</a>"
			                	+" <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
			                	+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
			                +"</div>"
			            +"</li>"
			            +"</ul>"
			        	+"</div>";*/
            //}
            //TODO Comment it For general case
            //{
            String president="";
            String vice="";
            if(candidateRep.getByID(vote.getVice().getId())==null){
            	vice="No Vote";
            }
            else 
            {
            	vice=candidateRep.getByID(vote.getVice().getId()).getName();
            }
            String activtiesPresident="";
            String servicesPresident="";
            
            String acadPresident="";
            if(candidateRep.getByID(vote.getAcademicPresident().getId())==null)
            {
            	acadPresident="No Vote";
            }
            else 
            {
            	acadPresident=candidateRep.getByID(vote.getAcademicPresident().getId()).getName();
            }
            
            String activties="";
            String services="";
            String academic="";
            String htmlText="<div style=width:700px;margin:0 auto;font:normal 13px/30px Segoe, Segoe UI, DejaVu Sans, Trebuchet MS, Verdana, sans-serif !important;>" 
			    	+"<ul style=margin:0;padding:0;>" 
			        	+"<li style=list-style:none;float:left;width:700px;margin:0;>"
			            +"	<ul style=margin:0;padding:0;width:700px;margin-top:18px;>"
			                	+"<li style=list-style:none;float:left;width:260px;padding:0;><img src=\"http://zclt.info/ZCTestMail/university_logo.png\" alt=Zewail City of Science and Technology /></li>"
			                    +"<li style=list-style:none;float:right;width:121px;padding:0;><img src=\"http://zclt.info/ZCTestMail/LT_logo_l.png\" alt=Center for Learning Technologies style=margin-top:4px; /></li>"
			                +"</ul>"
			            +"</li>"
			            +"<li style=list-style:none;float:left;width:700px;background:#f1f2f2;margin:15px 0 24px 0;padding:1px 0;>&nbsp;</li>"
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:24px;padding-left:24px;>"
			            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Students' Union Elections Code</h2>"
			            +"</li>"
			            	
			            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
			            	+"<div style=padding:24px 36px;color:#676767 !important;>"
			                	+"<span style=color:#676767>Dear " +personRep.getPersonByFileNo(vote.getStudentID()).getData().getNameInEnglish() +",</span><br/><br/><br/>"
			                    +"<span style=color:#676767>You have voted successfully for:</span><br/><br/>"
			                    +"<table cellpadding=6 style=border:0;line-height:26px;vertical-align:top;>" +
				            	"<tr>" +
				            	"<td style=width:300px><span style=color:#676767><b>Vice:</b></span></td>" +
				            	"<td style=width:40px>&nbsp;</td>" +
				            	"<td style=width:600px><span style=color:#676767>"+vice+"</span><br/></td>" +
				            	"</tr>" +
				            	
				            	"<tr>" +
				            	"<td><span style=color:#676767><b>Academic President:</b></span></td>" +
				            	"<td>&nbsp;</td>" +
				            	"<td><span style=color:#676767>"+acadPresident+"</span><br/></td>" +
				            	"</tr>" +
				            	
								"<tr>" +
								"<td><span style=color:#676767><b>Academic Committee Members:</b></span></td>" +
								"<td>&nbsp;</td>" +
								"<td><span style=color:#676767>"+academicList+"</span><br/></td>" +
								"</tr>" +
								
								"<tr>" +
								"<td><span style=color:#676767><b>Activties Committee Members:</b></span></td>" +
								"<td>&nbsp;</td>" +
								"<td><span style=color:#676767>"+actList+"</span><br/></td>" +
								"</tr>" +
								
								"<tr>" +
								"<td><span style=color:#676767><b>Services Committee Members:</b></span></td>" +
								"<td>&nbsp;</td>" +
								"<td><span style=color:#676767>"+servList+"</span><br/></td>" +
								"</tr>" +
								
				            	"</table>"
			                     +"<br/>"
			                    +"<span style=color:#676767>Thank you, </span><br/><br/>"
			                    +"<span style=color:#676767>Center for Learning Technologies</span>"
			                +"</div>"
						+"</li>"
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:4px;background:#ececec;>" 
			            	+"<ul style=margin:0;padding:0;>" 
			                	+"<li style=list-style:none;float:left;width:134px;margin:0;padding:18px 36px !important;color:#717070;>"
			            			+"<a href=http://www.zclt.info/ title=Center for Learning Technologies><img src=\"http://zclt.info/ZCTestMail/LT_logo_s.png\"  alt=Center for Learning Technologies /></a><br/>"
			                		+"<span style=color:#404040;font-size:11px;>Giving Fuel to Innovation</span>"
			                    +"</li>"
			                    +"<li style=list-style:none;float:right;width:59px;margin:0;padding:18px 36px !important;color:#717070;>"
			                    	+"<a href=http://www.zewailcity.edu.eg/ title=Zewail City of Science and Technology><img src=\"http://zclt.info/ZCTestMail/ZC_logo.png\"  alt=Zewail City of Science and Technology /></a>"
			                    +"</li>"
			                +"</ul>" 
			            +"</li>" 
			            +"<li style=list-style:none;float:left;width:700px;margin-bottom:12px;background:#ececec;>"
			            	+"<div style=padding:8px 16px;color:#a1a0a0;font-size:11px;line-height:20px;>"
			                	
			                	+" <br/><b><span style=color:#a1a0a0;font-size:11px;>Follow us:</sapn></b><a href=https://www.facebook.com/learning.technologies.zewailcity title=ZC LT Facebook><img src=\"http://zclt.info/ZCTestMail/facebook_square.png\"  alt=ZC LT Facebook style=vertical-align:middle;/></a>"
			                	+ "  <a href=https://www.youtube.com/channel/UCiajXXIv0rCpxVIgCDekm2A title=ZC LT Youtube><img src=\"http://zclt.info/ZCTestMail/youtube_square.png\"   alt=ZC LT Youtube style=vertical-align:middle;/></a>"
			                +"</div>"
			            +"</li>"
			            +"</ul>"
			        	+"</div>";
            //}
            message.setContent(htmlText, "text/html; charset=ISO-8859-1");
			
			Transport.send(message);

			
				
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT_edit);
				//timeRep.update(time);
			
		return true;
		} catch (MessagingException e) {
		
				//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED_edited);
				//timeRep.update(time);
			
			throw new RuntimeException(e);
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
			
		
		}
	
	}
}

