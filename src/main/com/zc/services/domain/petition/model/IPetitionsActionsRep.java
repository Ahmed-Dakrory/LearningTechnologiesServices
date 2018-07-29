/**
 * 
 */
package main.com.zc.services.domain.petition.model;

import java.util.List;

import main.com.zc.services.domain.shared.enumurations.PetitionActionTypeEnum;


/**
 * @author omnya
 *
 */
public interface IPetitionsActionsRep {

	public PetitionsActions add(PetitionsActions action);

	public boolean remove(Integer id);

	public PetitionsActions update(PetitionsActions action);

	public PetitionsActions getById(Integer id);
	
	public List<PetitionsActions> getByPetitionIDAndForm(Integer id ,Integer formType);
	public List<PetitionsActions> getByPetitionIDAndFormAndIns(Integer id ,Integer formType,Integer insID);
}
