/**
 * 
 */
package main.com.zc.services.presentation.forms.changeOfConcentration.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;

/**
 * @author omnya
 *
 */
public interface IChangeConcentrationRegistrarFacade {

	public ChangeConcentrationDTO updateRequest(ChangeConcentrationDTO dto);
	public List<ChangeConcentrationDTO> getPendingPetitionsOfstuent();
	public List<ChangeConcentrationDTO> getArchievedPetitionsOfstuent();
	public List<ChangeConcentrationDTO> getOldSummer(Integer year);
	public List<ChangeConcentrationDTO> getOldSpring(Integer year);
	public List<ChangeConcentrationDTO> getOldFall(Integer year);
}
