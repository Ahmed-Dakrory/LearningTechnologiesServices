/**
 * 
 */
package main.com.zc.services.applicationService.forms.changeMajor.services;

import java.util.List;

import main.com.zc.services.presentation.forms.changeMajor.dto.ChangeMajorDTO;

/**
 * @author omnya
 *
 */
public interface IReportsServiceChangeMajor {
	public List<ChangeMajorDTO> getOldSummer(Integer year);
	public List<ChangeMajorDTO> getOldSpring(Integer year);
	public List<ChangeMajorDTO> getOldFall(Integer year);
	public void generateExcelByList(List<ChangeMajorDTO> lst);
}
