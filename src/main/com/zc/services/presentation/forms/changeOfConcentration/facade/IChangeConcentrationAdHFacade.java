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
public interface IChangeConcentrationAdHFacade {

	
	public List<ChangeConcentrationDTO> getPendingFormsOfAdmissionHead();
	public List<ChangeConcentrationDTO> getArchievedFormsOfAdmissionHead();
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto);
	public List<ChangeConcentrationDTO> getOldSummer(Integer year);
	public List<ChangeConcentrationDTO> getOldSpring(Integer year);
	public List<ChangeConcentrationDTO> getOldFall(Integer year);
	
	
}
