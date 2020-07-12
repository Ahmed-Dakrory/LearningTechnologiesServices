/**
 * 
 */
package main.com.zc.service.academic_advising_instructor;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface aa_instructorRepository {

	public List<aa_instructor> getAll();
	public List<aa_instructor> getAllByYearAndSemester(int year,String semester);
	public aa_instructor getByMailAndYearAndSemester(String mail,int year,String semester);
	public aa_instructor addaa_instructor(aa_instructor data);
	public aa_instructor getById(int id);
	public boolean delete(aa_instructor data)throws Exception;
}
