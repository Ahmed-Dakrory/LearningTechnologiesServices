/**
 * 
 */
package main.com.zc.service.instructor_all_survey_ques;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface instructor_all_survey_quesRepository {

	public List<instructor_all_survey_ques> getAll();
	public List<instructor_all_survey_ques> getNumberOfGategoriesByYearAndSemestarAndMidtermOrFinal(int year,int semestar,int mode);
	public List<instructor_all_survey_ques> getAllByYearAndSemestar(int year,int semestar);
	public List<instructor_all_survey_ques> getAllByYearAndSemestarAndCategoryAndMidtermOrFinal(int year,int semestar,int category,int mode);
	public List<instructor_all_survey_ques> getAllByYearAndSemestarAndMidtermOrFinalAndType(int year,int semestar,int mode,int type);
	public instructor_all_survey_ques addinstructor_all_survey_ques(instructor_all_survey_ques data);
	public instructor_all_survey_ques getById(int id);
	public boolean delete(instructor_all_survey_ques data)throws Exception;
}
