/**
 * 
 */
package main.com.zc.services.applicationService.forms.readmission.services;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;


/**
 * @author omnya
 *
 */
public interface IAdmissionHeadReadmissionService {

	public List<ReadmissionDTO> getPendingFormsOfAdmissionHead();
	public List<ReadmissionDTO> getArchievedFormsOfAdmissionHead();
	public ReadmissionDTO updateStatusOfForm(ReadmissionDTO dto);
	
	public void addComment(Integer id, String comment);
}
