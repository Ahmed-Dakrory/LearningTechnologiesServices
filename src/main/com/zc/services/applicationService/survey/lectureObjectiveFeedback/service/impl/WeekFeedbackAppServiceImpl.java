/**
 * 
 */
package main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.configuration.services.ICoursesService;
import main.com.zc.services.applicationService.configuration.services.IStudentsCoursesNumberAppService;
import main.com.zc.services.applicationService.persons.service.IInstructorAppService;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.assembler.WeekFeedbackAssembler;
import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.ISemesterWeeksAppService;
import main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service.IWeekFeedbackAppService;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.IWeekFeedbackRepository;
import main.com.zc.services.domain.lectureObjectiveFeedback.model.WeekFeedback;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.CoursesPercentageDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackDTO;
import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.WeekFeedbackResultsDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
@Service
public class WeekFeedbackAppServiceImpl implements IWeekFeedbackAppService{

	@Autowired
	IWeekFeedbackRepository rep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	ICourseEvalQuestionsAppService questionAppService;
	@Autowired
	ICourseEvalQuestionsAppService courseEvalService;
	@Autowired
	ICourseStudentRep courseStudentRep;
	@Autowired
	ICoursesService coursesService;
	@Autowired
	IInstructorAppService insService;
	@Autowired
	IStudentsCoursesNumberAppService studentNumService;
	@Autowired
	ISemesterWeeksAppService weeksService;
	@Autowired
	ICourseEvalQuestionsAppService quesService;

	WeekFeedbackAssembler assem=new WeekFeedbackAssembler();
	
