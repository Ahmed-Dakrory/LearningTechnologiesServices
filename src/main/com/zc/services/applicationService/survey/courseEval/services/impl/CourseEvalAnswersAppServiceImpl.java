/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.com.zc.services.applicationService.survey.courseEval.assembler.CourseEvalAnswersAssembler;
import main.com.zc.services.applicationService.survey.courseEval.assembler.CourseEvalOtherQuesAnswersAssempler;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalAnswersAppService;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.courseEval.model.CourseEvalAnswers;
import main.com.zc.services.domain.courseEval.model.CourseEvalOtherQuesAnswers;
import main.com.zc.services.domain.courseEval.model.ICourseEvalAnswersRep;
import main.com.zc.services.domain.courseEval.model.ICourseEvalOtherQuesAnswersRep;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.person.model.Student;
import main.com.zc.services.domain.shared.enumurations.QuestionsCategory;
import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalAnswersDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieItemLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.DefaultFontMapper;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author omnya
 *
 */
@Service
public class CourseEvalAnswersAppServiceImpl implements ICourseEvalAnswersAppService{

	@Autowired
	ICourseEvalAnswersRep rep;
	@Autowired
	ICourseEvalOtherQuesAnswersRep otherRep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	ICourseEvalQuestionsAppService questionAppService;
	@Autowired
	ICourseEvalQuestionsAppService courseEvalService;
	@Autowired
	ICourseStudentRep courseStudentRep;
	
