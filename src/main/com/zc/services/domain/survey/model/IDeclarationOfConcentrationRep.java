/**
 * 
 */
package main.com.zc.services.domain.survey.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IDeclarationOfConcentrationRep {

	
	public DeclarationOfConcentration add(DeclarationOfConcentration form);
	public boolean remove(Integer id);
	public DeclarationOfConcentration update(DeclarationOfConcentration form);
	public List<DeclarationOfConcentration> getAll();
	public DeclarationOfConcentration getByStudentID(Integer id);
	public DeclarationOfConcentration getByStudentIDAndYearAndSemester(Integer id,Integer year, Integer semester);
	public DeclarationOfConcentration getById(Integer id);
	public List<DeclarationOfConcentration> getByConcentrationID(Integer id);
	public List<DeclarationOfConcentration> getAllByYearAndSemester(Integer year, Integer semester);
	public List<DeclarationOfConcentration> getAllByConcentrationIDAndYearAndSemester(Integer id, Integer year, Integer semester);
	
}
