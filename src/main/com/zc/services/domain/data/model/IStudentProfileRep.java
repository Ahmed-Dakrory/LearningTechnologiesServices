/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IStudentProfileRep {

	

	public StudentProfile add(StudentProfile form);

	public boolean remove(Integer id);

	public StudentProfile update(StudentProfile form);

	public List<StudentProfile> getAll();

	public StudentProfile getById(Integer id);
	
	public List<StudentProfile>getByStudentID(Integer id);
	
	public List<StudentProfile>getBySemesterAndYear(Integer sem, Integer year);
	
	public StudentProfile getBySemesterAndYearAndStudentId(Integer sem, Integer year , Integer id);

	
}
