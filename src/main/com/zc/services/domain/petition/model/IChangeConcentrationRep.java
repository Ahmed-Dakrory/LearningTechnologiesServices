/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

import main.com.zc.services.presentation.forms.emails.model.PendingPetitionCountObject;

/**
 * @author omnya
 *
 */
public interface IChangeConcentrationRep {

	
	public ChangeConcentration add(ChangeConcentration form);

	public boolean remove(Integer id);

	public ChangeConcentration update(ChangeConcentration form);

	public List<ChangeConcentration> getAll();

	public ChangeConcentration getById(Integer id);
	
	public List<ChangeConcentration> getByStudentID(Integer id);
	
	public List<ChangeConcentration> getPendingByPA(Integer id);
	
	public List<ChangeConcentration> getOldByPA(Integer id);
	
	public List<ChangeConcentration> getAdHeadPending(Integer id);
	
	public List<ChangeConcentration> getAdDeptPending(Integer id);
	
	public List<PendingPetitionCountObject> getInstructorPendingChangMajorPetition(boolean forDailyMAil ) ;
	public List<ChangeConcentration> getOldSummer(Integer year);
	public List<ChangeConcentration> getOldSpring(Integer year);
	public List<ChangeConcentration> getOldFall(Integer year);
	
}
