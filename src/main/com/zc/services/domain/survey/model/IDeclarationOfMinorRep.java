/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IDeclarationOfMinorRep {

	
	public DeclarationOfMinor add(DeclarationOfMinor form);
	public boolean remove(Integer id);
	public DeclarationOfMinor update(DeclarationOfMinor form);
	public List<DeclarationOfMinor> getAll();
	public DeclarationOfMinor getByStudentID(Integer id);
	public DeclarationOfMinor getByStudentIDAndYearAndSemester(Integer id,Integer year, Integer semester);
	public DeclarationOfMinor getById(Integer id);
	public List<DeclarationOfMinor> getByMinorID(Integer id);
	public List<DeclarationOfMinor> getAllByYearAndSemester(Integer year, Integer semester);
	public List<DeclarationOfMinor> getAllByMinorIDAndYearAndSemester(Integer id, Integer year, Integer semester);
	
}
