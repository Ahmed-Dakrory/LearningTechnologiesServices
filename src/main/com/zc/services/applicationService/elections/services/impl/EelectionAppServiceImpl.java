/**
 * 
 */
package main.com.zc.services.applicationService.elections.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import main.com.zc.services.applicationService.elections.services.IEelectionAppService;
import main.com.zc.services.domain.elections.model.ElectionCandidate;
import main.com.zc.services.domain.elections.model.ElectionCodes;
import main.com.zc.services.domain.elections.model.ElectionResults;
import main.com.zc.services.domain.elections.model.IElectionCandidateRepository;
import main.com.zc.services.domain.elections.model.IElectionCodesRepository;
import main.com.zc.services.domain.elections.model.IElectionsResultsRepository;
import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.FriendlyDate;
import main.com.zc.services.domain.time.model.Time;
import main.com.zc.services.presentation.elections.dto.ElectionResultDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

import org.apache.taglibs.standard.lang.jstl.ELException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * @author omnya
 *
 */
@Service
public class EelectionAppServiceImpl implements IEelectionAppService{

	@Autowired
	IElectionCandidateRepository electionCanRep;
	@Autowired
	IElectionsResultsRepository electionResultRep;
	@Autowired
	IStudentRepository personRep;
	@Autowired
	IElectionCodesRepository electionCodeRep;
	@Override
	public List<BaseDTO> getCandidateByType(int id) {
		try{
		List<ElectionCandidate> candidateList=electionCanRep.getAllByType(id);
		List<BaseDTO> candidates=new ArrayList<BaseDTO>();
		for(int i=0;i<candidateList.size();i++)
		{
			candidates.add(new BaseDTO(candidateList.get(i).getId(), candidateList.get(i).getName(), candidateList.get(i).getFacultyID(),candidateList.get(i).getImage()));
		}
		return candidates;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean addVote(ElectionResultDTO dto) {
		ElectionResults result=new ElectionResults();
		result.setStudentID(dto.getStudentID());
		try
		{
		try
		{
		result.setPreidentID(electionCanRep.getByID(dto.getPresident().getId()));
	    }
		catch (java.lang.IndexOutOfBoundsException ex) {
			System.out.println("No Vote for President");
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning president");
		}
		
		try
		{
		result.setViceID(electionCanRep.getByID(dto.getVice().getId()));
	    }
		catch (java.lang.IndexOutOfBoundsException ex) {
			System.out.println("No Vote for vice");
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning vice");
		}
		
		try
		{
			String act="";
			for(int i=0;i<dto.getActivities().size();i++)
			{
				act+="|"+dto.getActivities().get(i).getId();
				
			}
			if(act.startsWith("|"))
			{
				
				if(act.equals(""))
				{
					
						System.out.println("No activity memebers were selected");
						result.setActivties(null);
				}
				else 
				{
					result.setActivties(act.substring(1));
				}
				
			}
			else {
			
			if(act.equals(""))
			{
				
					System.out.println("No activity memebers were selected");
				
			}
			else 
			{
				result.setActivties(act);
			}
			}
			
	    }
		catch(Exception ex)
		{
			System.out.println("Error in assigning activities");
		}
		
		try
		{
			String service="";
			for(int i=0;i<dto.getServices().size();i++)
			{
				service+="|"+dto.getServices().get(i).getId();
				
			}
			if(service.startsWith("|"))
			{
			    if(service.equals(""))
			    	result.setServices(null);
			    else
				result.setServices(service.substring(1));
			}
			
			else {
				if(service.equals(""))
					result.setServices(null);
				else
			result.setServices(service);
			}
	    }
		catch(Exception ex)
		{
		}
		
		try
		{
			String acad="";
			for(int i=0;i<dto.getAcademic().size();i++)
			{
				acad+="|"+dto.getAcademic().get(i).getId();
				
			}
			if(acad.startsWith("|"))
			{
				if(acad.equals(""))
					result.setAcademic(null);
				else
				result.setAcademic(acad.substring(1));
			}
				else
				{
					if(acad.equals(""))
					 result.setAcademic(null);
					else
				result.setAcademic(acad);
				}
			
			
	    }
		catch(Exception ex)
		{
			System.out.println("Error in assigning academic");
		}
		
		try
		{
			  ElectionCandidate cand=electionCanRep.getByID(dto.getActivitiesPresident().getId());
			
				result.setActivitiesPresident(cand);
			
	    }
		catch (java.lang.IndexOutOfBoundsException ex) {
			System.out.println("No Vote for Avtivitt President");
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning activties president");
		}
		try
		{
			  ElectionCandidate cand=electionCanRep.getByID(dto.getServicesPresident().getId());
			
				result.setServicePresident(cand);
			
	    }
		catch (java.lang.IndexOutOfBoundsException ex) {
			System.out.println("No Vote for services President");
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning services president");
		}
		
		try
		{
			  ElectionCandidate cand=electionCanRep.getByID(dto.getAcademicPresident().getId());
			
				result.setAcademicPresident(cand);
			
	    }
		catch (java.lang.IndexOutOfBoundsException ex) {
			System.out.println("No Vote for academic President");
		}
		catch(Exception ex)
		{
			System.out.println("Error in assigning academic president");
		}
		
			electionResultRep.addResult(result);
			return true;
		}
		catch(Exception ex)
		{
		return false;
		}
	
	}
	@Override
	public boolean votedBefore(int stdeuntID) {
		try
		{
	ElectionResults result=electionResultRep.findByID(stdeuntID);
	if(result==null)
	{
		return false;
	}
	else 
		return true;
	}
	
	catch(IndexOutOfBoundsException ex)
		{
		return false;
		}
		}
	@Override
	public int totalNoOfVotes() {
		List<ElectionResults> results=new ArrayList<ElectionResults>();
		results=electionResultRep.getAll();
		
		return results.size();
	}

	@Override
	public List<ElectionResultDTO> resultsForPresident(int id) {
		try
		{
			List<ElectionResults> result=electionResultRep.getByPresidentID(id);
			List<ElectionResultDTO> presLstDTO=new ArrayList<ElectionResultDTO>();
            for(int i=0;i<result.size();i++)
            {
            	ElectionResultDTO dto=new ElectionResultDTO();
            	dto.setId(result.get(i).getId());
            /*	dto.setAcademic(new BaseDTO(result.get(i).getAcademicID().getId(),result.get(i).getAcademicID().getName()));
            	dto.setActivities(new BaseDTO(result.get(i).getActivitiesID().getId(),result.get(i).getActivitiesID().getName()));
            	dto.setServices(new BaseDTO(result.get(i).getServiceID().getId(),result.get(i).getServiceID().getName()));*/
            	dto.setPresident(new BaseDTO(result.get(i).getPreidentID().getId(),result.get(i).getPreidentID().getName()));
            	//dto.setVice(new BaseDTO(result.get(i).getViceID().getId(),result.get(i).getViceID().getName()));
            	presLstDTO.add(dto);
            }
	return presLstDTO;
		}
	catch(IndexOutOfBoundsException ex)
		{
		ex.printStackTrace();
		return null;
		}
	
		}
	
	@Override
	public List<ElectionResultDTO> resultsForVice(int id) {
		try
		{
			List<ElectionResults> result=electionResultRep.getByViceID(id);
			List<ElectionResultDTO> viceLstDTO=new ArrayList<ElectionResultDTO>();
            for(int i=0;i<result.size();i++)
            {
            	ElectionResultDTO dto=new ElectionResultDTO();
            	dto.setId(result.get(i).getId());
            /*	dto.setAcademic(new BaseDTO(result.get(i).getAcademicID().getId(),result.get(i).getAcademicID().getName()));
            	dto.setActivities(new BaseDTO(result.get(i).getActivitiesID().getId(),result.get(i).getActivitiesID().getName()));
            	dto.setServices(new BaseDTO(result.get(i).getServiceID().getId(),result.get(i).getServiceID().getName()));
            	dto.setPresident(new BaseDTO(result.get(i).getPreidentID().getId(),result.get(i).getPreidentID().getName()))*/;
            	dto.setVice(new BaseDTO(result.get(i).getViceID().getId(),result.get(i).getViceID().getName()));
            	viceLstDTO.add(dto);
            }
	return viceLstDTO;
		}
	catch(IndexOutOfBoundsException ex)
		{
		ex.printStackTrace();
		return null;
		}
	}
	@Override
	public List<ElectionResultDTO> resultsForAct(int id) {
		try
		{
			List<ElectionResults> result=electionResultRep.getByActvPresidentID(id);
			List<ElectionResultDTO> actLstDTO=new ArrayList<ElectionResultDTO>();
            for(int i=0;i<result.size();i++)
            {
            	ElectionResultDTO dto=new ElectionResultDTO();
            	dto.setId(result.get(i).getId());
            	/*dto.setAcademic(new BaseDTO(result.get(i).getAcademicID().getId(),result.get(i).getAcademicID().getName()));
            	dto.setActivities(new BaseDTO(result.get(i).getActivitiesID().getId(),result.get(i).getActivitiesID().getName()));
            	dto.setServices(new BaseDTO(result.get(i).getServiceID().getId(),result.get(i).getServiceID().getName()));
            	dto.setPresident(new BaseDTO(result.get(i).getPreidentID().getId(),result.get(i).getPreidentID().getName()));
            	dto.setVice(new BaseDTO(result.get(i).getViceID().getId(),result.get(i).getViceID().getName()));*/
            	dto.setActivitiesPresident(new BaseDTO(result.get(i).getActivitiesPresident().getId(),result.get(i).getActivitiesPresident().getName()));
            	actLstDTO.add(dto);
            }
	return actLstDTO;
		}
	catch(IndexOutOfBoundsException ex)
		{
		ex.printStackTrace();
		return null;
		}
	}
	@Override
	public List<ElectionResultDTO> resultsForService(int id) {
		try
		{
			List<ElectionResults> result=electionResultRep.getByServicePresidentID(id);
			List<ElectionResultDTO> serviceLstDTO=new ArrayList<ElectionResultDTO>();
            for(int i=0;i<result.size();i++)
            {
            	ElectionResultDTO dto=new ElectionResultDTO();
            	dto.setId(result.get(i).getId());
            	/*dto.setAcademic(new BaseDTO(result.get(i).getAcademicID().getId(),result.get(i).getAcademicID().getName()));
            	dto.setActivities(new BaseDTO(result.get(i).getActivitiesID().getId(),result.get(i).getActivitiesID().getName()));
            	dto.setServices(new BaseDTO(result.get(i).getServiceID().getId(),result.get(i).getServiceID().getName()));
            	dto.setPresident(new BaseDTO(result.get(i).getPreidentID().getId(),result.get(i).getPreidentID().getName()));
            	dto.setVice(new BaseDTO(result.get(i).getViceID().getId(),result.get(i).getViceID().getName()));*/
            	dto.setServicesPresident(new BaseDTO(result.get(i).getServicePresident().getId(),result.get(i).getServicePresident().getName()));
            	serviceLstDTO.add(dto);
            }
	return serviceLstDTO;
		}
	catch(IndexOutOfBoundsException ex)
		{
		ex.printStackTrace();
		return null;
		}
	}
	@Override
	public List<ElectionResultDTO> resultsForAcad(int id) {
		try
		{
			List<ElectionResults> result=electionResultRep.getByAcadPresidentID(id);
			List<ElectionResultDTO> acadLstDTO=new ArrayList<ElectionResultDTO>();
            for(int i=0;i<result.size();i++)
            {
            	ElectionResultDTO dto=new ElectionResultDTO();
            	dto.setId(result.get(i).getId());
            	/*dto.setAcademic(new BaseDTO(result.get(i).getAcademicID().getId(),result.get(i).getAcademicID().getName()));
            	dto.setActivities(new BaseDTO(result.get(i).getActivitiesID().getId(),result.get(i).getActivitiesID().getName()));
            	dto.setServices(new BaseDTO(result.get(i).getServiceID().getId(),result.get(i).getServiceID().getName()));
            	dto.setPresident(new BaseDTO(result.get(i).getPreidentID().getId(),result.get(i).getPreidentID().getName()));
            	dto.setVice(new BaseDTO(result.get(i).getViceID().getId(),result.get(i).getViceID().getName()));*/
            	dto.setAcademicPresident(new BaseDTO(result.get(i).getAcademicPresident().getId(),result.get(i).getAcademicPresident().getName()));
            	acadLstDTO.add(dto);
            }
	return acadLstDTO;
		}
	catch(IndexOutOfBoundsException ex)
		{
		ex.printStackTrace();
		return null;
		}
	}
	@Override
	public void  generateElectionCodes() {
		
		// add codes to all students 
		//1-get all persons from DB 
		
		List<Student> persons=personRep.getAll();
		for(int i=0;i<persons.size();i++)
		{
			try{
			ElectionCodes code=new ElectionCodes();
			String fileNo=Integer.toString(persons.get(i).getFileNo());
			//2-generate codes and add the new row for each person in election codes table
			String codes=generateUniqueExamCode();
			code.setElectionCode(codes);
			code.setFileNo(fileNo);
			electionCodeRep.addCode(code);
			
		
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			
			}
			
		}
	
		
		
		
		
		
		
	}
	public String generateUniqueExamCode()
	{
		
		String CODE_SPACE = "0123456789ABCDEF";
		
		int GeneratedCodeLength =4;
		int MaxIndex = CODE_SPACE.length()-1;
		
		Random rand = new Random(System.currentTimeMillis());
		//seed with random incremental number
		
		String GeneratedCode = "";
		boolean isUniqueCode = false;
		
		do
		{
			for (int i = 0; i < GeneratedCodeLength; i++) 
			{
				int randomeIndex = (int)Math.floor(rand.nextDouble()*MaxIndex);
				GeneratedCode = GeneratedCode + CODE_SPACE.charAt(randomeIndex);
			}
			
			//2- make sure that this code is not used before
		    ElectionCodes code=electionCodeRep.getByElectionCode(GeneratedCode);
		    if(code==null)
		    	isUniqueCode=true;
		    	else 
		    		isUniqueCode=false;
			
			
			
			
		}while(!isUniqueCode);
		
		//3 - return
		return GeneratedCode;
	}
	@Override
	public void sendEmailsWithCodes() {
	
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
		List<Student> persons=new ArrayList<Student>();
		persons=personRep.getAll();
		for(int i=0;i<persons.size();i++)
		{
			try {
				javax.mail.internet.InternetAddress[] addressBCC = new javax.mail.internet.InternetAddress[1];
				javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
				
				addressTo[0] = new javax.mail.internet.InternetAddress(
						"oalaaeddin@zewailcity.edu.eg");
				
				
				
				
					addressBCC[0] = new javax.mail.internet.InternetAddress(persons.get(i).getData().getMail());
				//	addressBCC[1] = new javax.mail.internet.InternetAddress("attendance@zewailcity.edu.eg");
					System.out.println("send to "+ persons.get(i).getData().getMail());
					
				

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("attendance@zewailcity.edu.eg"));
				
				message.setRecipients(Message.RecipientType.TO, addressTo);

				message.setSubject("Students' Union Elections");
				//TODO
				// Un-comment it for real case
				message.setRecipients(javax.mail.Message.RecipientType.BCC,
						addressBCC);
				String fileNo=Integer.toString(persons.get(i).getFileNo());
                ElectionCodes code=electionCodeRep.getByFileNo(fileNo);
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
				            	+"<h2 style=margin:0;padding:0;color:#404040 !important;>Students' Union Elections</h2>"
				            +"</li>"
				            +"<li style=list-style:none;float:left;width:700px;marin:0;background:#f2f0f0;>"
				            	+"<div style=padding:24px 36px;color:#676767 !important;>"
				                	+"<span style=color:#676767>Dear " +persons.get(i).getData().getNameInEnglish()+",</span><br/><br/><br/>"
				                    +"<span style=color:#676767>We would like to inform you with your code for Students' Union Elections:</span><br/><br/>"
				                    +"<span style=color:#676767><b>Your Election Code:</b> "+code.getElectionCode()+"</span><br/><br/>"
				                    +"<span style=color:#676767><b>Date:</b> Monday December 1, 2014</span><br/><br/>"
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
                message.setContent(htmlText, "text/html; charset=ISO-8859-1");
				/*message.setText("Dear " +persons.get(i).getData().getNameInEnglish()+","
						+"\n Code : "+code.getElectionCode()
						+ "\n Learning Technologies Department"
						+ "\n\n Please do not reply to this email ");*/

				Transport.send(message);

				code.setCodeRecevingStatus(true);
				electionCodeRep.update(code);
					
					//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_SENT_edit);
					//timeRep.update(time);
				
			
			} catch (MessagingException e) {
				
					//time.setSendMailStatus(Constants.SENT_MAIL_STATUS_FAILED_edited);
					//timeRep.update(time);
				
				throw new RuntimeException(e);
			}
			
			catch(Exception ex)
			{
				
				ex.printStackTrace();
			
			}
		}
	}
	@Override
	public boolean matchFileNoWithElcCode(int fileNo, String code) {
	
		String fileNoString=Integer.toString(fileNo);
		ElectionCodes electionCode=electionCodeRep.getByFileNo(fileNoString);
		if(electionCode!=null)
		{
			if(electionCode.getFileNo().equals(fileNoString)&&electionCode.getElectionCode().equals(code))
			{
				return true;
			}
			else return false;
		}
		else return false;
	}
	@Override
	public List<ElectionResultDTO> resultsForAcadPresident(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ElectionResultDTO> resultsForServicePresident(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ElectionResultDTO> resultsForActPresident(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BaseDTO> getAllActivtiesResults() {
		try
		{
			List<ElectionResults> results=new ArrayList<ElectionResults>();
			results=electionResultRep.getAll();
			List<BaseDTO> resultsDTO=new ArrayList<BaseDTO>();
			for(int i=0;i<results.size();i++)
			{
				BaseDTO dto=new BaseDTO();
				dto.setId(results.get(i).getId());
				dto.setName(results.get(i).getActivties());
				resultsDTO.add(dto);
			}
			return resultsDTO;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<BaseDTO> getAllServicesResults() {
		try
		{
			List<ElectionResults> results=new ArrayList<ElectionResults>();
			results=electionResultRep.getAll();
			List<BaseDTO> resultsDTO=new ArrayList<BaseDTO>();
			for(int i=0;i<results.size();i++)
			{
				BaseDTO dto=new BaseDTO();
				dto.setId(results.get(i).getId());
				dto.setName(results.get(i).getServices());
				resultsDTO.add(dto);
			}
			return resultsDTO;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public List<BaseDTO> getAllAcademicResults() {
		try
		{
			List<ElectionResults> results=new ArrayList<ElectionResults>();
			results=electionResultRep.getAll();
			List<BaseDTO> resultsDTO=new ArrayList<BaseDTO>();
			for(int i=0;i<results.size();i++)
			{
				BaseDTO dto=new BaseDTO();
				dto.setId(results.get(i).getId());
				dto.setName(results.get(i).getAcademic());
				resultsDTO.add(dto);
			}
			return resultsDTO;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	@Override
	public BaseDTO getCandByID(int id) {
	 try{
		ElectionCandidate candidate=electionCanRep.getByID(id);
		BaseDTO dto=new BaseDTO();
		dto.setId(candidate.getId());
		dto.setName(candidate.getName());
		return dto;
	 }
	 catch(Exception ex)
	 {
      ex.printStackTrace();
      return null;
	 }
		
	}
}
		
		

	
		
	


