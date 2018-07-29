/**
 * 
 */
package main.com.zc.services.applicationService.survey.courseEval.services.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.applicationService.survey.courseEval.assembler.CourseEvalInsQuestionsAssembler;
import main.com.zc.services.applicationService.survey.courseEval.assembler.CourseEvalQuestionsAssembler;
import main.com.zc.services.applicationService.survey.courseEval.services.ICourseEvalQuestionsAppService;
import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.courseEval.model.CourseEvalQuestions;
import main.com.zc.services.domain.courseEval.model.ICourseEvalQuestionsRep;
import main.com.zc.services.domain.courseEval.model.IScaleSelectionsRep;
import main.com.zc.services.domain.courseEval.model.IScaleTypeRep;
import main.com.zc.services.domain.courseEval.model.ScaleSelections;
import main.com.zc.services.domain.courseEval.model.ScaleType;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.Courses_Instructors;
import main.com.zc.services.domain.data.model.ICourse_InstructorRepository;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.domain.person.model.IEmployeeRepository;
import main.com.zc.services.domain.survey.model.ISectionRepository;
import main.com.zc.services.domain.survey.model.Section;
import main.com.zc.services.domain.person.model.Employee;
import main.com.zc.services.presentation.survey.CourseEvalNew.dto.CourseEvalInsQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.CourseEvalQuestionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleSelectionsDTO;
import main.com.zc.services.presentation.survey.courseEval.dto.ScaleTypeDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service
public class CourseEvalQuestionsAppServiceImpl implements ICourseEvalQuestionsAppService {

	@Autowired
	ICourseEvalQuestionsRep rep;
	@Autowired
	IEmployeeRepository insRep;
	@Autowired
	ICourseStudentRep courseStudentRep;
	@Autowired
	ICoursesRepository courseRep;
	@Autowired
	ICourse_InstructorRepository courseInsRep;
	@Autowired
	ICoursesRepository coursesRep;
	@Autowired
	IScaleTypeRep scaleTypeRep;
	@Autowired
	IScaleSelectionsRep selectionsRep;
	@Autowired
	ISectionRepository sectionRep;
	
