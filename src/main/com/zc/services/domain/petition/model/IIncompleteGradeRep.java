/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IIncompleteGradeRep {

	public IncompleteGrade add(IncompleteGrade form);

	public boolean remove(Integer id);

	public IncompleteGrade update(IncompleteGrade form);

	public List<IncompleteGrade> getAll();

	public IncompleteGrade getById(Integer id);
	
	public List<IncompleteGrade> getByStudentID(Integer id);
	public List<IncompleteGrade> getPendinByPA(Integer id);
	public List<IncompleteGrade> getPendingByInstructor(Integer id);
	public List<IncompleteGrade> getOldByInstructor(Integer id);
	public List<IncompleteGrade> getOldByPA(Integer id);
	public List<IncompleteGrade> getOldSummer(Integer year);
	public List<IncompleteGrade> getOldSpring(Integer year);
	public List<IncompleteGrade> getOldFall(Integer year);
	public List<IncompleteGrade> getIncompleteGradeFormJob( );
	
	
	
}
