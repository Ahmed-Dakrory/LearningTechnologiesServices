/**
 * 
 */
package main.com.zc.service.academic_advisingInstructorsDates;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iaa_instructor_dateAppService {

	public List<aa_instructor_date> getAll();
	public List<aa_instructor_date> getAllByYearAndSemester(String year,String semester);
	public List<aa_instructor_date> getByActionAndYearAndSemester(String state,String year,String semester);
	public List<aa_instructor_date> getByInstructorIdAndYearAndSemester(int id,String year,String semester);
	public List<aa_instructor_date> getAllAvailableByInstructorIdAndYearAndSemester(int id,String year,String semester);
	public aa_instructor_date addaa_instructor_date(aa_instructor_date data);
	public aa_instructor_date getById(int id);
	public boolean delete(aa_instructor_date data)throws Exception;
}