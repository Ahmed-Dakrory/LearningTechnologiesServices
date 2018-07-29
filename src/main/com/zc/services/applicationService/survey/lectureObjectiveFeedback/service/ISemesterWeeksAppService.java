/**
 * 
 */
package main.com.zc.services.applicationService.survey.lectureObjectiveFeedback.service;

import java.util.List;

import main.com.zc.services.presentation.survey.lectureObjectiveFeedback.dto.SemesterWeeksDTO;

/**
 * @author omnya
 *
 */
public interface ISemesterWeeksAppService {


	public SemesterWeeksDTO add(SemesterWeeksDTO week);
	public boolean remove(Integer id);
	public SemesterWeeksDTO update(SemesterWeeksDTO week);
	public List<SemesterWeeksDTO> getAll();
	public SemesterWeeksDTO getById(Integer id);
	public List<SemesterWeeksDTO> getBySemesterAndyear(Integer sem,Integer year);
	public List<SemesterWeeksDTO> getActiveBySemesterAndyear(Integer sem,Integer year);
	
	
}
