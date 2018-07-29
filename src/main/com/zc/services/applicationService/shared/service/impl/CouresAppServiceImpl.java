/**
 * 
 */
package main.com.zc.services.applicationService.shared.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.com.zc.services.applicationService.shared.service.ICouresAppService;
import main.com.zc.services.domain.configurations.model.CourseStudent;
import main.com.zc.services.domain.configurations.model.ICourseStudentRep;
import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.domain.data.model.ICoursesRepository;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;

/**
 * @author omnya
 *
 */
@Service
public class CouresAppServiceImpl implements ICouresAppService {

	@Autowired
	ICourseStudentRep courseStudentRep;   
	
	@Autowired
	ICoursesRepository coursesRep;
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
	public List<CoursesDTO> getCoursesByStudentIDAndSemesterAndYear(Integer studentId, Integer semester, Integer year) {
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
	public List<CoursesDTO> getCoursesBySemesterAndYear(Integer semester, Integer year) {
		List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		try {
			List<Courses> studentCourses=coursesRep.getByYearAndSemester(semester, year);
			for(int i=0;i<studentCourses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(studentCourses.get(i).getId());
				course.setName(studentCourses.get(i).getName());
				course.setSemester(studentCourses.get(i).getSemester());
				course.setYear(studentCourses.get(i).getYear());
				course.setHideCourseEval(studentCourses.get(i).getHideCourseEval());
				courses.add(course);
			}
				} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		return courses;
	}

	@Override
	public List<CoursesDTO> getAll() {
		List<CoursesDTO> courses=new ArrayList<CoursesDTO>();
		try {
			List<Courses> studentCourses=coursesRep.getAll();
			for(int i=0;i<studentCourses.size();i++)
			{
				CoursesDTO course=new CoursesDTO();
				course.setId(studentCourses.get(i).getId());
				course.setName(studentCourses.get(i).getName());
				course.setSemester(studentCourses.get(i).getSemester());
				course.setYear(studentCourses.get(i).getYear());
				course.setHideCourseEval(studentCourses.get(i).getHideCourseEval());
				courses.add(course);
			}
				} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		return courses;
	}

	@Override
	public CoursesDTO getcourseById(Integer id) {
		CoursesDTO dto=new CoursesDTO();
		try {
			Courses course=coursesRep.getById(id);
			dto.setId(course.getId());
			dto.setName(course.getName());
			dto.setSemester(course.getSemester());
			dto.setYear(course.getYear());
			return dto;
				} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}



	@Override
	public CoursesDTO getcourseByNameAndSemesterAndYear(String name, Integer semester, Integer year) {
		try
		{
			Courses course=coursesRep.getByNameAndYearAndSemester(name,year,semester);
			CoursesDTO dto=new CoursesDTO();
			dto.setId(course.getId());
			dto.setName(course.getName());
			dto.setYear(course.getYear());
			dto.setSemester(course.getSemester());
			return dto;
		}
		catch(Exception ex){
			ex.printStackTrace();return null;
		}
	}

}
