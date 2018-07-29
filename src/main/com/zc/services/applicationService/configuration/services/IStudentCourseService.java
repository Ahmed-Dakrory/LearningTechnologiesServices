/**
 * 
 */
package main.com.zc.services.applicationService.configuration.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import main.com.zc.services.domain.data.model.Courses;
import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.forms.academicPetition.facade.IStudentAcademicPetFacade;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public interface IStudentCourseService {
	public List<StudentCourseDTO> praseFile( InputStream input) throws IOException;
	public List<StudentDTO> praseStudentFile(InputStream input)throws IOException;
	public List<StudentCourseDTO> saveStudents( List<StudentCourseDTO> List);
	
	public List<CoursesDTO> parseCoursesFile(InputStream input);
	public List<CoursesDTO> saveCourses( List<CoursesDTO> List);
	public List<StudentDTO> getNewStudents(List<StudentDTO> originalList);
	public boolean addStudent(StudentDTO student);
	public List<CoursesDTO> getNewCourses(List<CoursesDTO> originalList);
	public List<StudentDTO> getAllStudents() ;
	public List<CoursesDTO> getCoursesOfStudent(Integer studenID);
	public boolean deleteCourseForStudent(Integer courseId, Integer studentID);
	public List<InstructorDTO> getAllInstructors();
	public  List<CoursesDTO> getCoursesOfInstructor(Integer insID);
	public List<CoursesDTO> getCoursesOfInstructorBySemesterAndYear(Integer insID,Integer year, Integer semester);
	public InstructorDTO addIns(InstructorDTO dto);
	List<InstructorDTO> getAllEmp();
}
