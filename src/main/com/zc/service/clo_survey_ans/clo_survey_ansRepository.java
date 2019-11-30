/**
 * 
 */
package main.com.zc.service.clo_survey_ans;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface clo_survey_ansRepository {

	public List<clo_survey_ans> getAll();
	public clo_survey_ans getByStudentIdAndCourseId(int idStudent,int courseId);
	public List<clo_survey_ans> getAllByCourseId(int courseId);
	public clo_survey_ans addclo_survey_ans(clo_survey_ans data);
	public clo_survey_ans getById(int id);
	public boolean delete(clo_survey_ans data)throws Exception;
}
