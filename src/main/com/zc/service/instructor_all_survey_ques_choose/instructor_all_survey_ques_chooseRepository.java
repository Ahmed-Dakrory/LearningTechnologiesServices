/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ques_choose;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface instructor_all_survey_ques_chooseRepository {

	public List<instructor_all_survey_ques_choose> getAll();
	public List<instructor_all_survey_ques_choose> getAllByQuesId(int quesId);
	public instructor_all_survey_ques_choose addinstructor_all_survey_ques_choose(instructor_all_survey_ques_choose data);
	public instructor_all_survey_ques_choose getById(int id);
	public boolean delete(instructor_all_survey_ques_choose data)throws Exception;
}