	@Override
	public List<WeekFeedbackDTO> getAll() {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getAll();
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByCategoryID(Integer id) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByCategoryID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByQuestID(Integer id) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByQuestID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByStudentID(Integer id) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByStudentID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByStudentIDAndCourseID(Integer id, Integer courseID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByStudentIDAndCourseID(id,courseID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByStudentIDAndCourseIDAndInstructor(Integer id, Integer courseID, Integer insID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByStudentIDAndCourseIDAndInstructor(id,courseID,insID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public WeekFeedbackDTO getByStudentIDAndCourseIDAndInstructorAndQuesID(Integer id, Integer courseID, Integer insID,
			Integer quesID) {
		WeekFeedbackDTO dto=new WeekFeedbackDTO();
		try
		{
			WeekFeedback feedback=rep.getByStudentIDAndCourseIDAndInstructorAndQuesID(id,courseID,insID,quesID);
		
				dto=assem.toDTO(feedback);
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public WeekFeedbackDTO getByQuestIDAndStudentIDAndCourseID(Integer quesID, Integer stID, Integer courseId) {
		WeekFeedbackDTO dto=new WeekFeedbackDTO();
		try
		{
			WeekFeedback feedback=rep.getByQuestIDAndStudentIDAndCourseID( quesID,  stID,  courseId);
		
				dto=assem.toDTO(feedback);
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public WeekFeedbackDTO getById(Integer id) {
		WeekFeedbackDTO dto=new WeekFeedbackDTO();
		try
		{
			WeekFeedback feedback=rep.getById(id);
		
				dto=assem.toDTO(feedback);
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public WeekFeedbackDTO add(WeekFeedbackDTO form) {
		try{
			WeekFeedback week=new WeekFeedback();
			week=assem.toEntity(form);
			WeekFeedback addedCopy=rep.add(week);
			WeekFeedbackDTO newDto=new WeekFeedbackDTO();
			newDto=assem.toDTO(addedCopy);
			return newDto;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public WeekFeedbackDTO update(WeekFeedbackDTO form) {
		try{
			WeekFeedback week=new WeekFeedback();
			week=assem.toEntity(form);
			WeekFeedback updated=rep.update(week);
			WeekFeedbackDTO newDto=new WeekFeedbackDTO();
			newDto=assem.toDTO(updated);
			return newDto;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean delete(WeekFeedbackDTO form) {
		try{ 
			
			return rep.delete(rep.getById(form.getId()));
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public WeekFeedbackDTO getByQuestIDAndStudentID(Integer id, Integer stID) {
		WeekFeedbackDTO dto=new WeekFeedbackDTO();
		try
		{
			WeekFeedback feedback=rep.getByQuestIDAndStudentID(id,stID);
		
				dto=assem.toDTO(feedback);
			
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<WeekFeedbackDTO> getAnswresByQuestionIDAndCourseIDAndInstructorID(Integer courseID, Integer insID,
			Integer quesID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getAnswresByQuestionIDAndCourseIDAndInstructorID(courseID,insID,quesID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getAnswresByQuesIDAndCourseID(Integer courseID, Integer quesID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getAnswresByQuesIDAndCourseID(courseID,quesID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseID(Integer courseID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByCourseID(courseID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseIDAndInsID(Integer courseId, Integer insID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByCourseIDAndInsID(courseId,insID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(Integer questionID, Integer courseID,
			Integer insID, Integer studentID) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByQuestionIDAndCourseIDAndInsIDAndStudentId(questionID,courseID,insID,studentID);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseIDAndInsIDAndStudentIdAndWeek(
			 Integer courseID, Integer insID,
			Integer studentID, Integer week) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByCourseIDAndInsIDAndStudentIdAndWeek(courseID,insID,
					 studentID,  week);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<WeekFeedbackDTO> getByCourseIDAndWeek(Integer courseID, Integer week) {
		List<WeekFeedbackDTO> dtos=new ArrayList<WeekFeedbackDTO>();
		try
		{
			List<WeekFeedback> all=rep.getByCourseIDAndWeek(courseID, week);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(assem.toDTO(all.get(i)));
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<CoursesPercentageDTO> getPercentageofCourse(SemesterWeeksDTO week) {
		List<CoursesPercentageDTO> dtos=new ArrayList<CoursesPercentageDTO>();
		try{
		//TODO
			//Get courses given semester and year for the given week
			List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
			week=weeksService.getById(week.getId());
			courses=coursesService.getCoursesByYearAndSemester(week.getYear(), week.getSemester());
			
		//1- Get distinct students feedback given course ID and week ID
			for(int i=0;i<courses.size();i++)
			{
				List<InstructorDTO> instructors=insService.getByCourseID(courses.get(i).getId());
				for(int j=0;j<instructors.size();j++)
				{
				List<WeekFeedback> feedbacks=rep.getDistinctStudentByCourseIDandWeekAndIns(courses.get(i).getId(), week.getId(),instructors.get(j).getId());
				//2- Get total number of registered students in this course
				if(feedbacks.size()>0){
				Integer total;
				try
				{
					total=studentNumService.getByCourseID(courses.get(i).getId()).getNum();
					//3- Calculate the percentage of participation at this course 
					//Percentage = (Obtained score x 100) / Total Score
					double percentage =((double)feedbacks.size()*100)/(double)total;
					CoursesPercentageDTO dto=new CoursesPercentageDTO();
					dto.setId(courses.get(i).getId());
					dto.setCoordinator(instructors.get(j));
					dto.setName(courses.get(i).getName());
					dto.setSemester(courses.get(i).getSemester());
					dto.setYear(courses.get(i).getYear());
					double roundOff = Math.round(percentage * 100.0) / 100.0;
					dto.setPersentage(roundOff);
					
					dtos.add(dto);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				
				}
				
				}
				
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		//4- Return the percentage and the course
		return dtos;
	}

	@Override
	public List<WeekFeedbackResultsDTO> getResultsDetails(Integer courseId, Integer insID, Integer weekID) {
		List<WeekFeedbackResultsDTO> results=new ArrayList<WeekFeedbackResultsDTO>();
		// TODO 
		//1- get questions of lecture objective feedback
		List<CourseEvalQuestionsDTO> questions=new ArrayList<CourseEvalQuestionsDTO>();
		questions=quesService.getBySectionID(QuestionsCategory.LECTURE_OBJECTIVES_FEEDBACK.getID());
	try{
		//2- Loop on questions
		for(int i=0;i<questions.size();i++)
		{
			WeekFeedbackResultsDTO dto=new WeekFeedbackResultsDTO();
			List<BaseDTO> selectionsStatistics=new ArrayList<BaseDTO>();
		//3- Loop on each selection in the question
			for(int j=0;j<questions.get(i).getScaleType().getSelections().size();j++)
			{
		//4- get feedback by courseID , insID , question ID , selection ID , week ID
				List<WeekFeedback> feedbacks=rep.getDistinctStudentByCourseIDandWeekAndInsAndQuesAndSelection
						(courseId, weekID,insID,questions.get(i).getId(),questions.get(i).getScaleType().getSelections().get(j).getId());
				
				//5- add selectionsStatistics list BaseDTO ID >> selection ID , name >> selection name , fileNO>> feedbacklst.size()
				BaseDTO selectionDTO=new BaseDTO();
				selectionDTO.setId(questions.get(i).getScaleType().getSelections().get(j).getId());
				selectionDTO.setName(questions.get(i).getScaleType().getSelections().get(j).getName());
				selectionDTO.setFileNo(feedbacks.size());
				selectionsStatistics.add(selectionDTO);
			
			}
			dto.setQuestionID(questions.get(i).getId());
			dto.setQuestionName(questions.get(i).getText());
			dto.setSelectionsStatistics(selectionsStatistics);
			results.add(dto);
		}
		
	
		
		
	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	//6- return  WeekFeedbackResultsDTO object
	
	return results;
	}

	@Override
	public Integer getTotalNumOfParticipatedStudents(SemesterWeeksDTO week) {
		// TODO Auto-generated method stub
		try{
		return rep.getTotalNumOfParticipatedStudents(week.getId());
		}
		catch(Exception ex){
			ex.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean sendEmail(CoursesPercentageDTO course, SemesterWeeksDTO week,List<WeekFeedbackResultsDTO> details) {
		// TODO 
		//1- write subject string
		String resultString="";
		for(int i=0;i<details.size();i++)
		{
			resultString+="<b> Q"+(i+1)+". </b>"+details.get(i).getQuestionName()+"<br/><br/>";
			resultString+="<table width='400'><tr>";
			for(int j=0;j<details.get(i).getSelectionsStatistics().size();j++)
			{
				if(details.get(i).getSelectionsStatistics().get(j).getId()==518)
				{
					resultString+="<td width='100' style=text-align:center><img src=\"http://zclt.info/LTS/icon_emo-happy-s.png\" alt=Happy Students/><br/>"+details.get(i).getSelectionsStatistics().get(j).getFileNo()+"<br/>student/s<br/></td>";
				}
				else if(details.get(i).getSelectionsStatistics().get(j).getId()==519)
				{
					resultString+="<td width='100' style=text-align:center><img src=\"http://zclt.info/LTS/icon_emo-average-s.png\" alt=Average Students/><br/>"+details.get(i).getSelectionsStatistics().get(j).getFileNo()+"<br/>student/s<br/></td>";
				}
				else if(details.get(i).getSelectionsStatistics().get(j).getId()==520)
				{
					resultString+="<td width='100' style=text-align:center><img src=\"http://zclt.info/LTS/icon_emo-sad-s.png\" alt=Average Students/><br/>"+details.get(i).getSelectionsStatistics().get(j).getFileNo()+"<br/>student/s<br/></td>";
				}
				
			}
			resultString+="</tr></table><br/>";
		}
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
				+ course.getCoordinator().getName()
				+ ",</span><br/><br/><br/>"
				+"We would like to inform you with lecture objective feedback for <b>"+weeksService.getById(week.getId()).getName()+".</b>"
				+"<br/><br/>"
				+"Course Name: <b>"+course.getName()+"</b><br/><br/>"+
				"The participation rate is :<b>"+course.getPersentage()+"%</b>"
				+"<br/><br/><br/>"
				+resultString
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
	
		boolean b=sendMail(course.getCoordinator().getMail(),htmlText,"Lecture Objective - "+	weeksService.getById(week.getId()).getName());
		return b;
	}
	public boolean sendMail(String email,String content,String subject){
	try{
			
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

			javax.mail.internet.InternetAddress[] addressTo = new javax.mail.internet.InternetAddress[1];
			
			//TODO testing environment
			/* addressTo[0] = new javax.mail.internet.InternetAddress(
			  "oalaaeddin@zewailcity.edu.eg");
			 addressTo[1] = new javax.mail.internet.InternetAddress(
					  "helbadry@zewailcity.edu.eg");*/
					
			 //TODO the real sender
			addressTo[0] = new javax.mail.internet.InternetAddress(email);
			//addressTo[0] = new javax.mail.internet.InternetAddress("oalaaeddin@zewailcity.edu.eg");
			javax.mail.internet.InternetAddress[] addressCC = new javax.mail.internet.InternetAddress[1];
			addressCC[0] = new javax.mail.internet.InternetAddress("teachingeffectiveness@zewailcity.edu.eg");



			message.setFrom(new InternetAddress(
					from));
			message.setRecipients(Message.RecipientType.TO, addressTo);
			message.setRecipients(Message.RecipientType.CC, addressCC);

			message.setSubject(subject);
            message.setText(content);

    		message.setContent(content, "text/html; charset=ISO-8859-1");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

			/*JavaScriptMessagesHandler.RegisterNotificationMessage("",
					"Please, Check Your Inbox");*/
			// System.out.println("Done sending ");
return true;
		

	} catch (Exception excep) {
		return false;

	}
	}
	}


