/**
 * 
 */
package main.com.zc.services.presentation.forms.dropAndAdd.facade;

import java.util.List;
import main.com.zc.services.presentation.forms.dropAndAdd.dto.DropAddFormDTO;

/**
 * @author omnya
 *
 */
public interface IReportsFacade {
	public List<DropAddFormDTO> getOldSummer(Integer year);
	public List<DropAddFormDTO> getOldSpring(Integer year);
	public List<DropAddFormDTO> getOldFall(Integer year);
	public void generateExcelByList(List<DropAddFormDTO> lst);
}
