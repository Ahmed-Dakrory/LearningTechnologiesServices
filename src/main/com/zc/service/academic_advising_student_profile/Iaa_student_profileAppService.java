/**
 * 
 */
package main.com.zc.service.academic_advising_student_profile;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iaa_student_profileAppService {

	public List<aa_student_profile> getAll();
	public List<aa_student_profile> getAllByYearAndSemester(int year,String semester);
	public aa_student_profile getByMailAndYearAndSemester(String mail,int year,String semester);
	public aa_student_profile addaa_student_profile(aa_student_profile data);
	public aa_student_profile getById(int id);
	public boolean delete(aa_student_profile data)throws Exception;
}
