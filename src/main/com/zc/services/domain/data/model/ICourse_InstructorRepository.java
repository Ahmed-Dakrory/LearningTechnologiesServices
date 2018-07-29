/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.List;

/**
 * @author Omnya Alaa
 * 
 */
public interface ICourse_InstructorRepository {
	public int add(Courses_Instructors courseInstructor);

	public boolean remove(Courses_Instructors courseInstructor);

	public Courses_Instructors update(Courses_Instructors courseInstructor);

	public List<Courses_Instructors> getAll();

	public Courses_Instructors getById(int id);

	public List<Courses_Instructors> getByCourseId(int id);
	public List<Courses_Instructors> getTAsByCourseID(int courseID);
	public List<Courses_Instructors>getInstructorsByCourseID(int courseID);
	public List<Courses_Instructors> getByInstructorId(int id);
	public List<Courses_Instructors> getByInstructorIdAndYearAndSemester(int id , Integer year, Integer semester);
	public Courses_Instructors getByInstructorMail(String mail);
	public List<Courses_Instructors> getByInstructorMaillst(String mail);
	
	public Courses_Instructors getByCourseIdAndInsId(int courseID,int instructorID);
}