	CourseEvalAnswersAssembler assem=new CourseEvalAnswersAssembler();
	CourseEvalOtherQuesAnswersAssempler otherQuesAssem=new CourseEvalOtherQuesAnswersAssempler();
	@Override
	public List<CourseEvalAnswersDTO> getAll() {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getAll();
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
	public List<CourseEvalAnswersDTO> getByCategoryID(Integer id) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getByCategoryID(id);
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
	public List<CourseEvalAnswersDTO> getByQuestID(Integer id) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getByQuestID(id);
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
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseID(Integer id,
			Integer courseID,Integer type) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getByStudentIDAndCourseID(id, courseID,type);
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
	public List<CourseEvalAnswersDTO> getByStudentIDAndCourseIDAndInstructor(
			Integer id, Integer courseID, Integer insID) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getByStudentIDAndCourseIDAndInstructor(id, courseID, insID);
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
	public List<CourseEvalAnswersDTO> getByStudentID(Integer id) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getByStudentID(id);
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
	public CourseEvalAnswersDTO getById(Integer id) {
		CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
		try
		{
			CourseEvalAnswers ans=rep.getById(id);
			dto=assem.toDTO(ans);
					
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public CourseEvalAnswersDTO add(CourseEvalAnswersDTO form,Integer type) {
		try{
		CourseEvalAnswers ans=new CourseEvalAnswers();
		ans=assem.toEntity(form);
		ans.setType(type);
		if(form.getInstructor()!=null){
			if(form.getInstructor().getId()!=null)
			{
		
		/*CourseEvalAnswers oldAnswer=rep.getByStudentIDAndCourseIDAndInstructorAndQuesID(form.getStudent().getId(),
				form.getCourse().getId(), form.getInstructor().getId(), form.getQuestion().getId());
	*/	CourseEvalAnswers oldAnswer=rep.getByStudentIDAndCourseIDAndInstructorAndQuesIDAndAnsID
			(form.getStudent().getId(),
			form.getCourse().getId(), form.getInstructor().getId(),
			form.getQuestion().getId(),form.getSelections());
	
				if(oldAnswer==null)
		
		{
			ans=rep.add(ans);
		}
		else {
			//Double check
			if(oldAnswer.getId()==null)
			{
				ans=rep.add(ans);
			}
		}
		}}	else 
			{
				/*List<CourseEvalAnswersDTO> oldanswers=getByQuestionIDAndCourseIDAndStudentId(form.getQuestion().getId(), form.getCourse().getId(),
						form.getStudent().getId());*/
				CourseEvalAnswers oldanswers=rep.getByQuestionIDAndCourseIDAndStudentIdAndAns(form.getQuestion().getId(), form.getCourse().getId(),
						form.getStudent().getId(),form.getSelections());
				if(oldanswers==null)
				{
					ans=rep.add(ans);
				}
				else if(oldanswers.getId()==null)
				{
					ans=rep.add(ans);
				}
			}
		
/*		else 
		{
			List<CourseEvalAnswersDTO> oldanswers=getByQuestionIDAndCourseIDAndStudentId(form.getQuestion().getId(), form.getCourse().getId(),
					form.getStudent().getId());
			if(oldanswers==null)
			{
				ans=rep.add(ans);
			}
			else if(oldanswers.size()==0)
			{
				ans=rep.add(ans);
			}
		}*/
		
		CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
		dto=assem.toDTO(ans);
		return dto;
		}
		catch(Exception ex)
		{
			System.out.println("Can't add ans");
			ex.printStackTrace();
		return null;
		}
	}

	@Override
	public CourseEvalAnswersDTO update(CourseEvalAnswersDTO form) {
		try{
			CourseEvalAnswers ans=new CourseEvalAnswers();
			ans=assem.toEntity(form);
			ans=rep.update(ans);
			CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
			dto=assem.toDTO(ans);
			return dto;
			}
			catch(Exception ex)
			{
				System.out.println("Can't add ans");
				ex.printStackTrace();
			return null;
			}
	}

	@Override
	public boolean delete(CourseEvalAnswersDTO form) {
		try
		{
			return rep.delete(assem.toEntity(form));
		}
		catch(Exception ex)
		{
			System.out.println("Can't delete ans");
			ex.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deleteAllcourseData(CourseEvalAnswersDTO form) {
		System.out.println("Ahmed Dakrory: Delete All forms with this data1: "+form.getCourse().getName());
		System.out.println("Ahmed Dakrory: Delete All forms with this data1New: "+(assem.toEntity(form)).getCourse().getName());
		
		try
		{
			return rep.deleteAllcourseDataNew(form);
		}
		catch(Exception ex)
		{
			System.out.println("Can't delete ans");
			ex.printStackTrace();
		}
		return false;
	}
	

	
	@Override
	public List<CourseEvalAnswersDTO> getAllOtherAnswers() {
		
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalOtherQuesAnswers> all=otherRep.getAll();
			for(int i=0;i<all.size();i++)
			{
				dtos.add(otherQuesAssem.toDTO(all.get(i)));
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
	public List<CourseEvalAnswersDTO> getByCategoryIDOtherAns(Integer id) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalOtherQuesAnswers> all=otherRep.getByCategoryID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(otherQuesAssem.toDTO(all.get(i)));
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
	public List<CourseEvalAnswersDTO> getByQuestIDOtherAns(Integer id) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalOtherQuesAnswers> all=otherRep.getByQuestID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(otherQuesAssem.toDTO(all.get(i)));
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
	public List<CourseEvalAnswersDTO> getByStudentIDOtherAns(Integer id) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalOtherQuesAnswers> all=otherRep.getByStudentID(id);
			for(int i=0;i<all.size();i++)
			{
				dtos.add(otherQuesAssem.toDTO(all.get(i)));
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
	public CourseEvalAnswersDTO getByIdOtherAns(Integer id) {
		CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
		try
		{
			CourseEvalOtherQuesAnswers ans=otherRep.getById(id);
			dto=otherQuesAssem.toDTO(ans);
					
			
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
		}
		return dto;
	}

	@Override
	public CourseEvalAnswersDTO addOtherAns(CourseEvalAnswersDTO form) {
		try{
			CourseEvalOtherQuesAnswers ans=new CourseEvalOtherQuesAnswers();
			ans=otherQuesAssem.toEntity(form);
			ans=otherRep.add(ans);
			CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
			dto=otherQuesAssem.toDTO(ans);
			return dto;
			}
			catch(Exception ex)
			{
				System.out.println("Can't add ans");
				ex.printStackTrace();
			return null;
			}
	}

	@Override
	public CourseEvalAnswersDTO updateOtherAns(CourseEvalAnswersDTO form) {
		try{
			CourseEvalOtherQuesAnswers ans=new CourseEvalOtherQuesAnswers();
			ans=otherQuesAssem.toEntity(form);
			ans=otherRep.update(ans);
			CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
			dto=otherQuesAssem.toDTO(ans);
			return dto;
			}
			catch(Exception ex)
			{
				System.out.println("Can't add ans");
				ex.printStackTrace();
			return null;
			}
	}

	@Override
	public boolean deleteOtherAns(CourseEvalAnswersDTO form) {
		try
		{
			return otherRep.delete(otherQuesAssem.toEntity(form));
		}
		catch(Exception ex)
		{
			System.out.println("Can't delete ans");
			ex.printStackTrace();
		}
		return false;
	}

	

	@Override
	public List<CoursesDTO> getCoursesByInsID(Integer insID) {
		
		List<CoursesDTO> courses =new ArrayList<CoursesDTO>();
		try{
		List<Courses_Instructors> courseInsLst=courseInsRep.getByInstructorId(insID);
		for(int i=0;i<courseInsLst.size();i++)
		{
			CoursesDTO course=new  CoursesDTO();
			course.setId(courseInsLst.get(i).getCourse().getId());
			course.setName(courseInsLst.get(i).getCourse().getName());
			courses.add(course);
		}
		}
		catch(Exception ex)
		{
			System.out.println("Can't get the courses of instructor");
			ex.printStackTrace();
			
		}
		return courses;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getFacEvalByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> facQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		facQuestions=questionAppService.getByCategoryID(1);
		for(int i=0;i<facQuestions.size();i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			int nACount=0;
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuestionIDAndCourseIDAndInstructorID(courseID, insID, facQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==1)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==2)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==3)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==4)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==5)
				{
					stronglyAgreeCount++;
				}
				else if(answers.get(j).getSelections()==6)
				{
					nACount++;
				}
				
			}
			model.set("Strongly Disagree : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Disagree : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Neutral : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Agree : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Strongly Agree : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			model.set("N/A : "+nACount+" Stduent(s)", nACount);
			facQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return facQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getCourseByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(2);
		for(int i=0;i<courseQuestions.size();i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			int nACount=0;
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==1)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==2)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==3)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==4)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==5)
				{
					stronglyAgreeCount++;
				}
				else if(answers.get(j).getSelections()==6)
				{
					nACount++;
				}
				
			}
			model.set("Strongly Disagree : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Disagree : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Neutral : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Agree : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Strongly Agree : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			model.set("N/A : "+nACount+" Stduent(s)", nACount);
			courseQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return courseQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getCourseAssignByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(3);
		
		// handle yes or no
				List<CourseEvalAnswers> lastQuestionAnswres=rep.getAnswresByQuesIDAndCourseID(courseID,courseQuestions.get(courseQuestions.size()-1).getId());
				PieChartModel model1=new PieChartModel();
				int yesCount=0;
				int noCount=0;
				for(int j=0;j<lastQuestionAnswres.size();j++)
				{
				/*	if(answers.get(j).getComment()!=null)
					{
						comments.add(answers.get(j).getComment());
					}*/
					
					if(lastQuestionAnswres.get(j).getSelections()==7)
					{
						yesCount++;
					}
					else if(lastQuestionAnswres.get(j).getSelections()==8)
					{
						noCount++;
					}
					
					
				}
				model1.set("Yes : "+yesCount+" Stduent(s)", yesCount);
				model1.set("No : "+noCount+" Stduent(s)", noCount);
				
				courseQuestions.get(courseQuestions.size()-1).setModel(model1);
				
				
		for(int i=0;i<courseQuestions.size()-1;i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			int nACount=0;
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==1)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==2)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==3)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==4)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==5)
				{
					stronglyAgreeCount++;
				}
				else if(answers.get(j).getSelections()==6)
				{
					nACount++;
				}
				
			}
			model.set("Strongly Disagree : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Disagree : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Neutral : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Agree : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Strongly Agree : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			model.set("N/A : "+nACount+" Stduent(s)", nACount);
			courseQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return courseQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getTAByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(4);
		for(int i=0;i<courseQuestions.size();i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			int nACount=0;
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==1)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==2)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==3)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==4)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==5)
				{
					stronglyAgreeCount++;
				}
				else if(answers.get(j).getSelections()==6)
				{
					nACount++;
				}
				
			}
			model.set("Strongly Disagree : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Disagree : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Neutral : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Agree : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Strongly Agree : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			model.set("N/A : "+nACount+" Stduent(s)", nACount);
			courseQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return courseQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getLabFacByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(5);
		for(int i=0;i<courseQuestions.size();i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			int nACount=0;
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==1)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==2)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==3)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==4)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==5)
				{
					stronglyAgreeCount++;
				}
				else if(answers.get(j).getSelections()==6)
				{
					nACount++;
				}
				
			}
			model.set("Strongly Disagree : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Disagree : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Neutral : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Agree : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Strongly Agree : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			model.set("N/A : "+nACount+" Stduent(s)", nACount);
			courseQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return courseQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getLabExpByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(6);
		for(int i=0;i<courseQuestions.size();i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			int nACount=0;
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==1)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==2)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==3)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==4)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==5)
				{
					stronglyAgreeCount++;
				}
				else if(answers.get(j).getSelections()==6)
				{
					nACount++;
				}
				
			}
			model.set("Strongly Disagree : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Disagree : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Neutral : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Agree : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Strongly Agree : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			model.set("N/A : "+nACount+" Stduent(s)", nACount);
			courseQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return courseQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getOthersTextByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(7);
		for(int i=0;i<courseQuestions.size();i++)
		{
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			List<String>comments=new ArrayList<String>();
			for(int j=0;j<answers.size();j++)
			{
				if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}
			}
			courseQuestions.get(i).setComments(comments);
		}
		return courseQuestions;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getOthersRateByCourseIDAndInstructorID(
			Integer courseID, Integer insID) {
		// get all faculty questions by category id from question app service
		// Loop on the list and get the answers of each question
		// Categorize the answers to strongly disagree and disagree and neutral and agree and strongly agree
		
		List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
		courseQuestions=questionAppService.getByCategoryID(0);
		for(int i=0;i<courseQuestions.size();i++)
		{
			int stronglyDisAgreeCount=0;
			int disAgreeCount=0;
			int netrualCount=0;
			int agreeCount=0;
			int stronglyAgreeCount=0;
			
			PieChartModel model=new PieChartModel();
			//List<String> comments=new ArrayList<String>();
			List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID, courseQuestions.get(i).getId());
			for(int j=0;j<answers.size();j++)
			{
			/*	if(answers.get(j).getComment()!=null)
				{
					comments.add(answers.get(j).getComment());
				}*/
				
				if(answers.get(j).getSelections()==9)
				{
					stronglyDisAgreeCount++;
				}
				else if(answers.get(j).getSelections()==10)
				{
					disAgreeCount++;
				}
				else if(answers.get(j).getSelections()==11)
				{
					netrualCount++;
				}
				else if(answers.get(j).getSelections()==12)
				{
					agreeCount++;
				}
				else if(answers.get(j).getSelections()==13)
				{
					stronglyAgreeCount++;
				}
				
				
			}
			model.set("Much Less : "+stronglyDisAgreeCount+" Stduent(s)", stronglyDisAgreeCount);
			model.set("Less : "+disAgreeCount+" Stduent(s)", disAgreeCount);
			model.set("Same : "+netrualCount+" Stduent(s)", netrualCount);
			model.set("Greater : "+agreeCount+" Stduent(s)", agreeCount);
			model.set("Much Greater : "+stronglyAgreeCount+" Stduent(s)", stronglyAgreeCount);
			courseQuestions.get(i).setModel(model);
			//facQuestions.get(i).setComments(comments);
		}
		
		return courseQuestions;
	}

	@Override
	public List<String> getCommentsByCategoryIDAndCourseIDAndInstructorID(
			Integer courseID, Integer insID, Integer categoryID) {
		// get all faculty answers by course id and instructor id 
		List<CourseEvalAnswers> facAnswers=rep.getCommentsByCategoryIDAndCourseIDAndInstructorID(courseID, insID, categoryID);
		List<String> comments=new ArrayList<String>();
		//initialize the list
		if(comments.size()==0)
		{
			for(int i=0;i<facAnswers.size();i++)
			{
				if(facAnswers.get(i).getComment()!=null)
				{
					comments.add(facAnswers.get(i).getComment());
					break;
				}
			}
		}
		if(comments.size()>0)
		{
			for(int i=0;i<facAnswers.size();i++)
			{
				if(facAnswers.get(i).getComment()!=null)
				{
				boolean existed=false;
				for(int j=0;j<comments.size();j++)
				{
					if(comments.get(j).equals(facAnswers.get(i).getComment()))
					{
						existed=true;
					
					break;
					}
				}
				if(!existed)
				{
					comments.add(facAnswers.get(i).getComment());
				}
			}}
		}
		return comments;
	}

	@Override
	public List<String> getCommentsByCategoryIDAndCourseID(Integer courseID,
			Integer categoryID) {
		// get all faculty answers by course id and instructor id 
		List<CourseEvalAnswers> facAnswers=rep.getCommentsByCategoryIDAndCourseID(courseID, categoryID);
		List<String> comments=new ArrayList<String>();
		//initialize the list
		if(comments.size()==0)
		{
			for(int i=0;i<facAnswers.size();i++)
			{
				if(facAnswers.get(i).getComment()!=null)
				{
					comments.add(facAnswers.get(i).getComment());
					break;
				}
			}
		}
		if(comments.size()>0)
		{
			for(int i=0;i<facAnswers.size();i++)
			{
				if(facAnswers.get(i).getComment()!=null)
				{
				boolean existed=false;
				for(int j=0;j<comments.size();j++)
				{
					
					if(comments.get(j).equals(facAnswers.get(i).getComment()))
					{
						existed=true;
					
					break;
					}
				}
				if(!existed)
				{
					comments.add(facAnswers.get(i).getComment());
				}
				}
			}
		}
		return comments;
	}

	@Override
	public List<StudentDTO> getTotalStudentsOfCourse(Integer courseID,Integer surveyID) {
		try
		{
			List<Student> students=rep.getTotalStudentsOfCourse(courseID,surveyID);
			List<StudentDTO> dtos= new  ArrayList<StudentDTO>();
			for(int i=0;i<students.size();i++)
			{
				StudentDTO dto=new StudentDTO();
				dto.setId(students.get(i).getId());
				dto.setFacultyId(students.get(i).getFileNo());
				dto.setName(students.get(i).getData().getNameInEnglish());
				dto.setMail(students.get(i).getData().getMail());
				dtos.add(dto);
			}
			return dtos;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<CourseEvalAnswersDTO> getByCourseID(Integer courseID) {
		List<CourseEvalAnswersDTO> dtos=new ArrayList<CourseEvalAnswersDTO>();
		try
		{
			List<CourseEvalAnswers> all=rep.getByCourseID(courseID);
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
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseID(
			Integer questionID, Integer courseID) {
		List<CourseEvalAnswersDTO> answersDTO=new ArrayList<CourseEvalAnswersDTO>();
		try{
		List<CourseEvalAnswers> answers=rep.getAnswresByQuesIDAndCourseID(courseID ,questionID);
		for(int i=0;i<answers.size();i++){
			answersDTO.add(assem.toDTO(answers.get(i)));
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return answersDTO;
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsID(
			Integer questionID, Integer courseID, Integer insID) {
		List<CourseEvalAnswersDTO> answersDTO=new ArrayList<CourseEvalAnswersDTO>();
		try{
		List<CourseEvalAnswers> answers=rep.getAnswresByQuestionIDAndCourseIDAndInstructorID(courseID, insID, questionID);
		for(int i=0;i<answers.size();i++){
			answersDTO.add(assem.toDTO(answers.get(i)));
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return answersDTO;
	}

	@Override
	public List<CourseEvalAnswersDTO> getByCourseIDAndInsID(Integer courseID,
			Integer insID) {
		List<CourseEvalAnswersDTO> answersDTO=new ArrayList<CourseEvalAnswersDTO>();
		try{
		List<CourseEvalAnswers> answers=rep.getByCourseIDAndInsID(courseID, insID);
		for(int i=0;i<answers.size();i++){
			answersDTO.add(assem.toDTO(answers.get(i)));
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return answersDTO;
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndInsIDAndStudentId(
			Integer questionID, Integer courseID, Integer insID,
			Integer studentID) {
		List<CourseEvalAnswersDTO> answersDTO=new ArrayList<CourseEvalAnswersDTO>();
		try{
		List<CourseEvalAnswers> answers=rep.getByQuestionIDAndCourseIDAndInsIDAndStudentId(questionID,courseID,insID,
				studentID);
		for(int i=0;i<answers.size();i++){
			answersDTO.add(assem.toDTO(answers.get(i)));
		}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return answersDTO;
	}

	@Override
	public List<CourseEvalAnswersDTO> getByQuestionIDAndCourseIDAndStudentId(
			Integer questionID, Integer courseID, Integer studentID) {
		List<CourseEvalAnswersDTO> answersDTO=new ArrayList<CourseEvalAnswersDTO>();
			try{
			CourseEvalAnswers answers=rep.getByQuestIDAndStudentIDAndCourseID(questionID, studentID, courseID);
			
			if(answers!=null)
				answersDTO.add(assem.toDTO(answers));
			
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			return answersDTO;
	}

	@Override
	public List<CourseEvalAnswersDTO> getEvalAnsByCourseIdAndEmpIdAndStudentId(
			Integer courseId, Integer empId, Integer studentId) {
		
		List<CourseEvalAnswersDTO> answersDTOs = new ArrayList<CourseEvalAnswersDTO>();
		
		List<CourseEvalAnswers> answers = rep.getByStudentIDAndCourseIDAndInstructor(studentId, courseId, empId);
		
		CourseEvalAnswersAssembler assem = new CourseEvalAnswersAssembler();
		
		for (CourseEvalAnswers courseEvalAnswers : answers) {
			
			answersDTOs.add(assem.toDTO(courseEvalAnswers));
		}
		
		return answersDTOs;
	}

	@Override
	public CourseEvalAnswersDTO addInstAns(CourseEvalAnswersDTO form,Integer type) {
		try{
			
			CourseEvalAnswers ans=new CourseEvalAnswers();
			ans=assem.toEntity(form);
			ans.setSubmissionDate(Calendar.getInstance());
			ans.setType(type);
			ans=rep.addInst(ans);
			
			CourseEvalAnswersDTO dto=new CourseEvalAnswersDTO();
			dto=assem.toDTO(ans);
			
			return dto;
		}
		catch(Exception ex)
		{
			System.out.println("Can't add ans");
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CourseEvalQuestionsDTO> getCourseEvalDynamic(Integer courseID,
			Integer category) {
		
		try {
		
			List<CourseEvalQuestionsDTO> courseQuestions=new ArrayList<CourseEvalQuestionsDTO>();
			
			// 1. get course questions.
			
			courseQuestions = questionAppService.getBySectionID(category);										
			
			for (CourseEvalQuestionsDTO courseEvalQuestionsDTO : courseQuestions) {
				
				PieChartModel model=new PieChartModel();										
				
				// 2. get question answers.
				
				List<CourseEvalAnswers> answers = rep.getAnswresByQuesIDAndCourseID(courseID, courseEvalQuestionsDTO.getId());
				
				// 3. get question selections.
				
				List<ScaleSelectionsDTO> selections = new ArrayList<ScaleSelectionsDTO>();
				
				if(courseEvalQuestionsDTO.getScaleType() != null)
					
					selections = courseEvalQuestionsDTO.getScaleType().getSelections();
				
				else { // in case of text questions exist in language of instructions 
					
					List<String> comments = new ArrayList<String>();
					
					for (CourseEvalAnswers courseEvalAnswers : answers) {
						
						if(courseEvalAnswers.getComment()!=null && courseEvalAnswers.getComment()!="")
							comments.add(courseEvalAnswers.getComment());
					}
					
					courseEvalQuestionsDTO.setComments(comments);
				}
				
				// 4. count student selection.
				
				for (ScaleSelectionsDTO scaleSelectionsDTO : selections) {
				
					int selectionCount = 0;
					
					for (CourseEvalAnswers courseEvalAnswers : answers) {
					
						if(scaleSelectionsDTO.getId().equals(courseEvalAnswers.getSelections())){
							
							selectionCount++;
						}														
					}
					
					model.set(scaleSelectionsDTO.getName() + " " + selectionCount + " Stduent(s)", selectionCount);
				}
				
				courseEvalQuestionsDTO.setModel(model);				
			}
			
			return courseQuestions;
			
		} catch (Exception e) {
			
			e.printStackTrace();	
			return null;
		}
						
	}

	@Override
	public List<CourseEvalInsQuestionsDTO> getInstCourseEvalDynamic(
			Integer courseID, Integer category, Integer instId) {
		
		try {
			
			List<CourseEvalInsQuestionsDTO> questions = new ArrayList<CourseEvalInsQuestionsDTO>();
			
			// 1. get questions of category - instructor, TA, Lab
			
			questions = questionAppService.getInstSectionQuestions(category);
			
			for (CourseEvalInsQuestionsDTO courseEvalInsQuestionsDTO : questions) {
			
				// 2. get answers of question depend on selected instructor
				
				List<CourseEvalAnswers> answers = new ArrayList<CourseEvalAnswers>(); 
				
				if (instId == null) // case of lab questions
					
					answers = rep.getAnswresByQuesIDAndCourseID(courseID, courseEvalInsQuestionsDTO.getId());					
				else 				
					answers = rep.getAnswresByQuestionIDAndCourseIDAndInstructorID
						(courseID, instId, courseEvalInsQuestionsDTO.getId());
				
				PieChartModel mainModel = new PieChartModel();
				
				// 3. get selections of question
				
				List<ScaleSelectionsDTO> selections = courseEvalInsQuestionsDTO.getScaleType().getSelections();												
				
				List<PieChartModel> strenghtModels = new ArrayList<PieChartModel>();
				List<PieChartModel> concernModels = new ArrayList<PieChartModel>();
				
				List<String> strengthsText = new ArrayList<String>();
				List<String> concernsText = new ArrayList<String>();
				
				for (ScaleSelectionsDTO scaleSelectionsDTO : selections) {
														
					if (scaleSelectionsDTO.getType().equals(ScaleSelectionTypeEnum.MAIN)) {
						
						// 4. count student answers for main selection
						
						int mainSelectionCount = 0;				
						
						// 5. list of students who answer by this type of main selection
						
						List<Integer> studentIds = new ArrayList<Integer>();
						
						for (CourseEvalAnswers courseEvalAnswers : answers) {
							
							if(courseEvalAnswers.getSelections().equals(scaleSelectionsDTO.getId())) {
								
								mainSelectionCount++;
								
								studentIds.add(courseEvalAnswers.getStudent().getId());								
							}													
						}
																						
						mainModel.set(scaleSelectionsDTO.getName() + " " + mainSelectionCount + " Stduent(s)", mainSelectionCount);
						
						PieChartModel strengthModel = new PieChartModel();
						PieChartModel concernModel = new PieChartModel();
						
						for (ScaleSelectionsDTO scaleSubSelectionsDTO : selections) {													
							
							if(!scaleSubSelectionsDTO.getType().equals(ScaleSelectionTypeEnum.MAIN)){
							
								int selectionCount = 0;							
													
								for (Integer studentId : studentIds) {
									
									List<CourseEvalAnswers> studentAns = new ArrayList<CourseEvalAnswers>();
									
									if (instId == null){ // case of lab questions
										studentAns = rep.getAllByQuestIDAndStudentIDAndCourseID
											(courseEvalInsQuestionsDTO.getId(), studentId, courseID);										
									}
									else 
										studentAns = rep.getByQuestionIDAndCourseIDAndInsIDAndStudentId
											(courseEvalInsQuestionsDTO.getId(), courseID, instId, studentId);
																
									for (CourseEvalAnswers courseEvalAnswers : studentAns) {
								
										if(courseEvalAnswers.getSelections().equals(scaleSubSelectionsDTO.getId())){
											selectionCount++;
											
											if(courseEvalAnswers.getComment()!=null && courseEvalAnswers.getComment()!="") {
												if(scaleSubSelectionsDTO.getType().equals(ScaleSelectionTypeEnum.STRENGTH))
													strengthsText.add(courseEvalAnswers.getComment());
												else
													concernsText.add(courseEvalAnswers.getComment());
											}
										}
																																				
									}								
								}
								
								if(scaleSubSelectionsDTO.getType().equals(ScaleSelectionTypeEnum.STRENGTH))
									strengthModel.set(scaleSubSelectionsDTO.getName() + " " + selectionCount + " Stduent(s)", selectionCount);
								else 
									concernModel.set(scaleSubSelectionsDTO.getName() + " " + selectionCount + " Stduent(s)", selectionCount);
							
							}
						}
						
						strenghtModels.add(strengthModel);
						concernModels.add(concernModel);						
					}
				
				}
				
				courseEvalInsQuestionsDTO.setStrengthMainSelection("Very good");
				courseEvalInsQuestionsDTO.setConcernMainSelection("Very good");
				
				courseEvalInsQuestionsDTO.setModel(mainModel);
				courseEvalInsQuestionsDTO.setStrengthModels(strenghtModels);
				courseEvalInsQuestionsDTO.setConcernModels(concernModels);
				
				courseEvalInsQuestionsDTO.setStrengthsSelection(strengthsText);
				courseEvalInsQuestionsDTO.setConcernsSelection(concernsText);
			}			
			
			return questions;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void exportResultsToPDF(CoursesDTO courseDto) {
		
		try {
			
			// 1. Get Course Evaluation Results.			
			List<CourseEvalQuestionsDTO> courseEval = new ArrayList<CourseEvalQuestionsDTO>();
			courseEval = getCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Course_Eval.getID());
			
			// 2. Get Student Evaluation Results.			
			List<CourseEvalQuestionsDTO> studentEval = new ArrayList<CourseEvalQuestionsDTO>();
			studentEval = getCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Student_Eval.getID());
			
			// 3. Get Lab Evaluation Results.			
			List<CourseEvalInsQuestionsDTO> labEval = new ArrayList<CourseEvalInsQuestionsDTO>();
			labEval = getInstCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Lab_Asis.getID(), null);
			
			// 4. Get Language of Instructions Evaluation Results.			
			List<CourseEvalQuestionsDTO> langOfInstEval = new ArrayList<CourseEvalQuestionsDTO>();
			langOfInstEval = getCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Languag_Of_Instruction.getID());
			
			// 5. Get Other Comments.
			List<CourseEvalQuestionsDTO> otherComments = new ArrayList<CourseEvalQuestionsDTO>();
			otherComments = getCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Other_Comments.getID());
			
			/*************************** Draw PDF **************************/
			
			// 1. Create PDF document
			
			Document document = new Document();			
			//File dir = new File("C:\\Users\\Omnya Alaa\\Desktop\\courses-pdf-summer-2016\\"+courseDto.getName().replaceAll("/","_"));
			File dir = new File("/home/omnya/Desktop/courses-pdf-fall-2016/"
					+courseDto.getName().replace("/","_")+"/");
			dir.mkdirs();
			File file = new File(dir, courseDto.getName().replaceAll("/","_")+".pdf");
			FileOutputStream out = new FileOutputStream(file);
			
			// 2. create PDF Writer
								
	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        document.open();
			
			createPDF(courseEval, courseDto, document, writer, "Course Evaluation:");
			document.newPage();
			
			createPDF(studentEval, courseDto, document, writer, "Student Evaluation:");
			document.newPage();
			
			createInstPDF(labEval, courseDto, document, writer, "Lab Evaluation:");
			document.newPage();
			
			createPDF(langOfInstEval, courseDto, document, writer, "Language of Instructions Evaluation:");
			document.newPage();
			
			createPDF(otherComments, courseDto, document, writer, "Other Comments:");
			
			// 3. close PDF document and PDF writer
			
			document.close();
	        writer.close();
	        
	        /*********************** End of first PDF *******************/
	        
	        // get list of course instructors
	        List<InstructorDTO> instructors = new ArrayList<InstructorDTO>();
	        instructors = courseEvalService.getInstructorsByCourseID(courseDto.getId());
	        
	        for (InstructorDTO instructorDTO : instructors) {
				
	        	List<CourseEvalInsQuestionsDTO> instQuestions = new ArrayList<CourseEvalInsQuestionsDTO>();
	        	instQuestions =  getInstCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Instructor_Asis.getID(), instructorDTO.getId());
	        	
	        	// 1. Create PDF document
				
				document = new Document();			
				//dir = new File("/home/omnya/Desktop/Courses/"+courseDto.getName().replaceAll("/","_")+instructorDTO.getName().replaceAll("/","_"));
				//dir = new File("C:\\Users\\Omnya Alaa\\Desktop\\courses-pdf-summer-2016\\"+courseDto.getName().replaceAll("/","_"));
				 dir = new File("/home/omnya/Desktop/courses-pdf-fall-2016/"
						+courseDto.getName().replace("/","_")+"/");
			
				dir.mkdirs();
				file = new File(dir, courseDto.getName().replaceAll("/","_")+instructorDTO.getName().replaceAll("/","_")+".pdf");
				out = new FileOutputStream(file);
				
				// 2. create PDF Writer
									
		        writer = PdfWriter.getInstance(document, out);
		        document.open();		
				
		        createInstPDF(instQuestions, courseDto, document, writer, null);
		        
				// 3. close PDF document and PDF writer
				
				document.close();
		        writer.close();
			}
	        
	        // get list of course TAs
	        instructors = courseEvalService.getTasByCourseID(courseDto.getId());
	        
	        for (InstructorDTO instructorDTO : instructors) {
				
	        	List<CourseEvalInsQuestionsDTO> instQuestions = new ArrayList<CourseEvalInsQuestionsDTO>();
	        	instQuestions =  getInstCourseEvalDynamic(courseDto.getId(), QuestionsCategory.Teaching_Asis.getID(), instructorDTO.getId());
	        	
	        	// 1. Create PDF document
				
				document = new Document();			
				//dir = new File("/home/omnya/Desktop/Courses/"+courseDto.getName().replaceAll("/","_")+instructorDTO.getName().replaceAll("/","_"));
				//dir = new File("C:\\Users\\Omnya Alaa\\Desktop\\courses-pdf-summer-2016\\"+courseDto.getName().replaceAll("/","_"));
				 dir = new File("/home/omnya/Desktop/courses-pdf-fall-2016/"
						+courseDto.getName().replace("/","_")+"/");
			
				dir.mkdirs();
				file = new File(dir, courseDto.getName().replaceAll("/","_")+instructorDTO.getName().replaceAll("/","_")+".pdf");
				out = new FileOutputStream(file);
				
				// 2. create PDF Writer
									
		        writer = PdfWriter.getInstance(document, out);
		        document.open();		
				
		        createInstPDF(instQuestions, courseDto, document, writer, null);
		        
				// 3. close PDF document and PDF writer
				
				document.close();
		        writer.close();
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	
	private void createPDF(List<CourseEvalQuestionsDTO> evalQuestions, CoursesDTO courseDto, 
			Document document, PdfWriter writer, String title) {
		/*
		try {			
	        
			Font blue = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(255, 0, 0, 0));
			
			Font arabic=FontFactory.getFont("/home/omnya/Desktop/arial.ttf",BaseFont.IDENTITY_H,9,Font.NORMAL);
			
	        // student total count
	        List<StudentDTO> students = getTotalStudentsOfCourse(courseDto.getId());
	        
	        int totalNumberOfStudentsInCourse = getStudentsNumberOfCourse(courseDto.getId());
	        
	        double totalPercentage=((double)students.size()/(double)totalNumberOfStudentsInCourse)*100;
	        
	        long totalFactor = (long) Math.pow(10, 2);
	        totalPercentage = totalPercentage * totalFactor;
  		    long totalTmp = Math.round(totalPercentage);	                 
	        
	        document.add(new Paragraph(courseDto.getName() + "/ total number of students : " + students.size() + " / " + totalNumberOfStudentsInCourse + " - " + (double) totalTmp / totalFactor + "%", blue));
	        
	        // Draw course evaluation -- page 1
	        
	        document.add(new Paragraph(title));
	        
	        int i=0;
	        
	        for (CourseEvalQuestionsDTO question : evalQuestions) {							    	  	      
	         
	        	i++;
	        	Integer courseEvalSubmitted=0;
	        	
	        	if(question.getScaleType()!=null) {
	        			        			        		        	
		        	DefaultPieDataset myPiedataset = new DefaultPieDataset();
		        	
		        	int x=0;
		        	
		        	for (Map.Entry<String, Number> entry : question.getModel().getData().entrySet())
		        	{   	        			        		        			        	
		        		myPiedataset.setValue(question.getScaleType().getSelections().get(x).getName(), entry.getValue());
		        		courseEvalSubmitted+=(Integer)entry.getValue();	        		
		        		x++;
		        	}
	
		        	double first = courseEvalSubmitted;
		        	double second = students.size();
		        	double percentage=(first/second)*100;		        	      				       
	
	      		    long factor = (long) Math.pow(10, 2);
	      		    percentage = percentage * factor;
	      		    long tmp = Math.round(percentage);	         
	              
	      		    document.add(new Paragraph(i+"- "+question.getText()+"( " + courseEvalSubmitted+" of " + students.size() + " - " + (double) tmp / factor +" % )"));
			      	      		    
	      		    *//********************** Draw Pie chart *******************//*
	      		    
	      		    JFreeChart PDFPieChart = ChartFactory.createPieChart("", myPiedataset,false,true,false);
	
			        final PiePlot plot = (PiePlot) PDFPieChart.getPlot();
			        plot.setBackgroundPaint(Color.white);
			        plot.setCircular(true);          
			        plot.setLabelGenerator(new StandardPieItemLabelGenerator(
			        		"{0},{2}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
			        ));
			        plot.setNoDataMessage("No data available");
			        plot.setSectionPaint(1, new Color(217,179, 39));
			        plot.setSectionPaint(2,new Color(69, 140, 65));
			        plot.setSectionPaint(3, new Color(207, 38, 39));
		            plot.setSectionPaint(4,new Color(92, 51, 97));
		            plot.setSectionPaint(5, new Color(94, 48, 50));
		            plot.setShadowYOffset(0);
		            plot.setShadowXOffset(0);
		            plot.setBaseSectionOutlinePaint(Color.white);
		            	            
		            int style = Font.UNDEFINED ;
		            java.awt.Font font = new java.awt.Font("Garamond", style , 7);
		            StandardLegend legend = new StandardLegend();
		            legend.setPreferredWidth(120);
		            legend.setPadding(new RectangleInsets(10, 60, 5, 10));
		            legend.setAnchor(Legend.EAST);
		            legend.setItemFont(font);
		            
			        PDFPieChart.setLegend(legend);
			        PDFPieChart.setBackgroundPaint(Color.white);
			        PDFPieChart.setBorderPaint(Color.white);
			        
			        plot.setLabelFont(font);
			        
			        PdfContentByte Add_Chart_Content= writer.getDirectContent();
			        PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,210);
			        Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,210,new DefaultFontMapper());                
			        Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,210);
			        PDFPieChart.draw(Graphics_Chart,Chart_Region);            
			        Graphics_Chart.dispose();
			        
			        Image chartImage = Image.getInstance(template_Chart_Holder);
			        document.add(chartImage);
			       
			        if(title.equals("Student Evaluation:") && i == 6){
			        	document.newPage();
			        }
			        
	        	}else {
	        		
	        		double first = question.getComments().size();
		        	double second = students.size();
		        	double percentage=(first/second)*100;		        	      				       
	
	      		    long factor = (long) Math.pow(10, 2);
	      		    percentage = percentage * factor;
	      		    long tmp = Math.round(percentage);
	        		
	        		document.add(new Paragraph(i+"- "+question.getText()+"( " + question.getComments().size()+" of " + students.size() + " - " + (double) tmp / factor +" % )"));
	        		
	        		// draw text questions
	        		
	        		Integer ii = 0;
	        		PdfPTable table=new PdfPTable(1);
	                table.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
	        		
	        		for (String comment : question.getComments()) {
	        			ii++;
	        				        					                
		                PdfPCell cell;		                 
		                cell=new PdfPCell(new Phrase(ii.toString() + ". " + comment, arabic));
		                cell.disableBorderSide(1);		               
		                cell.disableBorderSide(5);
		                cell.disableBorderSide(8);
		                cell.setPaddingBottom(20);
		                cell.setPaddingTop(20);
		                cell.setBorderColor(new BaseColor(224,222, 222));
		                
		                table.addCell(cell);		                 		               	        			
//	        			document.add(new Paragraph(ii.toString() + ". " + comment, arabic));
					}
	        		
	        		document.add(table);
	        	}	        		        
	        	
//		        LineSeparator separator = new LineSeparator();
//		        separator.setPercentage(59500f / 523f);
//		        Chunk linebreak = new Chunk(separator);		        		       
	        }	        	       
	        
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
	}

	
	private void createInstPDF(List<CourseEvalInsQuestionsDTO> evalQuestions, CoursesDTO courseDto, 
			Document document, PdfWriter writer, String title) {
		
		/*try {			
	        
			Font blue = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(255, 0, 0, 0));
	        Font fontques=FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD);
	        
	        Font arabic=FontFactory.getFont("/home/omnya/Desktop/arial.ttf",BaseFont.IDENTITY_H,7,Font.NORMAL);
	        // student total count
	        List<StudentDTO> students = getTotalStudentsOfCourse(courseDto.getId());
	        
//	        document.add(new Paragraph(courseDto.getName() + "/ total number of students : " + students.size(),blue));
	        
	        int totalNumberOfStudentsInCourse = getStudentsNumberOfCourse(courseDto.getId());
	        
	        double totalPercentage=((double)students.size()/(double)totalNumberOfStudentsInCourse)*100;
	        
	        long totalFactor = (long) Math.pow(10, 2);
	        totalPercentage = totalPercentage * totalFactor;
	        long totalTmp = Math.round(totalPercentage);	                 
	        	        
	        document.add(new Paragraph(courseDto.getName() + "/ total number of students : " + students.size() + " / " + totalNumberOfStudentsInCourse + " - " + (double) totalTmp / totalFactor + "%", blue));
	        
	        // Draw course evaluation -- page 1
	        
	        if(title != null && title != "")	        
	        	document.add(new Paragraph(title));
	        
	        int i=0;
	        
	        PdfPTable table;
	        
	        for (CourseEvalInsQuestionsDTO question : evalQuestions) {							    	  	      	       
	            
	        	i++;
	        	Integer courseEvalSubmitted=0;	        		        
	        			        			        		        	
	        	DefaultPieDataset mainPiedataset = new DefaultPieDataset();
	        	
	        	int x=0;
	        	
	        	for (Map.Entry<String, Number> entry : question.getModel().getData().entrySet())
	        	{  String temp="";
	        		if(entry.getKey().contains("Very Good")||entry.getKey().contains("Needs Improvement")||entry.getKey().contains("Below Average"))	
	        		 temp=entry.getKey().substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1)).substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1)).substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1));
	        		else {
	        			 temp=entry.getKey().substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1)).substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1)).substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1))
	   	        				.substring(0, entry.getKey().indexOf(" ", entry.getKey().indexOf(" ") + 1));
	        			 int ind = entry.getKey().indexOf(" ");
	        			 String word = entry.getKey().substring(0, ind);
	        			 String rest = entry.getKey().substring(ind);
	        			 temp=word;
	        		}
	   	        	
	        		mainPiedataset.setValue(temp, entry.getValue());
	        		
	        		if(question.getScaleType().getSelections().get(x).getName().equals("Very good") || 
	        			question.getScaleType().getSelections().get(x).getName().equals("Fine") || 
	        			question.getScaleType().getSelections().get(x).getName().equals("Needs improvement")) 
	        	//
	        		//if(entry.getKey().equals(ScaleSelectionTypeEnum.MAIN))
	        		if(temp.contains("Very Good".trim()) || 
	        				temp.contains("Average".trim()) || 
	        				temp.contains("Needs Improvement".trim())||
	        				temp.contains("Good".trim()) || 
	        				temp.contains("Below Average".trim())) 
	        			courseEvalSubmitted+=(Integer)entry.getValue();
	        		
	        		
	        		x++;
	        	}
	        	
	        	double first = courseEvalSubmitted;
	        	double second = students.size();
	        	double percentage=(first/second)*100;	        	      

      		    long factor = (long) Math.pow(10, 2);
      		    percentage = percentage * factor;
      		    long tmp = Math.round(percentage);	         
              
      		    document.add(new Paragraph(i+"- "+question.getText()+"( " + courseEvalSubmitted+" of " + students.size() + " - " + (double) tmp / factor +" % )"));		            		   
      		    
      		    // Table configuration
      		    
	        	table = new PdfPTable(1);
	        	
	        	table.setWidthPercentage(100);	
	        	
	        	PdfPTable nestedTable = new PdfPTable(2);
	        	
	        	nestedTable.setWidthPercentage(100);
	        	
	        	table.setSpacingBefore(5f);	        	
	        	
	        	PdfPCell h1 = new PdfPCell();
	            PdfPCell h2 = new PdfPCell();
	            PdfPCell h3 = new PdfPCell();
	        	
	            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
	            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
	            
	        	PdfPCell cell1 = new PdfPCell();
	            PdfPCell cell2 = new PdfPCell();
	            PdfPCell cell3 = new PdfPCell();	        	            	                 		   
	            
	            h1.addElement(new Paragraph("Overall Rating", fontques));
	            h2.addElement(new Paragraph("Specific Strengths", fontques));
	            h3.addElement(new Paragraph("Specific Concerns", fontques));	            
	            
	            table.addCell(h1);
	            
	            nestedTable.addCell(h2);
	            nestedTable.addCell(h3);
	                  		    
      		    *//********************** Draw Pie chart *******************//*
      		    
      		    JFreeChart PDFMainPieChart = ChartFactory.createPieChart("", mainPiedataset,false,true,false);

		        final PiePlot plot = (PiePlot) PDFMainPieChart.getPlot();
		        plot.setBackgroundPaint(Color.white);
		        plot.setCircular(true);          
		        plot.setLabelGenerator(new StandardPieItemLabelGenerator(
		        		"{0}={1} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()
		        ));
		        plot.setNoDataMessage("No data available");
		        plot.setSectionPaint(1, new Color(217,179, 39));
		        plot.setSectionPaint(2,new Color(69, 140, 65));
		        plot.setSectionPaint(3, new Color(207, 38, 39));
	            plot.setSectionPaint(4,new Color(92, 51, 97));
	            plot.setSectionPaint(5, new Color(94, 48, 50));
	            plot.setShadowYOffset(0);
	            plot.setShadowXOffset(0);
	            plot.setBaseSectionOutlinePaint(Color.white);
	            	            
	            int style = Font.UNDEFINED ;
	            java.awt.Font font = new java.awt.Font("Garamond", style , 7);
	            plot.setLabelFont(font);
	            
		        PDFMainPieChart.setBackgroundPaint(Color.white);
		        PDFMainPieChart.setBorderPaint(Color.white);		        		       
		        
		        PdfContentByte Add_Chart_Content= writer.getDirectContent();
		        PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(400,90);
		        Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(400,90,new DefaultFontMapper());                
		        Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,400,90);
		        PDFMainPieChart.draw(Graphics_Chart,Chart_Region);            
		        Graphics_Chart.dispose();
		        
		        Image mainChartImage = Image.getInstance(template_Chart_Holder);
		        
		        cell1.addElement(mainChartImage);
		     //   cell1.addElement(mainChartImage);		       	        	
		        // strengths pie charts		       
		        
		        int mainSelection = 0;
		        
		        int strenghtStart = x;		        		       
		        
		        for (PieChartModel pieModel : question.getStrengthModels()) {
					
		        	PdfPTable strNestedTable = new PdfPTable(1); 
		        	strNestedTable.setWidthPercentage(100);
		        	
		        	strenghtStart = x;
		        	
		        	DefaultPieDataset strPiedataset = new DefaultPieDataset();
		        	
		        	for (Map.Entry<String, Number> entry : pieModel.getData().entrySet()) {
						
		        		//strPiedataset.setValue(question.getScaleType().getSelections().get(strenghtStart).getName(), entry.getValue());
		        		 String s = entry.getKey();
			     		    Pattern pattern = Pattern.compile("^\\D*(\\d)");
			     		    Matcher matcher = pattern.matcher(s);
			     		    matcher.find();
			     		  //  System.out.println(s.substring(0,matcher.start(1)));
			     		   strPiedataset.setValue(s.substring(0,matcher.start(1)), entry.getValue());
		        		strenghtStart++;
					}
		        	
		        	String mainTitle = "";
		        	
		        	switch (mainSelection) {
					case 0:
						mainTitle = "Very good - Strengths";
						break;

					case 1:
						mainTitle = "Good - Strengths";
						break;	
					
					case 2:
						mainTitle = "Average - Strengths";
						break;	
					case 3:
						mainTitle = "Below Average - Strengths";
						break;

					case 4:
						mainTitle = "Needs Improvement - Strengths";
						break;	
					
				
					}
		        	
		        	strNestedTable.addCell(new Paragraph(mainTitle, fontques));
		        			        	
		        	mainSelection++;
		        	
		        	*//********************** Draw Pie chart *******************//*
	      		    
			        strNestedTable.addCell(drawPieChart(strPiedataset, writer));
			        
			        cell2.addElement(strNestedTable);
				}		        		       
		        
		        // concerns pie charts
		        
		        mainSelection = 0;
		        int concernStart = strenghtStart;
		        		    		        
		        for (PieChartModel pieModel : question.getConcernModels()) {
					
		        	PdfPTable concernNestedTable = new PdfPTable(1); concernNestedTable.setWidthPercentage(100);
		        	
		        	concernStart = strenghtStart;
		        	
		        	DefaultPieDataset ConcernPiedataset = new DefaultPieDataset();
		        	
		        	for (Map.Entry<String, Number> entry : pieModel.getData().entrySet()) {
		        		 String s = entry.getKey();
		     		    Pattern pattern = Pattern.compile("^\\D*(\\d)");
		     		    Matcher matcher = pattern.matcher(s);
		     		    matcher.find();
		     		  //  System.out.println(s.substring(0,matcher.start(1)));
		     		   //ConcernPiedataset.setValue(question.getScaleType().getSelections().get(concernStart).getName(), entry.getValue());
		        		ConcernPiedataset.setValue(s.substring(0,matcher.start(1)), entry.getValue());
		        		
		        		concernStart++;
					}
		        	
		        	String mainTitle = "";
		        	
		        	switch (mainSelection) {
		        	case 0:
						mainTitle = "Very good - Concerns";
						break;

					case 1:
						mainTitle = "Good - Concerns";
						break;	
					
					case 2:
						mainTitle = "Average - Concerns";
						break;	
					case 3:
						mainTitle = "Below Average - Concerns";
						break;

					case 4:
						mainTitle = "Needs Improvement - Concerns";
						break;	
						
					default:
						break;
					}
		        	
		        	concernNestedTable.addCell(new Paragraph(mainTitle, fontques));
		        	
		        	mainSelection++;
		        	
		        	*//********************** Draw Pie chart *******************//*
	      		    			        
			        concernNestedTable.addCell(drawPieChart(ConcernPiedataset, writer));
			        
			        cell3.addElement(concernNestedTable);			        
				}
		        
		        table.addCell(cell1);
		        nestedTable.addCell(cell2);
		        nestedTable.addCell(cell3);		        		       		        
		        table.addCell(nestedTable);
	            
	            document.add(table);	            	          
	            
//	           // document.newPage(); // after each question begin new page
		        
		        // draw other Strengths and other Concerns		       
		        
	            if(!question.getConcernsSelection().isEmpty() || !question.getStrengthsSelection().isEmpty()){
	            
		            table = new PdfPTable(1);
		        	
		        	table.setWidthPercentage(100);	
		        	
		        	nestedTable = new PdfPTable(2); nestedTable.setWidthPercentage(100);
		        	
		        	//table.setSpacingBefore(5f);	        		        		       
		            
		        	cell1 = new PdfPCell();
		            cell2 = new PdfPCell();
		            h1 = new PdfPCell();
		            h2 = new PdfPCell();
		            	            
		            h1.addElement(new Paragraph("Other Strengths", fontques));
		            h2.addElement(new Paragraph("Other Concerns", fontques));	            
		            	           
		            nestedTable.addCell(h1);
		            nestedTable.addCell(h2);	                  		    	           
		            
			        PdfPTable commentsNestedTable = new PdfPTable(1); commentsNestedTable.setWidthPercentage(100);
			        PdfPCell strCell = new PdfPCell();
			        strCell.disableBorderSide(1);		               
			        strCell.disableBorderSide(5);
			        strCell.disableBorderSide(8);
			        strCell.setPaddingBottom(5);
			        strCell.setPaddingTop(5);			        
			        strCell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
			        if (question.getStrengthsSelection()!=null && !question.getStrengthsSelection().isEmpty()){
	//			        commentsNestedTable.addCell(new Phrase("Other Strengths", fontques));
				        Integer ii = 0;
				        for(String str : question.getStrengthsSelection()){
				        	if(str!="" && str!=null && str!=" " && !str.isEmpty()){
					        	ii++;  
					        	strCell.addElement(new Phrase(ii.toString() + ". " +str, arabic));
				        	}
				        }
				        commentsNestedTable.addCell(strCell);			       
				        cell1.addElement(commentsNestedTable);
			        }
			        
			        if(question.getConcernsSelection()!=null && !question.getConcernsSelection().isEmpty()){
				        commentsNestedTable = new PdfPTable(1); commentsNestedTable.setWidthPercentage(100);				        
	//			        commentsNestedTable.addCell(new Phrase("Other Concerns", fontques));
				        strCell = new PdfPCell();
				        strCell.disableBorderSide(1);		               
				        strCell.disableBorderSide(5);
				        strCell.disableBorderSide(8);
				        strCell.setPaddingBottom(5);
				        strCell.setPaddingTop(5);
				        
				        strCell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
				        Integer ii = 0;
				        for(String str : question.getConcernsSelection()){
				        	if(str!="" && str!=null && str!=" " && !str.isEmpty()){
					        	ii++;
					        	strCell.addElement(new Phrase(ii.toString() + ". " + str, arabic));
				        	}
				        }
				        commentsNestedTable.addCell(strCell);
				        cell2.addElement(commentsNestedTable);		        
			        }
			        
			        nestedTable.addCell(cell1);
			        nestedTable.addCell(cell2);		        		       		        
			        table.addCell(nestedTable);
		            
		            document.add(table);	            	          
		            
		            document.newPage(); // after each question begin new page
	            }
//	        	LineSeparator separator = new LineSeparator();
//		        separator.setPercentage(59500f / 523f);
//		        Chunk linebreak = new Chunk(separator);
		        
		        
	        }	        	       
	        
		} catch (Exception e) {
			
			e.printStackTrace();
		}*/
	}
	
	public Image drawPieChart(DefaultPieDataset pieDataSet, PdfWriter writer) {
		
		try {
					
			JFreeChart PDFPieChart = ChartFactory.createPieChart("", pieDataSet,false,true,false);
			    
		    final PiePlot plot = (PiePlot) PDFPieChart.getPlot();	      		    			      
		    plot.setBackgroundPaint(Color.white);
		    plot.setCircular(true);
		    
		    // Tooltip format
		    plot.setLabelGenerator(new StandardPieItemLabelGenerator(
				"{0}={1} ({2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance()));	    
		    plot.setNoDataMessage("No data available");
		    plot.setSectionPaint(1, new Color(217,179, 39));
		    plot.setSectionPaint(2,new Color(69, 140, 65));
		    plot.setSectionPaint(3, new Color(207, 38, 39));
		    plot.setSectionPaint(4,new Color(92, 51, 97));
		    plot.setSectionPaint(5, new Color(94, 48, 50));
		    plot.setShadowYOffset(0);
		    plot.setShadowXOffset(0);
		    plot.setBaseSectionOutlinePaint(Color.white);
		            	           
			int style = Font.UNDEFINED ;
		    java.awt.Font font = new java.awt.Font("Garamond", style , 10);
		    plot.setLabelFont(font);
		    
		    // uncomment following lines if you want to add legend to pie
		//        StandardLegend legend = new StandardLegend();
		//        legend.setPreferredWidth(120);
		//        legend.setPadding(new RectangleInsets(10, 60, 5, 12));
		//        legend.setAnchor(Legend.SOUTH);
		//        legend.setItemFont(font);        
		//        PDFPieChart.setLegend(legend);
		    
		    PDFPieChart.setBackgroundPaint(Color.white);
		    PDFPieChart.setBorderPaint(Color.white);
		                   
		    PdfContentByte Add_Chart_Content= writer.getDirectContent();
		    PdfTemplate template_Chart_Holder=Add_Chart_Content.createTemplate(420,280);
		    Graphics2D Graphics_Chart=template_Chart_Holder.createGraphics(420,280,new DefaultFontMapper());                
		    Rectangle2D Chart_Region=new Rectangle2D.Double(0,0,420,280);
		    PDFPieChart.draw(Graphics_Chart,Chart_Region);            
		    Graphics_Chart.dispose();
		    
		    Image chartImage = Image.getInstance(template_Chart_Holder);
		
		    return chartImage;
        
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Integer getStudentsNumberOfCourse(Integer courseID) {
		
		try{
			List<CourseStudent> students = courseStudentRep.getByCourseID(courseID);
			return students.size();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return 0;
		}
			
		
	}

	@Override
	public boolean removeDuplicatesFromCourses() {
		return rep.removeDuplicatesFromCourses();

	}

	@Override
	public boolean removeDuplicatesFromIns() {
		return rep.removeDuplicatesFromIns();
	}

}