/**
 * 
 */
package main.com.zc.service.instructor_survey_ques;

import java.util.List;

/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface Iinstructor_survey_quesAppService {

	public List<instructor_survey_ques> getAll();
	public List<instructor_survey_ques> getAllByYearAndSemestar(int year,int semestar);
	public List<instructor_survey_ques> getAllByYearAndSemestarAndCategory(int year,int semestar,int category);
	public instructor_survey_ques addinstructor_survey_ques(instructor_survey_ques data);
	public instructor_survey_ques getById(int id);
	public boolean delete(instructor_survey_ques data)throws Exception;
}
