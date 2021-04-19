/**
 * 
 */
package main.com.zc.services.presentation.forms.change_grade_petition;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface change_grade_petitionRepository {

	public List<change_grade_petition> getAll();
	public List<change_grade_petition> getAllRefused();
	public change_grade_petition addchange_grade_petition(change_grade_petition ccc);
	public List<change_grade_petition> getByStudentId(int id);
	public List<change_grade_petition> getByMajorId(int id);
	public List<change_grade_petition> getAllForStepAndMajorId(int id,int step);
	public List<change_grade_petition> getAllForStepAndInstructorId(int id,int step);
	public List<change_grade_petition> getAllForStep(int step);
	public change_grade_petition getById(int id);
	public boolean delete(change_grade_petition ccc);
}
