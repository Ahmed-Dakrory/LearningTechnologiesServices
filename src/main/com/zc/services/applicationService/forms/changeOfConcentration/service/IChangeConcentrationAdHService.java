/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.service;

import java.util.List;

import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;

/**
 * @author omnya
 *
 */
public interface IChangeConcentrationAdHService {

	public List<ChangeConcentrationDTO> getPendingFormsOfAdmissionHead();
	public List<ChangeConcentrationDTO> getArchievedFormsOfAdmissionHead();
	public ChangeConcentrationDTO updateStatusOfForm(ChangeConcentrationDTO dto);
	}
