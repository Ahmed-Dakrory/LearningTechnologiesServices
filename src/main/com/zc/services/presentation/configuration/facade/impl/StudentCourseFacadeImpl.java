/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import main.com.zc.services.applicationService.configuration.services.IStudentCourseService;
import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author omnya
 *
 */
@Service("IStudentCourseFacade")
public class StudentCourseFacadeImpl implements IStudentCourseFacade{

	@Autowired
	IStudentCourseService service;
	@Override
	public List<StudentCourseDTO> praseFile(InputStream input) {
		try {
			List<StudentCourseDTO>lst=service.praseFile(input);
			return lst;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public List<StudentCourseDTO>  saveStudents(List<StudentCourseDTO> List) {
        return service.saveStudents(List);
		
	}

	@Override
	public List<CoursesDTO> parseCoursesFile(InputStream input) {
		// TODO Auto-generated method stub
		return service.parseCoursesFile(input);
	}

	@Override
	public List<CoursesDTO> saveCourses(List<CoursesDTO> List) {
		// TODO Auto-generated method stub
		return service.saveCourses(List);
	}

	@Override
	public List<StudentDTO> praseStudentFile(InputStream input) {
		try {
			List<StudentDTO>lst=service.praseStudentFile(input);
			return lst;
		} catch (IOException e) {
			
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<StudentDTO> getNewStudents(List<StudentDTO> originalList) {
		
		return service.getNewStudents(originalList);
	}

	@Override
	public boolean addStudent(StudentDTO student) {
		
		return service.addStudent(student);
	}

	@Override
	public List<CoursesDTO> getNewCourses(List<CoursesDTO> originalList) {
		
		return service.getNewCourses(originalList);
	}

	@Override
	public List<StudentDTO> getAllStudents() {
		
		return service.getAllStudents();
	}

	@Override
	public List<CoursesDTO> getCoursesOfStudent(Integer studenID) {
		
		return service.getCoursesOfStudent(studenID);
	}

	@Override
	public boolean deleteCourseForStudent(Integer courseId, Integer studentID) {
		
		return service.deleteCourseForStudent(courseId, studentID);
	}

	@Override
	public List<InstructorDTO> getAllInstructors() {
	
		return service.getAllInstructors();
	}@Override
	public List<InstructorDTO> getAllEmp() {
	
		return service.getAllEmp();
	}
	@Override
	public List<CoursesDTO> getCoursesOfInstructor(Integer insID) {
		
		return service.getCoursesOfInstructor(insID);
	}

	@Override
	public InstructorDTO addIns(InstructorDTO dto) {
		
		return service.addIns(dto);
	}

	@Override
	public List<CoursesDTO> getCoursesOfInstructorBySemesterAndYear(Integer insID ,Integer year, Integer semeter) {
	
		return service.getCoursesOfInstructorBySemesterAndYear(insID, year, semeter);
	}

	@Override
	public List<StudentDTO> getOldStudents(List<StudentDTO> originalList) {
		return service.getOldStudents(originalList);
	}


}
