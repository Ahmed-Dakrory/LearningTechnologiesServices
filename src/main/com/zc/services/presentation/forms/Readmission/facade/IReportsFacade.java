/**
 * 
 */
package main.com.zc.services.presentation.forms.Readmission.facade;

import java.util.List;

import main.com.zc.services.presentation.forms.Readmission.dto.ReadmissionDTO;

/**
 * @author omnya
 *
 */
public interface IReportsFacade {
	public List<ReadmissionDTO> getOldSummer(Integer year);
	public List<ReadmissionDTO> getOldSpring(Integer year);
	public List<ReadmissionDTO> getOldFall(Integer year);
	public void generateExcelByList(List<ReadmissionDTO> lst);
}
