/**
 * 
 */
package main.com.zc.services.domain.lectureObjectiveFeedback.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface ISemesterWeeksRepository {


	public SemesterWeeks add(SemesterWeeks week);
	public boolean remove(Integer id);
	public SemesterWeeks update(SemesterWeeks week);
	public List<SemesterWeeks> getAll();
	public SemesterWeeks getById(Integer id);
	public List<SemesterWeeks> getBySemesterAndyear(Integer sem,Integer year);
	public List<SemesterWeeks> getActiveBySemesterAndyear(Integer sem,Integer year);
	
	
}
