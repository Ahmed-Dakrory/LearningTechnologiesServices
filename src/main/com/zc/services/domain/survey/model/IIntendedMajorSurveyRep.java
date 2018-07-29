/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IIntendedMajorSurveyRep {

	public IntendedMajorSurvey add(IntendedMajorSurvey form);
	public boolean remove(Integer id);
	public IntendedMajorSurvey update(IntendedMajorSurvey form);
	public List<IntendedMajorSurvey> getAll();
	public IntendedMajorSurvey getByStudentID(Integer id);
	public IntendedMajorSurvey getById(Integer id);
	public List<IntendedMajorSurvey> getByMajorID(Integer id);
	public List<IntendedMajorSurvey> getByMajorHead(Integer id);
	public List<IntendedMajorSurvey> getByMajorIDAndYearAndSemester(Integer id,Integer year, Integer semester);
	public List<IntendedMajorSurvey> getByYearAndSemester(Integer year, Integer semester);
	
}
