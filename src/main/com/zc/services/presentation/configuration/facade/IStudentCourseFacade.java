/**
 * 
 */
package main.com.zc.services.presentation.configuration.facade;

import java.io.InputStream;
import java.util.List;

import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;

/**
 * @author omnya
 *
 */
public interface IStudentCourseFacade {

	public List<StudentCourseDTO> praseFile(InputStream input);
	public List<StudentDTO> praseStudentFile(InputStream input);
	public List<StudentCourseDTO> saveStudents( List<StudentCourseDTO> List);
	
	public List<CoursesDTO> parseCoursesFile(InputStream input);
	public List<CoursesDTO> saveCourses( List<CoursesDTO> List);
	public List<StudentDTO> getNewStudents(List<StudentDTO> originalList);
	public List<StudentDTO> getOldStudents(List<StudentDTO> originalList);
	public boolean addStudent(StudentDTO student);
	public List<CoursesDTO> getNewCourses(List< CoursesDTO> originalList);
	public List<StudentDTO> getAllStudents();
	public List<CoursesDTO> getCoursesOfStudent(Integer studenID);
	public boolean deleteCourseForStudent(Integer courseId, Integer studentID);
	public  List<InstructorDTO> getAllInstructors();
	public  List<CoursesDTO> getCoursesOfInstructor(Integer insID);
	public List<CoursesDTO> getCoursesOfInstructorBySemesterAndYear(Integer insID ,Integer year, Integer semeter);
	public InstructorDTO addIns(InstructorDTO dto);
	List<InstructorDTO> getAllEmp();
}
