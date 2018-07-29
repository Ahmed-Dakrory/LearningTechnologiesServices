/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeOfConcentration.service;

import java.util.List;

import main.com.zc.services.presentation.forms.changeOfConcentration.dto.ChangeConcentrationDTO;
import main.com.zc.services.presentation.users.dto.MajorDTO;
import main.com.zc.shared.presentation.dto.BaseDTO;

/**
 * @author omnya
 *
 */
public interface IChangeOfConcentrationStudentService {

	public List<MajorDTO> getAllMajors();
	public List<BaseDTO> getConcentrationsByMajor(Integer majorID);
	public ChangeConcentrationDTO add(ChangeConcentrationDTO request);
	public List<ChangeConcentrationDTO> getPendingPetitionsByStudentID(Integer studentID);
	public List<ChangeConcentrationDTO> getArchievedPetitionsByStudentID(Integer studentID);
}
