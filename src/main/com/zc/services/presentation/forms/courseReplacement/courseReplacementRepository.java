/**
 * 
 */
package main.com.zc.services.presentation.forms.courseReplacement;

import java.util.List;

/**
 * @author Dakrory
 *
 */
public interface courseReplacementRepository {

	public List<courseReplacement> getAll();
	public courseReplacement addcourseReplacement(courseReplacement ccc);
	public List<courseReplacement> getByStudentId(int id);
	public List<courseReplacement> getByMajorId(int id);
	public List<courseReplacement> getAllForStepAndMajorId(int id,int step);
	public List<courseReplacement> getAllForStepAndType(int type,int step);
	public List<courseReplacement> getAllForStep(int step);
	public courseReplacement getById(int id);
	public boolean delete(courseReplacement ccc);
}
