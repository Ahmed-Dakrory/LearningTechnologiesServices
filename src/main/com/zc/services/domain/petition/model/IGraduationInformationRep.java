/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IGraduationInformationRep {

	public GraduationInformation add(GraduationInformation form) ;
	public boolean remove(GraduationInformation form);
	public GraduationInformation update(GraduationInformation feedback);
	public List<GraduationInformation> getAll();
	public List<GraduationInformation> getFormByStudentID(Integer studentID);
	public GraduationInformation getById(Integer id);
	public GraduationInformation getFormByStudentIDAndSemesterAndYear(Integer studentID,Integer year,Integer semester);
	public List<GraduationInformation> getFormBySemesterAndYear(Integer year,Integer semester);
}
