/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

import main.com.zc.services.domain.shared.enumurations.SemesterEnum;

/**
 * @author omnya
 *
 */
public interface IOfficialMajorRep {

	public OfficialMajor add(OfficialMajor form);
	public boolean remove(Integer id);
	public OfficialMajor update(OfficialMajor form);
	public List<OfficialMajor> getAll();
	public OfficialMajor getByStudentID(Integer id);
	public OfficialMajor getByStudentIDAndYearAndSemester(Integer id,Integer year, Integer semester);
	public OfficialMajor getById(Integer id);
	public List<OfficialMajor> getByMajorID(Integer id);
	public List<OfficialMajor> getByMajorHead(Integer id);
	public List<OfficialMajor> getByMajorIDAndYearAndSemester(Integer id,Integer year, Integer semester);
	public List<OfficialMajor> getByYearAndSemester(Integer year, Integer semester);
	
}