	CourseEvalQuestionsAssembler assem=new CourseEvalQuestionsAssembler();
	@Autowired
	private SessionFactory sessionFactory;
	Session session;
	@Override
	public List<CourseEvalQuestionsDTO> getAll() {
		List<CourseEvalQuestionsDTO> dtos=new ArrayList<CourseEvalQuestionsDTO>();
		try
		{
			List<CourseEvalQuestions> all=rep.getAll();
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
	public List<CourseEvalQuestionsDTO> getByCategoryID(Integer id) {
		List<CourseEvalQuestionsDTO> dtos=new ArrayList<CourseEvalQuestionsDTO>();
		try
		{
			List<CourseEvalQuestions> all=rep.getByCategoryID(id);
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
	public CourseEvalQuestionsDTO getById(Integer id) {
		CourseEvalQuestionsDTO dto=new CourseEvalQuestionsDTO();
		try
		{
			CourseEvalQuestions ans=rep.getById(id);
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
	public CourseEvalQuestionsDTO add(CourseEvalQuestionsDTO form) {
		try{
			CourseEvalQuestions ans=new CourseEvalQuestions();
			ans=assem.toEntity(form);
			//just set scale type with null
			if(ans.getScaleType().getName()==null) ans.setScaleType(null);
			ans=rep.add(ans);
			
			CourseEvalQuestionsDTO dto=new CourseEvalQuestionsDTO();
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
	public CourseEvalQuestionsDTO update(CourseEvalQuestionsDTO form) {
		try{
			CourseEvalQuestions ans=new CourseEvalQuestions();
			ans=assem.toEntity(form);
			ans=rep.update(ans);
			CourseEvalQuestionsDTO dto=new CourseEvalQuestionsDTO();
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
	public boolean delete(CourseEvalQuestionsDTO form) {
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
	public InstructorDTO getInsByID(Integer id) {
		try {
			Employee ins = insRep.getById(id);
			InstructorDTO dto=new InstructorDTO();
			dto.setId(id);
			dto.setMail(ins.getMail());
			dto.setName(ins.getName());
			return dto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<CoursesDTO> getCoursesByStudentID(Integer studentId) {
		List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		try {
			List<CourseStudent> studentCourses=courseStudentRep.getByStudentID(studentId);
			for(int i=0;i<studentCourses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(studentCourses.get(i).getCourse().getId());
				course.setName(studentCourses.get(i).getCourse().getName());
				course.setSemester(studentCourses.get(i).getCourse().getSemester());
				course.setYear(studentCourses.get(i).getCourse().getYear());
				courses.add(course);
			}
				} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		return courses;
	}

	@Override
	public List<CourseEvalQuestionsDTO> getBySectionID(Integer id) {
		List<CourseEvalQuestionsDTO> dtos=new ArrayList<CourseEvalQuestionsDTO>();
		try
		{
			List<CourseEvalQuestions> all=rep.getByCategoryID(id);
			for(int i=0;i<all.size();i++)
			{
				CourseEvalQuestionsDTO dto=assem.toDTO(all.get(i));
				if(all.get(i).getScaleType()!=null){
				ScaleTypeDTO scale=new ScaleTypeDTO();
				scale.setId(all.get(i).getScaleType().getId());
				scale.setName(all.get(i).getScaleType().getName());
				List<ScaleSelectionsDTO> selections=new ArrayList<ScaleSelectionsDTO>();
				for(int j=0;j<all.get(i).getScaleType().getSelections().size();j++)
				{
					ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
					selection.setId(all.get(i).getScaleType().getSelections().get(j).getId());
					selection.setName(all.get(i).getScaleType().getSelections().get(j).getName());
					selection.setType(all.get(i).getScaleType().getSelections().get(j).getType());
					selections.add(selection);
				}
				scale.setSelections(selections);
				dto.setScaleType(scale);
				
				}
				dtos.add(dto);
				
			}
			return dtos;
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all answers");
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<CourseEvalInsQuestionsDTO> getInstSectionQuestions(Integer id) {
		
		List<CourseEvalInsQuestionsDTO> dtos = new ArrayList<CourseEvalInsQuestionsDTO>();
		
		CourseEvalInsQuestionsAssembler instAssem = new CourseEvalInsQuestionsAssembler();
		
		try
		{
			List<CourseEvalQuestions> all=rep.getByCategoryID(id);
			
			for(int i=0;i<all.size();i++)
			{
				CourseEvalInsQuestionsDTO dto = instAssem.toDTO(all.get(i));
				
				if(all.get(i).getScaleType()!=null){
					
					ScaleTypeDTO scale=new ScaleTypeDTO();
					
					scale.setId(all.get(i).getScaleType().getId());
					scale.setName(all.get(i).getScaleType().getName());
					List<ScaleSelectionsDTO> selections=new ArrayList<ScaleSelectionsDTO>();
				
					for(int j=0;j<all.get(i).getScaleType().getSelections().size();j++)
					{
						ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
						selection.setId(all.get(i).getScaleType().getSelections().get(j).getId());
						selection.setName(all.get(i).getScaleType().getSelections().get(j).getName());
						selection.setType(all.get(i).getScaleType().getSelections().get(j).getType());
						selections.add(selection);
					}
					
					scale.setSelections(selections);
					dto.setScaleType(scale);				
				}
				
				dtos.add(dto);
				
			}
			return dtos;
		}
		catch(Exception ex)
		{
			System.out.println("Can't get all questions");
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<InstructorDTO> getTasByCourseID(Integer courseID) {
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		try{
			
		
		List<Courses_Instructors> instructors=courseInsRep.getTAsByCourseID(courseID);
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			Employee emp=insRep.getById(instructors.get(i).getInstructor().getId());
			dto.setId(emp.getId());
			dto.setName(emp.getName());
			dto.setEmpID(emp.getEmpID());
			dto.setEmpType(emp.getType());
			dto.setMail(emp.getMail());
			dto.setTitle(emp.getTitle());
			dto.setPhone(emp.getPhone());
			dto.setPhoto(emp.getImage());
			
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}

	@Override
	public List<InstructorDTO> getInstructorsByCourseID(Integer courseID) {
		List<InstructorDTO> dtos=new ArrayList<InstructorDTO>();
		try{
			
		
		List<Courses_Instructors> instructors=courseInsRep.getInstructorsByCourseID(courseID);
		for(int i=0;i<instructors.size();i++)
		{
			InstructorDTO dto=new InstructorDTO();
			dto.setId(insRep.getById(instructors.get(i).getInstructor().getId()).getId());
			dto.setName(insRep.getById(instructors.get(i).getInstructor().getId()).getName());
			dtos.add(dto);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return dtos;
	}
	
	public List<CoursesDTO> getAllCourses() {
		
		List<CoursesDTO> coursesDTOs=new ArrayList<CoursesDTO>();
		
		try{
			List<Courses> courses=coursesRep.getAll();
			
			for(int i=0;i<courses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(courses.get(i).getId());
				course.setName(courses.get(i).getName());
				coursesDTOs.add(course);
			}
			
			return coursesDTOs;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
						
	}

	@Override
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(
			Integer studentId, Integer semester, Integer year) {
		List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		try {
			List<CourseStudent> studentCourses=courseStudentRep.getByStudentIDAndSemesterAndYear(studentId,semester,year);
			for(int i=0;i<studentCourses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(studentCourses.get(i).getCourse().getId());
				course.setName(studentCourses.get(i).getCourse().getName());
				course.setSemester(studentCourses.get(i).getCourse().getSemester());
				course.setYear(studentCourses.get(i).getCourse().getYear());
				course.setHideCourseEval(studentCourses.get(i).getCourse().getHideCourseEval());
				courses.add(course);
			}
				} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		return courses;
	}

	@Override
	public boolean deleteSelection(ScaleSelectionsDTO selection) {
		// TODO Auto-generated method stub
		try{ScaleSelections entity=new ScaleSelections();
		entity.setId(selection.getId());
		return selectionsRep.delete(entity);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}

	@Override
	public List<CourseEvalQuestionsDTO> getByCourseID(int id) {
		Section section=sectionRep.getByCourseID(id);
		if(section!=null)
		{
		
			List<CourseEvalQuestionsDTO> dtos=new ArrayList<CourseEvalQuestionsDTO>();
			try
			{	List<CourseEvalQuestions> all=rep.getByCategoryID(section.getId());
			
				for(int i=0;i<all.size();i++)
				{
					CourseEvalQuestionsDTO dto=assem.toDTO(all.get(i));
					if(all.get(i).getScaleType()!=null){
					ScaleTypeDTO scale=new ScaleTypeDTO();
					scale.setId(all.get(i).getScaleType().getId());
					scale.setName(all.get(i).getScaleType().getName());
					List<ScaleSelectionsDTO> selections=new ArrayList<ScaleSelectionsDTO>();
					for(int j=0;j<all.get(i).getScaleType().getSelections().size();j++)
					{
						ScaleSelectionsDTO selection=new ScaleSelectionsDTO();
						selection.setId(all.get(i).getScaleType().getSelections().get(j).getId());
						selection.setName(all.get(i).getScaleType().getSelections().get(j).getName());
						selection.setType(all.get(i).getScaleType().getSelections().get(j).getType());
						selections.add(selection);
					}
					scale.setSelections(selections);
					dto.setScaleType(scale);
					
					}
					dtos.add(dto);
					
				}
				return dtos;
			}
			catch(Exception ex)
			{
				System.out.println("Can't get all answers");
				ex.printStackTrace();
				return null;
			}
			
			
		}else
		return null;
	}
	
}